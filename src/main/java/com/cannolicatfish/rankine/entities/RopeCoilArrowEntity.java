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
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(RankineItems.ROPE_COIL_ARROW.get());
    }


    @Override
    protected void onInsideBlock(BlockState state) {

        if (this.inGround) {
            World worldIn = this.level;
            int rope = 1;
            if (this.getOwner() instanceof PlayerEntity) {
                PlayerEntity player = ((PlayerEntity) this.getOwner());
                rope += player.getOffhandItem().getItem() == RankineItems.ROPE.get() ? player.getOffhandItem().getCount() : 0;
            }
            int ropeCount = -1;
            for (int i = 0; i < rope; i++) {
                if (worldIn.isEmptyBlock(this.blockPosition().below(i))) {
                    worldIn.setBlockAndUpdate(this.blockPosition().below(i), RankineBlocks.ROPE.get().defaultBlockState());
                    ropeCount++;
                } else {
                    break;
                }
            }

            if (this.getOwner() instanceof PlayerEntity && !((PlayerEntity)this.getOwner()).isCreative() && ropeCount > 0) {
                ((PlayerEntity)this.getOwner()).getOffhandItem().shrink(ropeCount);
            }
            this.remove();
        }

        super.onInsideBlock(state);
    }
}
