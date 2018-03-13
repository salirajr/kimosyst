/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sjr.kimosyst.dao;

import com.sjr.kimosyst.model.IncomeComplianceMutasi;
import java.sql.Date;
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
public interface IncomeComplianceMutasiRepository extends CrudRepository<IncomeComplianceMutasi, Long> {

    @Query("select a.id as complianceId, b.id as incomeId, c.id as mutasiId, "
            + "c.sbssnId as sbssnRefId, c.tgl as mutasiTgl, c.kredit as mutasiKredit, c.stage as mutasiStage, c.rekAccount as mutasiRekAccount, c.memo as mutasiMemo, c.actor as mutasiActor, c.systDate as mutasiSysDate, c.systMemo as mutasiSystMemo,"
            + "b.type as incomeType, b.trxDate as incomeTrxDate, b.nominal as incomeNominal, b.memo as incomeMemo, b.rekAccount as incomeRekAccount, b.bank as incomeBank, b.noteType as incomeNoteType, b.stage as incomeStage, b.actor as incomeActor, b.systDate as incomeSystDate, b.systMemo as incomeSystMemo,"
            + "a.memo as complianceMemo, a.weight as complianceWeight, a.systDate as complianceSystDate, a.stage as complianceStage "
            + "from IncomeComplianceMutasi a, Income b, Mutasi c where a.incomeId = b.id and a.mutasiId = c.id "
            + "and a.systDate between :start and :end and b.type like %:incomeType% and b.rekAccount like %:rekeningNo% and b.bank like %:bank% and b.noteType like %:buktiTransfer% "
            + "order by a.systDate desc")
    List<Map> retVwOnCreation(@Param("start") Timestamp start, @Param("end") Timestamp end,
            @Param("incomeType") String incomeType, @Param("rekeningNo") String rekeningNo, @Param("bank") String bank,
            @Param("buktiTransfer") String buktiTransfer);

    @Query("select a.id as complianceId, b.id as incomeId, c.id as mutasiId, "
            + "c.sbssnId as sbssnRefId, c.tgl as mutasiTgl, c.kredit as mutasiKredit, c.stage as mutasiStage, c.rekAccount as mutasiRekAccount, c.memo as mutasiMemo, c.actor as mutasiActor, c.systDate as mutasiSysDate, c.systMemo as mutasiSystMemo,"
            + "b.type as incomeType, b.trxDate as incomeTrxDate, b.nominal as incomeNominal, b.memo as incomeMemo, b.rekAccount as incomeRekAccount, b.bank as incomeBank, b.noteType as incomeNoteType, b.stage as incomeStage, b.actor as incomeActor, b.systDate as incomeSystDate, b.systMemo as incomeSystMemo,"
            + "a.memo as complianceMemo, a.weight as complianceWeight, a.systDate as complianceSystDate, a.stage as complianceStage "
            + "from IncomeComplianceMutasi a, Income b, Mutasi c where a.incomeId = b.id and a.mutasiId = c.id "
            + "and a.systDate between :start and :end and b.type like %:incomeType% and b.rekAccount like %:rekeningNo% and b.bank like %:bank% and b.noteType like %:buktiTransfer% and b.nominal = :amount "
            + "order by a.systDate desc")
    List<Map> retVwOnCreationWithAmount(@Param("start") Timestamp start, @Param("end") Timestamp end,
            @Param("incomeType") String incomeType, @Param("rekeningNo") String rekeningNo, @Param("bank") String bank,
            @Param("buktiTransfer") String buktiTransfer, @Param("amount") Double amount);

    IncomeComplianceMutasi findByIncomeId(Long incomeId);

}
