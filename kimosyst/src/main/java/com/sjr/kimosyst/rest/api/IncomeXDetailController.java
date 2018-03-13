package com.sjr.kimosyst.rest.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sjr.kimosyst.model.Income;
import com.sjr.kimosyst.model.IncomeXDetail;
import com.sjr.kimosyst.rest.api.util.Payload;
import com.sjr.kimosyst.service.IncomeXDetailService;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author salirajr
 */
@RestController
@RequestMapping(ApiController.PREFIX + "/incomexdetail")
public class IncomeXDetailController {

    @Autowired
    private IncomeXDetailService incmXDService;

    @Resource
    ObjectMapper oMapper;

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Payload.Response get(@PathVariable("id") Long id, HttpServletRequest request)
            throws ServletException {
        Payload.Response rsp = new Payload.Response(IncomeXDetail.TABLE_NAME, Payload.ResponseStatus._00);
        rsp.body = new IncomeXDetail();
        return rsp;
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public Payload.Response insert(@RequestBody IncomeXDetail payload, HttpServletRequest request)
            throws ServletException {
        Payload.Response rsp = new Payload.Response(Income.TABLE_NAME, Payload.ResponseStatus._00);
        incmXDService.repo().save(payload);
        Map map = new HashMap();
        map.put("incomexdetailId", payload.getId());
        rsp.body = map;
        return rsp;
    }
}
