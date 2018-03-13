/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sjr.kimosyst.dao;

import com.sjr.kimosyst.model.Mutasi;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import javax.persistence.OrderBy;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author salirajr
 */
public interface MutasiRepository extends CrudRepository<Mutasi, Long> {

    List<Mutasi> findBySbssnId(Long sbssnId);

    @Query("from Mutasi m where m.stage <= 1 and m.tgl = :tgl and m.rekAccount = :rekAccount and m.kredit = :kredit  order by m.stage, m.tgl desc")
    List<Mutasi> retCluster1_0(@Param("tgl") Date tgl, @Param("rekAccount") String rekAccount, @Param("kredit") double kredit);

    @Query("from Mutasi m where m.stage <= 1 and m.rekAccount = :rekAccount and m.kredit = :kredit order by m.stage desc, m.tgl desc")
    List<Mutasi> retCluster2_0(@Param("rekAccount") String rekAccount, @Param("kredit") double kredit);

    @Query("from Mutasi m where m.rekAccount = :rekAccount and m.kredit = :kredit order by m.stage, m.tgl desc")
    List<Mutasi> retCluster2_1(@Param("rekAccount") String rekAccount, @Param("kredit") double kredit);

    @Query("select a.id as mutasiId, a.tgl as mutasiTgl, a.kredit as mutasiKredit, a.stage as mutasiStage, a.rekAccount as mutasiRekAccount, a.memo as mutasiMemmo, a.actor as mutasiActor, a.systDate as mutasiSystDate, a.systMemo as mutasiSystMemo, a.actor as mutasiActor, a.incomeId as mutasiIncomeId, a.sbssnId as mutasiSbssnId "
            + "from Mutasi a "
            + "where a.kredit != 0 and a.debit = 0 and a.stage between :gtStage and :ltStage and a.systDate between :start and :end and a.rekAccount like %:rekeningNo% "
            + "order by a.systDate desc ")
    List<Map> retVwOnCreation(@Param("start") Timestamp start, @Param("end") Timestamp end,
            @Param("rekeningNo") String rekeningNo, @Param("gtStage") Double gtStage,@Param("ltStage") Double ltStage);

    @Query("select a.id as mutasiId, a.tgl as mutasiTgl, a.kredit as mutasiKredit, a.stage as mutasiStage, a.rekAccount as mutasiRekAccount, a.memo as mutasiMemmo, a.actor as mutasiActor, a.systDate as mutasiSystDate, a.systMemo as mutasiSystMemo, a.actor as mutasiActor, a.incomeId as mutasiIncomeId, a.sbssnId as mutasiSbssnId "
            + "from Mutasi a "
            + "where a.kredit != 0 and a.debit = 0 and a.stage between :gtStage and :ltStage and a.systDate between :start and :end and a.rekAccount like %:rekeningNo% and a.sbssnId = :submissionId "
            + "order by a.systDate desc ")
    List<Map> retVwOnCreation(@Param("start") Timestamp start, @Param("end") Timestamp end,
            @Param("rekeningNo") String rekeningNo, @Param("submissionId") Long submissionId, @Param("gtStage") Double gtStage,@Param("ltStage") Double ltStage);

}
