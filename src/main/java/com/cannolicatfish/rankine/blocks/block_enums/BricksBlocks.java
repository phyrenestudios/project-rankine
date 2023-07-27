package com.cannolicatfish.rankine.blocks.block_enums;

import com.cannolicatfish.rankine.blocks.RankineSlabBlock;
import com.cannolicatfish.rankine.blocks.RankineStairsBlock;
import com.cannolicatfish.rankine.blocks.RankineWallBlock;
import com.cannolicatfish.rankine.blocks.buildingmodes.RankineBricksBlock;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.items.BuildingModeBlockItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public enum BricksBlocks {

    CLAY_BRICKS(MaterialColor.CLAY),
    KAOLINITE_BRICKS(MaterialColor.TERRACOTTA_WHITE),
    HALLOYSITE_BRICKS(MaterialColor.TERRACOTTA_YELLOW),
    PORCELAIN_CLAY_BRICKS(MaterialColor.TERRACOTTA_WHITE),
    FIRE_CLAY_BRICKS(MaterialColor.COLOR_BROWN),
    REFRACTORY_BRICKS(MaterialColor.TERRACOTTA_BROWN),
    HIGH_REFRACTORY_BRICKS(MaterialColor.TERRACOTTA_GRAY),
    ULTRA_HIGH_REFRACTORY_BRICKS(MaterialColor.TERRACOTTA_CYAN);

    RegistryObject<RankineBricksBlock> bricks;
    RegistryObject<RankineSlabBlock> bricksSlab;
    RegistryObject<RankineStairsBlock> bricksStairs;
    RegistryObject<RankineWallBlock> bricksWall;

    final MaterialColor materialColor;

    BricksBlocks(MaterialColor materialColor) {
        this.materialColor = materialColor;
    }

    public String getName() {
        return name().toLowerCase(Locale.ROOT);
    }
    public RankineBricksBlock getBricksBlock() {return this.bricks.get();}
    public RankineSlabBlock getBricksSlab() {return this.bricksSlab.get();}
    public RankineStairsBlock getBricksStairs() {return this.bricksStairs.get();}
    public RankineWallBlock getBricksWall() {return this.bricksWall.get();}
    public List<Block> getBricksBlocks() {
        return Arrays.asList(
                this.getBricksBlock(),
                this.getBricksSlab(),
                this.getBricksStairs(),
                this.getBricksWall()
        );
    }

    public static void registerBlocks() {
        for (BricksBlocks baseBricksBlock : values()) {
            baseBricksBlock.bricks =  RankineBlocks.BLOCKS.register(baseBricksBlock.getName(), () -> new RankineBricksBlock(BlockBehaviour.Properties.of(Material.STONE, baseBricksBlock.materialColor).requiresCorrectToolForDrops().strength(2.0F, 6.0F)));
            baseBricksBlock.bricksSlab =  RankineBlocks.BLOCKS.register(baseBricksBlock.getName()+"_slab", () -> new RankineSlabBlock(BlockBehaviour.Properties.copy(baseBricksBlock.bricks.get()).noCollission()));
            baseBricksBlock.bricksStairs =  RankineBlocks.BLOCKS.register(baseBricksBlock.getName()+"_stairs", () -> new RankineStairsBlock(BlockBehaviour.Properties.copy(baseBricksBlock.bricks.get())));
            baseBricksBlock.bricksWall =  RankineBlocks.BLOCKS.register(baseBricksBlock.getName()+"_wall", () -> new RankineWallBlock(BlockBehaviour.Properties.copy(baseBricksBlock.bricks.get())));
        }
    }

    public static void registerItems() {
        Item.Properties DEF_BUILDING = new Item.Properties().stacksTo(64);
        for (BricksBlocks baseBricksBlock : values()) {
            RankineBlocks.ITEMS.register(baseBricksBlock.getName(), () -> new BuildingModeBlockItem(baseBricksBlock.bricks.get(), DEF_BUILDING));
            RankineBlocks.ITEMS.register(baseBricksBlock.getName()+"_slab", () -> new BlockItem(baseBricksBlock.bricksSlab.get(), DEF_BUILDING));
            RankineBlocks.ITEMS.register(baseBricksBlock.getName()+"_stairs", () -> new BlockItem(baseBricksBlock.bricksStairs.get(), DEF_BUILDING));
            RankineBlocks.ITEMS.register(baseBricksBlock.getName()+"_wall", () -> new BlockItem(baseBricksBlock.bricksWall.get(), DEF_BUILDING));
        }
    }




}
