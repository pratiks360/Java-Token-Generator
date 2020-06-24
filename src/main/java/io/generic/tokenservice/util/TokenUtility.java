package io.generic.tokenservice.util;

import io.generic.tokenservice.exceptions.TokenGenerationException;
import io.generic.tokenservice.model.TokenDTO;
import io.generic.tokenservice.model.TokenRequestModel;
import io.generic.tokenservice.repository.TokenServieRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Component
public class TokenUtility {
    @Autowired
    DBUtil dbutil;
    @Autowired
    EncodingUtil ecutil;
    @Autowired
    TokenServieRepo trepo;
    private static final Logger logger = LoggerFactory.getLogger(TokenUtility.class);

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private static final String NUMERIC_STRING = "0123456789";

    public String getAlphaNumericToken(int count) {
        logger.info("Generating AlphaNumeric token with count {}", count);
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    public String getNumericToken(int count) {
        logger.info("Generating Numeric token with count {}", count);
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * NUMERIC_STRING.length());
            builder.append(NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    public TokenDTO dbMapper(TokenRequestModel request, String token) {
        try {
            TokenDTO dto = new TokenDTO();

            dto.setCustomerId(request.getCustomerId());
            dto.setChannel(request.getChannel());
            dto.setAlphanumeric(String.valueOf(request.getIsAlphanumeric()));
            dto.setLength(request.getLength());
            if (request.getExpiresIn() == 0)
                dto.setExpiresIn(172800);
            else
                dto.setExpiresIn(request.getExpiresIn());
            dto.setTime(dbutil.getCurrentTimeStamp());
            dto.setToken(ecutil.encrypt(token));
            String expiryTime = Instant.now().plusSeconds(dto.getExpiresIn()).atOffset(ZoneOffset.ofHoursMinutes(5, 30)).toString();
            dto.setExpiryAt(expiryTime);
            return dto;
        } catch (TokenGenerationException ex) {
            throw new TokenGenerationException(ex.getMessage(), ex.getCode());
        } catch (Exception ex) {
            throw new TokenGenerationException(ex.getMessage(), "501");
        }

    }

    public boolean checkIfTokenAlreadyPersists(TokenRequestModel request) {
        logger.info("Checking Token if it's already generated for CustomerId {} for channel {}", request.getCustomerId(), request.getChannel());
        boolean result = false;
        List<TokenDTO> al = trepo.findCustomerToken(request.getCustomerId(), request.getChannel());
        if (!al.isEmpty()) {
            TokenDTO token = al.get(0);
            OffsetDateTime currentTime = Instant.now().atOffset(ZoneOffset.ofHoursMinutes(5, 30));
            OffsetDateTime expiryTime = OffsetDateTime.parse(token.getExpiryAt());
            return checkTokenExpiryTime(currentTime, expiryTime);

        } else {
            return result;
        }
    }

    public boolean checkTokenValidity(TokenRequestModel request) {
        logger.info("Checking Validity of Token for CustomerId {} for channel {}", request.getCustomerId(), request.getChannel());
        boolean result = false;
        List<TokenDTO> al = trepo.findCustomerToken(request.getCustomerId(), request.getChannel());
        try {
            if (al.isEmpty()) {
                logger.info("No Token were generated", request.getCustomerId(), request.getChannel());
                throw new TokenGenerationException("Token never generated", "102");
            } else {
                TokenDTO token = al.get(0);
                OffsetDateTime currentTime = Instant.now().atOffset(ZoneOffset.ofHoursMinutes(5, 30));
                OffsetDateTime expiryTime = OffsetDateTime.parse(token.getExpiryAt());
                if (checkTokenExpiryTime(currentTime, expiryTime)) {

                    if (token.getToken().equals(ecutil.encrypt(request.getToken()))) {
                        return true;
                    } else {
                        logger.info("Token is invalid", request.getCustomerId(), request.getChannel());
                        throw new TokenGenerationException("Invalid Token", "103");
                    }
                } else {
                    logger.info("Token has for CustomerId {} for channel {}", request.getCustomerId(), request.getChannel());
                    throw new TokenGenerationException("Your Token Has Expired", "104");
                }

            }
        } catch (TokenGenerationException ex) {
            throw new TokenGenerationException(ex.getMessage(), ex.getCode());
        } catch (Exception ex) {
            throw new TokenGenerationException(ex.getMessage(), "501");
        }

    }

    /*
    return false is Token is expired
     */
    private boolean checkTokenExpiryTime(OffsetDateTime currentTime, OffsetDateTime expiryTime) {
        Duration diff = Duration.between(currentTime, expiryTime);
        if (diff.isNegative())
            return false;
        else
            return true;
    }

    public String reissueToken(TokenRequestModel request) {
        logger.info("Re-issuing Token for CustomerId {} for channel {}", request.getCustomerId(), request.getChannel());
        String renewedToken = null;
        try {
            List<TokenDTO> al = trepo.findCustomerToken(request.getCustomerId(), request.getChannel());
            if (al.isEmpty()) {
                throw new TokenGenerationException("Token never generated", "102");
            } else {
                TokenDTO oldLatestToken = al.get(0);
                TokenDTO reissuedToken = new TokenDTO();
                reissuedToken.setCustomerId(oldLatestToken.getCustomerId());
                reissuedToken.setChannel(oldLatestToken.getChannel());
                reissuedToken.setAlphanumeric(oldLatestToken.getAlphanumeric());
                reissuedToken.setLength(oldLatestToken.getLength());
                reissuedToken.setExpiresIn(oldLatestToken.getExpiresIn());
                reissuedToken.setTime(dbutil.getCurrentTimeStamp());
                if (oldLatestToken.getAlphanumeric().equalsIgnoreCase("true"))
                    renewedToken = getAlphaNumericToken(oldLatestToken.getLength());
                else
                    renewedToken = getNumericToken(oldLatestToken.getLength());
                reissuedToken.setToken(ecutil.encrypt(renewedToken));
                String expiryTime = Instant.now().plusSeconds(reissuedToken.getExpiresIn()).atOffset(ZoneOffset.ofHoursMinutes(5, 30)).toString();
                reissuedToken.setExpiryAt(expiryTime);
                trepo.save(reissuedToken);

                StringBuffer sb = new StringBuffer();
                sb.append("{\n");
                sb.append("\"token\":");
                sb.append("\"");
                sb.append(renewedToken);
                sb.append("\"\n");
                sb.append("}");


                return sb.toString();
            }

        } catch (TokenGenerationException ex) {
            throw new TokenGenerationException(ex.getMessage(), ex.getCode());
        } catch (Exception ex) {
            throw new TokenGenerationException(ex.getMessage(), "501");
        }

    }
}