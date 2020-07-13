package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.init.ModBlocks;
import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockMatcher;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RankineOreFeatureConfig implements IFeatureConfig {
    public static final Codec<RankineOreFeatureConfig> field_236566_a_ = RecordCodecBuilder.create((p_236568_0_) -> {
        return p_236568_0_.group(RankineOreFeatureConfig.RankineFillerBlockType.field_236571_d_.fieldOf("target").forGetter((p_236570_0_) -> {
            return p_236570_0_.target;
        }), BlockState.field_235877_b_.fieldOf("state").forGetter((p_236569_0_) -> {
            return p_236569_0_.state;
        }), Codec.INT.fieldOf("size").withDefault(0).forGetter((p_236567_0_) -> {
            return p_236567_0_.size;
        })).apply(p_236568_0_, RankineOreFeatureConfig::new);
    });
    public final RankineOreFeatureConfig.RankineFillerBlockType target;
    public final int size;
    public final BlockState state;

    public RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType target, BlockState state, int size) {
        this.size = size;
        this.state = state;
        this.target = target;
    }

    public static enum RankineFillerBlockType implements IStringSerializable, net.minecraftforge.common.IExtensibleEnum {
        OVERWORLD_STONES("overworld_stones", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                Block block = blockstate.getBlock();
                return block == ModBlocks.GRANITE || block == ModBlocks.ANDESITE || block == ModBlocks.DIORITE || block == ModBlocks.ANORTHOSITE || block == ModBlocks.LIMESTONE || block == ModBlocks.BASALT || block == ModBlocks.MARBLE || block == ModBlocks.GNEISS || block == ModBlocks.RHYOLITE || block == ModBlocks.SHALE;
            }
        }),
        NO_SHALE("no_shale", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                Block block = blockstate.getBlock();
                return block == ModBlocks.GRANITE || block == ModBlocks.ANDESITE || block == ModBlocks.DIORITE || block == ModBlocks.ANORTHOSITE || block == ModBlocks.LIMESTONE || block == ModBlocks.BASALT || block == ModBlocks.MARBLE || block == ModBlocks.GNEISS || block == ModBlocks.RHYOLITE;
            }
        }),
        OW_TOP_BASIC("ow_top_basic", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                Block block = blockstate.getBlock();
                return block == ModBlocks.GRANITE || block == ModBlocks.ANORTHOSITE || block == ModBlocks.DIORITE || block == ModBlocks.ANDESITE || block == ModBlocks.LIMESTONE;
            }
        }),
        OW_TOP_BASIC_B("ow_top_basic_b", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                Block block = blockstate.getBlock();
                return block == ModBlocks.GRANITE || block == ModBlocks.ANORTHOSITE || block == ModBlocks.DIORITE || block == ModBlocks.ANDESITE || block == ModBlocks.LIMESTONE || block == ModBlocks.BASALT;
            }
        }),
        LIMESTONE("limestone", new BlockMatcher(ModBlocks.LIMESTONE)),
        OW_TOP_NOSHALE("ow_top_noshale", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                Block block = blockstate.getBlock();
                return block == ModBlocks.GRANITE || block == ModBlocks.ANORTHOSITE || block == ModBlocks.DIORITE || block == ModBlocks.ANDESITE || block == ModBlocks.LIMESTONE || block == ModBlocks.BASALT || block == ModBlocks.RHYOLITE;
            }
        }),
        OW_IGNEOUS("ow_igneous", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                Block block = blockstate.getBlock();
                return block == ModBlocks.GRANITE || block == ModBlocks.ANORTHOSITE || block == ModBlocks.DIORITE || block == ModBlocks.ANDESITE || block == ModBlocks.BASALT || block == ModBlocks.RHYOLITE;
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
                return block == ModBlocks.GRANITE || block == ModBlocks.ANORTHOSITE || block == ModBlocks.LIMESTONE || block == ModBlocks.BASALT || block == ModBlocks.RHYOLITE || block == ModBlocks.MARBLE;
            }
        }),
        NATIVE_LOW("native", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                Block block = blockstate.getBlock();
                return block == ModBlocks.LIMESTONE || block == ModBlocks.ANORTHOSITE || block == ModBlocks.SHALE || block == ModBlocks.RHYOLITE;
            }
        }),
        NATIVE_HIGH("native_high", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                Block block = blockstate.getBlock();
                return block == ModBlocks.ANDESITE || block == ModBlocks.ANORTHOSITE || block == ModBlocks.DIORITE || block == ModBlocks.RHYOLITE;
            }
        }),
        NATIVE_GOLD("gold", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                Block block = blockstate.getBlock();
                return block == ModBlocks.GRANITE || block == ModBlocks.DIORITE || block == ModBlocks.ANORTHOSITE || block == ModBlocks.ANDESITE || block == ModBlocks.LIMESTONE || block == ModBlocks.BASALT || block == ModBlocks.RHYOLITE || block == ModBlocks.SHALE;
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

        public static final Codec<RankineOreFeatureConfig.RankineFillerBlockType> field_236571_d_ = IStringSerializable.createEnumCodec(RankineOreFeatureConfig.RankineFillerBlockType::values, RankineOreFeatureConfig.RankineFillerBlockType::byName);
        /** maps the filler block type name to the corresponding enum value. */
        private static final Map<String, RankineOreFeatureConfig.RankineFillerBlockType> VALUES_MAP = Arrays.stream(values()).collect(Collectors.toMap(RankineOreFeatureConfig.RankineFillerBlockType::getName, (p_236573_0_) -> {
            return p_236573_0_;
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

        public static RankineOreFeatureConfig.RankineFillerBlockType byName(String nameIn) {
            return VALUES_MAP.get(nameIn);
        }

        public Predicate<BlockState> getPredicate() {
            return this.predicate;
        }

        public static RankineOreFeatureConfig.RankineFillerBlockType create(String enumName, String name, Predicate<BlockState> predicate) {
            throw new IllegalStateException("Enum not extended");
        }

        public String getName() {
            return this.name;
        }

        @Override
        @Deprecated
        public void init() {
            VALUES_MAP.put(getName(), this);
        }

        @Override
        public String getString() {
            return this.name;
        }
    }
}