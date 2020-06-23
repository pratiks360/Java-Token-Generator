package io.generic.tokenservice.controllers;

import io.generic.tokenservice.model.TokenRequestModel;
import io.generic.tokenservice.service.TokenService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/tokenservice")
public class TokenController {
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    TokenService ts;

    @PostMapping(path = "/issueToken", consumes = "application/json", produces = "application/json")
    public String generateToken(@Valid @RequestBody TokenRequestModel request) {
        logger.info("Token Generation invoked for " + request.getCustomerId());
        return ts.generateToken(request);
    }

    @PostMapping(path = "/validateToken", consumes = "application/json", produces = "application/json")
    public String validateoken(@RequestBody TokenRequestModel request) {
        logger.info("Token Validation invoked for " + request.getCustomerId());
        return ts.validate(request);
    }

    @PostMapping(path = "/reissueToken", consumes = "application/json", produces = "application/json")
    public String reissue(@RequestBody TokenRequestModel request) {
        logger.info("Token Validation invoked for " + request.getCustomerId());
        return ts.reissueToken(request);
    }


}
