/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sjr.kimosyst.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sjr.kimosyst.dao.ComplianceXpressionRepository;
import com.sjr.kimosyst.dao.IncomeRepository;
import com.sjr.kimosyst.dao.IncomeXDetailRepository;
import com.sjr.kimosyst.dao.MutasiRepository;
import com.sjr.kimosyst.model.ComplianceXpression;
import com.sjr.kimosyst.model.Income;
import com.sjr.kimosyst.model.IncomeXDetail;
import com.sjr.kimosyst.model.Mutasi;
import com.sjr.kimosyst.model.util.Stage;
import com.sjr.kimosyst.rest.api.ApiController;
import com.sjr.kimosyst.util.ChiperUtil;
import com.sjr.kimosyst.util.DateUtil;
import com.sjr.kimosyst.util.ObjectUtil;
import com.sjr.kimosyst.util.SPELUtil;
import com.sjr.kimosyst.util.StringUtil;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
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
public class IncomeService {

    @Autowired
    EntityManager manager;

    @Autowired
    IncomeRepository repoI;

    @Autowired
    MutasiRepository repoM;

    @Autowired
    ComplianceXpressionRepository repoCX;

    @Autowired
    IncomeXDetailRepository repoIXD;

    @Resource
    ObjectMapper objectMapper;

    public IncomeRepository repo() {
        return repoI;
    }

    /**
     *
     * @param id
     * @param mutasiId
     * @param isCalcMax is required just for calculate maximum weight, value for
     * income.tweight
     * @return
     */
    public double calculateWeight(Long id, Long mutasiId, boolean isCalcMax) {
        SPELUtil spelUtil = new SPELUtil();
        Income in = repoI.findOne(id);
        Mutasi mutasi = repoM.findOne(mutasiId);
        List<IncomeXDetail> details = repoIXD.findByIncomeId(in.getId());

        double weight = 0;

        if (isCalcMax || (mutasi.getStage() != Stage.MUTASI._PAIRED && mutasi.getStage() != Stage.MUTASI._CORRUPT)) {
            weight += 20;
        }

        if (isCalcMax || (in.getNominal() == mutasi.getKredit())) {
            weight += 10;
        }

        if (isCalcMax || (in.getRekAccount().equals(mutasi.getRekAccount()))) {
            weight += 10;
        }

        System.out.println(in.getTrxDate() + " " + mutasi.getTgl());
        if (isCalcMax || (in.getTrxDate().toString().equals(mutasi.getTgl().toString()))) {
            weight += 10;
        }

        weight += Stage.MUTASI.val(isCalcMax, mutasi.getStage());

        weight += Stage.INCOME.val(isCalcMax, in.getStage());

        ComplianceXpression cx;
        String ketTrx = mutasi.getMemo().toUpperCase();
        for (IncomeXDetail detail : details) {
            cx = repoCX.findOne(detail.getXpressId());
            if (isCalcMax || spelUtil.xCVlte(ketTrx, detail.getPvalue().toUpperCase(), cx.getXpress())) {
                weight += cx.getWeight();
            }
        }
        return weight;
    }

    public List<Map> retVwOnCreation(Timestamp start, Timestamp end, String incomeType, String rekeningNo, String bank, String buktiTransfer, Double gtStage, Double ltStage) throws UnsupportedEncodingException {
        List<Map> result = repoI.retVwOnCreation(start, end, incomeType, rekeningNo, bank, buktiTransfer, gtStage, ltStage);
        String sIncomeId, ecIncomeId;
        Object sMutasiId;
        String actkey = StringUtil.asURL(ChiperUtil.encrypt(DateUtil.getChiperKey()));

        for (int i = 0; i < result.size(); i++) {
            sIncomeId = result.get(i).get("incomeId").toString();
            ecIncomeId = StringUtil.asURL(ChiperUtil.encrypt(String.valueOf(sIncomeId)));

            sMutasiId = result.get(i).get("incomeMutasiId");
            result.get(i).put("imgPblcURI", ApiController.PREFIX_PUBLIC + "/undh/income/img?id=" + ecIncomeId + "&actkey=" + actkey);
            if (!StringUtil.isNullOrBlank(sIncomeId)) {
                result.get(i).put("detail", repoIXD.retVwByIncomeId(Long.valueOf(sIncomeId)));
            }
            if (!ObjectUtil.isNull(sMutasiId)) {
                result.get(i).put("imgMCPblcURI", ApiController.PREFIX_PUBLIC + "/undh/incomecmutasi/img?type=income&id=" + ecIncomeId + "&actkey=" + actkey);
            }

        }
        return result;
    }

}
