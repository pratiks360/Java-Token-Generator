package io.generic.tokenservice.service.impl;

import io.generic.tokenservice.exceptions.TokenGenerationException;
import io.generic.tokenservice.model.TokenDTO;
import io.generic.tokenservice.model.TokenRequestModel;
import io.generic.tokenservice.repository.TokenServieRepo;
import io.generic.tokenservice.service.TokenService;
import io.generic.tokenservice.util.DBUtil;
import io.generic.tokenservice.util.TokenUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.UnexpectedTypeException;
import java.util.Objects;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    TokenUtility tokenutil;
    @Autowired
    DBUtil dbutil;

    @Autowired
    TokenServieRepo trepo;

    private static final Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);

    @Override
    public String generateToken(TokenRequestModel tokenrequest) throws TokenGenerationException {
        String computedToken = null;
        logger.info("Generation Token for CustomerId {} for channel {}", tokenrequest.getCustomerId(), tokenrequest.getChannel());
        try {

            //Check if token is present in DB
            if (!tokenutil.checkIfTokenAlreadyPersists(tokenrequest)) {
                if (tokenrequest.getIsAlphanumeric())
                    computedToken = tokenutil.getAlphaNumericToken(tokenrequest.getLength());
                else
                    computedToken = tokenutil.getNumericToken(tokenrequest.getLength());

                TokenDTO dto = tokenutil.dbMapper(tokenrequest, computedToken);
                trepo.save(dto);
            } else {
                throw new TokenGenerationException(null, null);
            }
        } catch (TokenGenerationException e) {
            logger.error("Error Occured Same token exists");
            throw new TokenGenerationException("Valid Token already exist for given customer", "101");
        } catch (Exception e) {
            logger.error("Error Occured");
            throw new TokenGenerationException(e.getMessage());
        }
        return computedToken;
    }


    @Override
    public String validate(TokenRequestModel tokenrequest) {
        try {
            logger.info("Validation Token for CustomerId {} for channel {}", tokenrequest.getCustomerId(), tokenrequest.getChannel());
            if (Objects.isNull(tokenrequest.getCustomerId()) || Objects.isNull(tokenrequest.getChannel()) || Objects.isNull(tokenrequest.getToken()))
                throw new UnexpectedTypeException("");
            if (tokenutil.checkTokenValidity(tokenrequest)) {
                return "{\n" +
                        "\n" +
                        " \n" +
                        "\n" +
                        "  \"status\" : \"SUCCESS\"\n" +
                        "\n" +
                        "}";
            }


            return null;
        } catch (UnexpectedTypeException e) {
            throw new UnexpectedTypeException("CustomerId/Channel/token are mandatory");
        }
    }

    @Override
    public String reissueToken(TokenRequestModel tokenrequest) {
        try {
            if (Objects.isNull(tokenrequest.getCustomerId()) || Objects.isNull(tokenrequest.getChannel()))
                throw new UnexpectedTypeException("");
            logger.info("Reissuing Token for CustomerId {} for channel {}", tokenrequest.getCustomerId(), tokenrequest.getChannel());
            String result = tokenutil.reissueToken(tokenrequest);
            return result;
        } catch (UnexpectedTypeException e) {
            throw new UnexpectedTypeException("CustomerId/Channel are mandatory");
        }
    }
}
