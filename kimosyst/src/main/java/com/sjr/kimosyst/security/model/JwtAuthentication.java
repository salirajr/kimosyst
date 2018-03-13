/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sjr.kimosyst.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author JOVIR
 */
@Data
@AllArgsConstructor
public class JwtAuthentication {

    private String username;
    private String password;
    private String actkey;

    @Data
    @AllArgsConstructor
    public static class JwtAuthorized {

        private String token;
        private String roles;
    }

}
