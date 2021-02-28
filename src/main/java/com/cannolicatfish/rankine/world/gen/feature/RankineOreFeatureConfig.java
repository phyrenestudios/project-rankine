package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.WGConfig;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.IFeatureConfig;

import java.util.Arrays;
import java.util.List;
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
        NATIVE_COPPER("native_copper", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.NATIVE_COPPER_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.NATIVE_COPPER_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.NATIVE_COPPER_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.NATIVE_COPPER_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.NATIVE_COPPER_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        NATIVE_TIN("native_tin", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.NATIVE_TIN_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.NATIVE_TIN_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.NATIVE_TIN_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.NATIVE_TIN_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.NATIVE_TIN_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        NATIVE_GOLD("native_gold", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.NATIVE_GOLD_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.NATIVE_GOLD_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.NATIVE_GOLD_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.NATIVE_GOLD_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.NATIVE_GOLD_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        NATIVE_ALUMINUM("native_aluminum", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.NATIVE_ALUMINUM_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.NATIVE_ALUMINUM_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.NATIVE_ALUMINUM_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.NATIVE_ALUMINUM_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.NATIVE_ALUMINUM_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        NATIVE_LEAD("native_lead", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.NATIVE_LEAD_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.NATIVE_LEAD_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.NATIVE_LEAD_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.NATIVE_LEAD_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.NATIVE_LEAD_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        NATIVE_SILVER("native_silver", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.NATIVE_SILVER_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.NATIVE_SILVER_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.NATIVE_SILVER_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.NATIVE_SILVER_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.NATIVE_SILVER_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        NATIVE_ARSENIC("native_arsenic", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.NATIVE_ARSENIC_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.NATIVE_ARSENIC_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.NATIVE_ARSENIC_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.NATIVE_ARSENIC_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.NATIVE_ARSENIC_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        NATIVE_BISMUTH("native_bismuth", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.NATIVE_BISMUTH_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.NATIVE_BISMUTH_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.NATIVE_BISMUTH_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.NATIVE_BISMUTH_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.NATIVE_BISMUTH_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        NATIVE_SULFUR("native_sulfur", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.NATIVE_SULFUR_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.NATIVE_SULFUR_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.NATIVE_SULFUR_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.NATIVE_SULFUR_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.NATIVE_SULFUR_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        NATIVE_GALLIUM("native_gallium", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.NATIVE_GALLIUM_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.NATIVE_GALLIUM_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.NATIVE_GALLIUM_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.NATIVE_GALLIUM_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.NATIVE_GALLIUM_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        NATIVE_INDIUM("native_indium", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.NATIVE_INDIUM_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.NATIVE_INDIUM_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.NATIVE_INDIUM_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.NATIVE_INDIUM_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.NATIVE_INDIUM_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        NATIVE_TELLURIUM("native_tellurium", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.NATIVE_TELLURIUM_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.NATIVE_TELLURIUM_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.NATIVE_TELLURIUM_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.NATIVE_TELLURIUM_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.NATIVE_TELLURIUM_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        NATIVE_SELENIUM("native_selenium", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.NATIVE_SELENIUM_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.NATIVE_SELENIUM_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.NATIVE_SELENIUM_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.NATIVE_SELENIUM_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.NATIVE_SELENIUM_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        MALACHITE("malachite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.MALACHITE_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.MALACHITE_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.MALACHITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.MALACHITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.MALACHITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        CASSITERITE("cassiterite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.CASSITERITE_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.CASSITERITE_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.CASSITERITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.CASSITERITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.CASSITERITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        BAUXITE("bauxite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.BAUXITE_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.BAUXITE_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.BAUXITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.BAUXITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.BAUXITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        SPHALERITE("sphalerite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.SPHALERITE_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.SPHALERITE_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.SPHALERITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.SPHALERITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.SPHALERITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        CINNABAR("cinnabar", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.CINNABAR_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.CINNABAR_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.CINNABAR_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.CINNABAR_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.CINNABAR_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        MAGNETITE("magnetite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.MAGNETITE_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.MAGNETITE_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.MAGNETITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.MAGNETITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.MAGNETITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        PENTLANDITE("pentlandite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.PENTLANDITE_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.PENTLANDITE_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.PENTLANDITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.PENTLANDITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.PENTLANDITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        MAGNESITE("magnesite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.MAGNESITE_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.MAGNESITE_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.MAGNESITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.MAGNESITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.MAGNESITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        GALENA("galena", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.GALENA_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.GALENA_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.GALENA_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.GALENA_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.GALENA_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        VANADINITE("vanadinite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.VANADINITE_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.VANADINITE_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.VANADINITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.VANADINITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.VANADINITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        BISMUTHINITE("bismuthinite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.BISMUTHINITE_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.BISMUTHINITE_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.BISMUTHINITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.BISMUTHINITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.BISMUTHINITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        ACANTHITE("acanthite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.ACANTHITE_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.ACANTHITE_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.ACANTHITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.ACANTHITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.ACANTHITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        PYROLUSITE("pyrolusite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.PYROLUSITE_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.PYROLUSITE_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.PYROLUSITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.PYROLUSITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.PYROLUSITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        CHROMITE("chromite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.CHROMITE_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.CHROMITE_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.CHROMITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.CHROMITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.CHROMITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        MOLYBDENITE("molybdenite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.MOLYBDENITE_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.MOLYBDENITE_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.MOLYBDENITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.MOLYBDENITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.MOLYBDENITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        ILMENITE("ilmenite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.ILMENITE_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.ILMENITE_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.ILMENITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.ILMENITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.ILMENITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        COLUMBITE("columbite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.COLUMBITE_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.COLUMBITE_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.COLUMBITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.COLUMBITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.COLUMBITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        WOLFRAMITE("wolframite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.WOLFRAMITE_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.WOLFRAMITE_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.WOLFRAMITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.WOLFRAMITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.WOLFRAMITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        TANTALITE("tantalite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.TANTALITE_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.TANTALITE_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.TANTALITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.TANTALITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.TANTALITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        PLUMBAGO("plumbago", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.PLUMBAGO_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.PLUMBAGO_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.PLUMBAGO_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.PLUMBAGO_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.PLUMBAGO_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        MOISSANITE("moissanite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.MOISSANITE_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.MOISSANITE_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.MOISSANITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.MOISSANITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.MOISSANITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        SPERRYLITE("sperrylite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.SPERRYLITE_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.SPERRYLITE_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.SPERRYLITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.SPERRYLITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.SPERRYLITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        LIGNITE("lignite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.LIGNITE_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.LIGNITE_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.LIGNITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.LIGNITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.LIGNITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        SUBBITUMINOUS("subbituminous", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.SUBBITUMINOUS_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.SUBBITUMINOUS_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.SUBBITUMINOUS_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.SUBBITUMINOUS_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.SUBBITUMINOUS_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        BITUMINOUS("bituminous", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.BITUMINOUS_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.BITUMINOUS_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.BITUMINOUS_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.BITUMINOUS_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.BITUMINOUS_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        ANTHRACITE("anthracite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.ANTHRACITE_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.ANTHRACITE_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.ANTHRACITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.ANTHRACITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.ANTHRACITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        LAZURITE("lazurite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.LAZURITE_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.LAZURITE_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.LAZURITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.LAZURITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.LAZURITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        DIAMOND("diamond", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.DIAMOND_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.DIAMOND_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.DIAMOND_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.DIAMOND_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.DIAMOND_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        GREENOCKITE("greenockite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.GREENOCKITE_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.GREENOCKITE_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.GREENOCKITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.GREENOCKITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.GREENOCKITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        EMERALD("emerald", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.EMERALD_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.EMERALD_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.EMERALD_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.EMERALD_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.EMERALD_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        AQUAMARINE("aquamarine", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.AQUAMARINE_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.AQUAMARINE_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.AQUAMARINE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.AQUAMARINE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.AQUAMARINE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        QUARTZ("quartz", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.QUARTZ_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.QUARTZ_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.QUARTZ_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.QUARTZ_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.QUARTZ_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        OPAL("opal", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.OPAL_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.OPAL_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.OPAL_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.OPAL_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.OPAL_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        MAJORITE("majorite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.MAJORITE_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.MAJORITE_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.MAJORITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.MAJORITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.MAJORITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        FLUORITE("fluorite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.FLUORITE_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.FLUORITE_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.FLUORITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.FLUORITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.FLUORITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        URANINITE("uraninite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.URANINITE_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.URANINITE_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.URANINITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.URANINITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.URANINITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        STIBNITE("stibnite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.STIBNITE_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.STIBNITE_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.STIBNITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.STIBNITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.STIBNITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        XENOTIME("xenotime", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.XENOTIME_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.XENOTIME_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.XENOTIME_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.XENOTIME_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.XENOTIME_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        HALITE("halite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.HALITE_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.HALITE_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.HALITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.HALITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.HALITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        PINK_HALITE("pink_halite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.PINK_HALITE_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.PINK_HALITE_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.PINK_HALITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.PINK_HALITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.PINK_HALITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        INTERSPINIFEX("interspinifex", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.INTERSPINIFEX_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.INTERSPINIFEX_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.INTERSPINIFEX_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.INTERSPINIFEX_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.INTERSPINIFEX_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        PETALITE("petalite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.PETALITE_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.PETALITE_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.PETALITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.PETALITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.PETALITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        COBALTITE("cobaltite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.COBALTITE_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.COBALTITE_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.COBALTITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.COBALTITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.COBALTITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        CRYOLITE("cryolite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.CRYOLITE_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.CRYOLITE_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.CRYOLITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.CRYOLITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.CRYOLITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        PYRITE("pyrite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.PYRITE_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.PYRITE_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.PYRITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.PYRITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.PYRITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        CELESTINE("celestine", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.CELESTINE_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.CELESTINE_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.CELESTINE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.CELESTINE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.CELESTINE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        MONAZITE("monazite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.MONAZITE_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.MONAZITE_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.MONAZITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.MONAZITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.MONAZITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        KAMACITE("kamacite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.KAMACITE_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.KAMACITE_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.KAMACITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.KAMACITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.KAMACITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        ANTITAENITE("antitaenite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.ANTITAENITE_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.ANTITAENITE_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.ANTITAENITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.ANTITAENITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.ANTITAENITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        TAENITE("taenite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.TAENITE_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.TAENITE_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.TAENITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.TAENITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.TAENITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        TETRATAENITE("tetrataenite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.TETRATAENITE_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.TETRATAENITE_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.TETRATAENITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.TETRATAENITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.TETRATAENITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
                } else {
                    return false;
                }
            }
        }),
        LONSDALEITE("lonsdaleite", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                if (WGConfig.ORES.LONSDALEITE_ORE_STONE_SPECIFIC.get()) {
                    for (String s : WGConfig.ORES.LONSDALEITE_ORE_BLOCK_LIST.get()) {
                        List<String> name = Arrays.asList(s.split("#"));
                        if (name.size() > 1) {
                            if (name.get(0).equals("T")) {
                                if (blockstate.getBlock().getTags().contains(new ResourceLocation(name.get(1)))) {
                                    return true;
                                }
                            } else if (name.get(0).equals("B")) {
                                if (blockstate.getBlock().getRegistryName().toString().equals(name.get(1))) {
                                    return true;
                                }
                            } else {
                                System.out.println(s+" is not a valid resource location for oregen");
                            }
                        } else {
                            System.out.println(s+" is not a valid resource location for oregen");
                        }
                    }
                    return false;
                } else if (WGConfig.ORES.LONSDALEITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (WGConfig.ORES.LONSDALEITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (WGConfig.ORES.LONSDALEITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("forge:base_stone_end")));
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
                return block == Blocks.SANDSTONE || block == Blocks.RED_SANDSTONE || block == RankineBlocks.ARKOSE_SANDSTONE.get() || block == RankineBlocks.QUARTZ_SANDSTONE.get();
            }
        }),
        DACITES("dacites", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                Block block = blockstate.getBlock();
                return block == RankineBlocks.RED_DACITE.get() || block == RankineBlocks.BLACK_DACITE.get();
            }
        }),
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
        OVERWORLD("overworld", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                Block block = blockstate.getBlock();
                return block.getTags().contains(new ResourceLocation("minecraft:base_stone_overworld"));
            }
        }),
        END("end", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                Block block = blockstate.getBlock();
                return block.getTags().contains(new ResourceLocation("rankine:base_stone_end"));
            }
        }),
        NETHER("nether", (blockstate) -> {
            if (blockstate == null) {
                return false;
            } else {
                Block block = blockstate.getBlock();
                return block.getTags().contains(new ResourceLocation("mine:base_stone_nether"));
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