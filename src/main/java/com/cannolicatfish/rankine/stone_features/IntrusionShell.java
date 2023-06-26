package com.cannolicatfish.rankine.stone_features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

public final class IntrusionShell {

    private ResourceLocation name;
    private final ResourceLocation parentStone;
    private final ResourceLocation shellStone;

    public static final Codec<IntrusionShell> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    ResourceLocation.CODEC.fieldOf("parentStone").forGetter(l -> l.parentStone),
                    ResourceLocation.CODEC.fieldOf("shellStone").forGetter(l -> l.shellStone)
            ).apply(instance, IntrusionShell::new));


    public IntrusionShell(ResourceLocation parentStoneIn, ResourceLocation shellStoneIn) {
        this.parentStone = parentStoneIn;
        this.shellStone = shellStoneIn;
    }

    public IntrusionShell setRegistryName(ResourceLocation name) {
        this.name = name;
        return this;
    }

    @Nullable
    public ResourceLocation getRegistryName() {
        return name;
    }


    public Block getParentBlock() {
        return ForgeRegistries.BLOCKS.getValue(parentStone);
    }
    public Block getShellBlock() {
        return ForgeRegistries.BLOCKS.getValue(shellStone);
    }


}
