package com.cannolicatfish.rankine.blocks.block_groups;

import com.cannolicatfish.rankine.ProjectRankine;
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
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;

import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.Arrays;
import java.util.List;

public class RankineBricks {

    private final String registryName;
    private DeferredBlock<RankineBricksBlock> bricks;
    private DeferredBlock<RankineSlabBlock> bricksSlab;
    private DeferredBlock<RankineStairsBlock> bricksStairs;
    private DeferredBlock<RankineWallBlock> bricksWall;

    public RankineBricks(String registryName) {
        this.registryName = registryName;
    }

    public static RankineBricks newBricksReg(String baseName, MapColor color) {
        RankineBricks newBricks = new RankineBricks(baseName);
//        RankineLists.RANKINE_BRICKS.add(newBricks);
        newBricks.bricks =  RankineBlocks.BLOCKS.register(baseName, () -> new RankineBricksBlock(BlockBehaviour.Properties.of().mapColor(color).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(2.0F, 6.0F)));
        newBricks.bricksSlab =  RankineBlocks.BLOCKS.register(baseName+"_slab", () -> new RankineSlabBlock(BlockBehaviour.Properties.ofFullCopy(newBricks.bricks.get()).noCollission()));
        newBricks.bricksStairs =  RankineBlocks.BLOCKS.register(baseName+"_stairs", () -> new RankineStairsBlock(BlockBehaviour.Properties.ofFullCopy(newBricks.bricks.get())));
        newBricks.bricksWall =  RankineBlocks.BLOCKS.register(baseName+"_wall", () -> new RankineWallBlock(BlockBehaviour.Properties.ofFullCopy(newBricks.bricks.get())));

        Item.Properties DEF_BUILDING = new Item.Properties().stacksTo(64);
        RankineBlocks.ITEMS.register(baseName, () -> new BuildingModeBlockItem(newBricks.bricks.get(), DEF_BUILDING));
        RankineBlocks.ITEMS.register(baseName+"_slab", () -> new BlockItem(newBricks.bricksSlab.get(), DEF_BUILDING));
        RankineBlocks.ITEMS.register(baseName+"_stairs", () -> new BlockItem(newBricks.bricksStairs.get(), DEF_BUILDING));
        RankineBlocks.ITEMS.register(baseName+"_wall", () -> new BlockItem(newBricks.bricksWall.get(), DEF_BUILDING));

        return newBricks;
    }

    public List<Block> getBricksBlocks() {
        return Arrays.asList(
                this.getBricksBlock(),
                this.getBricksSlab(),
                this.getBricksStairs(),
                this.getBricksWall()
        );
    }

    public String getBaseName() {return registryName;}
    public RankineBricksBlock getBricksBlock() {return this.bricks.get();}
    public RankineSlabBlock getBricksSlab() {return this.bricksSlab.get();}
    public RankineStairsBlock getBricksStairs() {return this.bricksStairs.get();}
    public RankineWallBlock getBricksWall() {return this.bricksWall.get();}

}
