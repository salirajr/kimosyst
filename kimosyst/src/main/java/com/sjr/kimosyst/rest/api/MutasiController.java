package com.sjr.kimosyst.rest.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sjr.kimosyst.model.Mutasi;
import com.sjr.kimosyst.model.util.BaseModel;
import com.sjr.kimosyst.model.util.Stage;
import com.sjr.kimosyst.rest.api.util.Payload;
import com.sjr.kimosyst.security.JwtFilter;
import com.sjr.kimosyst.service.MutasiService;
import com.sjr.kimosyst.util.ChiperUtil;
import com.sjr.kimosyst.util.DateUtil;
import com.sjr.kimosyst.util.StringUtil;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping(ApiController.PREFIX + "/mutasi")
public class MutasiController {

    @Autowired
    private MutasiService mtsiService;

    @Resource
    ObjectMapper oMapper;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity save(@RequestBody Mutasi payload, HttpServletRequest request)
            throws ServletException {
        Payload.Response rsp = new Payload.Response(Mutasi.TABLE_NAME, Payload.ResponseStatus._00);
        if (payload.getKredit() <= 0) {
            rsp = new Payload.BizzErrResponse(Mutasi.TABLE_NAME, "NOT-AN-INCOME");
            rsp.body = "Only kredit > 0 allowed.";
            return new ResponseEntity(rsp, HttpStatus.EXPECTATION_FAILED);
        }
        try {
            rsp.body = mtsiService.repo().save(payload);
        } catch (DataIntegrityViolationException di) {
            rsp = new Payload.BizzErrResponse(Mutasi.TABLE_NAME, "EXIST");
            rsp.body = di.getMostSpecificCause().getLocalizedMessage();
            return new ResponseEntity(rsp, HttpStatus.EXPECTATION_FAILED);
        } catch (Exception e) {
            rsp = new Payload.BizzErrResponse(Mutasi.TABLE_NAME, "FAILED");
            rsp.body = e.getLocalizedMessage();
            return new ResponseEntity(rsp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(rsp, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Payload.Response get(@RequestParam(name = "id") Long id, HttpServletRequest request)
            throws ServletException {
        Payload.Response rsp = new Payload.Response(Mutasi.TABLE_NAME, Payload.ResponseStatus._00);
        rsp.body = mtsiService.repo().findOne(id);
        return rsp;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("id") Long id,
            @RequestParam(name = "reason", required = true) String reason,
            HttpServletRequest request)
            throws ServletException {
        Payload.Response rsp = null;
        Map map = new HashMap();
        Mutasi mtsi = mtsiService.repo().findOne(id);
        System.out.println(mtsi);

        if (mtsi.getStage() == Stage.MUTASI._NEW) {
            rsp = new Payload.Response(Mutasi.TABLE_NAME, Payload.ResponseStatus._00);
            mtsi.setStage(Stage.MUTASI._DELETED);
            mtsi.addSystMemo("Deleted by [" + request.getHeader(JwtFilter.AUTHORIZATION) + "], reason=" + reason);
            map.put("info", "Mutasi[" + id + "] has been successfully deleted.");
            return new ResponseEntity(rsp, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            rsp = new Payload.BizzErrResponse(Mutasi.TABLE_NAME, "UNPROCESSABLE_ENTITY");
            map.put("info", "Mutasi[" + id + "] is on " + mtsi.getStage() + " stage instead of _NEW[" + Stage.MUTASI._NEW + "].");
            rsp.body = map;
            return new ResponseEntity(rsp, HttpStatus.UNPROCESSABLE_ENTITY);
        }

    }

    @RequestMapping(value = "/file-access/{id}", method = RequestMethod.GET)
    public Payload.Response generate(@PathVariable("id") Long id, HttpServletResponse response)
            throws ServletException, IOException {
        Payload.Response rsp = new Payload.Response(BaseModel.class.getCanonicalName(), Payload.ResponseStatus._00);
        rsp.body = ApiController.PREFIX_PUBLIC + "/undh" + "/" + Mutasi.TABLE_NAME + "/mmst?id=" + StringUtil.asURL(ChiperUtil.encrypt(String.valueOf(id))) + "&actkey=" + StringUtil.asURL(ChiperUtil.encrypt(DateUtil.getChiperKey()));
        return rsp;
    }

    @RequestMapping(value = "/cluster/{type}", method = RequestMethod.GET)
    public Payload.Response retCluster(@PathVariable("type") String type,
            @RequestParam(name = "tgl") Date tgl,
            @RequestParam(name = "rekAccount") String rekAccount,
            @RequestParam(name = "kredit") double kredit,
            HttpServletRequest request)
            throws ServletException {
        Payload.Response rsp = new Payload.Response(Mutasi.TABLE_NAME, Payload.ResponseStatus._00);
        switch (type) {
            case "1_0":
                rsp.body = mtsiService.repo().retCluster1_0(tgl, rekAccount, kredit);
                break;
            case "2_0":
                rsp.body = mtsiService.repo().retCluster2_0(rekAccount, kredit);
                break;
            case "2_1":
                rsp.body = mtsiService.repo().retCluster2_1(rekAccount, kredit);
                break;
        }

        return rsp;
    }

    @RequestMapping(value = "/vw/{type}", method = RequestMethod.GET)
    public Payload.Response vw(@PathVariable("type") String type,
            @RequestParam(name = "start") Timestamp start, @RequestParam(name = "end") Timestamp end,
            @RequestParam(name = "rekeningNo", required = false) String rekeningNo,
            @RequestParam(name = "submissionId", required = false) Long submissionId,
            @RequestParam(name = "ltStage", required = false) Double ltStage,
            @RequestParam(name = "gtStage", required = false) Double gtStage,
            HttpServletRequest request)
            throws ServletException {
        Payload.Response rsp = new Payload.Response(Mutasi.TABLE_NAME, Payload.ResponseStatus._00);
        if (StringUtil.isNullOrBlank(rekeningNo)) {
            rekeningNo = "%";
        }
        if (gtStage == null) {
            gtStage = 0.0;
        }
        if (ltStage == null) {
            ltStage = 10.0;
        }
        switch (type) {
            case "oncreation":
                if (submissionId == null) {
                    rsp.body = mtsiService.repo().retVwOnCreation(start, end, rekeningNo, gtStage, ltStage);
                } else {
                    rsp.body = mtsiService.repo().retVwOnCreation(start, end, rekeningNo, submissionId, gtStage, ltStage);
                }
                break;
        }
        return rsp;
    }

}
