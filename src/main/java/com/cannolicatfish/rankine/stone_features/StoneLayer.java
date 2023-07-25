package com.cannolicatfish.rankine.stone_features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public final class StoneLayer {

    private ResourceLocation name;
    private final ResourceLocation layerBlock;
    private final boolean upperContinental;
    private final boolean midContinental;
    private final boolean deepContinental;
    private final boolean upperOceanic;
    private final boolean deepOceanic;

    private static final Codec<List<String>> STRING_LIST_CODEC = Codec.STRING.listOf();

    public static final Codec<StoneLayer> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    ResourceLocation.CODEC.fieldOf("layerBlock").forGetter(l -> l.layerBlock),
                    Codec.BOOL.optionalFieldOf("upperContinental").forGetter(l -> Optional.of(l.upperContinental)),
                    Codec.BOOL.optionalFieldOf("midContinental").forGetter(l -> Optional.of(l.midContinental)),
                    Codec.BOOL.optionalFieldOf("deepContinental").forGetter(l -> Optional.of(l.deepContinental)),
                    Codec.BOOL.optionalFieldOf("upperOceanic").forGetter(l -> Optional.of(l.upperOceanic)),
                    Codec.BOOL.optionalFieldOf("deepOceanic").forGetter(l -> Optional.of(l.deepOceanic))
            ).apply(instance, StoneLayer::new));


    public StoneLayer(ResourceLocation layerBlockIn, Optional<Boolean> upperContinentalIn, Optional<Boolean> midContinentalIn, Optional<Boolean> deepContinentalIn, Optional<Boolean> upperOceanicIn, Optional<Boolean> deepOceanicIn) {
        this.layerBlock = layerBlockIn;
        this.upperContinental = upperContinentalIn.orElse(false);
        this.midContinental = midContinentalIn.orElse(false);
        this.deepContinental = deepContinentalIn.orElse(false);
        this.upperOceanic = upperOceanicIn.orElse(false);
        this.deepOceanic = deepOceanicIn.orElse(false);
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

    public boolean isUpperContinental() {
        return upperContinental;
    }
    public boolean isMidContinental() {
        return midContinental;
    }
    public boolean isDeepContinental() {
        return deepContinental;
    }
    public boolean isUpperOceanic() {
        return upperOceanic;
    }
    public boolean isDeepOceanic() {
        return deepOceanic;
    }

    public boolean isOceanic() {
        return deepOceanic || upperOceanic;
    }
    public boolean isDeep() {
        return deepContinental || deepOceanic;
    }



}
