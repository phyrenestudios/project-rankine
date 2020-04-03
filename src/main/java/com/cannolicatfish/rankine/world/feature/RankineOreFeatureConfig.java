package com.cannolicatfish.rankine.world.feature;

import com.cannolicatfish.rankine.blocks.ModBlocks;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockMatcher;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RankineOreFeatureConfig implements IFeatureConfig {
    public final RankineOreFeatureConfig.RankineFillerBlockType target;
    public final int size;
    public final BlockState state;

    public RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType target, BlockState state, int size) {
        this.size = size;
        this.state = state;
        this.target = target;
    }

    public <T> Dynamic<T> serialize(DynamicOps<T> ops) {
        return new Dynamic<>(ops, ops.createMap(ImmutableMap.of(ops.createString("size"), ops.createInt(this.size), ops.createString("target"), ops.createString(this.target.func_214737_a()), ops.createString("state"), BlockState.serialize(ops, this.state).getValue())));
    }

    public static RankineOreFeatureConfig deserialize(Dynamic<?> p_214641_0_) {
        int i = p_214641_0_.get("size").asInt(0);
        RankineOreFeatureConfig.RankineFillerBlockType rankineorefeatureconfig$fillerblocktype = RankineOreFeatureConfig.RankineFillerBlockType.func_214736_a(p_214641_0_.get("target").asString(""));
        BlockState blockstate = p_214641_0_.get("state").map(BlockState::deserialize).orElse(Blocks.AIR.getDefaultState());
        return new RankineOreFeatureConfig(rankineorefeatureconfig$fillerblocktype, blockstate, i);
    }

    public static enum RankineFillerBlockType implements net.minecraftforge.common.IExtensibleEnum {
        ONEFOUR("one_four", (p_214739_0_) -> {
            if (p_214739_0_ == null) {
                return false;
            } else {
                Block block = p_214739_0_.getBlock();
                return block == ModBlocks.GRANITE || block == ModBlocks.DIORITE || block == ModBlocks.ANDESITE || block == ModBlocks.LIMESTONE;
            }
        }),
        ONEFIVE("one_five", (p_214739_0_) -> {
            if (p_214739_0_ == null) {
                return false;
            } else {
                Block block = p_214739_0_.getBlock();
                return block == ModBlocks.GRANITE || block == ModBlocks.DIORITE || block == ModBlocks.ANDESITE || block == ModBlocks.LIMESTONE || block == ModBlocks.BASALT;
            }
        }),
        LIMESTONE("limestone", new BlockMatcher(ModBlocks.LIMESTONE)),
        ONESIX("one_six", (p_214739_0_) -> {
            if (p_214739_0_ == null) {
                return false;
            } else {
                Block block = p_214739_0_.getBlock();
                return block == ModBlocks.GRANITE || block == ModBlocks.DIORITE || block == ModBlocks.ANDESITE || block == ModBlocks.LIMESTONE || block == ModBlocks.BASALT || block == ModBlocks.RHYOLITE;
            }
        }),
        NATIVE("native", (p_214739_0_) -> {
            if (p_214739_0_ == null) {
                return false;
            } else {
                Block block = p_214739_0_.getBlock();
                return block == ModBlocks.LIMESTONE || block == ModBlocks.SHALE || block == ModBlocks.RHYOLITE;
            }
        }),
        NATIVE_HIGH("native_high", (p_214739_0_) -> {
            if (p_214739_0_ == null) {
                return false;
            } else {
                Block block = p_214739_0_.getBlock();
                return block == ModBlocks.ANDESITE || block == ModBlocks.DIORITE || block == ModBlocks.RHYOLITE;
            }
        }),
        NATIVE_GOLD("gold", (p_214739_0_) -> {
            if (p_214739_0_ == null) {
                return false;
            } else {
                Block block = p_214739_0_.getBlock();
                return block == ModBlocks.GRANITE || block == ModBlocks.DIORITE || block == ModBlocks.ANDESITE || block == ModBlocks.LIMESTONE || block == ModBlocks.BASALT || block == ModBlocks.RHYOLITE || block == ModBlocks.SHALE;
            }
        }),
        SEVENEIGHT("seven_eight", (p_214739_0_) -> {
            if (p_214739_0_ == null) {
                return false;
            } else {
                Block block = p_214739_0_.getBlock();
                return block == ModBlocks.MARBLE || block == ModBlocks.GNEISS;
            }
        }),
        MANTLE("mantle", new BlockMatcher(ModBlocks.PERIDOTITE)),
        TENTWELVE("ten_twelve", (p_214739_0_) -> {
            if (p_214739_0_ == null) {
                return false;
            } else {
                Block block = p_214739_0_.getBlock();
                return block == ModBlocks.PERIDOTITE || block == ModBlocks.KOMATIITE;
            }
        }),
        ELEVEN("eleven", new BlockMatcher(ModBlocks.KIMBERLITE));;

        private static final Map<String, RankineOreFeatureConfig.RankineFillerBlockType> field_214741_c = Arrays.stream(values()).collect(Collectors.toMap(RankineOreFeatureConfig.RankineFillerBlockType::func_214737_a, (p_214740_0_) -> {
            return p_214740_0_;
        }));
        private final String field_214742_d;
        private final Predicate<BlockState> field_214743_e;

        private RankineFillerBlockType(String p_i50618_3_, Predicate<BlockState> p_i50618_4_) {
            this.field_214742_d = p_i50618_3_;
            this.field_214743_e = p_i50618_4_;
        }

        public String func_214737_a() {
            return this.field_214742_d;
        }

        public static RankineOreFeatureConfig.RankineFillerBlockType func_214736_a(String p_214736_0_) {
            return field_214741_c.get(p_214736_0_);
        }

        public Predicate<BlockState> func_214738_b() {
            return this.field_214743_e;
        }

        public static RankineOreFeatureConfig.RankineFillerBlockType create(String enumName, String p_i50618_3_, Predicate<BlockState> p_i50618_4_) {
            throw new IllegalStateException("Enum not extended");
        }

        @Override
        @Deprecated
        public void init() {
            field_214741_c.put(func_214737_a(), this);
        }
    }
}