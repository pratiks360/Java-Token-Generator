package io.generic.tokenservice.service;

import io.generic.tokenservice.exceptions.TokenGenerationException;
import io.generic.tokenservice.model.TokenRequestModel;

public interface TokenService {

    String generateToken(TokenRequestModel tokenrequest)throws TokenGenerationException;

    String validate(TokenRequestModel tokenrequest)throws TokenGenerationException;

    String reissueToken(TokenRequestModel tokenrequest);
}
