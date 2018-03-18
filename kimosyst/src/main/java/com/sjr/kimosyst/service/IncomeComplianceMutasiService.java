/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sjr.kimosyst.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sjr.kimosyst.dao.IncomeComplianceMutasiRepository;
import com.sjr.kimosyst.dao.IncomeRepository;
import com.sjr.kimosyst.dao.IncomeXDetailRepository;
import com.sjr.kimosyst.dao.MutasiRepository;
import com.sjr.kimosyst.model.Income;
import com.sjr.kimosyst.model.IncomeComplianceMutasi;
import com.sjr.kimosyst.model.IncomeXDetail;
import com.sjr.kimosyst.model.Mutasi;
import com.sjr.kimosyst.model.util.Stage;
import com.sjr.kimosyst.rest.api.ApiController;
import com.sjr.kimosyst.util.ChiperUtil;
import com.sjr.kimosyst.util.DateUtil;
import com.sjr.kimosyst.util.FileUtil;
import com.sjr.kimosyst.util.StringUtil;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 *
 * @author salirajr
 */
@Service
public class IncomeComplianceMutasiService {

    @Autowired
    EntityManager manager;

    @Autowired
    IncomeRepository repoI;

    @Autowired
    IncomeXDetailRepository repoIXD;

    @Autowired
    MutasiRepository repoM;

    @Autowired
    IncomeComplianceMutasiRepository repoICM;

    @Resource
    ObjectMapper objectMapper;

    private final static String VERIFIER_DELIMITER = ", ";
    // E:\\Branches\\kampf\\kimosyst\\rsrc\\logo8.png
    public static String VERIFIED_SCHEME_SRC;
    public static String VOID_SCHEME_SRC;

    public IncomeComplianceMutasiRepository repo() {
        return repoICM;
    }

    @Transactional
    public Map comply(IncomeComplianceMutasi in) {
        Income income = repoI.findOne(in.getIncomeId());

        Map<String, Object> rsp = new HashMap<>();
        if (income.getId() != null) {
            if (income.getStage() == Stage.INCOME._DELETED) {
                rsp.put("msg", "Pairing failed, you need to create new Income data, is already marked as deleted!");
                rsp.put("code", "11");
            } else {
                Mutasi mutasi = repoM.findOne(in.getMutasiId());
                if (mutasi.getId() != null) {
                    if (mutasi.getStage() == Stage.MUTASI._CORRUPT) {
                        rsp.put("msg", "Pairing failed, Mutasi is marked as corrupted!");
                        rsp.put("code", "21");
                    } else {
                        in.setMemo("Kimosyst-Complied|");
                        repoICM.save(in);
                        mutasi.setIncomeId(in.getIncomeId());
                        mutasi.setSystMemo(mutasi.getSystMemo() + "stage[" + mutasi.getStage() + "-" + Stage.MUTASI._PAIRED + "]pairedto[" + income.getId() + "]trackedby[" + in.getId() + "]@" + DateUtil.getMemoTNow() + "by" + in.getActor() + "|");
                        mutasi.setStage(Stage.MUTASI._PAIRED);
                        repoM.save(mutasi);
                        income.setMutasiId(in.getMutasiId());
                        income.setSystMemo(income.getSystMemo() + "stage[" + income.getStage() + "-" + Stage.INCOME._PAIRED + "]pairedto[" + mutasi.getId() + "]trackedby[" + in.getId() + "]@" + DateUtil.getMemoTNow() + "by" + in.getActor() + "|");
                        income.setStage(Stage.INCOME._PAIRED);
                        repoI.save(income);
                        rsp.put("msg", "Compliance saved successfully!");
                        rsp.put("code", "00");
                        rsp.put("incomeCMutasiId", in.getId());
                    }
                } else {
                    rsp.put("msg", "Mutasi is not exist");
                    rsp.put("code", HttpStatus.NOT_FOUND);
                }
            }
        } else {
            rsp.put("msg", "Income is not exist");
            rsp.put("code", HttpStatus.NOT_FOUND);
        }
        return rsp;
    }

