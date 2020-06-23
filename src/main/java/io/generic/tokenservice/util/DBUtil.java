package io.generic.tokenservice.util;

import org.springframework.stereotype.Component;

@Component
public class DBUtil {
    public java.sql.Timestamp getCurrentTimeStamp() {

        java.util.Date today = new java.util.Date();
        return new java.sql.Timestamp(today.getTime());

    }
}
