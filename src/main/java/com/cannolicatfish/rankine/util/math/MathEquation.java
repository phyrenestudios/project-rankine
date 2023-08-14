package com.cannolicatfish.rankine.util.math;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Mth;

import java.math.RoundingMode;
import java.util.Optional;

public class MathEquation {

    private static final Codec<FormulaType> FORMULA_TYPE_CODEC = Codec.STRING.comapFlatMap((s) -> {
        try {
            return DataResult.success(FormulaType.valueOf(s));
        } catch (IllegalArgumentException illegalargumentexception) {
            return DataResult.error(() -> "Invalid Formula Modifier " + s + ": " + illegalargumentexception.getMessage());
        }
    }, FormulaType::toString);
    private static final Codec<RoundingMode> ROUNDING_MODE_CODEC = Codec.STRING.comapFlatMap((s) -> {
        try {
            return DataResult.success(RoundingMode.valueOf(s));
        } catch (IllegalArgumentException illegalargumentexception) {
            return DataResult.success(RoundingMode.UNNECESSARY);
        }
    }, RoundingMode::toString);

    public static final Codec<MathEquation> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            FORMULA_TYPE_CODEC.fieldOf("type").forGetter(l -> l.formulaType),
            Codec.SHORT.optionalFieldOf("stopAt").forGetter(l -> Optional.of(l.endPercentage)),
            Codec.FLOAT.optionalFieldOf("a").forGetter(l -> Optional.of(l.varA)),
            Codec.FLOAT.optionalFieldOf("b").forGetter(l -> Optional.of(l.varB)),
            Codec.FLOAT.optionalFieldOf("c").forGetter(l -> Optional.of(l.varC)),
            Codec.FLOAT.optionalFieldOf("d").forGetter(l -> Optional.of(l.varD)),
            Codec.FLOAT.optionalFieldOf("min").forGetter(l -> Optional.of(l.minValue)),
            Codec.FLOAT.optionalFieldOf("max").forGetter(l -> Optional.of(l.maxValue)),
            Codec.BOOL.optionalFieldOf("min").forGetter(l -> Optional.of(l.absValue)),
            ROUNDING_MODE_CODEC.optionalFieldOf("rounding").forGetter(l -> Optional.of(l.roundingType))
    ).apply(instance, MathEquation::new));
    private final FormulaType formulaType;
    private final Short endPercentage;
    private final float varA;
    private final float varB;
    private final float varC;
    private final float varD;

    private final Float minValue;
    private final Float maxValue;
    private final Boolean absValue;
    private final RoundingMode roundingType;
    MathEquation(FormulaType typeIn, Optional<Short> endPercentageIn, Optional<Float> varAIn, Optional<Float> varBIn, Optional<Float> varCIn, Optional<Float> varDIn,
                 Optional<Float> minValueIn, Optional<Float> maxValueIn, Optional<Boolean> absValueIn, Optional<RoundingMode> roundingTypeIn) {
        this.formulaType = typeIn;
        this.endPercentage = endPercentageIn.orElse((short) 100);
        this.varA = varAIn.orElse(0f);
        this.varB = varBIn.orElse(0f);
        this.varC = varCIn.orElse(0f);
        this.varD = varDIn.orElse(0f);
        this.minValue = minValueIn.orElse(Float.MIN_VALUE);
        this.maxValue = maxValueIn.orElse(Float.MAX_VALUE);
        this.absValue = absValueIn.orElse(false);
        this.roundingType = roundingTypeIn.orElse(RoundingMode.UNNECESSARY);
    }

    public float calculateValue(short x) {
        switch (formulaType) {
            case CONSTANT -> {
                return getFinalValue(getVarC());
            }
            case LINEAR -> {
                return getFinalValue(getVarC() * x + getVarD());
            }
            case QUADRATIC -> {
                return getFinalValue((float) (Math.pow(getVarA() * x,3) + Math.pow(getVarB() * x,2) + getVarC() * x + getVarD()));
            }
            case EXPONENTIAL -> {
                return getFinalValue((float) (Math.pow(getVarA(),(getVarB() * x + getVarC())) + getVarD()));
            }
            case POWER -> {
                return getFinalValue((float) (Math.pow(getVarA() * x+getVarB(),(getVarC())) + getVarD()));
            }
            case LN -> {
                return getFinalValue((float) (getVarA() * Math.log(getVarB() * x + getVarC()) + getVarD()));
            }
            case LOG -> {
                return getFinalValue((float) (getVarA() * Math.log10(getVarB() * x + getVarC()) + getVarD()));
            }
            case SIN -> {
                return getFinalValue((float) (getVarA() * Math.sin(getVarB() * x + getVarC()) + getVarD()));
            }
            default -> {
                return 0;
            }
        }
    }

    public Short getEndPercentage() { return endPercentage; }
    Float getVarA() { return varA;}
    Float getVarB() { return varB;}
    Float getVarC() { return varC;}
    Float getVarD() { return varD;}
    Float getMin() { return minValue;}
    Float getMax() { return maxValue;}
    Boolean isAbsValue() { return absValue;}
    RoundingMode getRoundingType() { return roundingType;}

    float getBoundedValue(float value) {
        return Mth.clamp(value,getMin(),getMax());
    }

    float getFinalValue(float value) {
        if (isAbsValue()) {
            return Math.abs(Mth.clamp(getRoundedValue(value),getMin(),getMax()));
        } else {
            return Mth.clamp(getRoundedValue(value),getMin(),getMax());
        }

    }
    float getRoundedValue(float value) {
        switch (getRoundingType()){
            case CEILING -> {
                return (float) Math.ceil(value);
            }
            case FLOOR -> {
                return (float) Math.floor(value);
            }
            case UNNECESSARY -> {
                return value;
            }
            default -> {
                return (float) Math.round(value);
            }
        }
    }

    public enum FormulaType {
        LINEAR,
        POWER,
        EXPONENTIAL,
        LN,
        LOG,
        QUADRATIC,
        SIN,
        CONSTANT

    }
}
