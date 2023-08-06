package com.cannolicatfish.rankine.blocks.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;

public class RankineCropsBlock extends CropBlock {

    private final float[] temperatureScore;
    private final float[] moistureScore;
    private final float[] soilScore;
    public RankineCropsBlock(Properties builder) {
        super(builder);
        this.temperatureScore = new float[]{1,1,1,1,1};
        this.moistureScore = new float[]{1,1,1};
        this.soilScore = new float[]{1,1,1};
    }

    public RankineCropsBlock(Properties builder, float[] temperatureScoreIn, float[] moistureScoreIn, float[] soilScoreIn) {
        super(builder);
        this.temperatureScore = temperatureScoreIn;
        this.moistureScore = moistureScoreIn;
        this.soilScore = soilScoreIn;
    }

    public void placeAt(LevelAccessor worldIn, BlockPos pos, int flags) {
        worldIn.setBlock(pos, this.defaultBlockState().setValue(AGE, 7), flags);
    }

    @Override
    public void randomTick(BlockState p_52292_, ServerLevel p_52293_, BlockPos p_52294_, RandomSource p_52295_) {
        if (!p_52293_.isAreaLoaded(p_52294_, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
        if (p_52293_.getRawBrightness(p_52294_, 0) >= 9) {
            int i = this.getAge(p_52292_);
            if (i < this.getMaxAge()) {
                float f = getGrowthSpeed(this, p_52293_, p_52294_);
                float s = this.calcTotalCropScore(p_52293_,p_52294_);
                if (s >= 1) {
                    for (int a = 0; a < s; a++) {
                        if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(p_52293_, p_52294_, p_52292_, p_52295_.nextInt((int)(25.0F / f) + 1) == 0)) {
                            p_52293_.setBlock(p_52294_, this.getStateForAge(i + 1), 2);
                            net.minecraftforge.common.ForgeHooks.onCropsGrowPost(p_52293_, p_52294_, p_52292_);
                        }
                    }
                } else if ((1 - s) >= p_52295_.nextFloat()) {
                    if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(p_52293_, p_52294_, p_52292_, p_52295_.nextInt((int)(25.0F / f) + 1) == 0)) {
                        p_52293_.setBlock(p_52294_, this.getStateForAge(i + 1), 2);
                        net.minecraftforge.common.ForgeHooks.onCropsGrowPost(p_52293_, p_52294_, p_52292_);
                    }
                }

            }
        }

    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        if (state.getValue(AGE) == 7) {
            return worldIn.getBlockState(pos.below()).is(BlockTags.DIRT) || super.canSurvive(state, worldIn, pos);
        }
        return super.canSurvive(state, worldIn, pos);
    }

    public float calcTotalCropScore(LevelReader worldIn, BlockPos pos) {
        return Mth.floor(calcTemperatureScore(worldIn,pos) + calcMoistureScore(worldIn,pos) + calcSoilScore(worldIn,pos)/3);
    }

    // Temperature for crops is scored by an int[5] array; [Freezing, Cold, Temperate, Warm, Scorching]
    public float calcTemperatureScore(LevelReader worldIn, BlockPos pos) {
        float temp = Mth.clamp(worldIn.getBiome(pos).value().getBaseTemperature(),0,2);
        for (int i = 0; i < 5; i++) {
            if ((i+1) * 0.4f >= temp) {
                return this.temperatureScore[i];
            }
        }
        return this.temperatureScore[4];
    }

    // Temperature for crops is scored by an int[3] array; [Dry, Moderate, Wet]
    public float calcMoistureScore(LevelReader worldIn, BlockPos pos) {
        float humidity = worldIn.getBiome(pos).value().modifiableBiomeInfo().get().climateSettings().downfall();
        if (humidity < 0.25f) {
            return this.moistureScore[0];
        } else if (humidity < 0.75f) {
            return this.moistureScore[1];
        } else {
            return this.moistureScore[2];
        }
    }

    // Soil type for crops is scored by an int[3] array; [Sand, Silt, Clay]
    public float calcSoilScore(LevelReader worldIn, BlockPos pos) {
        /*
        if (worldIn.getBlockState(pos.below()).getBlock() instanceof TilledSoilBlock && worldIn.getBlockState(pos.below()).hasProperty(TilledSoilBlock.SOIL_TYPE)) {
            switch (worldIn.getBlockState(pos.below()).getValue(TilledSoilBlock.SOIL_TYPE)) {
                case DIRT, SOUL_SOIL -> { return 1; }
                case HUMUS -> { return 2; }
                case LOAM -> { return 0.4f * this.soilScore[0]
                        + 0.4f * this.soilScore[1]
                        + 0.2f * this.soilScore[2]; }
                case CLAY_LOAM -> { return 0.35f * this.soilScore[0]
                        + 0.35f * this.soilScore[1]
                        + 0.35f * this.soilScore[2]; }
                case LOAMY_SAND -> { return 0.85f * this.soilScore[0]
                        + 0.075f * this.soilScore[1]
                        + 0.075f * this.soilScore[2]; }
                case SANDY_CLAY -> { return 0.55f * this.soilScore[0]
                        + 0.1f * this.soilScore[1]
                        + 0.45f * this.soilScore[2]; }
                case SANDY_LOAM -> { return 0.665f * this.soilScore[0]
                        + 0.235f * this.soilScore[1]
                        + 0.1f * this.soilScore[2]; }
                case SILTY_CLAY -> { return 0.1f * this.soilScore[0]
                        + 0.45f * this.soilScore[1]
                        + 0.45f * this.soilScore[2]; }
                case SILTY_LOAM -> { return 0.1f * this.soilScore[0]
                        + 0.55f * this.soilScore[1]
                        + 0.35f * this.soilScore[2]; }
                case SANDY_CLAY_LOAM -> { return 0.6f * this.soilScore[0]
                        + 0.1f * this.soilScore[1]
                        + 0.3f * this.soilScore[2]; }
                case SILTY_CLAY_LOAM -> { return 0.1f * this.soilScore[0]
                        + 0.35f * this.soilScore[1]
                        + 0.55f * this.soilScore[2]; }
            }
        }

         */
        return 1;

    }
}
