package io.generic.tokenservice.model;

import javax.validation.constraints.*;

public class TokenRequestModel {
    @NotNull(message = "customerId is mandatory")
    private String customerId;
    @NotNull(message = "channel is mandatory")
    private String channel;
    private String token;
    private int expiresIn;
    private boolean isAlphanumeric;

    @Max(8)
    @Min(4)
    private int length;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public boolean getIsAlphanumeric() {
        return isAlphanumeric;
    }

    public void setAlphanumeric(boolean alphanumeric) {
        isAlphanumeric = alphanumeric;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
