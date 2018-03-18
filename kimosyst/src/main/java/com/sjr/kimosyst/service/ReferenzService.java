/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sjr.kimosyst.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sjr.kimosyst.dao.ReferenzRepository;
import com.sjr.kimosyst.util.FileUtil;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author salirajr
 */
@Service
public class ReferenzService {

    @Autowired
    EntityManager manager;

    @Autowired
    ReferenzRepository repoRef;

    @Resource
    ObjectMapper objectMapper;

    final Logger logger = LogManager.getLogger(ReferenzService.class);

    public ReferenzRepository repo() {
        return repoRef;
    }

    @PostConstruct
    public void loadBaseConfiguraiton() {
        try {
            FileUtil.BASEDIR = repoRef.findByGruppeAndScopeAndRname("BASE_CONFIGURATION", "SYST_OWNER", "BASEDIR").getRvalue();
            logger.info("\"BASE_CONFIGURATION\", \"SYST_OWNER\", \"BASEDIR\" [" + FileUtil.BASEDIR + "] reloaded succesfully");
        } catch (java.lang.NullPointerException npe) {
            logger.error("\"BASE_CONFIGURATION\", \"SYST_OWNER\", \"BASEDIR\" is null: ", npe);
        }
        try {
            FileUtil.BASEDIR_IDTFR = repoRef.findByGruppeAndScopeAndRname("BASE_CONFIGURATION", "SYST_OWNER", "BASEDIR_IDTFR").getRvalue();
            logger.info("\"BASE_CONFIGURATION\", \"SYST_OWNER\", \"BASEDIR_IDTFR\" [" + FileUtil.BASEDIR_IDTFR + "] reloaded succesfully");
        } catch (java.lang.NullPointerException npe) {
            logger.error("\"BASE_CONFIGURATION\", \"SYST_OWNER\", \"BASEDIR_IDTFR\" is null: ", npe);
        }
        try {
            IncomeComplianceMutasiService.VERIFIED_SCHEME_SRC = repoRef.findByGruppeAndScopeAndRname("BASE_CONFIGURATION", "SYST_OWNER", "VERIFIED_SCHEME_SRC").getRvalue();
            logger.info("\"BASE_CONFIGURATION\", \"SYST_OWNER\", \"VERIFIED_SCHEME_SRC\" [" + IncomeComplianceMutasiService.VERIFIED_SCHEME_SRC + "] reloaded succesfully");
        } catch (java.lang.NullPointerException npe) {
            logger.error("\"BASE_CONFIGURATION\", \"SYST_OWNER\", \"VERIFIED_SCHEME_SRC\" is null: ", npe);
        }
        try {
            IncomeComplianceMutasiService.VOID_SCHEME_SRC = repoRef.findByGruppeAndScopeAndRname("BASE_CONFIGURATION", "SYST_OWNER", "VOID_SCHEME_SRC").getRvalue();
            logger.info("\"BASE_CONFIGURATION\", \"SYST_OWNER\", \"VOID_SCHEME_SRC\" [" + IncomeComplianceMutasiService.VOID_SCHEME_SRC + "] reloaded succesfully");
        } catch (java.lang.NullPointerException npe) {
            logger.error("\"BASE_CONFIGURATION\", \"SYST_OWNER\", \"VOID_SCHEME_SRC\" is null: ", npe);
        }

    }
}
