package com.cannolicatfish.rankine.blocks.block_groups;

import com.cannolicatfish.rankine.blocks.*;
import com.cannolicatfish.rankine.blocks.buildingmodes.RankinePolishedStoneBlock;
import com.cannolicatfish.rankine.blocks.buildingmodes.RankineStoneBricksBlock;
import com.cannolicatfish.rankine.init.RankineBlocks;
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
import java.util.Locale;

public enum StoneBlocks {


    //GRANITE(StoneType.IGNEOUS,false,true),
    //DIORITE(StoneType.IGNEOUS,false,true),
    //ANDESITE(StoneType.IGNEOUS,false,true),
    //BASALT(StoneType.IGNEOUS,false,true),
    //DEEPSLATE(StoneType.IGNEOUS,false,true),

    QUARTZOLITE(StoneType.IGNEOUS,false,false),
    PEGMATITE(StoneType.IGNEOUS,false,false),
    PORPHYRY(StoneType.IGNEOUS,false,false),
    APLITE(StoneType.IGNEOUS,false,false),
    GRANODIORITE(StoneType.IGNEOUS,false,false),
    TONALITE(StoneType.IGNEOUS,false,false),
    SYENITE(StoneType.IGNEOUS,false,false),
    MONZONITE(StoneType.IGNEOUS,false,false),
    GABBRO(StoneType.IGNEOUS,false,false),
    NORITE(StoneType.IGNEOUS,false,false),
    TROCTOLITE(StoneType.IGNEOUS,false,false),
    SHONKINITE(StoneType.IGNEOUS,false,false),
    FOIDOLITE(StoneType.IGNEOUS,false,false),

    RHYOLITE(StoneType.IGNEOUS,false,false),
    COMENDITE(StoneType.IGNEOUS,false,false),
    PHONOLITE(StoneType.IGNEOUS,false,false),
    DACITE(StoneType.IGNEOUS,false,false),
    ANORTHOSITE(StoneType.IGNEOUS,false,false),
    DIABASE(StoneType.IGNEOUS,false,false),
    THOLEIITIC_BASALT(StoneType.IGNEOUS,false,false),

    DUNITE(StoneType.IGNEOUS,false,false),
    HARZBURGITE(StoneType.IGNEOUS,false,false),
    LHERZOLITE(StoneType.IGNEOUS,false,false),
    WEHRLITE(StoneType.IGNEOUS,false,false),
    WEBSTERITE(StoneType.IGNEOUS,false,false),
    PYROXENITE(StoneType.IGNEOUS,false,false),
    KOMATIITE(StoneType.IGNEOUS,false,false),
    KIMBERLITE(StoneType.IGNEOUS,false,false),

    SOMMANITE(StoneType.MANTLE,false,false),
    RINGWOODINE(StoneType.MANTLE,false,false),
    WADSLEYONE(StoneType.MANTLE,false,false),
    BRIDGMANHAM(StoneType.MANTLE,false,false),
    POST_PEROVSKITE(StoneType.MANTLE,false,false),
    BLACK_MARBLE(StoneType.METAMORPHIC,false,false),
    GRAY_MARBLE(StoneType.METAMORPHIC,false,false),
    WHITE_MARBLE(StoneType.METAMORPHIC,false,false),
    ROSE_MARBLE(StoneType.METAMORPHIC,false,false),
    SLATE(StoneType.METAMORPHIC,false,false),
    PHYLLITE(StoneType.METAMORPHIC,false,false),
    SCHIST(StoneType.METAMORPHIC,false,false),
    BLUESCHIST(StoneType.METAMORPHIC,false,false),
    GREENSCHIST(StoneType.METAMORPHIC,false,false),
    WHITESCHIST(StoneType.METAMORPHIC,false,false),
    GNEISS(StoneType.METAMORPHIC,false,false),
    QUARTZITE(StoneType.METAMORPHIC,false,false),
    SERPENTINITE(StoneType.METAMORPHIC,false,false),
    MARIPOSITE(StoneType.METAMORPHIC,false,false),
    ECLOGITE(StoneType.METAMORPHIC,false,false),
    LIMESTONE(StoneType.SEDIMENTARY,false,false),
    DOLOSTONE(StoneType.SEDIMENTARY,false,false),
    CHALK(StoneType.SEDIMENTARY,false,false),
    MARLSTONE(StoneType.SEDIMENTARY,false,false),
    SOAPSTONE(StoneType.SEDIMENTARY,false,false),
    SHALE(StoneType.SEDIMENTARY,false,false),
    MUDSTONE(StoneType.SEDIMENTARY,false,false),
    SILTSTONE(StoneType.SEDIMENTARY,false,false),
    ITACOLUMITE(StoneType.SEDIMENTARY,false,false),
    ARKOSE(StoneType.SEDIMENTARY,false,false),
    GRAYWACKE(StoneType.SEDIMENTARY,false,false),
    HONEYSTONE(StoneType.SEDIMENTARY,false,false);


