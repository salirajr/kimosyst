/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sjr.kimosyst.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sjr.kimosyst.dao.ComplianceXpressionRepository;
import com.sjr.kimosyst.model.ComplianceXpression;
import com.sjr.kimosyst.model.IncomeComplianceMutasi;
import com.sjr.kimosyst.util.StringUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
public class ComplianceXpressionService {

    @Autowired
    EntityManager manager;

    @Autowired
    ComplianceXpressionRepository repoCX;

    @Resource
    ObjectMapper objectMapper;

    public ComplianceXpressionRepository repo() {
        return repoCX;
    }

    @Transactional
    public Map save(String bank, String noteType, List<ComplianceXpression> in) {
        List<ComplianceXpression> curr = repoCX.findByBankAndNoteType(bank, noteType);

        List<Long> ldelete = new ArrayList<>();
        curr.forEach((tcx) -> {
            boolean isDeleted = true;
            for (int i = 0; i < in.size(); i++) {
                if (Objects.equals(in.get(i).getId(), tcx.getId())) {
                    isDeleted = false;
                    break;
                }
            }
            if (isDeleted) {
                ldelete.add(tcx.getId());
                repoCX.delete(tcx);
            }
        });

        List<ComplianceXpression> lstored = new ArrayList<>();
        in.forEach((temp) -> {
            temp.setBank(bank);
            temp.setNoteType(noteType);
            repoCX.save(temp);
            lstored.add(temp);
        });
        
        Map map = new HashMap();
        map.put("deleted", ldelete);
        map.put("stored", lstored);
        return map;
    }

}
