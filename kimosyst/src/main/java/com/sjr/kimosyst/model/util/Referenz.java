/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sjr.kimosyst.model.util;

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
@Table(name = Referenz.TABLE_NAME, uniqueConstraints = {
    @UniqueConstraint(columnNames = {"gruppe", "rname", "scope"})
})
public class Referenz implements Serializable {

    public static final String TABLE_NAME = "referenz";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String gruppe;

    @Column(nullable = false)
    private String rname;

    @Column(nullable = true)
    private String rvalue;

    @Column(nullable = false)
    private String scope;

}
