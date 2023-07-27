package com.cannolicatfish.rankine.blocks.block_enums;

import com.cannolicatfish.rankine.blocks.QuarterSlabPoleBlock;
import com.cannolicatfish.rankine.blocks.RankineStairsBlock;
import com.cannolicatfish.rankine.blocks.RankineWallBlock;
import com.cannolicatfish.rankine.init.RankineBlocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public enum CementBlocks {

    WHITE_CEMENT(DyeColor.WHITE),
    LIGHT_GRAY_CEMENT(DyeColor.LIGHT_GRAY),
    GRAY_CEMENT(DyeColor.GRAY),
    BLACK_CEMENT(DyeColor.BLACK),
    BROWN_CEMENT(DyeColor.BROWN),
    RED_CEMENT(DyeColor.RED),
    ORANGE_CEMENT(DyeColor.ORANGE),
    YELLOW_CEMENT(DyeColor.YELLOW),
    LIME_CEMENT(DyeColor.LIME),
    GREEN_CEMENT(DyeColor.GREEN),
    CYAN_CEMENT(DyeColor.CYAN),
    LIGHT_BLUE_CEMENT(DyeColor.LIGHT_BLUE),
    BLUE_CEMENT(DyeColor.BLUE),
    MAGENTA_CEMENT(DyeColor.MAGENTA),
    PURPLE_CEMENT(DyeColor.PURPLE),
    PINK_CEMENT(DyeColor.PINK),
    CONCRETE(DyeColor.LIGHT_GRAY),
    ROMAN_CONCRETE(DyeColor.LIGHT_GRAY),
    POLISHED_ROMAN_CONCRETE(DyeColor.LIGHT_GRAY),
    ROMAN_CONCRETE_BRICKS(DyeColor.LIGHT_GRAY);

    RegistryObject<Block> cement;
    RegistryObject<QuarterSlabPoleBlock> cementSlab;
    RegistryObject<RankineStairsBlock> cementStairs;
    RegistryObject<RankineWallBlock> cementWall;

    final DyeColor dyeColor;

    CementBlocks(DyeColor dyeColor) {
        this.dyeColor = dyeColor;
    }

    public String getName() {
        return name().toLowerCase(Locale.ROOT);
    }
    public DyeColor getDyeColor() {
        return dyeColor;
    }
    public Block getCementBlock() {return this.cement.get();}
    public QuarterSlabPoleBlock getCementSlab() {return this.cementSlab.get();}
    public RankineStairsBlock getCementStairs() {return this.cementStairs.get();}
    public RankineWallBlock getCementWall() {return this.cementWall.get();}
    public List<Block> getCementBlocks() {
        return Arrays.asList(
                this.getCementBlock(),
                this.getCementSlab(),
                this.getCementStairs(),
                this.getCementWall()
        );
    }

    public static void registerBlocks() {
        for (CementBlocks baseCementBlock : values()) {
            baseCementBlock.cement =  RankineBlocks.BLOCKS.register(baseCementBlock.getName(), () -> new Block(BlockBehaviour.Properties.of(Material.STONE, baseCementBlock.dyeColor).requiresCorrectToolForDrops().strength(4.0f)));
            baseCementBlock.cementSlab =  RankineBlocks.BLOCKS.register(baseCementBlock.getName()+"_slab", () -> new QuarterSlabPoleBlock(BlockBehaviour.Properties.copy(baseCementBlock.cement.get()).noCollission()));
            baseCementBlock.cementStairs =  RankineBlocks.BLOCKS.register(baseCementBlock.getName()+"_stairs", () -> new RankineStairsBlock(BlockBehaviour.Properties.copy(baseCementBlock.cement.get())));
            baseCementBlock.cementWall =  RankineBlocks.BLOCKS.register(baseCementBlock.getName()+"_wall", () -> new RankineWallBlock(BlockBehaviour.Properties.copy(baseCementBlock.cement.get())));
        }
    }

    public static void registerItems() {
        Item.Properties DEF_BUILDING = new Item.Properties().stacksTo(64);
        for (CementBlocks baseCementBlock : values()) {
            RankineBlocks.ITEMS.register(baseCementBlock.getName(), () -> new BlockItem(baseCementBlock.cement.get(), DEF_BUILDING));
            RankineBlocks.ITEMS.register(baseCementBlock.getName()+"_slab", () -> new BlockItem(baseCementBlock.cementSlab.get(), DEF_BUILDING));
            RankineBlocks.ITEMS.register(baseCementBlock.getName()+"_stairs", () -> new BlockItem(baseCementBlock.cementStairs.get(), DEF_BUILDING));
            RankineBlocks.ITEMS.register(baseCementBlock.getName()+"_wall", () -> new BlockItem(baseCementBlock.cementWall.get(), DEF_BUILDING));
        }
    }




}