    @Transactional
    public Map complyImg(Long in) throws IOException {

        Map<String, Object> rsp = new HashMap<>();
        IncomeComplianceMutasi oicm = repoICM.findOne(in);
        Income oi = repoI.findOne(oicm.getIncomeId());
        if (!StringUtil.isNullOrBlank(oi.getPrintFSL()) && FileUtil.isExist(FileUtil.getAbsDir(oi.getPrintFSL()))) {

            Mutasi om = repoM.findOne(oicm.getMutasiId());

            //Get img comply
            String baseLImg = FileUtil.getAbsDir(oi.getPrintFSL());
            System.out.println("baseLImg=" + baseLImg);
            BufferedImage baseImg = ImageIO.read(new File(baseLImg));

            Graphics g = baseImg.getGraphics();
            g.setFont(g.getFont().deriveFont(12f));
            g.setColor(Color.YELLOW);

            int wrapLength = 70;
            String[] temps = StringUtil.textWrap(wrapLength, StringUtil.concats(VERIFIER_DELIMITER, "#ICM=" + in + " #I=" + oi.getId() + " #M=" + om.getId() + " #S=" + om.getSbssnId(), DateUtil.asMemo(om.getSystDate()), om.getRekAccount(), om.getDebit(), om.getKredit(), DateUtil.asMemo(om.getTgl()), om.getMemo()));

            int x = 10, y = 20;
            for (String t : temps) {
                g.drawString(t, x, y);
                y += 20;
                System.out.println("YELLOW=" + t);
            }

            g.setColor(Color.MAGENTA);
            List<IncomeXDetail> oid = repoIXD.findByIncomeId(oi.getId());
            String tid = "";
            tid = oid.stream().map((id) -> id.getPname() + "=" + id.getPvalue() + " ").reduce(tid, String::concat);

            temps = StringUtil.textWrap(wrapLength, StringUtil.concats(VERIFIER_DELIMITER, oi.getId(), DateUtil.asMemo(oi.getSystDate()), oi.getNominal(), oi.getNoteType(), oi.getNominal(), oi.getRekAccount(), oi.getBank(), oi.getTrxDate(), oi.getTrxTime(), tid));
            for (String t : temps) {
                g.drawString(t, x, y);
                y += 20;
                System.out.println("GREEN=" + t);
            }

            g.setColor(Color.CYAN);
            temps = StringUtil.textWrap(wrapLength, StringUtil.concats(VERIFIER_DELIMITER, oicm.getId(), oicm.getWeight()));
            for (String t : temps) {
                g.drawString(t, x, y);
                y += 20;
                System.out.println("MAGENTA=" + t);
            }
            g.setFont(g.getFont().deriveFont(12f));
            g.setColor(Color.ORANGE);
            g.drawString(StringUtil.concats("", "validatedby:", oicm.getActor(), " @", DateUtil.asMemo(oicm.getSystDate())),
                    10,
                    480);

            BufferedImage idtfr00Img = ImageIO.read(new File(VERIFIED_SCHEME_SRC));
            //default // g.drawImage(idtfr00Img, 170, 227, 160, 47, null);
            g.drawImage(idtfr00Img, 0, 227, 500, 72, null);
            //uncomply sample // g.drawImage(image3, 0, 297, 500, 22, null);
            g.dispose();

            String flsExt = FileUtil.getFileExtension(oi.getPrintFSL());
            String fsl = FileUtil.getDir(oi.getPrintFSL()) + oi.getId() + "_" + oicm.getId() + "." + flsExt;
            oicm.setPrintFSL(fsl);
            System.out.println("comply=" + oicm.getPrintFSL());
            ImageIO.write(baseImg, flsExt, new File(FileUtil.getAbsDir(fsl)));

            repoICM.save(oicm);
            rsp.put("fsl", oicm.getPrintFSL());
            rsp.put("imgPblcURI", ApiController.PREFIX_PUBLIC + "/undh/incomecmutasi/img?id=" + StringUtil.asURL(ChiperUtil.encrypt(String.valueOf(in))) + "&actkey=" + StringUtil.asURL(ChiperUtil.encrypt(DateUtil.getChiperKey())));
            System.out.println("complyimg=" + oicm.getPrintFSL());

        } else {
            rsp.put("error", ".img income is not exist!");
        }
        return rsp;
    }

