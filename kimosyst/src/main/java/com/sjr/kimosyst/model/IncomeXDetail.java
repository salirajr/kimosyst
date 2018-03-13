/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sjr.kimosyst.model;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author JOVIR
 */
@Entity
@Data
@Table(name = IncomeXDetail.TABLE_NAME)
public class IncomeXDetail implements Serializable {

    public static final String TABLE_NAME = "income_xdetail";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long incomeId;

    @Column(nullable = false)
    private Long xpressId;

    @Column(nullable = false)
    private String pname;
    
    @Column(nullable = false)
    private String pvalue;

    @Column(nullable = false)
    private Timestamp systDate;

    @Column(nullable = false)
    private String systMemo;

    

}
