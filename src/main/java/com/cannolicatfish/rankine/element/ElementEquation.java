package com.cannolicatfish.rankine.element;


import com.mojang.serialization.Codec;

import java.util.List;

public class ElementEquation {

    public static final Codec<List<Integer>> INTEGER_LIST_CODEC = Codec.INT.listOf();
    public static final Codec<List<Float>> FLOAT_LIST_CODEC = Codec.FLOAT.listOf();
   /* public static final Codec<ElementEquation> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    INTEGER_LIST_CODEC.fieldOf("breaks").forGetter(l -> l.breaks),
                    Codec.INT.optionalFieldOf("color").forGetter(l -> Optional.of(Optional.of(l.num).orElse(16777215))),
                    RESOURCE_LOCATION_LIST_CODEC.fieldOf("items").forGetter(l -> l.items),
                    INTEGER_LIST_CODEC.fieldOf("materialValues").forGetter(l -> l.values),
                    Codec.INT.optionalFieldOf("minfloors").forGetter(l -> l.minFloors == -1 ? Optional.<Integer>empty() : Optional.of(l.minFloors)),
                    Codec.INT.optionalFieldOf("maxcellars").forGetter(l -> l.maxCellars == -1 ? Optional.<Integer>empty() : Optional.of(l.maxCellars)),
                    Codec.INT.optionalFieldOf("maxfloors").forGetter(l -> l.maxFloors == -1 ? Optional.<Integer>empty() : Optional.of(l.maxFloors)),
                    Codec.BOOL.optionalFieldOf("allowDoors").forGetter(l -> Optional.ofNullable(l.getAllowDoors())),
                    Codec.BOOL.optionalFieldOf("allowFillers").forGetter(l -> Optional.ofNullable(l.getAllowFillers())),
                    Codec.FLOAT.optionalFieldOf("preferslonely").forGetter(l -> l.prefersLonely == 0 ? Optional.<Float>empty() : Optional.of(l.prefersLonely)),
                    Codec.STRING.optionalFieldOf("enchantments").forGetter(l -> Optional.ofNullable(l.parts2)),
                    Codec.list(PartRef.CODEC).optionalFieldOf("enchantmentTypes").forGetter(l -> Optional.ofNullable(l.parts2)),
                    Codec.list(PartRef.CODEC).optionalFieldOf("enchantmentFactors").forGetter(l -> Optional.ofNullable(l.parts2))
            ).apply(instance, ElementEquation::new));*/
    List<Integer> breaks;
    FormulaType[] formulaTypes;
    FormulaModifier[] formulaModifiers;
    float[] a;
    float[] b;
    float[] limit;
    public ElementEquation(List<Integer> breaksIn, FormulaType[] formulaTypesIn, float[] aIn, float[] bIn, FormulaModifier[] formulaModifiersIn, float[] limitIn) {
        this.breaks = breaksIn;
        this.formulaTypes = formulaTypesIn;
        this.a = aIn;
        this.b = bIn;
        this.formulaModifiers = formulaModifiersIn;
        this.limit = limitIn;
    }

    public ElementEquation() {
        this(null,null,null,null,null,null);
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
        float percent = x;
        if (formulaModifiers[index].equals(FormulaModifier.MULTIPLIER)) {
            percent = x * limit[index];
        } else if (formulaModifiers[index].equals(FormulaModifier.ADDITION)) {
            percent = x + limit[index];
        }
        int modulo = 2;
        switch (formulaTypes[index]) {
            case LINEAR:
            default:
                return a[index] * percent + b[index];
            case POWER:
                return (float) (Math.pow(percent,a[index]) + b[index]);
            case EXPONENTIAL:
                return (float) (Math.pow(a[index],percent) + b[index]);
            case LOGARITHMIC:
                return (float) (a[index]*Math.log(percent) + b[index]);
            case LOG10:
                return (float) (a[index]*Math.log10(percent) + b[index]);
            case QUADRATIC:
                return (float) (a[index]*Math.pow(percent,2) + b[index]*percent);
            case SIN:
                return (float) (a[index]*Math.sin(b[index]*percent));
            case COS:
                return (float) (a[index]*Math.cos(b[index]*percent));
            case ALTERNATING:
                if (formulaModifiers[index].equals(FormulaModifier.ALTERNATING_MODULO)) {
                    modulo = (int) limit[index];
                }
                if (x % modulo == 0) {
                    return a[index] * percent;
                } else {
                    return b[index] * percent;
                }
            case CONSTANT:
                return (a[index]);
            case CONSTANT_ALTERNATING:
                if (formulaModifiers[index].equals(FormulaModifier.ALTERNATING_MODULO)) {
                    modulo = (int) limit[index];
                }
                if (x % modulo == 0) {
                    return a[index];
                } else {
                    return b[index];
                }
        }

    }

    public boolean isEmpty() {
        return breaks.size() == 0;
    }

    public List<Integer> getBreaks() {
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
        LOG10,
        QUADRATIC,
        SIN,
        COS,
        ALTERNATING,
        CONSTANT,
        CONSTANT_ALTERNATING

    }

    public enum FormulaModifier {
        ABSOLUTE_VALUE,
        FLOOR,
        CEILING,
        MAX,
        MIN,
        ADDITION,
        MULTIPLIER,
        ALTERNATING_MODULO,
        NONE
    }
}
