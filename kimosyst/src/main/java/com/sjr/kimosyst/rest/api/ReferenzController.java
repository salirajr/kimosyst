package com.sjr.kimosyst.rest.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sjr.kimosyst.model.util.Referenz;
import com.sjr.kimosyst.rest.api.util.Payload;
import com.sjr.kimosyst.service.ReferenzService;
import com.sjr.kimosyst.util.StringUtil;
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
@RequestMapping(ApiController.PREFIX + "/referenz")
public class ReferenzController {

    @Autowired
    private ReferenzService rfrzService;

    @Resource
    ObjectMapper oMapper;

    @RequestMapping(value = "/scopegruppe/{type}", method = RequestMethod.GET)
    public Payload.Response gets(@PathVariable("type") String type, HttpServletRequest request)
            throws ServletException {
        Payload.Response rsp = new Payload.Response(Referenz.TABLE_NAME, Payload.ResponseStatus._00);
        switch (type) {
            case "syst":
                rsp.body = rfrzService.repo().findSystGruppeAndScope();
        }
        return rsp;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Payload.Response get(@RequestParam(name = "group", required = true) String group,
            @RequestParam(name = "scope", required = true) String scope, @RequestParam(name = "q", required = false) String q, @RequestParam(name = "name", required = false) String name,
            HttpServletRequest request)
            throws ServletException {
        Payload.Response rsp = new Payload.Response(Referenz.TABLE_NAME, Payload.ResponseStatus._00);
        if (StringUtil.isNullOrBlank(q)) {
            if (StringUtil.isNullOrBlank(name)) {
                rsp.body = rfrzService.repo().findByGruppeAndScope(group, scope);
            } else {
                rsp.body = rfrzService.repo().findByGruppeAndScopeAndRname(group, scope, name);
            }

        } else {
            rsp.body = rfrzService.repo().findByGruppeAndScopeAndName(group, scope, q);
        }
        return rsp;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Payload.Response post(@RequestBody Referenz body,
            HttpServletRequest request)
            throws ServletException {
        Payload.Response rsp = new Payload.Response(Referenz.TABLE_NAME, Payload.ResponseStatus._00);
        rsp.body = rfrzService.repo().save(body);
        return rsp;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Payload.Response delete(@PathVariable("id") Long id,
            HttpServletRequest request)
            throws ServletException {
        rfrzService.repo().delete(id);
        return new Payload.Response(Referenz.TABLE_NAME, Payload.ResponseStatus._00);
    }

    @RequestMapping(value = "/sync", method = RequestMethod.POST)
    public Payload.Response delete(HttpServletRequest request)
            throws ServletException {
        rfrzService.loadBaseConfiguraiton();
        return new Payload.Response(Referenz.TABLE_NAME, Payload.ResponseStatus._00);
    }

}
