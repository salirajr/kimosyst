/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sjr.kimosyst.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author JOVIR
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizedUser {

    private String username;
    private String role;
}
