package com.cannolicatfish.rankine.stone_features;

import com.cannolicatfish.rankine.blocks.block_enums.SoilBlocks;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Optional;

public final class SoilLayer {

    private ResourceLocation name;
    private final String soilType;
    private final ResourceLocation oLayer;
    private final ResourceLocation aLayer;
    private final ResourceLocation bLayer;

    public static final Codec<SoilLayer> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.optionalFieldOf("soilType").forGetter(l -> Optional.of(l.soilType)),
                    ResourceLocation.CODEC.optionalFieldOf("O").forGetter(l -> Optional.of(l.oLayer)),
                    ResourceLocation.CODEC.optionalFieldOf("A").forGetter(l -> Optional.of(l.aLayer)),
                    ResourceLocation.CODEC.optionalFieldOf("B").forGetter(l -> Optional.of(l.bLayer))
            ).apply(instance, SoilLayer::new));


    public SoilLayer(Optional<String> soilTypeIn, Optional<ResourceLocation> oLayerIn, Optional<ResourceLocation> aLayerIn, Optional<ResourceLocation> bLayerIn) {
        this.soilType = soilTypeIn.orElse("null");
        this.oLayer = oLayerIn.orElse(new ResourceLocation("minecraft:air"));
        this.aLayer = aLayerIn.orElse(new ResourceLocation("minecraft:air"));
        this.bLayer = bLayerIn.orElse(new ResourceLocation("minecraft:air"));
    }

    public SoilLayer setRegistryName(ResourceLocation name) {
        this.name = name;
        return this;
    }

    @Nullable
    public ResourceLocation getRegistryName() {
        return name;
    }

    public SoilBlocks getSoilType() {
        return soilType.equals("null") ? null : SoilBlocks.valueOf(soilType);
    }
    public Block getOLayer() {
        return ForgeRegistries.BLOCKS.getValue(oLayer);
    }
    public Block getALayer() {
        return ForgeRegistries.BLOCKS.getValue(aLayer);
    }
    public Block getBLayer() {
        return ForgeRegistries.BLOCKS.getValue(bLayer);
    }


}
