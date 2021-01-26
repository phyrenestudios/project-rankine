package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.Config;
import com.cannolicatfish.rankine.init.ModBlocks;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
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
        IGNEOUS("igneous", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                Block block = blockstate.getBlock();
                return block.getTags().contains(new ResourceLocation("rankine:igneous_stones"));
            }
        }),
        SEDIMENTARY("sedimentary", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                Block block = blockstate.getBlock();
                return block.getTags().contains(new ResourceLocation("rankine:sedimentary_stones"));
            }
        }),
        METAMORPHIC("metamorphic", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                Block block = blockstate.getBlock();
                return block.getTags().contains(new ResourceLocation("rankine:metamorphic_stones"));
            }
        }),
        NATIVE_COPPER("native_copper", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.NATIVE_COPPER_ORE_STONE_SPECIFIC.get()) {
                    return Config.NATIVE_COPPER_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.NATIVE_COPPER_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.NATIVE_COPPER_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.NATIVE_COPPER_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        NATIVE_TIN("native_tin", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.NATIVE_TIN_ORE_STONE_SPECIFIC.get()) {
                    return Config.NATIVE_TIN_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.NATIVE_TIN_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.NATIVE_TIN_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.NATIVE_TIN_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        NATIVE_GOLD("native_gold", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.NATIVE_GOLD_ORE_STONE_SPECIFIC.get()) {
                    return Config.NATIVE_GOLD_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.NATIVE_GOLD_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.NATIVE_GOLD_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.NATIVE_GOLD_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        NATIVE_ALUMINUM("native_aluminum", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.NATIVE_ALUMINUM_ORE_STONE_SPECIFIC.get()) {
                    return Config.NATIVE_ALUMINUM_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.NATIVE_ALUMINUM_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.NATIVE_ALUMINUM_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.NATIVE_ALUMINUM_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        NATIVE_LEAD("native_lead", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.NATIVE_LEAD_ORE_STONE_SPECIFIC.get()) {
                    return Config.NATIVE_LEAD_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.NATIVE_LEAD_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.NATIVE_LEAD_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.NATIVE_LEAD_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        NATIVE_SILVER("native_silver", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.NATIVE_SILVER_ORE_STONE_SPECIFIC.get()) {
                    return Config.NATIVE_SILVER_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.NATIVE_SILVER_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.NATIVE_SILVER_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.NATIVE_SILVER_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        NATIVE_ARSENIC("native_arsenic", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.NATIVE_ARSENIC_ORE_STONE_SPECIFIC.get()) {
                    return Config.NATIVE_ARSENIC_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.NATIVE_ARSENIC_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.NATIVE_ARSENIC_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.NATIVE_ARSENIC_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        NATIVE_BISMUTH("native_bismuth", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.NATIVE_BISMUTH_ORE_STONE_SPECIFIC.get()) {
                    return Config.NATIVE_BISMUTH_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.NATIVE_BISMUTH_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.NATIVE_BISMUTH_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.NATIVE_BISMUTH_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        NATIVE_SULFUR("native_sulfur", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.NATIVE_SULFUR_ORE_STONE_SPECIFIC.get()) {
                    return Config.NATIVE_SULFUR_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.NATIVE_SULFUR_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.NATIVE_SULFUR_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.NATIVE_SULFUR_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        NATIVE_GALLIUM("native_gallium", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.NATIVE_GALLIUM_ORE_STONE_SPECIFIC.get()) {
                    return Config.NATIVE_GALLIUM_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.NATIVE_GALLIUM_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.NATIVE_GALLIUM_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.NATIVE_GALLIUM_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        NATIVE_INDIUM("native_indium", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.NATIVE_INDIUM_ORE_STONE_SPECIFIC.get()) {
                    return Config.NATIVE_INDIUM_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.NATIVE_INDIUM_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.NATIVE_INDIUM_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.NATIVE_INDIUM_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        NATIVE_TELLURIUM("native_tellurium", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.NATIVE_TELLURIUM_ORE_STONE_SPECIFIC.get()) {
                    return Config.NATIVE_TELLURIUM_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.NATIVE_TELLURIUM_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.NATIVE_TELLURIUM_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.NATIVE_TELLURIUM_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        NATIVE_SELENIUM("native_selenium", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.NATIVE_SELENIUM_ORE_STONE_SPECIFIC.get()) {
                    return Config.NATIVE_SELENIUM_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.NATIVE_SELENIUM_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.NATIVE_SELENIUM_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.NATIVE_SELENIUM_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        MALACHITE("malachite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.MALACHITE_ORE_STONE_SPECIFIC.get()) {
                    return Config.MALACHITE_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.MALACHITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.MALACHITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.MALACHITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        CASSITERITE("cassiterite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.CASSITERITE_ORE_STONE_SPECIFIC.get()) {
                    return Config.CASSITERITE_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.CASSITERITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.CASSITERITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.CASSITERITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        BAUXITE("bauxite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.BAUXITE_ORE_STONE_SPECIFIC.get()) {
                    return Config.BAUXITE_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.BAUXITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.BAUXITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.BAUXITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        SPHALERITE("sphalerite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.SPHALERITE_ORE_STONE_SPECIFIC.get()) {
                    return Config.SPHALERITE_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.SPHALERITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.SPHALERITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.SPHALERITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        CINNABAR("cinnabar", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.CINNABAR_ORE_STONE_SPECIFIC.get()) {
                    return Config.CINNABAR_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.CINNABAR_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.CINNABAR_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.CINNABAR_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        MAGNETITE("magnetite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.MAGNETITE_ORE_STONE_SPECIFIC.get()) {
                    return Config.MAGNETITE_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.MAGNETITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.MAGNETITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.MAGNETITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        PENTLANDITE("pentlandite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.PENTLANDITE_ORE_STONE_SPECIFIC.get()) {
                    return Config.PENTLANDITE_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.PENTLANDITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.PENTLANDITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.PENTLANDITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        MAGNESITE("magnesite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.MAGNESITE_ORE_STONE_SPECIFIC.get()) {
                    return Config.MAGNESITE_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.MAGNESITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.MAGNESITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.MAGNESITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        GALENA("galena", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.GALENA_ORE_STONE_SPECIFIC.get()) {
                    return Config.GALENA_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.GALENA_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.GALENA_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.GALENA_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        VANADINITE("vanadinite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.VANADINITE_ORE_STONE_SPECIFIC.get()) {
                    return Config.VANADINITE_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.VANADINITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.VANADINITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.VANADINITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        BISMUTHINITE("bismuthinite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.BISMUTHINITE_ORE_STONE_SPECIFIC.get()) {
                    return Config.BISMUTHINITE_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.BISMUTHINITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.BISMUTHINITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.BISMUTHINITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        ACANTHITE("acanthite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.ACANTHITE_ORE_STONE_SPECIFIC.get()) {
                    return Config.ACANTHITE_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.ACANTHITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.ACANTHITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.ACANTHITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        PYROLUSITE("pyrolusite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.PYROLUSITE_ORE_STONE_SPECIFIC.get()) {
                    return Config.PYROLUSITE_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.PYROLUSITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.PYROLUSITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.PYROLUSITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        CHROMITE("chromite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.CHROMITE_ORE_STONE_SPECIFIC.get()) {
                    return Config.CHROMITE_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.CHROMITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.CHROMITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.CHROMITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        MOLYBDENITE("molybdenite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.MOLYBDENITE_ORE_STONE_SPECIFIC.get()) {
                    return Config.MOLYBDENITE_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.MOLYBDENITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.MOLYBDENITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.MOLYBDENITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        ILMENITE("ilmenite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.ILMENITE_ORE_STONE_SPECIFIC.get()) {
                    return Config.ILMENITE_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.ILMENITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.ILMENITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.ILMENITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        COLUMBITE("columbite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.COLUMBITE_ORE_STONE_SPECIFIC.get()) {
                    return Config.COLUMBITE_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.COLUMBITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.COLUMBITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.COLUMBITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        WOLFRAMITE("wolframite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.WOLFRAMITE_ORE_STONE_SPECIFIC.get()) {
                    return Config.WOLFRAMITE_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.WOLFRAMITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.WOLFRAMITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.WOLFRAMITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        TANTALITE("tantalite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.TANTALITE_ORE_STONE_SPECIFIC.get()) {
                    return Config.TANTALITE_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.TANTALITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.TANTALITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.TANTALITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        PLUMBAGO("plumbago", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.PLUMBAGO_ORE_STONE_SPECIFIC.get()) {
                    return Config.PLUMBAGO_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.PLUMBAGO_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.PLUMBAGO_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.PLUMBAGO_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        MOISSANITE("moissanite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.MOISSANITE_ORE_STONE_SPECIFIC.get()) {
                    return Config.MOISSANITE_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.MOISSANITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.MOISSANITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.MOISSANITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        SPERRYLITE("sperrylite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.SPERRYLITE_ORE_STONE_SPECIFIC.get()) {
                    return Config.SPERRYLITE_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.SPERRYLITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.SPERRYLITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.SPERRYLITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        LIGNITE("lignite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.LIGNITE_ORE_STONE_SPECIFIC.get()) {
                    return Config.LIGNITE_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.LIGNITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.LIGNITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.LIGNITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        SUBBITUMINOUS("subbituminous", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.SUBBITUMINOUS_ORE_STONE_SPECIFIC.get()) {
                    return Config.SUBBITUMINOUS_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.SUBBITUMINOUS_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.SUBBITUMINOUS_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.SUBBITUMINOUS_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        BITUMINOUS("bituminous", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.BITUMINOUS_ORE_STONE_SPECIFIC.get()) {
                    return Config.BITUMINOUS_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.BITUMINOUS_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.BITUMINOUS_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.BITUMINOUS_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        ANTHRACITE("anthracite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.ANTHRACITE_ORE_STONE_SPECIFIC.get()) {
                    return Config.ANTHRACITE_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.ANTHRACITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.ANTHRACITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.ANTHRACITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        LAZURITE("lazurite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.LAZURITE_ORE_STONE_SPECIFIC.get()) {
                    return Config.LAZURITE_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.LAZURITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.LAZURITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.LAZURITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        DIAMOND("diamond", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.DIAMOND_ORE_STONE_SPECIFIC.get()) {
                    return Config.DIAMOND_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.DIAMOND_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.DIAMOND_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.DIAMOND_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        GREENOCKITE("greenockite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.GREENOCKITE_ORE_STONE_SPECIFIC.get()) {
                    return Config.GREENOCKITE_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.GREENOCKITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.GREENOCKITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.GREENOCKITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        EMERALD("emerald", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.EMERALD_ORE_STONE_SPECIFIC.get()) {
                    return Config.EMERALD_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.EMERALD_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.EMERALD_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.EMERALD_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        AQUAMARINE("aquamarine", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.AQUAMARINE_ORE_STONE_SPECIFIC.get()) {
                    return Config.AQUAMARINE_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.AQUAMARINE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.AQUAMARINE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.AQUAMARINE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        QUARTZ("quartz", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.QUARTZ_ORE_STONE_SPECIFIC.get()) {
                    return Config.QUARTZ_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.QUARTZ_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.QUARTZ_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.QUARTZ_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        OPAL("opal", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.OPAL_ORE_STONE_SPECIFIC.get()) {
                    return Config.OPAL_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.OPAL_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.OPAL_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.OPAL_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        MAJORITE("majorite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.MAJORITE_ORE_STONE_SPECIFIC.get()) {
                    return Config.MAJORITE_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.MAJORITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.MAJORITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.MAJORITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        FLUORITE("fluorite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.FLUORITE_ORE_STONE_SPECIFIC.get()) {
                    return Config.FLUORITE_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.FLUORITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.FLUORITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.FLUORITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        URANINITE("uraninite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.URANINITE_ORE_STONE_SPECIFIC.get()) {
                    return Config.URANINITE_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.URANINITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.URANINITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.URANINITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        STIBNITE("stibnite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.STIBNITE_ORE_STONE_SPECIFIC.get()) {
                    return Config.STIBNITE_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.STIBNITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.STIBNITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.STIBNITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        XENOTIME("xenotime", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.XENOTIME_ORE_STONE_SPECIFIC.get()) {
                    return Config.XENOTIME_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.XENOTIME_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.XENOTIME_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.XENOTIME_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        HALITE("halite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.HALITE_ORE_STONE_SPECIFIC.get()) {
                    return Config.HALITE_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.HALITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.HALITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.HALITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        PINK_HALITE("pink_halite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.PINK_HALITE_ORE_STONE_SPECIFIC.get()) {
                    return Config.PINK_HALITE_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.PINK_HALITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.PINK_HALITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.PINK_HALITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        INTERSPINIFEX("interspinifex", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.INTERSPINIFEX_ORE_STONE_SPECIFIC.get()) {
                    return Config.INTERSPINIFEX_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.INTERSPINIFEX_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.INTERSPINIFEX_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.INTERSPINIFEX_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        PETALITE("petalite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.PETALITE_ORE_STONE_SPECIFIC.get()) {
                    return Config.PETALITE_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.PETALITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.PETALITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.PETALITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        COBALTITE("cobaltite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.COBALTITE_ORE_STONE_SPECIFIC.get()) {
                    return Config.COBALTITE_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.COBALTITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.COBALTITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.COBALTITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        CRYOLITE("cryolite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.CRYOLITE_ORE_STONE_SPECIFIC.get()) {
                    return Config.CRYOLITE_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.CRYOLITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.CRYOLITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.CRYOLITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        PYRITE("pyrite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.PYRITE_ORE_STONE_SPECIFIC.get()) {
                    return Config.PYRITE_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.PYRITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.PYRITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.PYRITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        CELESTINE("celestine", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (Config.CELESTINE_ORE_STONE_SPECIFIC.get()) {
                    return Config.CELESTINE_BLOCK_LIST.get().contains(blockstate.getBlock().getRegistryName().toString());
                } else if (Config.CELESTINE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.CELESTINE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.CELESTINE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("rankine:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        SANDSTONES("sandstones", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                Block block = blockstate.getBlock();
                return block == Blocks.SANDSTONE || block == Blocks.RED_SANDSTONE || block == ModBlocks.ARKOSE_SANDSTONE || block == ModBlocks.QUARTZ_SANDSTONE;
            }
        }),
        DACITES("dacites", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                Block block = blockstate.getBlock();
                return block == ModBlocks.RED_DACITE || block == ModBlocks.BLACK_DACITE;
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
        NETHER("nk_b", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                Block block = blockstate.getBlock();
                return block == Blocks.NETHERRACK || block == Blocks.BASALT || block == Blocks.BLACKSTONE;
            }
        }),
        NETHERRACK("netherrack", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                Block block = blockstate.getBlock();
                return block == Blocks.NETHERRACK;
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