package com.cannolicatfish.rankine.blocks.block_groups;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.blocks.*;
import com.cannolicatfish.rankine.blocks.buildingmodes.RankineBookshelvesBlock;
import com.cannolicatfish.rankine.blocks.buildingmodes.RankinePlanksBlock;
import com.cannolicatfish.rankine.blocks.signs.RankineSignBlock;
import com.cannolicatfish.rankine.blocks.signs.RankineWallSignBlock;
import com.cannolicatfish.rankine.entities.RankineBoatEntity;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.items.BuildingModeBlockItem;
import com.cannolicatfish.rankine.items.RankineBoatItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.List;

public class RankineWood {

    private final String registryName;
    private boolean hasLogs;
    private boolean isTree;
    private WoodType woodType;
    private RegistryObject<RankineLogBlock> log;
    private RegistryObject<RankineLogBlock> strippedLog;
    private RegistryObject<RankineLogBlock> wood;
    private RegistryObject<RankineLogBlock> strippedWood;
    private RegistryObject<HollowLogBlock> hollowLog;
    private RegistryObject<RankinePlanksBlock> planks;
    private RegistryObject<RankineWoodenSlabBlock> slab;
    private RegistryObject<RankineWoodenStairsBlock> stairs;
    private RegistryObject<RankineWoodenFence> fence;
    private RegistryObject<RankineWoodenFenceGate> fenceGate;
    private RegistryObject<RankineWoodenDoor> door;
    private RegistryObject<RankineWoodenTrapDoor> trapdoor;
    private RegistryObject<RankineWoodenPressurePlate> pressurePlate;
    private RegistryObject<RankineWoodenButton> button;
    private RegistryObject<RankineSignBlock> sign;
    private RegistryObject<RankineWallSignBlock> wallSign;
    private RegistryObject<RankineBookshelvesBlock> bookshelf;
    private RegistryObject<RankineLeavesBlock> leaves;
    private RegistryObject<LeafLitterBlock> leafLitter;
    private RegistryObject<RankineSaplingBlock> sapling;
    private RegistryObject<FlowerPotBlock> pottedSapling;
    private RegistryObject<SignItem> signItem;
    private RegistryObject<Item> boat;


    public RankineWood(String registryName) {
        this.registryName = registryName;
    }

