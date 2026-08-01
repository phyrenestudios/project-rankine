package com.cannolicatfish.rankine.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.BlockSetType;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

// PORT-TODO(RankineWoodenDoor): DoorBlock's constructor now requires a BlockSetType (which also supplies the
// sound type internally via BlockSetType#soundType()), so the old explicit .sound(SoundType.WOOD) is redundant
// but harmless. `Block.Properties` no longer exists as a type alias - using BlockBehaviour.Properties directly.
public class RankineWoodenDoor extends DoorBlock
{
    public static final MapCodec<RankineWoodenDoor> CODEC = MapCodec.unit(RankineWoodenDoor::new);

    @Override
    public MapCodec<? extends RankineWoodenDoor> codec() {
        return CODEC;
    }

    public RankineWoodenDoor() {
        super(BlockSetType.OAK, Properties.of().sound(SoundType.WOOD).strength(3.0F).noOcclusion());
    }
}
