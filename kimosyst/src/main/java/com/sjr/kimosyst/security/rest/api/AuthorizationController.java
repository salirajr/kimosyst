/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sjr.kimosyst.security.rest.api;

import com.sjr.kimosyst.rest.api.ApiController;
import com.sjr.kimosyst.security.model.AuthorizedUser;
import com.sjr.kimosyst.security.model.JwtAuthentication;
import com.sjr.kimosyst.security.service.JwtAuthenticationService;
import com.sjr.kimosyst.security.service.JwtAuthorizationService;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author JOVIR
 */
@RestController
public class AuthorizationController {

    @Autowired
    private JwtAuthenticationService userService;

    @Autowired
    private JwtAuthorizationService jwtService;

    @GetMapping(value = ApiController.PREFIX + "/echo/{idtfr}")
    public ResponseEntity<?> helloSecure(@PathVariable String idtfr) {
        String result = String.format("Hello JWT, %s! (Secure)", idtfr);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = ApiController.PREFIX_PUBLIC + "/echo/{idtfr}")
    public ResponseEntity<?> helloPublic(@PathVariable String idtfr) {
        String result = String.format("Hello JWT, %s! (Public)", idtfr);
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = ApiController.PREFIX_PUBLIC + "/auth")
    public ResponseEntity<?> auth(@RequestBody JwtAuthentication auth) {
        String userName = auth.getUsername();
        String passWord = auth.getPassword();
        Boolean correctCredentials = userService.authenticate(userName, passWord);
        if (correctCredentials) {
            AuthorizedUser jwtUser = new AuthorizedUser(userName, passWord);
            return ResponseEntity.ok(new JwtAuthentication.JwtAuthorized(jwtService.getToken(jwtUser), ""));
        }
        return new ResponseEntity(new HashMap<>(), HttpStatus.UNAUTHORIZED);
    }
}