    RegistryObject<RankineStoneBlock> stone;
    RegistryObject<RankinePolishedStoneBlock> polished;
    RegistryObject<RankineStoneBricksBlock> bricks;
    RegistryObject<RankineStoneBricksBlock> mossyBricks;
    RegistryObject<RankineSlabBlock> slab;
    RegistryObject<RankineSlabBlock> polishedSLab;
    RegistryObject<RankineSlabBlock> bricksSlab;
    RegistryObject<RankineSlabBlock> mossyBricksSlab;
    RegistryObject<RankineStairsBlock> stairs;
    RegistryObject<RankineStairsBlock> polishedStairs;
    RegistryObject<RankineStairsBlock> bricksStairs;
    RegistryObject<RankineStairsBlock> mossyBricksStairs;
    RegistryObject<RankineWallBlock> wall;
    RegistryObject<RankineWallBlock> polishedWall;
    RegistryObject<RankineWallBlock> bricksWall;
    RegistryObject<RankineWallBlock> mossyBricksWall;
    RegistryObject<RankineStonePressurePlate> pressurePlate;
    RegistryObject<RankineStonePressurePlate> bricksPressurePlate;
    RegistryObject<RankineStoneButton> button;
    RegistryObject<StoneColumnBlock> column;
    RegistryObject<CobbleBlock> cobble;
    RegistryObject<InfestedBlock> infested;

    final StoneType stoneType;
    final boolean vanilla;
    final boolean pillarStone;

    StoneBlocks(StoneType stoneType, boolean pillarStone, boolean vanilla) {
        this.stoneType = stoneType;
        this.pillarStone = pillarStone;
        this.vanilla = vanilla;
    }

    public String getBaseName() {
        return name().toLowerCase(Locale.ROOT);
    }
    public StoneType getStoneType() {return this.stoneType;}
    public boolean isPillarStone() {return this.pillarStone;}
    public boolean isVanillaStone() {return this.vanilla;}

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


