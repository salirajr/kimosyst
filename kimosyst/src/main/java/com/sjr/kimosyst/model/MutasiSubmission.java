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
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Data;

/**
 *
 * @author JOVIR
 */
@Entity
@Data
@Table(name = MutasiSubmission.TABLE_NAME,
        indexes = {
            @Index(name = MutasiSubmission.TABLE_NAME + "_systdate", columnList = "systDate")})
public class MutasiSubmission implements Serializable {

    public static final String TABLE_NAME = "mutasi_sbssn";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
    private String headers;

    @Column(nullable = false, unique = true)
    private String fileSrc;
    
    // File Store Location
    @Column(nullable = true)
    private String rawFSL;

    @Transient
    private String content;

    @Column(nullable = false)
    private Long nRow;

    @Column(nullable = true)
    private String memo;

    @Column(nullable = true)
    private String actor;

    @Column(nullable = false)
    private Timestamp systDate;

}
