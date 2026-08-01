package com.cannolicatfish.rankine.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

// PORT-TODO(RankineWoodenTrapDoor): TrapDoorBlock's constructor now requires a BlockSetType (which also supplies
// the sound type internally), so the old explicit .sound(SoundType.WOOD) is redundant but harmless.
public class RankineWoodenTrapDoor extends TrapDoorBlock {
    public static final MapCodec<RankineWoodenTrapDoor> CODEC = MapCodec.unit(RankineWoodenTrapDoor::new);

    @Override
    public MapCodec<? extends RankineWoodenTrapDoor> codec() {
        return CODEC;
    }

    public RankineWoodenTrapDoor() {
        super(BlockSetType.OAK, Properties.of().sound(SoundType.WOOD).strength(3.0F).noOcclusion());
    }
}
