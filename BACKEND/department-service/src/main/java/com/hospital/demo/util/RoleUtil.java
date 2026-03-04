package com.hospital.demo.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RoleUtil {

    public static void checkAdmin(String role) {
        if (role == null || !role.equals("ADMIN")) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Access Denied: Only ADMIN allowed"
            );
        }
    }
}