    public static RankineWood newWoodReg(String baseName, float strength, boolean hasLogs, boolean isTree, int saplingType, AbstractTreeGrower treeGrower) {

        Block.Properties woodProps = Block.Properties.of(Material.WOOD).sound(SoundType.WOOD).requiresCorrectToolForDrops().strength(strength, 1.5f*strength);

        RankineWood newWood = new RankineWood(baseName);
        newWood.hasLogs = hasLogs;
        newWood.isTree = isTree;
        newWood.woodType = WoodType.create("rankine:"+baseName);
        if (newWood.hasLogs) {
            newWood.log = RankineBlocks.BLOCKS.register(baseName + "_log", () -> new RankineLogBlock(woodProps));
            newWood.strippedLog = RankineBlocks.BLOCKS.register("stripped_" + baseName + "_log", () -> new RankineLogBlock(woodProps));
            newWood.wood = RankineBlocks.BLOCKS.register(baseName + "_wood", () -> new RankineLogBlock(woodProps));
            newWood.strippedWood = RankineBlocks.BLOCKS.register("stripped_" + baseName + "_wood", () -> new RankineLogBlock(woodProps));
            newWood.hollowLog = RankineBlocks.BLOCKS.register("hollow_" + baseName + "_log", HollowLogBlock::new);
        }
        if (newWood.getBaseName().equals("bamboo_culms")) {
            newWood.planks = RankineBlocks.BLOCKS.register(baseName, () -> new RankinePlanksBlock(woodProps));
        } else {
            newWood.planks = RankineBlocks.BLOCKS.register(baseName+"_planks", () -> new RankinePlanksBlock(woodProps));
        }
        newWood.slab = RankineBlocks.BLOCKS.register(baseName+"_slab", () -> new RankineWoodenSlabBlock(woodProps));
        newWood.stairs = RankineBlocks.BLOCKS.register(baseName+"_stairs", () -> new RankineWoodenStairsBlock(woodProps));
        newWood.fence = RankineBlocks.BLOCKS.register(baseName+"_fence", () -> new RankineWoodenFence(woodProps));
        newWood.fenceGate = RankineBlocks.BLOCKS.register(baseName+"_fence_gate", () -> new RankineWoodenFenceGate(woodProps));
        newWood.door = RankineBlocks.BLOCKS.register(baseName+"_door", RankineWoodenDoor::new);
        newWood.trapdoor = RankineBlocks.BLOCKS.register(baseName+"_trapdoor", RankineWoodenTrapDoor::new);
        newWood.pressurePlate = RankineBlocks.BLOCKS.register(baseName+"_pressure_plate", RankineWoodenPressurePlate::new);
        newWood.button = RankineBlocks.BLOCKS.register(baseName+"_button", RankineWoodenButton::new);
        newWood.sign = RankineBlocks.BLOCKS.register(baseName+"_sign", () -> new RankineSignBlock(newWood.woodType));
        newWood.wallSign = RankineBlocks.BLOCKS.register(baseName+"_wall_sign", () -> new RankineWallSignBlock(newWood.woodType));
        newWood.bookshelf = RankineBlocks.BLOCKS.register(baseName+"_bookshelf", RankineBookshelvesBlock::new);
        if (newWood.isTree) {
            newWood.leaves = RankineBlocks.BLOCKS.register(baseName + "_leaves", RankineLeavesBlock::new);
            newWood.leafLitter = RankineBlocks.BLOCKS.register(baseName + "_leaf_litter", LeafLitterBlock::new);
            newWood.sapling = RankineBlocks.BLOCKS.register(baseName + "_sapling", () -> new RankineSaplingBlock(treeGrower, Block.Properties.of(Material.PLANT).noCollission().randomTicks().strength(0).sound(SoundType.GRASS), saplingType));
            newWood.pottedSapling = RankineBlocks.BLOCKS.register("potted_"+baseName+"_sapling", () -> new FlowerPotBlock(null, newWood.sapling, Block.Properties.of(Material.DECORATION).strength(0.0f).noOcclusion()));
        }
        
        Item.Properties DEF_BUILDING = new Item.Properties().stacksTo(64).tab(ProjectRankine.setup.rankineBiota);
        if (newWood.hasLogs) {
             RankineBlocks.ITEMS.register(baseName + "_log", () -> new BlockItem(newWood.log.get(), DEF_BUILDING));
             RankineBlocks.ITEMS.register("stripped_" + baseName + "_log", () -> new BlockItem(newWood.strippedLog.get(), DEF_BUILDING));
             RankineBlocks.ITEMS.register(baseName + "_wood", () -> new BlockItem(newWood.wood.get(), DEF_BUILDING));
             RankineBlocks.ITEMS.register("stripped_" + baseName + "_wood", () -> new BlockItem(newWood.strippedWood.get(), DEF_BUILDING));
             RankineBlocks.ITEMS.register("hollow_" + baseName + "_log", () -> new BlockItem(newWood.hollowLog.get(), DEF_BUILDING));
        }
        if (newWood.getBaseName().equals("bamboo_culms")) {
             RankineBlocks.ITEMS.register(baseName, () -> new BlockItem(newWood.planks.get(), DEF_BUILDING));
        } else {
             RankineBlocks.ITEMS.register(baseName+"_planks", () -> new BuildingModeBlockItem(newWood.planks.get(), DEF_BUILDING));
        }
         RankineBlocks.ITEMS.register(baseName+"_slab", () -> new BlockItem(newWood.slab.get(), DEF_BUILDING));
         RankineBlocks.ITEMS.register(baseName+"_stairs", () -> new BlockItem(newWood.stairs.get(), DEF_BUILDING));
         RankineBlocks.ITEMS.register(baseName+"_fence", () -> new BlockItem(newWood.fence.get(), DEF_BUILDING));
         RankineBlocks.ITEMS.register(baseName+"_fence_gate", () -> new BlockItem(newWood.fenceGate.get(), DEF_BUILDING));
         RankineBlocks.ITEMS.register(baseName+"_door", () -> new BlockItem(newWood.door.get(), DEF_BUILDING));
         RankineBlocks.ITEMS.register(baseName+"_trapdoor", () -> new BlockItem(newWood.trapdoor.get(), DEF_BUILDING));
         RankineBlocks.ITEMS.register(baseName+"_pressure_plate", () -> new BlockItem(newWood.pressurePlate.get(), DEF_BUILDING));
         RankineBlocks.ITEMS.register(baseName+"_button", () -> new BlockItem(newWood.button.get(), DEF_BUILDING));
        newWood.signItem =  RankineBlocks.ITEMS.register(baseName+"_sign", () -> new SignItem(new Item.Properties().stacksTo(16).tab(ProjectRankine.setup.rankineBiota),newWood.sign.get(),newWood.wallSign.get()));
         RankineBlocks.ITEMS.register(baseName+"_bookshelf", () -> new BuildingModeBlockItem(newWood.bookshelf.get(), DEF_BUILDING));
        newWood.boat =  RankineBlocks.ITEMS.register(baseName+"_boat", () -> new RankineBoatItem(RankineBoatEntity.Type.getTypeFromString(baseName), new Item.Properties().stacksTo(1).tab(ProjectRankine.setup.rankineBiota)));
        if (newWood.isTree) {
             RankineBlocks.ITEMS.register(baseName + "_leaves", () -> new BuildingModeBlockItem(newWood.leaves.get(), DEF_BUILDING));
             RankineBlocks.ITEMS.register(baseName + "_leaf_litter", () -> new BuildingModeBlockItem(newWood.leafLitter.get(), DEF_BUILDING));
             RankineBlocks.ITEMS.register(baseName + "_sapling", () -> new BuildingModeBlockItem(newWood.sapling.get(), DEF_BUILDING));
        }

        return newWood;
    }
    

