package com.cannolicatfish.rankine.blocks.block_enums;

import com.cannolicatfish.rankine.blocks.RankinePointedDripstoneBlock;
import com.cannolicatfish.rankine.init.RankineBlocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.List;

public class RankineDripstone {

    private final String registryName;
    private RegistryObject<Block> dripstone;
    private RegistryObject<RankinePointedDripstoneBlock> pointedDripstone;

    public RankineDripstone(String registryName) {
        this.registryName = registryName;
    }

    public static RankineDripstone newDripstoneReg(String baseName, MaterialColor color) {
        RankineDripstone newDripstone = new RankineDripstone(baseName);
        newDripstone.dripstone =  RankineBlocks.BLOCKS.register(baseName+"_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, color).sound(SoundType.DRIPSTONE_BLOCK).requiresCorrectToolForDrops().strength(1.5F, 1.0F)));
        newDripstone.pointedDripstone = RankineBlocks.BLOCKS.register("pointed_"+baseName, () -> new RankinePointedDripstoneBlock(BlockBehaviour.Properties.of(Material.STONE, color).noOcclusion().sound(SoundType.POINTED_DRIPSTONE).randomTicks().strength(1.5F, 3.0F).dynamicShape(), newDripstone.dripstone.get()));

        Item.Properties DEF_BUILDING = new Item.Properties().stacksTo(64);
        RankineBlocks.ITEMS.register(baseName+"_block", () -> new BlockItem(newDripstone.dripstone.get(), DEF_BUILDING));
        RankineBlocks.ITEMS.register("pointed_"+baseName, () -> new BlockItem(newDripstone.pointedDripstone.get(), DEF_BUILDING));

        return newDripstone;
    }

    public List<Block> getBlocks() {
        return Arrays.asList(
                this.getDripstone(),
                this.getPointedDripstone()
        );
    }

    public String getBaseName() {return registryName;}
    public Block getDripstone() {return this.dripstone.get();}
    public RankinePointedDripstoneBlock getPointedDripstone() {return this.pointedDripstone.get();}

}
