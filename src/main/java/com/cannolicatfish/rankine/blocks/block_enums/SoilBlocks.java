package com.cannolicatfish.rankine.blocks.block_enums;

import com.cannolicatfish.rankine.blocks.*;
import com.cannolicatfish.rankine.blocks.farmland.RankineFarmlandBlock;
import com.cannolicatfish.rankine.init.RankineBlocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MudBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public enum SoilBlocks {

    HUMUS(),
    LOAM(),
    SILTY_LOAM(),
    LOAMY_SAND(),
    SANDY_LOAM(),
    CLAY_LOAM(),
    SANDY_CLAY_LOAM(),
    SILTY_CLAY_LOAM(),
    SANDY_CLAY(),
    SILTY_CLAY();

    RegistryObject<SoilBlock> soil;
    RegistryObject<CoarseSoilBlock> coarseSoil;
    RegistryObject<RootedSoilBlock> rootedSoil;
    RegistryObject<MudBlock> mud;
    RegistryObject<SoilPathBlock> path;
    RegistryObject<RankineFarmlandBlock> farmland;
    RegistryObject<GrassySoilBlock> grass;
    RegistryObject<RankinePodzolBlock> podzol;
    RegistryObject<RankineMyceliumBlock> mycelium;
    //permafrost?
    //underground mycelium?
    //mangove roots?


    SoilBlocks() {}

    public String getName() {
        return name().toLowerCase(Locale.ROOT);
    }
    public SoilBlock getSoilBlock() {return this.soil.get();}
    public CoarseSoilBlock getCoarseSoilBlock() {return this.coarseSoil.get();}
    public RootedSoilBlock getRootedSoilBlock() {return this.rootedSoil.get();}
    public MudBlock getMudBlock() {return this.mud.get();}
    public SoilPathBlock getPathBlock() {return this.path.get();}
    public GrassySoilBlock getGrassBlock() {return this.grass.get();}
    public RankinePodzolBlock getPodzolBlock() {return this.podzol.get();}
    public RankineMyceliumBlock getMyceliumBlock() {return this.mycelium.get();}
    public RankineFarmlandBlock getFarmlandBlock() {return this.farmland.get();}
    public List<Block> getAlBllocks() {
        return Arrays.asList(
                this.getSoilBlock(),
                this.getCoarseSoilBlock(),
                this.getRootedSoilBlock(),
                this.getMudBlock(),
                this.getPathBlock(),
                this.getGrassBlock(),
                this.getPodzolBlock(),
                this.getMyceliumBlock(),
                this.getFarmlandBlock()
        );
    }

    public static SoilBlocks getSoilFromBlock(Block blockIn) {
        for (SoilBlocks base : SoilBlocks.values()) {
            for (Block blk : base.getAlBllocks()) {
                if (blk == blockIn) return base;
            }
        }
        return null;
    }

    public static void registerBlocks() {
        for (SoilBlocks baseSoilBlock : values()) {
            baseSoilBlock.grass =  RankineBlocks.BLOCKS.register(baseSoilBlock.getName()+"_grass_block", () -> new GrassySoilBlock(baseSoilBlock));
            baseSoilBlock.podzol =  RankineBlocks.BLOCKS.register(baseSoilBlock.getName()+"_podzol", RankinePodzolBlock::new);
            baseSoilBlock.mycelium =  RankineBlocks.BLOCKS.register(baseSoilBlock.getName()+"_mycelium", () -> new RankineMyceliumBlock(baseSoilBlock));
            baseSoilBlock.path =  RankineBlocks.BLOCKS.register(baseSoilBlock.getName()+"_path", () -> new SoilPathBlock(baseSoilBlock));
            baseSoilBlock.soil =  RankineBlocks.BLOCKS.register(baseSoilBlock.getName(), SoilBlock::new);
            baseSoilBlock.coarseSoil =  RankineBlocks.BLOCKS.register("coarse_"+baseSoilBlock.getName(), CoarseSoilBlock::new);
            baseSoilBlock.rootedSoil =  RankineBlocks.BLOCKS.register("rooted_"+baseSoilBlock.getName(), RootedSoilBlock::new);
            baseSoilBlock.farmland =  RankineBlocks.BLOCKS.register(baseSoilBlock.getName()+"_farmland", () -> new RankineFarmlandBlock(BlockBehaviour.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.5F).sound(SoundType.GRAVEL)));
            baseSoilBlock.mud =  RankineBlocks.BLOCKS.register(baseSoilBlock.getName()+"_mud", () -> new MudBlock(BlockBehaviour.Properties.copy(baseSoilBlock.soil.get()).color(MaterialColor.TERRACOTTA_BROWN).sound(SoundType.MUD)));
        }
    }

    public static void registerItems() {
        Item.Properties DEF_BUILDING = new Item.Properties().stacksTo(64);
        for (SoilBlocks baseSoilBlock : values()) {
            RankineBlocks.ITEMS.register(baseSoilBlock.getName()+"_grass_block", () -> new BlockItem(baseSoilBlock.grass.get(), DEF_BUILDING));
            RankineBlocks.ITEMS.register(baseSoilBlock.getName()+"_podzol", () -> new BlockItem(baseSoilBlock.podzol.get(), DEF_BUILDING));
            RankineBlocks.ITEMS.register(baseSoilBlock.getName()+"_mycelium", () -> new BlockItem(baseSoilBlock.mycelium.get(), DEF_BUILDING));
            RankineBlocks.ITEMS.register(baseSoilBlock.getName()+"_path", () -> new BlockItem(baseSoilBlock.path.get(), DEF_BUILDING));
            RankineBlocks.ITEMS.register(baseSoilBlock.getName(), () -> new BlockItem(baseSoilBlock.soil.get(), DEF_BUILDING));
            RankineBlocks.ITEMS.register("coarse_"+baseSoilBlock.getName(), () -> new BlockItem(baseSoilBlock.coarseSoil.get(), DEF_BUILDING));
            RankineBlocks.ITEMS.register("rooted_"+baseSoilBlock.getName(), () -> new BlockItem(baseSoilBlock.rootedSoil.get(), DEF_BUILDING));
            RankineBlocks.ITEMS.register(baseSoilBlock.getName()+"_mud", () -> new BlockItem(baseSoilBlock.mud.get(), DEF_BUILDING));
            RankineBlocks.ITEMS.register(baseSoilBlock.getName()+"_farmland", () -> new BlockItem(baseSoilBlock.farmland.get(), DEF_BUILDING));
        }
    }




}
