package com.cannolicatfish.rankine.entities;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineEntityTypes;
import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

public class RopeCoilArrowEntity extends AbstractArrowEntity {

    public RopeCoilArrowEntity(World worldIn, LivingEntity shooter) {
        super(RankineEntityTypes.ROPE_COIL_ARROW, shooter, worldIn);
    }

    @OnlyIn(Dist.CLIENT)
    public RopeCoilArrowEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world, EntityType<RopeCoilArrowEntity> e) {
        super(e, world);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(RankineItems.ROPE_COIL_ARROW.get());
    }


    @Override
    protected void onInsideBlock(BlockState state) {

        if (this.inGround) {
            World worldIn = this.world;
            int rope = 1;
            if (this.getShooter() instanceof PlayerEntity) {
                PlayerEntity player = ((PlayerEntity) this.getShooter());
                rope += player.getHeldItemOffhand().getItem() == RankineItems.ROPE.get() ? player.getHeldItemOffhand().getCount() : 0;
            }
            int ropeCount = -1;
            for (int i = 0; i < rope; i++) {
                if (worldIn.isAirBlock(this.getPosition().down(i))) {
                    worldIn.setBlockState(this.getPosition().down(i), RankineBlocks.ROPE.get().getDefaultState());
                    ropeCount++;
                } else {
                    break;
                }
            }

            if (this.getShooter() instanceof PlayerEntity && !((PlayerEntity)this.getShooter()).isCreative() && ropeCount > 0) {
                ((PlayerEntity)this.getShooter()).getHeldItemOffhand().shrink(ropeCount);
            }
            this.remove();
        }

        super.onInsideBlock(state);
    }
}
