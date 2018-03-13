/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sjr.kimosyst.security.service;

import com.sjr.kimosyst.security.model.AuthorizedUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 *
 * @author JOVIR
 */

@Service
public class JwtAuthorizationService
{

    //@Value("${jwt.expire.hours}")
    final private Long expireHours = new Long(1);

    //@Value("${jwt.token.secret}")
    final private String plainSecret = "m1m0-b4w3l!*@&#^$%k1m05y5t";
    private String encodedSecret;

    @PostConstruct
    protected void init() {
        this.encodedSecret = generateEncodedSecret(this.plainSecret);
    }

    protected String generateEncodedSecret(String plainSecret)
    {
        if (StringUtils.isEmpty(plainSecret))
        {
            throw new IllegalArgumentException("JWT secret cannot be null or empty.");
        }
        return Base64
                .getEncoder()
                .encodeToString(this.plainSecret.getBytes());
    }

    protected Date getExpirationTime()
    {
        Date now = new Date();
        //Long expireInMilis = TimeUnit.HOURS.toMillis(expireHours);
        Long expireInMilis = TimeUnit.MINUTES.toMillis(120);
        return new Date(expireInMilis + now.getTime());
    }

    protected AuthorizedUser getUser(String encodedSecret, String token)
    {
        Claims claims = Jwts.parser()
                .setSigningKey(encodedSecret)
                .parseClaimsJws(token)
                .getBody();
        String userName = claims.getSubject();
        String role = (String) claims.get("role");
        AuthorizedUser securityUser = new AuthorizedUser();
        securityUser.setUsername(userName);
        securityUser.setRole(role);
        return securityUser;
    }

    public AuthorizedUser authorized(String token)
    {
        return getUser(this.encodedSecret, token);
    }

    protected String getToken(String encodedSecret, AuthorizedUser jwtUser)
    {
        Date now = new Date();
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(jwtUser.getUsername())
                .claim("role", jwtUser.getRole())
                .setIssuedAt(now)
                .setExpiration(getExpirationTime())
                .signWith(SignatureAlgorithm.HS512, encodedSecret)
                .compact();
    }

    public String getToken(AuthorizedUser jwtUser)
    {
        return getToken(this.encodedSecret, jwtUser);
    }
}
