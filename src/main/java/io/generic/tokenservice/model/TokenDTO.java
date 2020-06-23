package io.generic.tokenservice.model;

import javax.persistence.*;
import java.sql.Timestamp;
@Entity(name = "TOKEN_MASTER")
@Table(name = "TOKEN_MASTER")
public class TokenDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TOKEN_ID", nullable = false)
    private Long id;
    @Column(name = "CUSTOMER_ID")
    private String customerId;
    @Column(name = "CHANNEL")
    private String channel;
    @Column(name = "EXPIRES_IN")
    private int expiresIn;
    @Column(name = "LENGTH")
    private int length;
    @Column(name = "ALPHA_NUMERIC")
    private String alphanumeric;
    @Column(name = "CREATED_ON")
    public Timestamp time;



    @Column(name = "TOKEN")
    public String token;
    @Column(name = "EXPIRY_AT")
    public String expiryAt;

    public String getExpiryAt() {
        return expiryAt;
    }

    public void setExpiryAt(String expiryAt) {
        this.expiryAt = expiryAt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getAlphanumeric() {
        return alphanumeric;
    }

    public void setAlphanumeric(String alphanumeric) {
        this.alphanumeric = alphanumeric;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
