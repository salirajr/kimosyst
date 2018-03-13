package com.sjr.kimosyst.rest.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sjr.kimosyst.model.MutasiSubmission;
import com.sjr.kimosyst.model.util.BaseModel;
import com.sjr.kimosyst.model.util.MutasiParserCSVMandiri;
import com.sjr.kimosyst.rest.api.util.Payload;
import com.sjr.kimosyst.service.MutasiService;
import com.sjr.kimosyst.service.MutasiSubmissionService;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(ApiController.PREFIX + "/xcross")
public class MutasiValidatorController {

    @Resource
    private MutasiParserCSVMandiri parser;

    @Autowired
    private MutasiService mtsiService;

    @Autowired
    private MutasiSubmissionService mtsiSbssnService;

    @Resource
    ObjectMapper oMapper;

    @RequestMapping(value = "/generate", method = RequestMethod.GET)
    public Payload.Response generate(@RequestParam String filePath, @RequestParam String type, HttpServletRequest request)
            throws ServletException {
        Payload.Response rsp = new Payload.Response("MutasiParserCSV", Payload.ResponseStatus._00);
        try {
            switch (type) {
                case ".mmst":
                    rsp.body = parser.doParse(filePath);
                    break;
            }

        } catch (IOException ex) {
            Logger.getLogger(MutasiValidatorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rsp;
    }



    @RequestMapping(value = "/ret/mutasi", method = RequestMethod.GET)
    public Payload.Response retMutasiSubmission(@RequestParam(name = "id") Long id)
            throws ServletException {
        Payload.Response rsp = new Payload.Response(MutasiSubmission.TABLE_NAME, Payload.ResponseStatus._00);
        rsp.body = mtsiService.repo().findOne(id);
        return rsp;
    }

    @RequestMapping(value = "/save/mutasi/submission", method = RequestMethod.POST)
    public Payload.Response saveMutasiSubmission(@RequestBody BaseModel payload, @RequestParam String actor, @RequestParam String fileName, @RequestParam String memo)
            throws ServletException {
        Payload.Response rsp = new Payload.Response(MutasiSubmission.TABLE_NAME, Payload.ResponseStatus._00);
        try {
            rsp.body = mtsiSbssnService.addNew(payload, actor, fileName, memo);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(MutasiValidatorController.class.getName()).log(Level.SEVERE, null, ex);
            rsp = new Payload.BizzErrResponse(MutasiSubmission.TABLE_NAME, "Submission mutasi failed, check your .csv content!");
        }
        return rsp;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Payload.Response upload(@RequestParam String fileName, HttpServletRequest request) throws IOException, ServletException {
        Payload.Response rsp = new Payload.Response(this.getClass().getCanonicalName() + ".upload()", Payload.ResponseStatus._00);
        byte[] bytfile = IOUtils.toByteArray(request.getInputStream());
        rsp.body = parser.doParse(bytfile);
        return rsp;
    }

    
    @RequestMapping(value = "/submission", method = RequestMethod.POST)
    public Payload.Response saveSubmission(@RequestBody MutasiSubmission payload, HttpServletRequest request)
            throws ServletException {
        Payload.Response rsp = new Payload.Response(MutasiSubmission.TABLE_NAME, Payload.ResponseStatus._00);
        System.out.println(payload);
        return rsp;
    }

}
