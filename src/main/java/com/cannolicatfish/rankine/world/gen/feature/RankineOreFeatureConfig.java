package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
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
                if (Config.NATIVE_COPPER_ORE_STONE_SPECIFIC.get()) {
                    for (String s : Config.NATIVE_COPPER_BLOCK_LIST.get()) {
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
                } else if (Config.NATIVE_COPPER_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.NATIVE_COPPER_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.NATIVE_COPPER_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.NATIVE_TIN_BLOCK_LIST.get()) {
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
                } else if (Config.NATIVE_TIN_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.NATIVE_TIN_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.NATIVE_TIN_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.NATIVE_GOLD_BLOCK_LIST.get()) {
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
                } else if (Config.NATIVE_GOLD_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.NATIVE_GOLD_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.NATIVE_GOLD_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.NATIVE_ALUMINUM_BLOCK_LIST.get()) {
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
                } else if (Config.NATIVE_ALUMINUM_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.NATIVE_ALUMINUM_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.NATIVE_ALUMINUM_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.NATIVE_LEAD_BLOCK_LIST.get()) {
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
                } else if (Config.NATIVE_LEAD_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.NATIVE_LEAD_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.NATIVE_LEAD_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.NATIVE_SILVER_BLOCK_LIST.get()) {
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
                } else if (Config.NATIVE_SILVER_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.NATIVE_SILVER_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.NATIVE_SILVER_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.NATIVE_ARSENIC_BLOCK_LIST.get()) {
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
                } else if (Config.NATIVE_ARSENIC_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.NATIVE_ARSENIC_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.NATIVE_ARSENIC_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.NATIVE_BISMUTH_BLOCK_LIST.get()) {
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
                } else if (Config.NATIVE_BISMUTH_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.NATIVE_BISMUTH_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.NATIVE_BISMUTH_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.NATIVE_SULFUR_BLOCK_LIST.get()) {
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
                } else if (Config.NATIVE_SULFUR_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.NATIVE_SULFUR_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.NATIVE_SULFUR_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.NATIVE_GALLIUM_BLOCK_LIST.get()) {
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
                } else if (Config.NATIVE_GALLIUM_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.NATIVE_GALLIUM_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.NATIVE_GALLIUM_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.NATIVE_INDIUM_BLOCK_LIST.get()) {
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
                } else if (Config.NATIVE_INDIUM_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.NATIVE_INDIUM_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.NATIVE_INDIUM_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.NATIVE_TELLURIUM_BLOCK_LIST.get()) {
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
                } else if (Config.NATIVE_TELLURIUM_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.NATIVE_TELLURIUM_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.NATIVE_TELLURIUM_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.NATIVE_SELENIUM_BLOCK_LIST.get()) {
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
                } else if (Config.NATIVE_SELENIUM_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.NATIVE_SELENIUM_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.NATIVE_SELENIUM_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.MALACHITE_BLOCK_LIST.get()) {
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
                } else if (Config.MALACHITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.MALACHITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.MALACHITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.CASSITERITE_BLOCK_LIST.get()) {
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
                } else if (Config.CASSITERITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.CASSITERITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.CASSITERITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.BAUXITE_BLOCK_LIST.get()) {
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
                } else if (Config.BAUXITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.BAUXITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.BAUXITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.SPHALERITE_BLOCK_LIST.get()) {
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
                } else if (Config.SPHALERITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.SPHALERITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.SPHALERITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.CINNABAR_BLOCK_LIST.get()) {
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
                } else if (Config.CINNABAR_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.CINNABAR_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.CINNABAR_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.MAGNETITE_BLOCK_LIST.get()) {
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
                } else if (Config.MAGNETITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.MAGNETITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.MAGNETITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.PENTLANDITE_BLOCK_LIST.get()) {
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
                } else if (Config.PENTLANDITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.PENTLANDITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.PENTLANDITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.MAGNESITE_BLOCK_LIST.get()) {
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
                } else if (Config.MAGNESITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.MAGNESITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.MAGNESITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.GALENA_BLOCK_LIST.get()) {
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
                } else if (Config.GALENA_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.GALENA_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.GALENA_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.VANADINITE_BLOCK_LIST.get()) {
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
                } else if (Config.VANADINITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.VANADINITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.VANADINITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.BISMUTHINITE_BLOCK_LIST.get()) {
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
                } else if (Config.BISMUTHINITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.BISMUTHINITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.BISMUTHINITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.ACANTHITE_BLOCK_LIST.get()) {
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
                } else if (Config.ACANTHITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.ACANTHITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.ACANTHITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.PYROLUSITE_BLOCK_LIST.get()) {
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
                } else if (Config.PYROLUSITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.PYROLUSITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.PYROLUSITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.CHROMITE_BLOCK_LIST.get()) {
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
                } else if (Config.CHROMITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.CHROMITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.CHROMITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.MOLYBDENITE_BLOCK_LIST.get()) {
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
                } else if (Config.MOLYBDENITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.MOLYBDENITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.MOLYBDENITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.ILMENITE_BLOCK_LIST.get()) {
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
                } else if (Config.ILMENITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.ILMENITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.ILMENITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.COLUMBITE_BLOCK_LIST.get()) {
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
                } else if (Config.COLUMBITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.COLUMBITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.COLUMBITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.WOLFRAMITE_BLOCK_LIST.get()) {
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
                } else if (Config.WOLFRAMITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.WOLFRAMITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.WOLFRAMITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.TANTALITE_BLOCK_LIST.get()) {
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
                } else if (Config.TANTALITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.TANTALITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.TANTALITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.PLUMBAGO_BLOCK_LIST.get()) {
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
                } else if (Config.PLUMBAGO_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.PLUMBAGO_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.PLUMBAGO_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.MOISSANITE_BLOCK_LIST.get()) {
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
                } else if (Config.MOISSANITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.MOISSANITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.MOISSANITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.SPERRYLITE_BLOCK_LIST.get()) {
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
                } else if (Config.SPERRYLITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.SPERRYLITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.SPERRYLITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.LIGNITE_BLOCK_LIST.get()) {
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
                } else if (Config.LIGNITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.LIGNITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.LIGNITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.SUBBITUMINOUS_BLOCK_LIST.get()) {
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
                } else if (Config.SUBBITUMINOUS_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.SUBBITUMINOUS_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.SUBBITUMINOUS_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.BITUMINOUS_BLOCK_LIST.get()) {
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
                } else if (Config.BITUMINOUS_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.BITUMINOUS_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.BITUMINOUS_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.ANTHRACITE_BLOCK_LIST.get()) {
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
                } else if (Config.ANTHRACITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.ANTHRACITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.ANTHRACITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.LAZURITE_BLOCK_LIST.get()) {
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
                } else if (Config.LAZURITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.LAZURITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.LAZURITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.DIAMOND_BLOCK_LIST.get()) {
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
                } else if (Config.DIAMOND_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.DIAMOND_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.DIAMOND_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.GREENOCKITE_BLOCK_LIST.get()) {
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
                } else if (Config.GREENOCKITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.GREENOCKITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.GREENOCKITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.EMERALD_BLOCK_LIST.get()) {
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
                } else if (Config.EMERALD_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.EMERALD_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.EMERALD_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.AQUAMARINE_BLOCK_LIST.get()) {
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
                } else if (Config.AQUAMARINE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.AQUAMARINE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.AQUAMARINE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.QUARTZ_BLOCK_LIST.get()) {
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
                } else if (Config.QUARTZ_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.QUARTZ_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.QUARTZ_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.OPAL_BLOCK_LIST.get()) {
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
                } else if (Config.OPAL_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.OPAL_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.OPAL_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.MAJORITE_BLOCK_LIST.get()) {
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
                } else if (Config.MAJORITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.MAJORITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.MAJORITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.FLUORITE_BLOCK_LIST.get()) {
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
                } else if (Config.FLUORITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.FLUORITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.FLUORITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.URANINITE_BLOCK_LIST.get()) {
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
                } else if (Config.URANINITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.URANINITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.URANINITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.STIBNITE_BLOCK_LIST.get()) {
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
                } else if (Config.STIBNITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.STIBNITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.STIBNITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.XENOTIME_BLOCK_LIST.get()) {
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
                } else if (Config.XENOTIME_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.XENOTIME_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.XENOTIME_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.HALITE_BLOCK_LIST.get()) {
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
                } else if (Config.HALITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.HALITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.HALITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.PINK_HALITE_BLOCK_LIST.get()) {
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
                } else if (Config.PINK_HALITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.PINK_HALITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.PINK_HALITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.INTERSPINIFEX_BLOCK_LIST.get()) {
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
                } else if (Config.INTERSPINIFEX_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.INTERSPINIFEX_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.INTERSPINIFEX_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.PETALITE_BLOCK_LIST.get()) {
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
                } else if (Config.PETALITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.PETALITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.PETALITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.COBALTITE_BLOCK_LIST.get()) {
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
                } else if (Config.COBALTITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.COBALTITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.COBALTITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.CRYOLITE_BLOCK_LIST.get()) {
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
                } else if (Config.CRYOLITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.CRYOLITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.CRYOLITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.PYRITE_BLOCK_LIST.get()) {
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
                } else if (Config.PYRITE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.PYRITE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.PYRITE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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
                    for (String s : Config.CELESTINE_BLOCK_LIST.get()) {
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
                } else if (Config.CELESTINE_ORE_DIMENSION_LIST.get().contains("overworld")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_overworld")));
                } else if (Config.CELESTINE_ORE_DIMENSION_LIST.get().contains("nether")) {
                    return blockstate.getBlock().getTags().contains(new ResourceLocation(("minecraft:base_stone_nether")));
                } else if (Config.CELESTINE_ORE_DIMENSION_LIST.get().contains("end")) {
                    return blockstate.getBlock() == Blocks.END_STONE;
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