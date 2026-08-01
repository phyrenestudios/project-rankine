package com.cannolicatfish.rankine.entities;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineEntityTypes;
import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class RopeCoilArrowEntity extends AbstractArrow {

    public RopeCoilArrowEntity(EntityType<? extends RopeCoilArrowEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    public RopeCoilArrowEntity(Level worldIn, LivingEntity shooter) {
        super(RankineEntityTypes.ROPE_COIL_ARROW.get(), shooter, worldIn, new ItemStack(RankineItems.ROPE_COIL_ARROW.get()), null);
    }

    // 1.21.1: getAddEntityPacket/NetworkHooks override removed — vanilla handles projectile spawn packets.

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(RankineItems.ROPE_COIL_ARROW.get());
    }

    @Override
    protected void onInsideBlock(BlockState state) {

        if (this.inGround) {
            Level worldIn = this.level();
            int rope = 1;
            if (this.getOwner() instanceof Player) {
                Player player = ((Player) this.getOwner());
                rope += player.getOffhandItem().getItem() == RankineItems.ROPE.get() ? player.getOffhandItem().getCount() : 0;
            }
            int ropeCount = -1;
            for (int i = 0; i < (this.getOwner() instanceof Player ? ((Player) this.getOwner()).isCreative() ? this.getBlockY()-worldIn.getMinBuildHeight() : rope : 1); i++) {
                if (worldIn.isEmptyBlock(this.blockPosition().below(i))) {
                    worldIn.setBlockAndUpdate(this.blockPosition().below(i), RankineBlocks.ROPE.get().defaultBlockState());
                    ropeCount++;
                } else {
                    break;
                }
            }

            if (this.getOwner() instanceof Player && !((Player)this.getOwner()).isCreative() && ropeCount > 0) {
                ((Player)this.getOwner()).getOffhandItem().shrink(ropeCount);
            }
            this.remove(RemovalReason.DISCARDED);
        }

        super.onInsideBlock(state);
    }
}
