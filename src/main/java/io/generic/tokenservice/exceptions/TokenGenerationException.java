package io.generic.tokenservice.exceptions;

public class TokenGenerationException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String code;

    public TokenGenerationException(String exception, String code) {
        super(exception);
        this.code = code;

    }
    public TokenGenerationException(String exception) {
        super(exception);

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}
