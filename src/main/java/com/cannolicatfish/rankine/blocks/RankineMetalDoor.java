package com.cannolicatfish.rankine.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;

// PORT-NOTE: Material/MaterialColor are gone -> .mapColor(MapColor.METAL). DoorBlock now requires a BlockSetType;
// BlockSetType.IRON reproduces the 1.18.2 behavior that Material.METAL doors gave us (cannot be opened by hand,
// iron open/close sounds), so the explicit .sound(SoundType.METAL) is now redundant but kept for parity.
public class RankineMetalDoor extends DoorBlock
{
    public static final MapCodec<RankineMetalDoor> CODEC = MapCodec.unit(RankineMetalDoor::new);

    @Override
    public MapCodec<? extends RankineMetalDoor> codec() {
        return CODEC;
    }

    public RankineMetalDoor() {
        super(BlockSetType.IRON, BlockBehaviour.Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(5.0F).sound(SoundType.METAL).noOcclusion());
    }
}
