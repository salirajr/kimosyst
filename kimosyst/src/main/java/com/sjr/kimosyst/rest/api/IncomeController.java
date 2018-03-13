package com.sjr.kimosyst.rest.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sjr.kimosyst.model.Income;
import com.sjr.kimosyst.model.IncomeComplianceMutasi;
import com.sjr.kimosyst.model.Mutasi;
import com.sjr.kimosyst.model.util.BaseModel;
import com.sjr.kimosyst.rest.api.util.Payload;
import com.sjr.kimosyst.service.IncomeService;
import com.sjr.kimosyst.util.ChiperUtil;
import com.sjr.kimosyst.util.DateUtil;
import com.sjr.kimosyst.util.FileUtil;
import com.sjr.kimosyst.util.ImageUtil;
import com.sjr.kimosyst.util.ObjectUtil;
import com.sjr.kimosyst.util.StringUtil;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author salirajr
 */
@RestController
@RequestMapping(ApiController.PREFIX + "/income")
public class IncomeController {

    @Autowired
    private IncomeService incmService;

    @Resource
    ObjectMapper oMapper;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Payload.Response get(@PathVariable("id") Long id, HttpServletRequest request)
            throws ServletException {
        Payload.Response rsp = new Payload.Response(Income.TABLE_NAME, Payload.ResponseStatus._00);
        rsp.body = incmService.repo().findOne(id);
        return rsp;
    }

    private String getBase64FileExtension(String header) {
        if (header.toLowerCase().contains("jpg")) {
            return "jpg";
        } else if (header.toLowerCase().contains("jpeg")) {
            return "jpeg";
        } else if (header.toLowerCase().contains("png")) {
            return "png";
        } else {
            return "";
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public Payload.Response insert(@RequestBody Income payload, HttpServletRequest request)
            throws ServletException {
        Payload.Response rsp = new Payload.Response(Income.TABLE_NAME, Payload.ResponseStatus._00);
        Map map = new HashMap();
        System.out.println("income-fsl=" + payload.getPrintFSL());
        if (StringUtil.isNullOrBlank(payload.getPrintFSL())) {
            incmService.repo().save(payload);
            payload.setPrintFSL(FileUtil.getDir(DateUtil.getDToday(), Income.TABLE_NAME, payload.getId() + "." + getBase64FileExtension(payload.getPrintSrc().split(";")[0])));
            incmService.repo().save(payload);
        } else {
            incmService.repo().save(payload);
        }

        map.put("incomeId", payload.getId());
        map.put("fsl", payload.getPrintFSL());
        if (!StringUtil.isNullOrBlank(payload.getPrintSrc())) {
            try {
                FileUtil.storeAsFile(FileUtil.getAbsDir(payload.getPrintFSL()), ImageUtil.toArrayImgBytes(payload.getPrintSrc().split(",")[payload.getPrintSrc().split(",").length - 1]));
            } catch (IOException ex) {
                rsp = new Payload.BizzErrResponse(Income.TABLE_NAME, "Failed to store raw data!");
                rsp.body = ex.getLocalizedMessage();
            }
        }

        rsp.body = map;
        return rsp;
    }

    @RequestMapping(value = "/wcalc/{id}/{mutasiId}", method = RequestMethod.GET)
    public Payload.Response calcWeight(@PathVariable("id") Long id, @PathVariable("mutasiId") Long mutasiId, HttpServletRequest request)
            throws ServletException {
        Payload.Response rsp = new Payload.Response(Income.TABLE_NAME, Payload.ResponseStatus._00);
        rsp.body = incmService.calculateWeight(id, mutasiId, false);
        return rsp;
    }

    @RequestMapping(value = "/vw/{type}", method = RequestMethod.GET)
    public Payload.Response vw(@PathVariable("type") String type,
            @RequestParam(name = "start") Timestamp start, @RequestParam(name = "end") Timestamp end,
            @RequestParam(name = "incomeType", required = false) String incomeType, @RequestParam(name = "rekeningNo", required = false) String rekeningNo,
            @RequestParam(name = "bank", required = false) String bank, @RequestParam(name = "buktiTransfer", required = false) String buktiTransfer,
            @RequestParam(name = "gtStage", required = false) Double gtStage, @RequestParam(name = "ltStage", required = false) Double ltStage,
            HttpServletRequest request)
            throws ServletException, UnsupportedEncodingException {
        Payload.Response rsp = new Payload.Response(Income.TABLE_NAME, Payload.ResponseStatus._00);
        if (StringUtil.isNullOrBlank(incomeType)) {
            incomeType = "%";
        }
        if (StringUtil.isNullOrBlank(rekeningNo)) {
            rekeningNo = "%";
        }
        if (StringUtil.isNullOrBlank(bank)) {
            bank = "%";
        }
        if (StringUtil.isNullOrBlank(buktiTransfer)) {
            buktiTransfer = "%";
        }
        if (StringUtil.isNullOrBlank(buktiTransfer)) {
            buktiTransfer = "%";
        }
        if (gtStage == null) {
            gtStage = 0.0;
        }
        if (ltStage == null) {
            ltStage = 10.0;
        }

        switch (type) {
            case "oncreation":
                rsp.body = incmService.retVwOnCreation(start, end, incomeType, rekeningNo, bank, buktiTransfer, gtStage, ltStage);
                break;
        }
        return rsp;
    }

}
