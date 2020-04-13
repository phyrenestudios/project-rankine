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
        return new Dynamic<>(ops, ops.createMap(ImmutableMap.of(ops.createString("size"), ops.createInt(this.size), ops.createString("target"), ops.createString(this.target.returnName()), ops.createString("state"), BlockState.serialize(ops, this.state).getValue())));
    }

    public static RankineOreFeatureConfig deserialize(Dynamic<?> p_214641_0_) {
        int i = p_214641_0_.get("size").asInt(0);
        RankineOreFeatureConfig.RankineFillerBlockType rankineorefeatureconfig$fillerblocktype = RankineOreFeatureConfig.RankineFillerBlockType.targetString(p_214641_0_.get("target").asString(""));
        BlockState blockstate = p_214641_0_.get("state").map(BlockState::deserialize).orElse(Blocks.AIR.getDefaultState());
        return new RankineOreFeatureConfig(rankineorefeatureconfig$fillerblocktype, blockstate, i);
    }

    public static enum RankineFillerBlockType implements net.minecraftforge.common.IExtensibleEnum {
        OW_TOP_BASIC("ow_top_basic", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                Block block = blockstate.getBlock();
                return block == ModBlocks.GRANITE || block == ModBlocks.DIORITE || block == ModBlocks.ANDESITE || block == ModBlocks.LIMESTONE;
            }
        }),
        OW_TOP_BASIC_B("ow_top_basic_b", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                Block block = blockstate.getBlock();
                return block == ModBlocks.GRANITE || block == ModBlocks.DIORITE || block == ModBlocks.ANDESITE || block == ModBlocks.LIMESTONE || block == ModBlocks.BASALT;
            }
        }),
        LIMESTONE("limestone", new BlockMatcher(ModBlocks.LIMESTONE)),
        OW_TOP_NOSHALE("ow_top_noshale", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                Block block = blockstate.getBlock();
                return block == ModBlocks.GRANITE || block == ModBlocks.DIORITE || block == ModBlocks.ANDESITE || block == ModBlocks.LIMESTONE || block == ModBlocks.BASALT || block == ModBlocks.RHYOLITE;
            }
        }),
        OW_IGNEOUS("ow_igneous", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                Block block = blockstate.getBlock();
                return block == ModBlocks.GRANITE || block == ModBlocks.DIORITE || block == ModBlocks.ANDESITE || block == ModBlocks.BASALT || block == ModBlocks.RHYOLITE;
            }
        }),
        OW_MARBLE("ow_marble", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                Block block = blockstate.getBlock();
                return block == ModBlocks.GRANITE || block == ModBlocks.DIORITE || block == ModBlocks.ANDESITE || block == ModBlocks.BASALT || block == ModBlocks.RHYOLITE || block == ModBlocks.MARBLE;
            }
        }),
        OW_MARBLE_NOTOP("ow_marble_notop", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                Block block = blockstate.getBlock();
                return block == ModBlocks.GRANITE || block == ModBlocks.LIMESTONE || block == ModBlocks.BASALT || block == ModBlocks.RHYOLITE || block == ModBlocks.MARBLE;
            }
        }),
        NATIVE_LOW("native", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                Block block = blockstate.getBlock();
                return block == ModBlocks.LIMESTONE || block == ModBlocks.SHALE || block == ModBlocks.RHYOLITE;
            }
        }),
        NATIVE_HIGH("native_high", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                Block block = blockstate.getBlock();
                return block == ModBlocks.ANDESITE || block == ModBlocks.DIORITE || block == ModBlocks.RHYOLITE;
            }
        }),
        NATIVE_GOLD("gold", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                Block block = blockstate.getBlock();
                return block == ModBlocks.GRANITE || block == ModBlocks.DIORITE || block == ModBlocks.ANDESITE || block == ModBlocks.LIMESTONE || block == ModBlocks.BASALT || block == ModBlocks.RHYOLITE || block == ModBlocks.SHALE;
            }
        }),
        MARBLE_GNEISS("marble_gneiss", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                Block block = blockstate.getBlock();
                return block == ModBlocks.MARBLE || block == ModBlocks.GNEISS;
            }
        }),
        MARBLE("marble", new BlockMatcher(ModBlocks.MARBLE)),
        PERIDOTITE("peridotite", new BlockMatcher(ModBlocks.PERIDOTITE)),
        PERIDOT_KOMA("peridot_koma", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                Block block = blockstate.getBlock();
                return block == ModBlocks.PERIDOTITE || block == ModBlocks.KOMATIITE;
            }
        }),
        PERIDOT_WADS("peridot_wads", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                Block block = blockstate.getBlock();
                return block == ModBlocks.PERIDOTITE || block == ModBlocks.WADSLEYITE;
            }
        }),
        KOMATIITE("komatiite", new BlockMatcher(ModBlocks.KOMATIITE)),
        KIMBERLITE("kimberlite", new BlockMatcher(ModBlocks.KIMBERLITE));;

        private static final Map<String, RankineOreFeatureConfig.RankineFillerBlockType> map = Arrays.stream(values()).collect(Collectors.toMap(RankineOreFeatureConfig.RankineFillerBlockType::returnName, (getData) -> {
            return getData;
        }));
        private final String name;
        private final Predicate<BlockState> predicate;

        private RankineFillerBlockType(String name, Predicate<BlockState> predicate) {
            this.name = name;
            this.predicate = predicate;
        }

        public String returnName() {
            return this.name;
        }

        public static RankineOreFeatureConfig.RankineFillerBlockType targetString(String target) {
            return map.get(target);
        }

        public Predicate<BlockState> getPredicate() {
            return this.predicate;
        }

        public static RankineOreFeatureConfig.RankineFillerBlockType create(String enumName, String name, Predicate<BlockState> predicate) {
            throw new IllegalStateException("Enum not extended");
        }

        @Override
        @Deprecated
        public void init() {
            map.put(returnName(), this);
        }
    }
}