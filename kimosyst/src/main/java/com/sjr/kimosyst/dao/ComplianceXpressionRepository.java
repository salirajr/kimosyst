/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sjr.kimosyst.dao;

import com.sjr.kimosyst.model.ComplianceXpression;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author salirajr
 */
public interface ComplianceXpressionRepository extends CrudRepository<ComplianceXpression, Long> {

    public List<ComplianceXpression> findByBankAndNoteType(String bank, String noteType);

    @Query("select distinct cx.bank from ComplianceXpression cx")
    public List<String> retAllBank();
    
    
    @Query("select distinct cx.noteType from ComplianceXpression cx where cx.bank = ?1")
    public List<String> retAllNoteTypeByBank(String bank);
    
}
