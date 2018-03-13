/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sjr.kimosyst.dao;

import com.sjr.kimosyst.model.util.Referenz;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author salirajr
 */
public interface ReferenzRepository extends CrudRepository<Referenz, Long> {

    List<Referenz> findByGruppeAndScope(String group, String scope);

    @Query("from Referenz r where r.gruppe = :group and r.scope = :scope and (r.rname like %:rqName% or r.rvalue like %:rqName%)")
    List<Referenz> findByGruppeAndScopeAndName(@Param("group") String group, @Param("scope") String scope, @Param("rqName") String rqName);
    
    
    Referenz findByGruppeAndScopeAndRname(String group, String scope, String rname);
    
    @Query("select r.gruppe, r.scope from Referenz r where r.scope not in ('SYST_ADMNSTR') group by r.gruppe, r.scope")
    List<Referenz> findSystGruppeAndScope(); 

}
