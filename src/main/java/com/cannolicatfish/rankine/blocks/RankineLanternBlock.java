package com.cannolicatfish.rankine.blocks;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.HitResult;

// PORT-NOTE: Material.METAL -> .mapColor(MapColor.METAL) (METAL implied no extra behavior flags).
public class RankineLanternBlock extends LanternBlock {
    // LanternBlock.codec() is not wildcarded (returns the concrete MapCodec<LanternBlock>), so this subclass's
    // codec() must match that exact signature; the cast is safe since generics are erased at runtime.
    public static final MapCodec<RankineLanternBlock> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Codec.INT.fieldOf("alloy_color").forGetter(RankineLanternBlock::getColor)
            ).apply(instance, RankineLanternBlock::new)
    );

    int alloyColor;
    //Item nugget;

    @SuppressWarnings("unchecked")
    @Override
    public MapCodec<LanternBlock> codec() {
        return (MapCodec<LanternBlock>) (MapCodec<?>) CODEC;
    }

    public RankineLanternBlock(int color) {
        super(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(3.5F).sound(SoundType.LANTERN).lightLevel((state) -> 15).noOcclusion());
        //this.nugget = nugget;
        this.alloyColor = color;
    }

    public int getColor() {
        return this.alloyColor;
    }

    // PORT-NOTE: IBlockExtension#getCloneItemStack now takes a LevelReader instead of a BlockGetter.
    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
        return new ItemStack(Items.LANTERN);
    }

    // public Item getNugget() {
   //     return this.nugget;
   // }
}
