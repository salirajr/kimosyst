/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sjr.kimosyst.model.util;

/**
 *
 * @author JOVIR
 */
public class Stage {

    public static class MUTASI {

        public static double _NEW = 0.0;
        public static double _PAIRED = 9.9;
        public static double _UNPAIRED = 5.0;
        public static double _CORRUPT = -9.9;

        /**
         * 
         * @param isCalcMax is required just for calculate maximum weight, value for income.tweight
         * @param stage
         * @return 
         */
        public static double val(boolean isCalcMax, double stage) {
            double weight = 0;
            if (isCalcMax || stage == Stage.MUTASI._NEW) {
                weight += 10;
            } else if (stage == Stage.MUTASI._UNPAIRED) {
                weight += 5;
            } else if (stage == Stage.MUTASI._PAIRED) {
                weight += 0;
            } else if (stage == Stage.MUTASI._CORRUPT) {
                weight -= 30;
            }
            return weight;

        }
    }

    public static class INCOME {

        public static double _NEW = 0.0;
        public static double _PAIRED = 9.9;
        public static double _UNPAIRED = 5.0;
        public static double _DELETED = -5.0;

        /**
         * 
         * @param isCalcMax is required just for calculate maximum weight, value for income.tweight
         * @param stage
         * @return 
         */
        public static double val(boolean isCalcMax, double stage) {
            double weight = 0;
            if (isCalcMax || stage == Stage.INCOME._NEW) {
                weight += 10;
            } else if (stage == Stage.INCOME._UNPAIRED) {
                weight += 5;
            } else if (stage == Stage.INCOME._PAIRED) {
                weight += 0;
            } else if (stage == Stage.INCOME._DELETED) {
                weight -= 30;
            }
            return weight;

        }
    }
}
