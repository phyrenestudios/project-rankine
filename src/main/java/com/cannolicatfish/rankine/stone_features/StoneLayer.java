package com.cannolicatfish.rankine.stone_features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

public final class StoneLayer {

    private ResourceLocation name;
    private final ResourceLocation layerBlock;

    public static final Codec<StoneLayer> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    ResourceLocation.CODEC.fieldOf("layerBlock").forGetter(l -> l.layerBlock)
            ).apply(instance, StoneLayer::new));


    public StoneLayer(ResourceLocation layerBlockIn) {
        this.layerBlock = layerBlockIn;
    }

    public StoneLayer setRegistryName(ResourceLocation name) {
        this.name = name;
        return this;
    }

    @Nullable
    public ResourceLocation getRegistryName() {
        return name;
    }


    public Block geLayerBlock() {
        return ForgeRegistries.BLOCKS.getValue(layerBlock);
    }


}
