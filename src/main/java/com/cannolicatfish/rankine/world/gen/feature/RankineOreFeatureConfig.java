package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.init.ModBlocks;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.gen.feature.IFeatureConfig;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RankineOreFeatureConfig implements IFeatureConfig {
    public static final Codec<RankineOreFeatureConfig> CODEC = RecordCodecBuilder.create((p_236568_0_) -> {
        return p_236568_0_.group(RankineOreFeatureConfig.RankineFillerBlockType.field_236571_d_.fieldOf("target").forGetter((p_236570_0_) -> {
            return p_236570_0_.target;
        }), BlockState.CODEC.fieldOf("state").forGetter((p_236569_0_) -> {
            return p_236569_0_.state;
        }), Codec.INT.fieldOf("size").orElse(0).forGetter((p_236567_0_) -> {
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
        OVERWORLD("overworld_stones", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                Block block = blockstate.getBlock();
                return block == ModBlocks.RED_GRANITE || block == ModBlocks.HORNBLENDE_ANDESITE || block == ModBlocks.GRANODIORITE || block == ModBlocks.ANORTHOSITE || block == ModBlocks.LIMESTONE || block == ModBlocks.THOLEIITIC_BASALT || block == ModBlocks.MARBLE || block == ModBlocks.GNEISS || block == ModBlocks.RHYOLITE || block == ModBlocks.SHALE || block == ModBlocks.PERIDOTITE;
            }
        }),
        NO_SHALE("no_shale", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                Block block = blockstate.getBlock();
                return block == ModBlocks.RED_GRANITE || block == ModBlocks.HORNBLENDE_ANDESITE || block == ModBlocks.GRANODIORITE || block == ModBlocks.ANORTHOSITE || block == ModBlocks.LIMESTONE || block == ModBlocks.THOLEIITIC_BASALT || block == ModBlocks.MARBLE || block == ModBlocks.GNEISS || block == ModBlocks.RHYOLITE || block == ModBlocks.PERIDOTITE;
            }
        }),
        NO_PERIDOTITE("no_peridotite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                Block block = blockstate.getBlock();
                return block == ModBlocks.RED_GRANITE || block == ModBlocks.HORNBLENDE_ANDESITE || block == ModBlocks.GRANODIORITE || block == ModBlocks.ANORTHOSITE || block == ModBlocks.LIMESTONE || block == ModBlocks.THOLEIITIC_BASALT || block == ModBlocks.MARBLE || block == ModBlocks.GNEISS || block == ModBlocks.RHYOLITE || block == ModBlocks.SHALE;
            }
        }),
        NO_SP("no_per_shale", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                Block block = blockstate.getBlock();
                return block == ModBlocks.RED_GRANITE || block == ModBlocks.HORNBLENDE_ANDESITE || block == ModBlocks.GRANODIORITE || block == ModBlocks.ANORTHOSITE || block == ModBlocks.LIMESTONE || block == ModBlocks.THOLEIITIC_BASALT || block == ModBlocks.GNEISS || block == ModBlocks.RHYOLITE || block == ModBlocks.MARBLE;
            }
        }),
        NO_SMP("no_per_shale_marble", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                Block block = blockstate.getBlock();
                return block == ModBlocks.RED_GRANITE || block == ModBlocks.HORNBLENDE_ANDESITE || block == ModBlocks.GRANODIORITE || block == ModBlocks.ANORTHOSITE || block == ModBlocks.LIMESTONE || block == ModBlocks.THOLEIITIC_BASALT || block == ModBlocks.GNEISS || block == ModBlocks.RHYOLITE;
            }
        }),
        IGNEOUS("igneous", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                Block block = blockstate.getBlock();
                return block == ModBlocks.RED_GRANITE || block == ModBlocks.HORNBLENDE_ANDESITE || block == ModBlocks.GRANODIORITE || block == ModBlocks.THOLEIITIC_BASALT || block == ModBlocks.RHYOLITE;
            }
        }),
        MARBLE("marble", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                Block block = blockstate.getBlock();
                return block == ModBlocks.MARBLE;
            }
        }),
        PERIDOTITE("peridotite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                Block block = blockstate.getBlock();
                return block == ModBlocks.PERIDOTITE;
            }
        }),
        END("end", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                Block block = blockstate.getBlock();
                return block == Blocks.END_STONE;
            }
        }),
        NETHER("nether", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                Block block = blockstate.getBlock();
                return block == Blocks.NETHERRACK || block == ModBlocks.KOMATIITE || block == ModBlocks.PEROVSKITE || block == ModBlocks.RINGWOODITE || block == ModBlocks.BRIDGMANITE || block == ModBlocks.FERROPERICLASE || block == ModBlocks.WADSLEYITE;
            }
        });;



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