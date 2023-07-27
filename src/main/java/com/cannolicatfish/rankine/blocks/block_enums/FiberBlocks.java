package com.cannolicatfish.rankine.blocks.block_enums;

import com.cannolicatfish.rankine.blocks.*;
import com.cannolicatfish.rankine.init.RankineBlocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public enum FiberBlocks {

    FIBER(null,true),
    RED_FIBER(DyeColor.RED, false),
    ORANGE_FIBER(DyeColor.ORANGE, false),
    YELLOW_FIBER(DyeColor.YELLOW, false),
    LIME_FIBER(DyeColor.LIME, false),
    GREEN_FIBER(DyeColor.GREEN, false),
    CYAN_FIBER(DyeColor.CYAN, false),
    LIGHT_BLUE_FIBER(DyeColor.LIGHT_BLUE, false),
    BLUE_FIBER(DyeColor.BLUE, false),
    MAGENTA_FIBER(DyeColor.MAGENTA, false),
    PURPLE_FIBER(DyeColor.PURPLE, false),
    PINK_FIBER(DyeColor.PINK, false),
    BROWN_FIBER(DyeColor.BROWN, false),
    BLACK_FIBER(DyeColor.BLACK, false),
    GRAY_FIBER(DyeColor.GRAY, false),
    LIGHT_GRAY_FIBER(DyeColor.LIGHT_GRAY, false),
    WHITE_FIBER(DyeColor.WHITE, false);

    RegistryObject<FiberBlock> fiberBlock;
    RegistryObject<RankineSlabBlock> slab;
    RegistryObject<RankineStairsBlock> stairs;
    RegistryObject<RankineWallBlock> wall;
    RegistryObject<FiberMatBlock> mat;

    final boolean extraBlocks;
    final DyeColor dyeColor;
    final List<Block> blockList = new ArrayList<>();

    FiberBlocks(DyeColor dyeColor, boolean extraBlocks) {
        this.extraBlocks = extraBlocks;
        this.dyeColor = dyeColor;
    }

    public String getName() {
        return name().toLowerCase(Locale.ROOT);
    }

    public boolean hasExtraBlocks() {
        return extraBlocks;
    }
    public DyeColor getDyeColor() {
        return dyeColor;
    }
    public FiberBlock getBlock() {
        return fiberBlock.get();
    }
    public RankineSlabBlock getSlab() {
        return slab.get();
    }
    public RankineStairsBlock getStairs() {
        return stairs.get();
    }
    public RankineWallBlock getWall() {
        return wall.get();
    }
    public FiberMatBlock getMat() {
        return mat.get();
    }
    public List<Block> getAllBlocks() {
        return blockList;
    }


    public static void registerBlocks() {
        for (FiberBlocks fiber : values()) {
            fiber.fiberBlock = RankineBlocks.BLOCKS.register(fiber.getName()+"_block", () -> new FiberBlock(BlockBehaviour.Properties.of(Material.WOOL, MaterialColor.SNOW).strength(0.1F).sound(SoundType.WOOL)));
            if (fiber.extraBlocks) {
                fiber.slab = RankineBlocks.BLOCKS.register(fiber.getName()+"_block_slab", () -> new RankineSlabBlock(BlockBehaviour.Properties.of(Material.WOOL, MaterialColor.SNOW).strength(0.1F).sound(SoundType.WOOL)));
                fiber.stairs = RankineBlocks.BLOCKS.register(fiber.getName()+"_block_stairs", () -> new RankineStairsBlock(BlockBehaviour.Properties.of(Material.WOOL, MaterialColor.SNOW).strength(0.1F).sound(SoundType.WOOL)));
                fiber.wall = RankineBlocks.BLOCKS.register(fiber.getName()+"_block_wall", () -> new RankineWallBlock(BlockBehaviour.Properties.of(Material.WOOL, MaterialColor.SNOW).strength(0.1F).sound(SoundType.WOOL)));
            }
            fiber.mat = RankineBlocks.BLOCKS.register(fiber.getName()+"_mat", () -> new FiberMatBlock(BlockBehaviour.Properties.of(Material.CLOTH_DECORATION, MaterialColor.SNOW).strength(0.1F).sound(SoundType.WOOL)));

        }

    }

    public static void registerItems() {
        Item.Properties DEF_BUILDING = new Item.Properties().stacksTo(64);
        for (FiberBlocks fiber : values()) {
            RankineBlocks.ITEMS.register(fiber.getName()+"_block", () -> new BlockItem(fiber.fiberBlock.get(), DEF_BUILDING));
            if (fiber.extraBlocks) {
                RankineBlocks.ITEMS.register(fiber.getName()+"_block_slab", () -> new BlockItem(fiber.slab.get(), DEF_BUILDING));
                RankineBlocks.ITEMS.register(fiber.getName()+"_block_stairs", () -> new BlockItem(fiber.stairs.get(), DEF_BUILDING));
                RankineBlocks.ITEMS.register(fiber.getName()+"_block_wall", () -> new BlockItem(fiber.wall.get(), DEF_BUILDING));
            }
            RankineBlocks.ITEMS.register(fiber.getName()+"_mat", () -> new BlockItem(fiber.mat.get(), DEF_BUILDING));
        }

    }




}
