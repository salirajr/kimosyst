/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sjr.kimosyst.model.util;

/**
 *
 * @author JOVIR
 *
 * 1 -> DELETE/REMOVE/ERASE 2 -> CORRUPT 5 -> NEW 6 -> AVAILABLE 9 ->
 * ALLOCATED/RESERVED
 *
 *
 */
public class Stage {
    
    public static long _NULLIFIED = -1;

    public static class MUTASI {

        public static double _NEW = 5.0;// required identificaiton
        public static double _PAIRED = 9.9;
        public static double _UNPAIRED = 6.1;// release income from mutasi
        public static double _CORRUPT = 2.1;// ?
        public static double _DELETED = 1.1;// delete by user

        /**
         *
         * @param isCalcMax is required just for calculate maximum weight, value
         * for income.tweight
         * @param stage
         * @return
         */
        public static double val(boolean isCalcMax, double stage) {
            double weight = 0;
            if (isCalcMax || stage == Stage.MUTASI._NEW) {
                weight += 10;
            } else if (stage == Stage.MUTASI._UNPAIRED) {
                weight += 10;
            } else if (stage == Stage.MUTASI._PAIRED) {
                weight -= 10;
            } else if (stage == Stage.MUTASI._CORRUPT) {
                weight -= 20;
            } else if (stage == Stage.MUTASI._DELETED) {
                weight -= 30;
            }
            return weight;

        }
    }

    public static class INCOME {

        public static double _NEW = 5.0;
        public static double _PAIRED = 9.9;
        public static double _UNPAIRED = 6.1;
        public static double _CORRUPT = 2.1;
        public static double _DELETED = 1.1;

        /**
         *
         * @param isCalcMax is required just for calculate maximum weight, value
         * for income.tweight
         * @param stage
         * @return
         */
        public static double val(boolean isCalcMax, double stage) {
            double weight = 0;
            if (isCalcMax || stage == Stage.INCOME._NEW) {
                weight += 10;
            } else if (stage == Stage.INCOME._PAIRED) {
                weight -= 10;
            } else if (stage == Stage.INCOME._UNPAIRED) {
                weight += 10;
            } else if (stage == Stage.INCOME._CORRUPT) {
                weight -= 20;
            } else if (stage == Stage.INCOME._DELETED) {
                weight -= 30;
            }
            return weight;

        }
    }

    public static class INCOMECOMPLIANCEMUTASI {

        public static double _PAIRED = 9.9;
        public static double _UNCOMPLIED = 1.2;

    }
}
