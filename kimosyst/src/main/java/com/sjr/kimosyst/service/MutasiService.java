/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sjr.kimosyst.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sjr.kimosyst.dao.MutasiRepository;
import com.sjr.kimosyst.model.IncomeComplianceMutasi;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author salirajr
 */
@Service
public class MutasiService {

    @Autowired
    EntityManager manager;

    @Autowired
    MutasiRepository repoM;

    @Resource
    ObjectMapper objectMapper;

    public MutasiRepository repo() {
        return repoM;
    }
    
}