    public List<Block> getWoodBlocks() {
        return Arrays.asList(
                this.getLog(),
                this.getStrippedLog(),
                this.getWood(),
                this.getStrippedWood(),
                this.getHollowLog(),
                this.getPlanks(),
                this.getSlab(),
                this.getStairs(),
                this.getFence(),
                this.getFenceGate(),
                this.getDoor(),
                this.getTrapdoor(),
                this.getPressurePlate(),
                this.getButton(),
                this.getSign(),
                this.getWallSign(),
                this.getBookshelf(),
                this.getLeaves(),
                this.getLeafLitter(),
                this.getSapling(),
                this.getPottedSapling()
                );
    }


    public String getBaseName() {return registryName;}
    public boolean hasLogs() {return this.hasLogs;}
    public boolean isTree() {return this.isTree;}
    public WoodType getWoodType() {return this.woodType;}
    public RankineLogBlock getLog() {return this.log == null ? null : this.log.get();}
    public RankineLogBlock getStrippedLog() {return this.strippedLog == null ? null : this.strippedLog.get();}
    public RankineLogBlock getWood() {return this.wood == null ? null : this.wood.get();}
    public RankineLogBlock getStrippedWood() {return this.strippedWood == null ? null : this.strippedWood.get();}
    public HollowLogBlock getHollowLog() {return this.hollowLog == null ? null : this.hollowLog.get();}
    public RankinePlanksBlock getPlanks() {return this.planks.get();}
    public RankineWoodenSlabBlock getSlab() {return this.slab.get();}
    public RankineWoodenStairsBlock getStairs() {return this.stairs.get();}
    public RankineWoodenFence getFence() {return this.fence.get();}
    public RankineWoodenFenceGate getFenceGate() {return this.fenceGate.get();}
    public RankineWoodenDoor getDoor() {return this.door.get();}
    public RankineWoodenTrapDoor getTrapdoor() {return this.trapdoor.get();}
    public RankineWoodenPressurePlate getPressurePlate() {return this.pressurePlate.get();}
    public RankineWoodenButton getButton() {return this.button.get();}
    public RankineSignBlock getSign() {return this.sign.get();}
    public RankineWallSignBlock getWallSign() {return this.wallSign.get();}
    public RankineBookshelvesBlock getBookshelf() {return this.bookshelf.get();}
    public RankineLeavesBlock getLeaves() {return this.leaves == null ? null :  this.leaves.get();}
    public LeafLitterBlock getLeafLitter() {return this.leafLitter == null ? null :  this.leafLitter.get();}
    public RankineSaplingBlock getSapling() {return this.sapling == null ? null :  this.sapling.get();}
    public FlowerPotBlock getPottedSapling() {return this.pottedSapling == null ? null :  this.pottedSapling.get();}
    public Item getSignItem() {return this.signItem.get();}
    public Item getBoat() {return this.boat.get();}


}
