/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sjr.kimosyst.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
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
@Table(name = Income.TABLE_NAME)
public class Income implements Serializable {

    public static final String TABLE_NAME = "income";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date trxDate;

    @Column(nullable = true)
    private Time trxTime;

    @Column(nullable = false)
    private double nominal;

    @Transient
    private String printSrc;
    
    //File Store Location
    @Column(nullable = true)
    private String printFSL;
    

    @Column(nullable = false)
    private String memo;

    // generate to tgl-mutasi, folder auto generate
    @Column(nullable = false)
    private String rekAccount;

    @Column(nullable = false)
    private String bank;

    @Column(nullable = false)
    private String noteType;
    
    @Column(nullable = true)
    private String type;

    @Column(nullable = false)
    private double stage;

    @Column(nullable = false)
    private String actor;

    @Column(nullable = false)
    private Timestamp systDate;

    @Column(nullable = false)
    private String systMemo;

    @Column(nullable = true)
    private Long mutasiId;

    

}
