package com.cannolicatfish.rankine.blocks.block_enums;

import com.cannolicatfish.rankine.blocks.buildingmodes.RankineBricksBlock;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.items.BuildingModeBlockItem;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.List;

public enum MeteorBlocks implements StringRepresentable {
    CHONDRITE("chondrite", false),
    ENSTATITE_CHONDRITE("enstatite_chondrite", false),
    CARBONACEOUS_CHONDRITE("carbonaceous_chondrite", false),
    ICY_CHONDRITE("icy_chondrite", false),
    ANGRITE("angrite", false),
    UREILITE("ureilite", false),
    PALLASITE("pallasite", false),
    MESOSIDERITE("mesosiderite", false),
    KAMACITE("kamacite", true),
    TAENITE("taenite", true),
    TETRATAENITE("tetrataenite", true);


    RegistryObject<Block> block;

    private final String name;
    private final boolean isMetal;

    MeteorBlocks(String name, boolean isMetal) {
        this.name = name;
        this.isMetal = isMetal;
    }

    public boolean isMetalBased() {
        return this.isMetal;
    }
    public String toString() {
        return this.name;
    }
    public String getSerializedName() {
        return this.name;
    }
    public Block getMeteorBlock() {return this.block.get();}
    public List<Block> getAlBllocks() {
        return Arrays.asList(
                this.getMeteorBlock()
        );
    }
    public static void registerBlocks() {
        for (MeteorBlocks baseBlock : values()) {
            baseBlock.block =  RankineBlocks.BLOCKS.register(baseBlock.getSerializedName(), () -> new RankineBricksBlock(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(2.0F, 6.0F)));
        }
    }

    public static void registerItems() {
        Item.Properties DEF_BUILDING = new Item.Properties().stacksTo(64);
        for (MeteorBlocks baseBlock : values()) {
            RankineBlocks.ITEMS.register(baseBlock.getSerializedName(), () -> new BuildingModeBlockItem(baseBlock.block.get(), DEF_BUILDING));
        }
    }
}