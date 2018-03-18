/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sjr.kimosyst.security;

import com.sjr.kimosyst.rest.api.ApiController;
import com.sjr.kimosyst.security.model.AuthorizedUser;
import com.sjr.kimosyst.security.service.JwtAuthorizationService;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author JOVIR
 */
@WebFilter(urlPatterns = {ApiController.PREFIX + "/*"})
public class JwtFilter implements Filter {

    @Autowired
    private JwtAuthorizationService jwtTokenService;

    //@Value("${jwt.auth.header}") // no final
    //final String authHeader = "something-secret-you-cannot-keep-it";

    public static final String AUTHORIZATION = "Authorization", BEARER = "Bearer ",
            CLAIMS = "claims", USER = "jwtUser";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        final HttpServletResponse httpResponse = (HttpServletResponse) response;
        final String authHeaderVal = httpRequest.getHeader(AUTHORIZATION);

        if (null == authHeaderVal) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        try {
            AuthorizedUser jwtUser = jwtTokenService.authorized(authHeaderVal.substring(BEARER.length()));
            httpRequest.setAttribute("jwtUser", jwtUser);
        } catch (JwtException e) {
            httpResponse.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            return;
        }

        chain.doFilter(httpRequest, httpResponse);
    }
}
