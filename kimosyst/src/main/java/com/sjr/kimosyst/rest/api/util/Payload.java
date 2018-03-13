/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sjr.kimosyst.rest.api.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author salirajr
 */
public class Payload {

    public enum HeaderKeys {
        status, msg, name, code, info, error;
    }

    public enum ResponseStatus {
        _00, _BizzErr, _SystErr;
    }

    public static class Response implements Serializable {

        public Map header;
        public Object body;

        public Response(String name, Payload.ResponseStatus status, Object body) {
            this.header = new HashMap();
            this.header.put(HeaderKeys.name, name);
            this.header.put(HeaderKeys.status, status);

            this.body = body;

        }

        public Response(String name, Payload.ResponseStatus status) {
            this.header = new HashMap();
            this.header.put(HeaderKeys.name, name);
            this.header.put(HeaderKeys.status, status);
        }

    }

    public static class BizzErrResponse extends Payload.Response {

        public BizzErrResponse(String name, String code) {
            super(name, ResponseStatus._BizzErr);
            this.header.put(HeaderKeys.code, code);
        }
        
        public BizzErrResponse(String name, String code, String info) {
            super(name, ResponseStatus._BizzErr);
            this.header.put(HeaderKeys.code, code);
            this.header.put(HeaderKeys.info, info);
        }

    }
    
    public static class SystErrResponse extends Payload.Response {

        public SystErrResponse(String name, String code) {
            super(name, ResponseStatus._SystErr);
            this.header.put(HeaderKeys.code, code);
        }
        
        public SystErrResponse(String name, String code, String error) {
            super(name, ResponseStatus._SystErr);
            this.header.put(HeaderKeys.code, code);
            this.header.put(HeaderKeys.error, error);
        }

    }

}
