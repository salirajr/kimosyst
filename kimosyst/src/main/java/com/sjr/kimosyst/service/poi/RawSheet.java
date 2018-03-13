/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sjr.kimosyst.service.poi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author salirajr
 */
public class RawSheet implements Serializable{

    public List<Map<String, Object>> content;
    public Map<String, Object> metainfo;
    public List<String> keys;

    public RawSheet() {
        this.content = new ArrayList<>();
        this.metainfo = new HashMap<>();
        this.keys = new ArrayList<>();
    }

    public void putMetaInfo(String key, Object value) {
        this.metainfo.put(key, value);
    }
    
    void print(){
        for(Map temp: this.content){
            System.out.println(temp);
        }
    }
}
