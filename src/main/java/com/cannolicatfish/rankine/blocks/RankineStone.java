package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.blocks.buildingmodes.RankinePolishedStoneBlock;
import com.cannolicatfish.rankine.blocks.buildingmodes.RankineStoneBricksBlock;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.items.BuildingModeBlockItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.InfestedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.List;

public class RankineStone {

    private final String registryName;
    private RegistryObject<RankineStoneBlock> stone;
    private RegistryObject<RankinePolishedStoneBlock> polished;
    private RegistryObject<RankineStoneBricksBlock> bricks;
    private RegistryObject<RankineStoneBricksBlock> mossyBricks;
    private RegistryObject<RankineSlabBlock> slab;
    private RegistryObject<RankineSlabBlock> polishedSLab;
    private RegistryObject<RankineSlabBlock> bricksSlab;
    private RegistryObject<RankineSlabBlock> mossyBricksSlab;
    private RegistryObject<RankineStairsBlock> stairs;
    private RegistryObject<RankineStairsBlock> polishedStairs;
    private RegistryObject<RankineStairsBlock> bricksStairs;
    private RegistryObject<RankineStairsBlock> mossyBricksStairs;
    private RegistryObject<RankineWallBlock> wall;
    private RegistryObject<RankineWallBlock> polishedWall;
    private RegistryObject<RankineWallBlock> bricksWall;
    private RegistryObject<RankineWallBlock> mossyBricksWall;
    private RegistryObject<RankineStonePressurePlate> pressurePlate;
    private RegistryObject<RankineStonePressurePlate> bricksPressurePlate;
    private RegistryObject<RankineStoneButton> button;
    private RegistryObject<StoneColumnBlock> column;
    private RegistryObject<CobbleBlock> cobble;
    private RegistryObject<InfestedBlock> infested;


    public RankineStone(String registryName) {
        this.registryName = registryName;
    }


