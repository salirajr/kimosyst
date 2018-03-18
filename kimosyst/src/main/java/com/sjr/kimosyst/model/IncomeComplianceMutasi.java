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
import javax.persistence.Transient;
import lombok.Data;

/**
 *
 * @author JOVIR
 */
@Entity
@Data
@Table(name = IncomeComplianceMutasi.TABLE_NAME)
public class IncomeComplianceMutasi implements Serializable {

    public static final String TABLE_NAME = "income_cce_mutasi";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long incomeId;

    @Column(nullable = false)
    private Long mutasiId;

    @Column(nullable = true)
    private String actor;

    @Column(nullable = true)
    private String memo;

    @Column(nullable = false)
    private double weight;

    @Column(nullable = false)
    private Timestamp systDate;

    @Column(nullable = false)
    private double stage;

    //File Store Location
    @Column(nullable = true)
    private String printFSL;

}
