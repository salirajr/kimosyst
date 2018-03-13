package com.sjr.kimosyst.rest.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sjr.kimosyst.model.IncomeComplianceMutasi;
import com.sjr.kimosyst.rest.api.util.Payload;
import com.sjr.kimosyst.service.IncomeComplianceMutasiService;
import com.sjr.kimosyst.util.FileUtil;
import com.sjr.kimosyst.util.ImageUtil;
import com.sjr.kimosyst.util.StringUtil;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
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
@RequestMapping(ApiController.PREFIX + "/incomecmutasi")
public class IncomeComplianceMutasiController {

    @Autowired
    private IncomeComplianceMutasiService incmCMtsiService;

    @Resource
    ObjectMapper oMapper;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Payload.Response get(@PathVariable("id") Long id, HttpServletRequest request)
            throws ServletException {
        Payload.Response rsp = new Payload.Response(IncomeComplianceMutasi.TABLE_NAME, Payload.ResponseStatus._00);
        rsp.body = incmCMtsiService.repo().findOne(id);
        return rsp;
    }

    @RequestMapping(value = "/vw/{type}", method = RequestMethod.GET)
    public Payload.Response vw(@PathVariable("type") String type,
            @RequestParam(name = "start") Timestamp start, @RequestParam(name = "end") Timestamp end,
            @RequestParam(name = "incomeType", required = false) String incomeType, @RequestParam(name = "rekeningNo", required = false) String rekeningNo,
            @RequestParam(name = "bank", required = false) String bank, @RequestParam(name = "buktiTransfer", required = false) String buktiTransfer, @RequestParam(name = "amount", required = false) String amount,
            HttpServletRequest request)
            throws ServletException {
        Payload.Response rsp = new Payload.Response(IncomeComplianceMutasi.TABLE_NAME, Payload.ResponseStatus._00);
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
        if (!StringUtil.isNullOrBlank(amount)) {
            type += "-withamount";
        }

        try {
            switch (type) {
                case "oncreation":
                    rsp.body = incmCMtsiService.repo().retVwOnCreation(start, end, incomeType, rekeningNo, bank, buktiTransfer);
                    break;
                case "oncreation-withamount":
                    rsp.body = incmCMtsiService.repo().retVwOnCreationWithAmount(start, end, incomeType, rekeningNo, bank, buktiTransfer, new Double(amount));
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            rsp.body = new HashMap();
        }

        return rsp;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Payload.Response insert(@RequestBody IncomeComplianceMutasi payload, HttpServletRequest request)
            throws ServletException {
        Payload.Response rsp = new Payload.Response(IncomeComplianceMutasi.TABLE_NAME, Payload.ResponseStatus._00);

        return rsp;
    }

    @RequestMapping(value = "/comply", method = RequestMethod.POST)
    public Payload.Response comply(@RequestBody IncomeComplianceMutasi payload, HttpServletRequest request)
            throws ServletException {
        Payload.Response rsp = new Payload.Response(IncomeComplianceMutasi.TABLE_NAME, Payload.ResponseStatus._00);
        rsp.body = incmCMtsiService.comply(payload);
        return rsp;
    }

    @RequestMapping(value = "/complied/img/{id}", method = RequestMethod.POST)
    public Payload.Response getCompliedImg(@PathVariable("id") Long id, HttpServletRequest request)
            throws ServletException, IOException {
        Payload.Response rsp = new Payload.Response(IncomeComplianceMutasi.TABLE_NAME, Payload.ResponseStatus._00);
        rsp.body = incmCMtsiService.complyImg(id);
        return rsp;
    }

    @RequestMapping(value = "/img/{id}", method = RequestMethod.GET)
    public byte[] getImg(@PathVariable("id") Long id, HttpServletRequest request) throws IOException {
        IncomeComplianceMutasi icm = incmCMtsiService.repo().findOne(id);
        return ImageUtil.grabImgBytesArrays(FileUtil.getAbsDir(icm.getPrintFSL()));
    }

}
