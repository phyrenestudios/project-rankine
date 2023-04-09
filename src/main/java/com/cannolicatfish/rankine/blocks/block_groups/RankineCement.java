package com.cannolicatfish.rankine.blocks.block_groups;

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

public class RankineCement {

    private final String registryName;
    private RegistryObject<Block> cement;
    private RegistryObject<QuarterSlabPoleBlock> cementSlab;
    private RegistryObject<RankineStairsBlock> cementStairs;
    private RegistryObject<RankineWallBlock> cementWall;

    public RankineCement(String registryName) {
        this.registryName = registryName;
    }

    public static RankineCement newCementReg(String baseName, DyeColor color) {
        RankineCement newCement = new RankineCement(baseName);
        newCement.cement =  RankineBlocks.BLOCKS.register(baseName, () -> new Block(BlockBehaviour.Properties.of(Material.STONE, color).requiresCorrectToolForDrops().strength(4.0f)));
        newCement.cementSlab =  RankineBlocks.BLOCKS.register(baseName+"_slab", () -> new QuarterSlabPoleBlock(BlockBehaviour.Properties.copy(newCement.cement.get()).noCollission()));
        newCement.cementStairs =  RankineBlocks.BLOCKS.register(baseName+"_stairs", () -> new RankineStairsBlock(BlockBehaviour.Properties.copy(newCement.cement.get())));
        newCement.cementWall =  RankineBlocks.BLOCKS.register(baseName+"_wall", () -> new RankineWallBlock(BlockBehaviour.Properties.copy(newCement.cement.get())));

        Item.Properties DEF_BUILDING = new Item.Properties().stacksTo(64);
        RankineBlocks.ITEMS.register(baseName, () -> new BlockItem(newCement.cement.get(), DEF_BUILDING));
        RankineBlocks.ITEMS.register(baseName+"_slab", () -> new BlockItem(newCement.cementSlab.get(), DEF_BUILDING));
        RankineBlocks.ITEMS.register(baseName+"_stairs", () -> new BlockItem(newCement.cementStairs.get(), DEF_BUILDING));
        RankineBlocks.ITEMS.register(baseName+"_wall", () -> new BlockItem(newCement.cementWall.get(), DEF_BUILDING));

        return newCement;
    }

    public List<Block> getCementBlocks() {
        return Arrays.asList(
                this.getCementBlock(),
                this.getCementSlab(),
                this.getCementStairs(),
                this.getCementWall()
        );
    }

    public String getBaseName() {return registryName;}
    public Block getCementBlock() {return this.cement.get();}
    public QuarterSlabPoleBlock getCementSlab() {return this.cementSlab.get();}
    public RankineStairsBlock getCementStairs() {return this.cementStairs.get();}
    public RankineWallBlock getCementWall() {return this.cementWall.get();}

}
