/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sjr.kimosyst.model.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author JOVIR
 */
public class BaseModel implements Serializable {

    private List<Map<String, Object>> content;
    private Map<String, Object> headers;

    public List<Map<String, Object>> getContent() {
        return content;
    }

    public void setContent(List<Map<String, Object>> content) {
        this.content = content;
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, Object> headers) {
        this.headers = headers;
    }

    public BaseModel() {
        this.headers = new HashMap<>();
        this.content = new ArrayList<>();
    }

    public BaseModel(List<Map<String, Object>> body, Map<String, Object> headers) {
        this.content = body;
        this.headers = headers;
    }

}
