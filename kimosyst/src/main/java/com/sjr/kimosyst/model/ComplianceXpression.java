/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sjr.kimosyst.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Data;

/**
 *
 * @author JOVIR
 */
@Entity
@Data
@Table(name = ComplianceXpression.TABLE_NAME, uniqueConstraints = {
    @UniqueConstraint(columnNames = {"keyV", "bank", "noteType"})})
public class ComplianceXpression implements Serializable {

    public static final String TABLE_NAME = "cce_xpression";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String xpress;

    @Column(nullable = false)
    private int i;

    @Column(nullable = false)
    private String keyV;

    @Column(nullable = false)
    private String bank;
    
    @Column(nullable = true)
    private String memo;

    @Column(nullable = false)
    private double weight;
    
    @Column(nullable = true)
    private String noteType;

}
