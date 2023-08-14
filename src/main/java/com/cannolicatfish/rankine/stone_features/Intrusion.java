package com.cannolicatfish.rankine.stone_features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Optional;

public final class Intrusion {

    private ResourceLocation name;
    private final ResourceLocation block;
    private final Float quartzMin;
    private final Float quartzMax;
    private final Float alkalinityMin;
    private final Float alkalinityMax;
    private final Float maficMin;
    private final Float maficMax;
    private final ResourceLocation oreBlock;
    private final Float oreChance;

    public static final Codec<Intrusion> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    ResourceLocation.CODEC.fieldOf("block").forGetter(l -> l.block),
                    Codec.FLOAT.fieldOf("quartzMin").forGetter(l -> l.quartzMin),
                    Codec.FLOAT.fieldOf("quartzMax").forGetter(l -> l.quartzMax),
                    Codec.FLOAT.fieldOf("alkalinityMin").forGetter(l -> l.alkalinityMin),
                    Codec.FLOAT.fieldOf("alkalinityMax").forGetter(l -> l.alkalinityMax),
                    Codec.FLOAT.fieldOf("maficMin").forGetter(l -> l.maficMin),
                    Codec.FLOAT.fieldOf("maficMax").forGetter(l -> l.maficMax),
                    ResourceLocation.CODEC.optionalFieldOf("oreBlock").forGetter(l -> Optional.of(l.oreBlock)),
                    Codec.FLOAT.optionalFieldOf("oreChance").forGetter(l -> Optional.of(l.oreChance))
            ).apply(instance, Intrusion::new));


    public Intrusion(ResourceLocation blockIn, Float quartzMin, Float quartzMax, Float alkalinityMin, Float alkalinityMax, Float maficMin, Float maficMax, Optional<ResourceLocation> oreBlock, Optional<Float> oreChance) {
        this.block = blockIn;
        this.quartzMin = quartzMin;
        this.quartzMax = quartzMax;
        this.alkalinityMin = alkalinityMin;
        this.alkalinityMax = alkalinityMax;
        this.maficMin = maficMin;
        this.maficMax = maficMax;
        this.oreBlock = oreBlock.orElse(new ResourceLocation("minecraft:air"));
        this.oreChance = oreChance.orElse(0f);
    }

    public Intrusion setRegistryName(ResourceLocation name) {
        this.name = name;
        return this;
    }

    @Nullable
    public ResourceLocation getRegistryName() {
        return name;
    }


    public Block getBlock() {
        return ForgeRegistries.BLOCKS.getValue(block);
    }
    public float getQuartzMin() {
        return quartzMin;
    }
    public float getQuartzMax() {
        return quartzMax;
    }
    public float getAlkalinityMin() {
        return alkalinityMin;
    }
    public float getAlkalinityMax() {
        return alkalinityMax;
    }
    public float getMaficMin() {
        return maficMin;
    }
    public float getMaficMax() {
        return maficMax;
    }
    public Block getOreBlock() {
        return ForgeRegistries.BLOCKS.getValue(oreBlock);
    }
    public float getOreChance() {
        return oreChance;
    }


}
