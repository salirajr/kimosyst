/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sjr.kimosyst.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sjr.kimosyst.dao.MutasiSubmissionRepository;
import com.sjr.kimosyst.model.MutasiSubmission;
import com.sjr.kimosyst.model.util.BaseModel;
import com.sjr.kimosyst.util.DateUtil;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author salirajr
 */
@Service
public class MutasiSubmissionService {

    @Autowired
    EntityManager manager;

    @Autowired
    MutasiSubmissionRepository repoMSbssn;

    @Resource
    ObjectMapper objectMapper;

    public MutasiSubmissionRepository repo() {
        return repoMSbssn;
    }

    public Map addNew(BaseModel payload, String actor, String fileName, String memo) throws JsonProcessingException {

        Map rsp = new HashMap();
        MutasiSubmission dt = new MutasiSubmission();
        dt.setActor(actor);
        /*dt.setContent(objectMapper.writeValueAsString(payload.getContent()));*/
        dt.setFileSrc(fileName);
        /*dt.setHeaders(objectMapper.writeValueAsString(payload.getHeaders()));*/
        dt.setMemo(memo);
        dt.setNRow(new Long(payload.getContent().size()));
        Timestamp now = DateUtil.getTNow();
        rsp.put("MutasiSubmission.tNow", now);
        dt.setSystDate(now);
        repoMSbssn.save(dt);
        rsp.put("MutasiSubmission.id", now);
        return rsp;
    }


}
