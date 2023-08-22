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
    CHONDRITE("chondrite"),
    ENSTATITE_CHONDRITE("enstatite_chondrite"),
    CARBONACEOUS_CHONDRITE("carbonaceous_chondrite"),
    ICY_CHONDRITE("icy_chondrite"),
    ANGRITE("angrite"),
    UREILITE("ureilite"),
    PALLASITE("pallasite"),
    MESOSIDERITE("mesosiderite");


    RegistryObject<Block> block;

    private final String name;

    MeteorBlocks(String name) {
        this.name = name;
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