    public static void registerBlocks() {
        for (StoneBlocks baseStone : values()) {
            BlockBehaviour.Properties stoneProperties;
            switch (baseStone.stoneType) {
                case SEDIMENTARY:
                    stoneProperties = Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(1.0F, 4.0F);
                    break;
                case MANTLE:
                    stoneProperties = Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(3.0F, 15.0F);
                    break;
                case METAMORPHIC:
                case IGNEOUS:
                default:
                    stoneProperties = Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 6.0F);
            }

            baseStone.stone = RankineBlocks.BLOCKS.register(baseStone.getBaseName(), () -> new RankineStoneBlock(stoneProperties));
            baseStone.polished = RankineBlocks.BLOCKS.register("polished_"+baseStone.getBaseName(), () -> new RankinePolishedStoneBlock(stoneProperties));
            baseStone.bricks = RankineBlocks.BLOCKS.register(baseStone.getBaseName()+"_bricks", () -> new RankineStoneBricksBlock(stoneProperties));
            baseStone.mossyBricks = RankineBlocks.BLOCKS.register("mossy_"+baseStone.getBaseName()+"_bricks", () -> new RankineStoneBricksBlock(stoneProperties));
            baseStone.slab = RankineBlocks.BLOCKS.register(baseStone.getBaseName()+"_slab", () -> new RankineSlabBlock(stoneProperties));
            baseStone.polishedSLab = RankineBlocks.BLOCKS.register("polished_"+baseStone.getBaseName()+"_slab", () -> new RankineSlabBlock(stoneProperties));
            baseStone.bricksSlab = RankineBlocks.BLOCKS.register(baseStone.getBaseName()+"_bricks"+"_slab", () -> new RankineSlabBlock(stoneProperties));
            baseStone.mossyBricksSlab = RankineBlocks.BLOCKS.register("mossy_"+baseStone.getBaseName()+"_bricks"+"_slab", () -> new RankineSlabBlock(stoneProperties));
            baseStone.stairs = RankineBlocks.BLOCKS.register(baseStone.getBaseName()+"_stairs", () -> new RankineStairsBlock(stoneProperties));
            baseStone.polishedStairs = RankineBlocks.BLOCKS.register("polished_"+baseStone.getBaseName()+"_stairs", () -> new RankineStairsBlock(stoneProperties));
            baseStone.bricksStairs = RankineBlocks.BLOCKS.register(baseStone.getBaseName()+"_bricks"+"_stairs", () -> new RankineStairsBlock(stoneProperties));
            baseStone.mossyBricksStairs = RankineBlocks.BLOCKS.register("mossy_"+baseStone.getBaseName()+"_bricks"+"_stairs", () -> new RankineStairsBlock(stoneProperties));
            baseStone.wall = RankineBlocks.BLOCKS.register(baseStone.getBaseName()+"_wall", () -> new RankineWallBlock(stoneProperties));
            baseStone.polishedWall = RankineBlocks.BLOCKS.register("polished_"+baseStone.getBaseName()+"_wall", () -> new RankineWallBlock(stoneProperties));
            baseStone.bricksWall = RankineBlocks.BLOCKS.register(baseStone.getBaseName()+"_bricks"+"_wall", () -> new RankineWallBlock(stoneProperties));
            baseStone.mossyBricksWall = RankineBlocks.BLOCKS.register("mossy_"+baseStone.getBaseName()+"_bricks"+"_wall", () -> new RankineWallBlock(stoneProperties));
            baseStone.pressurePlate = RankineBlocks.BLOCKS.register(baseStone.getBaseName()+"_pressure_plate", RankineStonePressurePlate::new);
            baseStone.bricksPressurePlate = RankineBlocks.BLOCKS.register(baseStone.getBaseName()+"_bricks"+"_pressure_plate", RankineStonePressurePlate::new);
            baseStone.button = RankineBlocks.BLOCKS.register(baseStone.getBaseName()+"_button", RankineStoneButton::new);
            baseStone.column = RankineBlocks.BLOCKS.register(baseStone.getBaseName()+"_column", () -> new StoneColumnBlock(stoneProperties));
            baseStone.cobble = RankineBlocks.BLOCKS.register(baseStone.getBaseName()+"_cobble", CobbleBlock::new);
            baseStone.infested = RankineBlocks.BLOCKS.register("infested_"+baseStone.getBaseName(), () -> new InfestedBlock(baseStone.stone.get(), stoneProperties));
        }

    }

    public static void registerItems() {
        Item.Properties DEF_BUILDING = new Item.Properties().stacksTo(64);
        for (StoneBlocks baseStone : values()) {
            RankineBlocks.ITEMS.register(baseStone.getBaseName(), () -> new BlockItem(baseStone.stone.get(), DEF_BUILDING));
            RankineBlocks.ITEMS.register("polished_"+baseStone.getBaseName(), () -> new BuildingModeBlockItem(baseStone.polished.get(), DEF_BUILDING));
            RankineBlocks.ITEMS.register(baseStone.getBaseName()+"_bricks", () -> new BuildingModeBlockItem(baseStone.bricks.get(), DEF_BUILDING));
            RankineBlocks.ITEMS.register("mossy_"+baseStone.getBaseName()+"_bricks", () -> new BuildingModeBlockItem(baseStone.mossyBricks.get(), DEF_BUILDING));
            RankineBlocks.ITEMS.register(baseStone.getBaseName()+"_slab", () -> new BlockItem(baseStone.slab.get(), DEF_BUILDING));
            RankineBlocks.ITEMS.register("polished_"+baseStone.getBaseName()+"_slab", () -> new BlockItem(baseStone.polishedSLab.get(), DEF_BUILDING));
            RankineBlocks.ITEMS.register(baseStone.getBaseName()+"_bricks"+"_slab", () -> new BlockItem(baseStone.bricksSlab.get(), DEF_BUILDING));
            RankineBlocks.ITEMS.register("mossy_"+baseStone.getBaseName()+"_bricks"+"_slab", () -> new BlockItem(baseStone.mossyBricksSlab.get(), DEF_BUILDING));
            RankineBlocks.ITEMS.register(baseStone.getBaseName()+"_stairs", () -> new BlockItem(baseStone.stairs.get(), DEF_BUILDING));
            RankineBlocks.ITEMS.register("polished_"+baseStone.getBaseName()+"_stairs", () -> new BlockItem(baseStone.polishedStairs.get(), DEF_BUILDING));
            RankineBlocks.ITEMS.register(baseStone.getBaseName()+"_bricks"+"_stairs", () -> new BlockItem(baseStone.bricksStairs.get(), DEF_BUILDING));
            RankineBlocks.ITEMS.register("mossy_"+baseStone.getBaseName()+"_bricks"+"_stairs", () -> new BlockItem(baseStone.mossyBricksStairs.get(), DEF_BUILDING));
            RankineBlocks.ITEMS.register(baseStone.getBaseName()+"_wall", () -> new BlockItem(baseStone.wall.get(), DEF_BUILDING));
            RankineBlocks.ITEMS.register("polished_"+baseStone.getBaseName()+"_wall", () -> new BlockItem(baseStone.polishedWall.get(), DEF_BUILDING));
            RankineBlocks.ITEMS.register(baseStone.getBaseName()+"_bricks"+"_wall", () -> new BlockItem(baseStone.bricksWall.get(), DEF_BUILDING));
            RankineBlocks.ITEMS.register("mossy_"+baseStone.getBaseName()+"_bricks"+"_wall", () -> new BlockItem(baseStone.mossyBricksWall.get(), DEF_BUILDING));
            RankineBlocks.ITEMS.register(baseStone.getBaseName()+"_pressure_plate", () -> new BlockItem(baseStone.pressurePlate.get(), DEF_BUILDING));
            RankineBlocks.ITEMS.register(baseStone.getBaseName()+"_bricks"+"_pressure_plate", () -> new BlockItem(baseStone.bricksPressurePlate.get(), DEF_BUILDING));
            RankineBlocks.ITEMS.register(baseStone.getBaseName()+"_button", () -> new BlockItem(baseStone.button.get(), DEF_BUILDING));
            RankineBlocks.ITEMS.register(baseStone.getBaseName()+"_column", () -> new BlockItem(baseStone.column.get(), DEF_BUILDING));
            RankineBlocks.ITEMS.register(baseStone.getBaseName()+"_cobble", () -> new BlockItem(baseStone.cobble.get(), DEF_BUILDING));
            RankineBlocks.ITEMS.register("infested_"+baseStone.getBaseName(), () -> new BlockItem(baseStone.infested.get(), DEF_BUILDING));
        }

    }




}
