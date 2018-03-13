/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sjr.kimosyst.dao;

import com.sjr.kimosyst.model.IncomeXDetail;
import com.sjr.kimosyst.model.Mutasi;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author salirajr
 */
public interface IncomeXDetailRepository extends CrudRepository<IncomeXDetail, Long> {

    public List<IncomeXDetail> findByIncomeId(Long incomeId);
    
    @Query("select d.pname as name, d.pvalue as value from IncomeXDetail d where d.incomeId = :incomeId")
    List<Map> retVwByIncomeId(@Param("incomeId") Long incomeId);
}

