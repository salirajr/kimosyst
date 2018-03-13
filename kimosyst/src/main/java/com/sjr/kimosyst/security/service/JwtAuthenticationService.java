/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sjr.kimosyst.security.service;

import com.sjr.kimosyst.security.model.AccessUser;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 *
 * @author JOVIR
 */
@Service
public class JwtAuthenticationService {

    private static Map<String, AccessUser> users = new HashMap<String, AccessUser>();

    static {
        users.put(
                "ksysadm", AccessUser
                        .builder()
                        .username("ksysadm")
                        .password("41log-") // Never do this!
                        .build()
        );
    }

    public AccessUser findUserByUserName(String userName) {
        return users.get(userName);
    }

    public Boolean authenticate(String username, String password) {
        AccessUser user = findUserByUserName(username);
        if (null != user) {
            return user.getPassword().equals(password);
        }
        return false;
    }
}