    public static RankineStone newStoneReg(String baseName, String stoneCLass) {

        BlockBehaviour.Properties stoneProperties;
        switch (stoneCLass) {
            case "sedimentary":
                stoneProperties = Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(1.0F, 4.0F);
                break;
            case "metamorphic":
                stoneProperties = Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 6.0F);
                break;
            case "igneous":
            default:
                stoneProperties = Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 6.0F);
        }

        RankineStone newStone = new RankineStone(baseName);
        newStone.stone = RankineBlocks.BLOCKS.register(baseName, () -> new RankineStoneBlock(stoneProperties));
        newStone.polished = RankineBlocks.BLOCKS.register("polished_"+baseName, () -> new RankinePolishedStoneBlock(stoneProperties));
        newStone.bricks = RankineBlocks.BLOCKS.register(baseName+"_bricks", () -> new RankineStoneBricksBlock(stoneProperties));
        newStone.mossyBricks = RankineBlocks.BLOCKS.register("mossy_"+baseName+"_bricks", () -> new RankineStoneBricksBlock(stoneProperties));
        newStone.slab = RankineBlocks.BLOCKS.register(baseName+"_slab", () -> new RankineSlabBlock(stoneProperties));
        newStone.polishedSLab = RankineBlocks.BLOCKS.register("polished_"+baseName+"_slab", () -> new RankineSlabBlock(stoneProperties));
        newStone.bricksSlab = RankineBlocks.BLOCKS.register(baseName+"_bricks"+"_slab", () -> new RankineSlabBlock(stoneProperties));
        newStone.mossyBricksSlab = RankineBlocks.BLOCKS.register("mossy_"+baseName+"_bricks"+"_slab", () -> new RankineSlabBlock(stoneProperties));
        newStone.stairs = RankineBlocks.BLOCKS.register(baseName+"_stairs", () -> new RankineStairsBlock(stoneProperties));
        newStone.polishedStairs = RankineBlocks.BLOCKS.register("polished_"+baseName+"_stairs", () -> new RankineStairsBlock(stoneProperties));
        newStone.bricksStairs = RankineBlocks.BLOCKS.register(baseName+"_bricks"+"_stairs", () -> new RankineStairsBlock(stoneProperties));
        newStone.mossyBricksStairs = RankineBlocks.BLOCKS.register("mossy_"+baseName+"_bricks"+"_stairs", () -> new RankineStairsBlock(stoneProperties));
        newStone.wall = RankineBlocks.BLOCKS.register(baseName+"_wall", () -> new RankineWallBlock(stoneProperties));
        newStone.polishedWall = RankineBlocks.BLOCKS.register("polished_"+baseName+"_wall", () -> new RankineWallBlock(stoneProperties));
        newStone.bricksWall = RankineBlocks.BLOCKS.register(baseName+"_bricks"+"_wall", () -> new RankineWallBlock(stoneProperties));
        newStone.mossyBricksWall = RankineBlocks.BLOCKS.register("mossy_"+baseName+"_bricks"+"_wall", () -> new RankineWallBlock(stoneProperties));
        newStone.pressurePlate = RankineBlocks.BLOCKS.register(baseName+"_pressure_plate", RankineStonePressurePlate::new);
        newStone.bricksPressurePlate = RankineBlocks.BLOCKS.register(baseName+"_bricks"+"_pressure_plate", RankineStonePressurePlate::new);
        newStone.button = RankineBlocks.BLOCKS.register(baseName+"_button", RankineStoneButton::new);
        newStone.column = RankineBlocks.BLOCKS.register(baseName+"_column", () -> new StoneColumnBlock(stoneProperties));
        newStone.cobble = RankineBlocks.BLOCKS.register(baseName+"_cobble", CobbleBlock::new);
        newStone.infested = RankineBlocks.BLOCKS.register("infested_"+baseName, () -> new InfestedBlock(newStone.stone.get(), stoneProperties));

        Item.Properties DEF_BUILDING = new Item.Properties().stacksTo(64).tab(ProjectRankine.setup.rankineWorld);
        RankineItems.ITEMS.register(baseName, () -> new BlockItem(newStone.stone.get(), DEF_BUILDING));
        RankineItems.ITEMS.register("polished_"+baseName, () -> new BuildingModeBlockItem(newStone.polished.get(), DEF_BUILDING));
        RankineItems.ITEMS.register(baseName+"_bricks", () -> new BuildingModeBlockItem(newStone.bricks.get(), DEF_BUILDING));
        RankineItems.ITEMS.register("mossy_"+baseName+"_bricks", () -> new BuildingModeBlockItem(newStone.mossyBricks.get(), DEF_BUILDING));
        RankineItems.ITEMS.register(baseName+"_slab", () -> new BlockItem(newStone.slab.get(), DEF_BUILDING));
        RankineItems.ITEMS.register("polished_"+baseName+"_slab", () -> new BlockItem(newStone.polishedSLab.get(), DEF_BUILDING));
        RankineItems.ITEMS.register(baseName+"_bricks"+"_slab", () -> new BlockItem(newStone.bricksSlab.get(), DEF_BUILDING));
        RankineItems.ITEMS.register("mossy_"+baseName+"_bricks"+"_slab", () -> new BlockItem(newStone.mossyBricksSlab.get(), DEF_BUILDING));
        RankineItems.ITEMS.register(baseName+"_stairs", () -> new BlockItem(newStone.stairs.get(), DEF_BUILDING));
        RankineItems.ITEMS.register("polished_"+baseName+"_stairs", () -> new BlockItem(newStone.polishedStairs.get(), DEF_BUILDING));
        RankineItems.ITEMS.register(baseName+"_bricks"+"_stairs", () -> new BlockItem(newStone.bricksStairs.get(), DEF_BUILDING));
        RankineItems.ITEMS.register("mossy_"+baseName+"_bricks"+"_stairs", () -> new BlockItem(newStone.mossyBricksStairs.get(), DEF_BUILDING));
        RankineItems.ITEMS.register(baseName+"_wall", () -> new BlockItem(newStone.wall.get(), DEF_BUILDING));
        RankineItems.ITEMS.register("polished_"+baseName+"_wall", () -> new BlockItem(newStone.polishedWall.get(), DEF_BUILDING));
        RankineItems.ITEMS.register(baseName+"_bricks"+"_wall", () -> new BlockItem(newStone.bricksWall.get(), DEF_BUILDING));
        RankineItems.ITEMS.register("mossy_"+baseName+"_bricks"+"_wall", () -> new BlockItem(newStone.mossyBricksWall.get(), DEF_BUILDING));
        RankineItems.ITEMS.register(baseName+"_pressure_plate", () -> new BlockItem(newStone.pressurePlate.get(), DEF_BUILDING));
        RankineItems.ITEMS.register(baseName+"_bricks"+"_pressure_plate", () -> new BlockItem(newStone.bricksPressurePlate.get(), DEF_BUILDING));
        RankineItems.ITEMS.register(baseName+"_button", () -> new BlockItem(newStone.button.get(), DEF_BUILDING));
        RankineItems.ITEMS.register(baseName+"_column", () -> new BlockItem(newStone.column.get(), DEF_BUILDING));
        RankineItems.ITEMS.register(baseName+"_cobble", () -> new BlockItem(newStone.cobble.get(), DEF_BUILDING));
        RankineItems.ITEMS.register("infested_"+baseName, () -> new BlockItem(newStone.infested.get(), DEF_BUILDING));

        return newStone;
    }

    public List<Block> getStoneBlocks() {
        return Arrays.asList(
                this.getStone(),
                this.getPolished(),
                this.getBricks(),
                this.getMossyBricks(),
                this.getSlab(),
                this.getPolishedSlab(),
                this.getBricksSlab(),
                this.getMossyBricksSlab(),
                this.getStairs(),
                this.getPolishedStairs(),
                this.getBricksStairs(),
                this.getMossyBricksStairs(),
                this.getWall(),
                this.getPolishedWall(),
                this.getBricksWall(),
                this.getMossyBricksWall(),
                this.getPressurePlate(),
                this.getBricksPressurePlate(),
                this.getButton(),
                this.getColumn(),
                this.getCobble(),
                this.getInfested()
                );
    }


    public String getBaseName() {return registryName;}
    public RankineStoneBlock getStone() {return this.stone.get();}
    public RankinePolishedStoneBlock getPolished() {return this.polished.get();}
    public RankineStoneBricksBlock getBricks() {return this.bricks.get();}
    public RankineStoneBricksBlock getMossyBricks() {return this.mossyBricks.get();}
    public RankineSlabBlock getSlab() {return this.slab.get();}
    public RankineSlabBlock getPolishedSlab() {return this.polishedSLab.get();}
    public RankineSlabBlock getBricksSlab() {return this.bricksSlab.get();}
    public RankineSlabBlock getMossyBricksSlab() {return this.mossyBricksSlab.get();}
    public RankineStairsBlock getStairs() {return this.stairs.get();}
    public RankineStairsBlock getPolishedStairs() {return this.polishedStairs.get();}
    public RankineStairsBlock getBricksStairs() {return this.bricksStairs.get();}
    public RankineStairsBlock getMossyBricksStairs() {return this.mossyBricksStairs.get();}
    public RankineWallBlock getWall() {return this.wall.get();}
    public RankineWallBlock getPolishedWall() {return this.polishedWall.get();}
    public RankineWallBlock getBricksWall() {return this.bricksWall.get();}
    public RankineWallBlock getMossyBricksWall() {return this.mossyBricksWall.get();}
    public RankineStonePressurePlate getPressurePlate() {return this.pressurePlate.get();}
    public RankineStonePressurePlate getBricksPressurePlate() {return this.bricksPressurePlate.get();}
    public RankineStoneButton getButton() {return this.button.get();}
    public StoneColumnBlock getColumn() {return this.column.get();}
    public CobbleBlock getCobble() {return this.cobble.get();}
    public InfestedBlock getInfested() {return this.infested.get();}


}
