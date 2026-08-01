package com.cannolicatfish.rankine.blocks;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.BlockSetType;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

// PORT-TODO(RankineStoneButton): Forge's StoneButtonBlock subclass is gone; vanilla now builds all stone/wood
// buttons directly from ButtonBlock(BlockSetType, ticksToStayPressed, Properties). Ported to extend ButtonBlock
// directly with BlockSetType.STONE and vanilla's stone-button value of 20 ticks-to-stay-pressed, matching
// Blocks#stoneButton() in vanilla registration.
public class RankineStoneButton extends ButtonBlock {
    public static final MapCodec<RankineStoneButton> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(propertiesCodec()).apply(instance, RankineStoneButton::new)
    );

    @SuppressWarnings("unchecked")
    @Override
    public MapCodec<ButtonBlock> codec() {
        return (MapCodec<ButtonBlock>) (MapCodec<?>) CODEC;
    }

    public RankineStoneButton(Properties properties) {
        super(BlockSetType.STONE, 20, properties);
    }

    public RankineStoneButton() {
        this(Properties.of().noCollission().strength(0.5F).sound(SoundType.STONE));
    }
}

