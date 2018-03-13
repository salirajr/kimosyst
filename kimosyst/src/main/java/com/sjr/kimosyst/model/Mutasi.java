/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sjr.kimosyst.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Data;

/**
 *
 * @author JOVIR
 */
@Entity
@Data
@Table(name = Mutasi.TABLE_NAME, uniqueConstraints = {
    @UniqueConstraint(columnNames = {"memo", "tgl", "kredit", "debit"})
},
        indexes = {
            @Index(name = Mutasi.TABLE_NAME + "_sbssnId", columnList = "sbssnId"),
            @Index(name = Mutasi.TABLE_NAME + "_tgl", columnList = "tgl"),
            @Index(name = Mutasi.TABLE_NAME + "_kredit", columnList = "kredit"),
            @Index(name = Mutasi.TABLE_NAME + "_debit", columnList = "debit"),
            @Index(name = Mutasi.TABLE_NAME + "_rekAccount", columnList = "rekAccount"),
            @Index(name = Mutasi.TABLE_NAME + "_systDate", columnList = "systDate"),
            @Index(name = Mutasi.TABLE_NAME + "_incomeId", columnList = "incomeId")})
public class Mutasi implements Serializable {

    public static final String TABLE_NAME = "mutasi";
    
    

    @Column(nullable = false)
    private Long sbssnId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date tgl;

    @Column(nullable = false)
    private double kredit;

    @Column(nullable = false)
    private double debit;
    
    @Column(nullable = false, length = 1)
    private double stage;

    @Column(nullable = true)
    private String rekAccount;

    /* Free text from mutasi*/
    @Column(nullable = true)
    private String memo;

    @Column(nullable = true)
    private String actor;

    @Column(nullable = false)
    private Timestamp systDate;

    @Column(nullable = true)
    private String systMemo;

    @Column(nullable = true)
    private Long incomeId;
}
