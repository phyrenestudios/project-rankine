package com.cannolicatfish.rankine.blocks;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.BlockSetType;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

// PORT-TODO(RankineWoodenButton): Forge's WoodButtonBlock subclass is gone; vanilla now builds all stone/wood
// buttons directly from ButtonBlock(BlockSetType, ticksToStayPressed, Properties). Ported to extend ButtonBlock
// directly with BlockSetType.OAK and vanilla's wood-button value of 30 ticks-to-stay-pressed, matching
// Blocks#woodenButton() in vanilla registration.
public class RankineWoodenButton extends ButtonBlock {
    public static final MapCodec<RankineWoodenButton> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(propertiesCodec()).apply(instance, RankineWoodenButton::new)
    );

    @SuppressWarnings("unchecked")
    @Override
    public MapCodec<ButtonBlock> codec() {
        return (MapCodec<ButtonBlock>) (MapCodec<?>) CODEC;
    }

    public RankineWoodenButton(Properties properties) {
        super(BlockSetType.OAK, 30, properties);
    }

    public RankineWoodenButton() {
        this(Properties.of().noCollission().strength(0.5F).sound(SoundType.WOOD));
    }
}

