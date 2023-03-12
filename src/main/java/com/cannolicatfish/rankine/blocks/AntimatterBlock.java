package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.entities.SphericalExplosion;
import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.event.ForgeEventFactory;

public class AntimatterBlock extends Block {
    public AntimatterBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        explode(worldIn, pos, null);
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (player.getItemInHand(handIn).getItem() != RankineItems.PENNING_TRAP.get()) explode(worldIn, pos, player);
        return super.use(state, worldIn, pos, player, handIn, hit);
    }

    @Override
    public void entityInside(BlockState p_60495_, Level levelIn, BlockPos posIn, Entity entityIn) {
        explode(levelIn, posIn, entityIn);
    }

    public static void explode(Level levelIn, BlockPos posIn, Entity entityIn) {
        SphericalExplosion explosion = new SphericalExplosion(levelIn, entityIn, null, null, posIn.getX(), posIn.getY(), posIn.getZ(), 12f, false, Explosion.BlockInteraction.DESTROY, ParticleTypes.PORTAL);
        if (!ForgeEventFactory.onExplosionStart(levelIn, explosion)) {
            if (!levelIn.isClientSide) explosion.explode();
            explosion.finalizeExplosion(true);
            levelIn.removeBlock(posIn, false);
        }
    }
}