    @Transactional
    public Map<String, Object> uncomply(IncomeComplianceMutasi in, String memo, String actor) {
        Map<String, Object> rsp = new HashMap<>();

        if (in.getId() != null) {
            if (in.getIncomeId() != null) {
                Income oi = repoI.findOne(in.getIncomeId());
                if (oi.getMutasiId() != null) {
                    Mutasi om = repoM.findOne(oi.getMutasiId());
                    om.setSystMemo("uncomplied,caused " + memo + ",by=" + actor);
                    om.setStage(Stage.MUTASI._UNPAIRED);
                    om.setIncomeId(Stage._NULLIFIED);
                    repoM.save(om);

                    oi.setSystMemo("uncomplied,caused " + memo + ",by=" + actor);
                    oi.setStage(Stage.INCOME._UNPAIRED);
                    oi.setMutasiId(Stage._NULLIFIED);
                    repoI.save(oi);

                    in.setMemo("uncomplied,caused " + memo + ",by=" + actor);
                    in.setStage(Stage.INCOMECOMPLIANCEMUTASI._UNCOMPLIED);
                    in.setMutasiId(Stage._NULLIFIED);
                    in.setIncomeId(Stage._NULLIFIED);
                    repoICM.save(in);
                    
                    rsp.put("msg", "uncomply successfully executed");
                    rsp.put("code", HttpStatus.OK);
                } else {
                    rsp.put("msg", "Mutasi is not exist");
                    rsp.put("code", HttpStatus.NOT_FOUND);
                }
            } else {
                rsp.put("msg", "Income is not exist");
                rsp.put("code", HttpStatus.NOT_FOUND);
            }

        } else {
            rsp.put("msg", "IncomeComplianceMutasi is not exist");
            rsp.put("code", HttpStatus.NOT_FOUND);
        }
        return rsp;
    }

    @Transactional
    public Map uncomplyImg(Long in, String memo, String actor) throws IOException {

        Map<String, Object> rsp = new HashMap<>();
        IncomeComplianceMutasi oicm = repoICM.findOne(in);
        if (!StringUtil.isNullOrBlank(oicm.getPrintFSL()) && FileUtil.isExist(FileUtil.getAbsDir(oicm.getPrintFSL()))) {

            //Get img comply
            String baseLImg = FileUtil.getAbsDir(oicm.getPrintFSL());
            System.out.println("baseLImg=" + baseLImg);
            BufferedImage baseImg = ImageIO.read(new File(baseLImg));

            Graphics g = baseImg.getGraphics();
            g.setFont(g.getFont().deriveFont(16f));
            g.setColor(Color.RED);

            int wrapLength = 70;
            String[] temps = StringUtil.textWrap(wrapLength, StringUtil.concats(VERIFIER_DELIMITER, memo + " by " + actor + " @ " + DateUtil.getMemoTNow()));

            int x = 10, y = 420;
            for (String t : temps) {
                g.drawString(t, x, y);
                y += 20;
            }

            BufferedImage idtfr00Img = ImageIO.read(new File(VOID_SCHEME_SRC));
            //default // g.drawImage(idtfr00Img, 170, 227, 160, 47, null);
            g.drawImage(idtfr00Img, 0, 227, 500, 72, null);
            //uncomply sample // g.drawImage(image3, 0, 297, 500, 22, null);
            g.dispose();

            String flsExt = FileUtil.getFileExtension(oicm.getPrintFSL());
            System.out.println("comply=" + oicm.getPrintFSL());
            ImageIO.write(baseImg, flsExt, new File(FileUtil.getAbsDir(oicm.getPrintFSL())));

            repoICM.save(oicm);
            rsp.put("fsl", oicm.getPrintFSL());
            rsp.put("imgPblcURI", ApiController.PREFIX_PUBLIC + "/undh/incomecmutasi/img?id=" + StringUtil.asURL(ChiperUtil.encrypt(String.valueOf(in))) + "&actkey=" + StringUtil.asURL(ChiperUtil.encrypt(DateUtil.getChiperKey())));
            System.out.println("complyimg=" + oicm.getPrintFSL());

        } else {
            rsp.put("msg", ".img IncomeComplianceMutasi is not exist!");
            rsp.put("code", HttpStatus.NOT_FOUND);
        }
        return rsp;
    }

}
