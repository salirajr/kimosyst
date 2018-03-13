/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sjr.kimosyst.om;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author JOVIR
 */
public class CObjectMapper {
    public static void main(String[] args) throws JsonProcessingException {
        
         ObjectMapper objectMapper = new ObjectMapper();
         Map data = new HashMap<>();
         data.put("A", 1);
         data.put("B", "test");
         data.put("C", true);
         System.out.println(objectMapper.writeValueAsString(data));
    }
}
