package com.sjr.kimosyst.rest.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sjr.kimosyst.model.MutasiSubmission;
import com.sjr.kimosyst.rest.api.util.Payload;
import com.sjr.kimosyst.service.MutasiSubmissionService;
import com.sjr.kimosyst.util.DateUtil;
import com.sjr.kimosyst.util.FileUtil;
import java.io.IOException;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping(ApiController.PREFIX + "/mutasisubmission")
public class MutasiSubmissionController {

    @Autowired
    private MutasiSubmissionService mtsiSbssnService;

    @Resource
    ObjectMapper oMapper;

    @Transactional
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity saveSubmission(@RequestBody MutasiSubmission payload, HttpServletRequest request)
            throws ServletException {
        Payload.Response rsp = new Payload.Response(MutasiSubmission.TABLE_NAME, Payload.ResponseStatus._00);

        try {
            mtsiSbssnService.repo().save(payload);
            String fsl = FileUtil.getDir(DateUtil.getDToday(), MutasiSubmission.TABLE_NAME, payload.getId().toString() + ".json");
            FileUtil.storeAsFile(FileUtil.getAbsDir(fsl), oMapper.writeValueAsBytes(payload));
            MutasiSubmission temp = mtsiSbssnService.repo().findOne(payload.getId());
            temp.setRawFSL(fsl);
            rsp.body = mtsiSbssnService.repo().save(payload);
            return new ResponseEntity(rsp, HttpStatus.OK);
        } catch (IOException ioe) {
            rsp = new Payload.BizzErrResponse(MutasiSubmission.TABLE_NAME, "Input / Output violated");
            rsp.body = ioe.getLocalizedMessage();
            return new ResponseEntity(rsp, HttpStatus.EXPECTATION_FAILED);
        } catch (DataIntegrityViolationException dive) {
            rsp = new Payload.BizzErrResponse(MutasiSubmission.TABLE_NAME, "MUTASISUBMISSION_EXISTS", "File with the name is already saved!");
            return new ResponseEntity(rsp, HttpStatus.EXPECTATION_FAILED);
        } catch (Exception e) {
            rsp = new Payload.SystErrResponse(MutasiSubmission.TABLE_NAME, e.getClass().toGenericString(), e.getCause().toString());
            rsp.body = e.getLocalizedMessage();
            return new ResponseEntity(rsp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @RequestMapping(value = "/vw/{type}", method = RequestMethod.GET)
    public ResponseEntity vw(@PathVariable("type") String type, HttpServletRequest request)
            throws ServletException {
        Payload.Response rsp = new Payload.Response(MutasiSubmission.TABLE_NAME, Payload.ResponseStatus._00);
        rsp.body = mtsiSbssnService.repo().findAll();
        return new ResponseEntity(rsp, HttpStatus.OK);
    }

}
