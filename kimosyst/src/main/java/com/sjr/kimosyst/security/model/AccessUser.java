package com.sjr.kimosyst.security.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccessUser
{

    private String username;
    private String password;
    private String role;
}
