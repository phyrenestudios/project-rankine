package com.cannolicatfish.rankine.blocks.block_groups;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.blocks.RankineSlabBlock;
import com.cannolicatfish.rankine.blocks.RankineStairsBlock;
import com.cannolicatfish.rankine.blocks.RankineWallBlock;
import com.cannolicatfish.rankine.init.RankineBlocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;

import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.Arrays;
import java.util.List;

public class RankineSandstone {

    private final String registryName;
    private DeferredBlock<Block> sandstone;
    private DeferredBlock<RankineSlabBlock> sandstoneSlab;
    private DeferredBlock<RankineStairsBlock> sandstoneStairs;
    private DeferredBlock<RankineWallBlock> sandstoneWall;
    private DeferredBlock<Block> smoothSandstone;
    private DeferredBlock<RankineSlabBlock> smoothSandstoneSlab;
    private DeferredBlock<RankineStairsBlock> smoothSandstoneStairs;
    private DeferredBlock<RankineWallBlock> smoothSandstoneWall;
    private DeferredBlock<Block> cutSandstone;
    private DeferredBlock<RankineSlabBlock> cutSandstoneSlab;
    private DeferredBlock<Block> chiseledSandstone;


    public RankineSandstone(String registryName) {
        this.registryName = registryName;
    }

    public static RankineSandstone newSandstoneReg(String baseName) {
        RankineSandstone newSandstone = new RankineSandstone(baseName);
        newSandstone.sandstone =  RankineBlocks.BLOCKS.register(baseName, () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(0.8F)));
        newSandstone.sandstoneSlab =  RankineBlocks.BLOCKS.register(baseName+"_slab", () -> new RankineSlabBlock(BlockBehaviour.Properties.ofFullCopy(newSandstone.sandstone.get())));
        newSandstone.sandstoneStairs =  RankineBlocks.BLOCKS.register(baseName+"_stairs", () -> new RankineStairsBlock(BlockBehaviour.Properties.ofFullCopy(newSandstone.sandstone.get())));
        newSandstone.sandstoneWall =  RankineBlocks.BLOCKS.register(baseName+"_wall", () -> new RankineWallBlock(BlockBehaviour.Properties.ofFullCopy(newSandstone.sandstone.get())));
        newSandstone.smoothSandstone =  RankineBlocks.BLOCKS.register("smooth_"+baseName, () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(2.0F, 6.0F)));
        newSandstone.smoothSandstoneSlab =  RankineBlocks.BLOCKS.register("smooth_"+baseName+"_slab", () -> new RankineSlabBlock(BlockBehaviour.Properties.ofFullCopy(newSandstone.smoothSandstone.get())));
        newSandstone.smoothSandstoneStairs =  RankineBlocks.BLOCKS.register("smooth_"+baseName+"_stairs", () -> new RankineStairsBlock(BlockBehaviour.Properties.ofFullCopy(newSandstone.smoothSandstone.get())));
        newSandstone.smoothSandstoneWall =  RankineBlocks.BLOCKS.register("smooth_"+baseName+"_wall", () -> new RankineWallBlock(BlockBehaviour.Properties.ofFullCopy(newSandstone.smoothSandstone.get())));
        newSandstone.cutSandstone =  RankineBlocks.BLOCKS.register("cut_"+baseName, () -> new RankineSlabBlock(BlockBehaviour.Properties.ofFullCopy(newSandstone.sandstone.get())));
        newSandstone.cutSandstoneSlab =  RankineBlocks.BLOCKS.register("cut_"+baseName+"_slab", () -> new RankineSlabBlock(BlockBehaviour.Properties.ofFullCopy(newSandstone.sandstone.get())));
        newSandstone.chiseledSandstone =  RankineBlocks.BLOCKS.register("chiseled_"+baseName, () -> new RankineSlabBlock(BlockBehaviour.Properties.ofFullCopy(newSandstone.sandstone.get())));

        Item.Properties DEF_BUILDING = new Item.Properties().stacksTo(64);
        RankineBlocks.ITEMS.register(baseName, () -> new BlockItem(newSandstone.sandstone.get(), DEF_BUILDING));
        RankineBlocks.ITEMS.register(baseName+"_slab", () -> new BlockItem(newSandstone.sandstoneSlab.get(), DEF_BUILDING));
        RankineBlocks.ITEMS.register(baseName+"_stairs", () -> new BlockItem(newSandstone.sandstoneStairs.get(), DEF_BUILDING));
        RankineBlocks.ITEMS.register(baseName+"_wall", () -> new BlockItem(newSandstone.sandstoneWall.get(), DEF_BUILDING));
        RankineBlocks.ITEMS.register("smooth_"+baseName, () -> new BlockItem(newSandstone.smoothSandstone.get(), DEF_BUILDING));
        RankineBlocks.ITEMS.register("smooth_"+baseName+"_slab", () -> new BlockItem(newSandstone.smoothSandstoneSlab.get(), DEF_BUILDING));
        RankineBlocks.ITEMS.register("smooth_"+baseName+"_stairs", () -> new BlockItem(newSandstone.smoothSandstoneStairs.get(), DEF_BUILDING));
        RankineBlocks.ITEMS.register("smooth_"+baseName+"_wall", () -> new BlockItem(newSandstone.smoothSandstoneWall.get(), DEF_BUILDING));
        RankineBlocks.ITEMS.register("cut_"+baseName, () -> new BlockItem(newSandstone.cutSandstone.get(), DEF_BUILDING));
        RankineBlocks.ITEMS.register("cut_"+baseName+"_slab", () -> new BlockItem(newSandstone.cutSandstoneSlab.get(), DEF_BUILDING));
        RankineBlocks.ITEMS.register("chiseled_"+baseName, () -> new BlockItem(newSandstone.chiseledSandstone.get(), DEF_BUILDING));

        return newSandstone;
    }

    public List<Block> getSandstoneBlocks() {
        return Arrays.asList(
                this.getSandstone(),
                this.getSandstoneSlab(),
                this.getSandstoneStairs(),
                this.getSandstoneWall(),
                this.getSmoothSandstone(),
                this.getSmoothSandstoneSlab(),
                this.getSmoothSandstoneStairs(),
                this.getSmoothSandstoneWall(),
                this.getCutSandstone(),
                this.getCutSandstoneSlab(),
                this.getChiseledSandstone()
        );
    }

    public String getBaseName() {return registryName;}
    public Block getSandstone() {return this.sandstone.get();}
    public RankineSlabBlock getSandstoneSlab() {return this.sandstoneSlab.get();}
    public RankineStairsBlock getSandstoneStairs() {return this.sandstoneStairs.get();}
    public RankineWallBlock getSandstoneWall() {return this.sandstoneWall.get();}
    public Block getSmoothSandstone() {return this.smoothSandstone.get();}
    public RankineSlabBlock getSmoothSandstoneSlab() {return this.smoothSandstoneSlab.get();}
    public RankineStairsBlock getSmoothSandstoneStairs() {return this.smoothSandstoneStairs.get();}
    public RankineWallBlock getSmoothSandstoneWall() {return this.smoothSandstoneWall.get();}
    public Block getCutSandstone() {return this.cutSandstone.get();}
    public RankineSlabBlock getCutSandstoneSlab() {return this.cutSandstoneSlab.get();}
    public Block getChiseledSandstone() {return this.chiseledSandstone.get();}
}
