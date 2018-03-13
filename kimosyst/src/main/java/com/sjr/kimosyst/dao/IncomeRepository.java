/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sjr.kimosyst.dao;

import com.sjr.kimosyst.model.Income;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author salirajr
 */
public interface IncomeRepository extends CrudRepository<Income, Long> {

    @Query("select a.id as incomeId, a.type as incomeType, a.trxDate as incomeTrxDate, a.trxTime as incomeTrxTime, a.nominal as incomeNominal, a.memo as incomeMemo, a.rekAccount as incomeRekAccount, a.bank as incomeBank, a.noteType as incomeNoteType, a.stage as incomeStage, a.actor as incomeActor, a.systDate as incomeSystDate, a.systMemo as incomeSystMemo, a.mutasiId as incomeMutasiId "
            + "from Income a "
            + "where a.stage between :gtStage and :ltStage and a.systDate between :start and :end and a.type like %:incomeType% and a.rekAccount like %:rekeningNo% and a.bank like %:bank% and a.noteType like %:buktiTransfer% "
            + "order by a.systDate desc ")
    List<Map> retVwOnCreation(@Param("start") Timestamp start, @Param("end") Timestamp end,
            @Param("incomeType") String incomeType, @Param("rekeningNo") String rekeningNo, @Param("bank") String bank,
            @Param("buktiTransfer") String buktiTransfer, @Param("gtStage") Double gtStage, @Param("ltStage") Double ltStage);

}
