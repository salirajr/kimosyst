/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sjr.kimosyst.rest.api.pblc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sjr.kimosyst.model.Income;
import com.sjr.kimosyst.model.IncomeComplianceMutasi;
import com.sjr.kimosyst.model.Mutasi;
import com.sjr.kimosyst.model.MutasiSubmission;
import com.sjr.kimosyst.model.util.BaseModel;
import com.sjr.kimosyst.rest.api.ApiController;
import com.sjr.kimosyst.rest.api.util.Payload;
import com.sjr.kimosyst.service.IncomeComplianceMutasiService;
import com.sjr.kimosyst.service.IncomeService;
import com.sjr.kimosyst.service.MutasiService;
import com.sjr.kimosyst.util.ChiperUtil;
import com.sjr.kimosyst.util.DateUtil;
import com.sjr.kimosyst.util.FileUtil;
import com.sjr.kimosyst.util.ImageUtil;
import com.sjr.kimosyst.util.StringUtil;
import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author JOVIR
 */
@RestController
@RequestMapping(ApiController.PREFIX_PUBLIC + "/undh")
public class UnduhController {

    @Autowired
    private MutasiService mtsiService;

    @Autowired
    private IncomeComplianceMutasiService incmCMtsiService;

    @Autowired
    private IncomeService incmService;

    @Resource
    ObjectMapper oMapper;

    @RequestMapping(value = "/" + Mutasi.TABLE_NAME + "/mmst", method = RequestMethod.GET)
    public void downloadMutasi(String id, String actkey, HttpServletResponse response)
            throws ServletException, IOException {
        Payload.Response rsp = new Payload.Response(BaseModel.class.getCanonicalName(), Payload.ResponseStatus._00);
        System.out.println(id + " | " + actkey);

        if (DateUtil.getChiperKey().equals(ChiperUtil.decrypt(actkey))) {
            Long sbssnId = new Long(ChiperUtil.decrypt(id));
            rsp.body = mtsiService.repo().findBySbssnId(sbssnId);
            response.setContentType("text/plain");
            response.setHeader("Content-Disposition", "attachment;filename=" + MutasiSubmission.TABLE_NAME + "-" + sbssnId + ".mmst");
            try (ServletOutputStream out = response.getOutputStream()) {
                out.println(oMapper.writeValueAsString(rsp));
                out.flush();
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        }

    }

    @RequestMapping(value = "/incomecmutasi/img", method = RequestMethod.GET)
    public byte[] getIncomeCMutasiImgPrint(@RequestParam(name = "type", required = false) String type, @RequestParam(name = "id") String id, @RequestParam(name = "actkey") String actkey, HttpServletRequest request) throws IOException {
        System.out.println(id + " " + actkey);
        if (DateUtil.getChiperKey().equals(ChiperUtil.decrypt(actkey))) {
            Long lId = new Long(ChiperUtil.decrypt(id));
            System.err.println("lId=" + lId);
            IncomeComplianceMutasi icm;
            if (StringUtil.isNullOrBlank(type)) {
                icm = incmCMtsiService.repo().findOne(lId);
            } else if (type.equals("income")) {
                icm = incmCMtsiService.repo().findByIncomeId(lId);
            } else {
                icm = incmCMtsiService.repo().findOne(lId);
            }
            return ImageUtil.grabImgBytesArrays(FileUtil.getAbsDir(icm.getPrintFSL()));
        }
        return null;
    }

    @RequestMapping(value = "/income/img", method = RequestMethod.GET)
    public byte[] getIncomeImgPrint(@RequestParam(name = "id") String id,
            @RequestParam(name = "actkey") String actkey, HttpServletRequest request) throws IOException {
        System.out.println(id + " " + actkey);
        if (DateUtil.getChiperKey().equals(ChiperUtil.decrypt(actkey))) {
            Long incomeId = new Long(ChiperUtil.decrypt(id));
            Income icm = incmService.repo().findOne(incomeId);
            return ImageUtil.grabImgBytesArrays(FileUtil.getAbsDir(icm.getPrintFSL()));
        }
        return null;
    }

}
