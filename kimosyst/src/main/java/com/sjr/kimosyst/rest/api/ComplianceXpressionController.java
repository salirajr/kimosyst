package com.sjr.kimosyst.rest.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sjr.kimosyst.model.ComplianceXpression;
import com.sjr.kimosyst.rest.api.util.Payload;
import com.sjr.kimosyst.service.ComplianceXpressionService;
import com.sjr.kimosyst.util.ObjectUtil;
import com.sjr.kimosyst.util.SPELUtil;
import com.sjr.kimosyst.util.StringUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
@RequestMapping(ApiController.PREFIX + "/ccexpression")
public class ComplianceXpressionController {

    @Autowired
    private ComplianceXpressionService cXService;

    @Resource
    ObjectMapper oMapper;

    @RequestMapping(method = RequestMethod.POST)
    public Payload.Response save(@RequestBody ComplianceXpression payload, HttpServletRequest request)
            throws ServletException {
        Payload.Response rsp = new Payload.Response(ComplianceXpression.TABLE_NAME, Payload.ResponseStatus._00);
        try {
            rsp.body = cXService.repo().save(payload);
        } catch (DataIntegrityViolationException di) {
            rsp = new Payload.BizzErrResponse(ComplianceXpression.TABLE_NAME, "saving '" + payload.getMemo() + "' failed");
            rsp.body = di.getMostSpecificCause().getLocalizedMessage();
        }

        System.out.println(payload);

        return rsp;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Payload.Response get(@RequestParam(name = "id") Long id, HttpServletRequest request)
            throws ServletException {
        Payload.Response rsp = new Payload.Response(ComplianceXpression.TABLE_NAME, Payload.ResponseStatus._00);
        rsp.body = cXService.repo().findOne(id);
        return rsp;
    }

    @RequestMapping(value = "/byBankAndNoteType", method = RequestMethod.GET)
    public Payload.Response getByBankAndPayChannel(@RequestParam(name = "bank") String bank, @RequestParam(name = "noteType") String noteType, HttpServletRequest request)
            throws ServletException {
        Payload.Response rsp = new Payload.Response(ComplianceXpression.TABLE_NAME, Payload.ResponseStatus._00);
        rsp.body = cXService.repo().findByBankAndNoteType(bank, noteType);
        return rsp;
    }

    @RequestMapping(value = "/ret/{refernce}", method = RequestMethod.GET)
    public Payload.Response retBank(@PathVariable("refernce") String refernce, @RequestParam(value = "in1", required = false) String in1, HttpServletRequest request)
            throws ServletException {
        Payload.Response rsp = new Payload.Response(ComplianceXpression.TABLE_NAME, Payload.ResponseStatus._00);
        switch (refernce) {
            case "bank":
                rsp.body = cXService.repo().retAllBank();
                break;
            case "noteTypeByBank":
                rsp.body = cXService.repo().retAllNoteTypeByBank(in1);
                break;
        }
        return rsp;
    }

    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public Payload.Response validate(@RequestBody ComplianceXpression payload, HttpServletRequest request)
            throws ServletException {
        Payload.Response rsp = new Payload.Response(ComplianceXpression.TABLE_NAME, Payload.ResponseStatus._00);
        Map map = new HashMap();
        if (StringUtil.isNullOrBlank(payload.getXpress())) {
            map.put("xprEval", "Xpress is null or blank!");
        } else {
            SPELUtil util = new SPELUtil();
            map.put("xprEval", util.evaluateCVlte(payload.getXpress()));
        }
        rsp.body = map;
        return rsp;
    }

    @RequestMapping(value = "/save/asrfnce", method = RequestMethod.POST)
    public Payload.Response save(@RequestParam(name = "bank") String bank, @RequestParam(name = "noteType") String noteType, @RequestBody List<ComplianceXpression> payload, HttpServletRequest request)
            throws ServletException {
        Payload.Response rsp = new Payload.Response(ComplianceXpression.TABLE_NAME, Payload.ResponseStatus._00);
        rsp.body = cXService.save(bank, noteType, payload);
        return rsp;
    }

    @RequestMapping(value = "/{operation}/asrfnce", method = RequestMethod.POST)
    public Payload.Response calcAsRfnce(@PathVariable("operation") String operation, @RequestBody Map payload, HttpServletRequest request)
            throws ServletException {
        Payload.Response rsp = new Payload.Response(ComplianceXpression.TABLE_NAME, Payload.ResponseStatus._00);
        SPELUtil util = new SPELUtil();
        if (payload.containsKey("V") && payload.containsKey("in") && payload.containsKey("xpress")) {
            switch (operation) {
                case "evaluate":
                    rsp.body = util.xCVlte(ObjectUtil.asString(payload.get("V")), ObjectUtil.asString(payload.get("in")), ObjectUtil.asString(payload.get("xpress")));
                    break;
                case "execute":
                    rsp.body = util.xCXcte(ObjectUtil.asString(payload.get("V")), ObjectUtil.asString(payload.get("in")), ObjectUtil.asString(payload.get("xpress")));
                    break;
                default : rsp.body = -1;
            }

        }
        return rsp;
    }

}
