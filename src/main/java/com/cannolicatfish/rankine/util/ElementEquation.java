package com.cannolicatfish.rankine.util;


public class ElementEquation {

    int[] breaks;
    FormulaType[] formulaTypes;
    FormulaModifier[] formulaModifiers;
    float[] a;
    float[] b;
    float[] limit;
    public ElementEquation(int[] breaksIn, FormulaType[] formulaTypesIn, float[] aIn, float[] bIn, FormulaModifier[] formulaModifiersIn, float[] limitIn) {
        this.breaks = breaksIn;
        this.formulaTypes = formulaTypesIn;
        this.a = aIn;
        this.b = bIn;
        this.formulaModifiers = formulaModifiersIn;
        this.limit = limitIn;
    }

    public ElementEquation() {
        this(new int[]{},new FormulaType[]{},new float[]{},new float[]{},new FormulaModifier[]{},new float[]{});
    }

    public float calculateFloat(int x) {
        int index = 0;
        for (int br : breaks) {
            if (x <= br) {
                return applyFormulaModifier(constructFormula(x,index),index);
            }
            index += 1;
        }
        return 0;
    }


    public int calculateRounded(int x) {
        int index = 0;
        for (int br : breaks) {
            if (x <= br) {
                return Math.round(applyFormulaModifier(constructFormula(x,index),index));
            }
            index += 1;
        }
        return 0;
    }

    public float applyFormulaModifier(float output, int index) {
        switch (formulaModifiers[index]) {
            case ABSOLUTE_VALUE:
                return Math.abs(output);
            case CEILING:
                return (float) Math.ceil(output);
            case FLOOR:
                return (float) Math.floor(output);
            case MAX:
                return Math.max(output,limit[index]);
            case MIN:
                return Math.min(output,limit[index]);
            default:
            case NONE:
                return output;
        }
    }

    public float constructFormula(int x, int index) {
        switch (formulaTypes[index]) {
            case LINEAR:
            default:
                return a[index] * x + b[index];
            case POWER:
                return (float) (Math.pow(x,a[index]) + b[index]);
            case EXPONENTIAL:
                return (float) (Math.pow(a[index],x) + b[index]);
            case LOGARITHMIC:
                return (float) (a[index]*Math.log(x) + b[index]);
            case CONSTANT:
                return (a[index]);
        }

    }

    public boolean isEmpty() {
        return breaks.length == 0;
    }

    public int[] getBreaks() {
        return breaks;
    }

    public FormulaType[] getFormulaTypes() {
        return formulaTypes;
    }

    public float[] getA() {
        return a;
    }

    public float[] getB() {
        return b;
    }

    public FormulaModifier[] getFormulaModifiers() {
        return formulaModifiers;
    }

    public float[] getLimit() {
        return limit;
    }

    public enum FormulaType {
        LINEAR,
        POWER,
        EXPONENTIAL,
        LOGARITHMIC,
        CONSTANT

    }

    public enum FormulaModifier {
        ABSOLUTE_VALUE,
        FLOOR,
        CEILING,
        MAX,
        MIN,
        NONE
    }
}
