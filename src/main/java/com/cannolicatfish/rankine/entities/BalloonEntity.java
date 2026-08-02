package com.cannolicatfish.rankine.entities;

import com.cannolicatfish.rankine.init.RankineBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public class BalloonEntity extends Entity {
    private BlockState blockState = RankineBlocks.VULCANIZED_RUBBER_BLOCK.get().defaultBlockState();
    public int time;
    public float yMovement = 0.2f;
    @Nullable
    public CompoundTag blockData;
    protected static final EntityDataAccessor<BlockPos> DATA_START_POS = SynchedEntityData.defineId(BalloonEntity.class, EntityDataSerializers.BLOCK_POS);

    public BalloonEntity(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(DATA_START_POS, BlockPos.ZERO);
    }

    // 1.21.1: getAddEntityPacket/NetworkHooks override removed — vanilla handles spawn packets.

    public BlockState getBlockState() {
        return this.blockState;
    }

    public void setStartPos(BlockPos p_31960_) {
        this.entityData.set(DATA_START_POS, p_31960_);
    }

    public BlockPos getStartPos() {
        return this.entityData.get(DATA_START_POS);
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public void tick() {
        if (this.blockState.isAir()) {
            this.discard();
        } else {
            ++this.time;
            if (!this.isNoGravity()) {
                this.setDeltaMovement(new Vec3(0.0D, yMovement, 0.0D));
                List<LivingEntity> entitiesOnBlock = this.level().getEntitiesOfClass(LivingEntity.class, AABB.encapsulatingFullBlocks(this.blockPosition(), this.blockPosition().above()).expandTowards(1, 1, 1), (e) -> e instanceof Mob || e instanceof Player);
                for (LivingEntity i : entitiesOnBlock) {
                    i.setDeltaMovement(new Vec3(i.getDeltaMovement().x(), yMovement, i.getDeltaMovement().z()));
                }
            }

            this.move(MoverType.SELF, this.getDeltaMovement());
            if (!this.level().isClientSide) {
                BlockPos blockpos = this.blockPosition();
                if (((blockpos.getY() <= this.level().getMinBuildHeight() || blockpos.getY() > this.level().getMaxBuildHeight()) || this.time > 600) ||
                        ((this.level().getBlockState(this.blockPosition().above())).isFaceSturdy(this.level(),this.blockPosition().above(), Direction.DOWN) && this.yMovement > 0) ||
                        ((this.level().getBlockState(this.blockPosition().below())).isFaceSturdy(this.level(),this.blockPosition().above(),Direction.UP) && this.yMovement < 0)) {
                    this.discard();
                }
            }
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag p_31973_) {
        p_31973_.put("BlockState", NbtUtils.writeBlockState(this.blockState));
        p_31973_.putInt("Time", this.time);
        p_31973_.putFloat("YMovement",this.yMovement);

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag p_31964_) {
        // 1.21.1: readBlockState needs a HolderGetter<Block>
        this.blockState = NbtUtils.readBlockState(this.level().holderLookup(Registries.BLOCK), p_31964_.getCompound("BlockState"));
        this.time = p_31964_.getInt("Time");
        this.yMovement = p_31964_.getInt("YMovement");

        if (this.blockState.isAir()) {
            this.blockState = RankineBlocks.VULCANIZED_RUBBER_BLOCK.get().defaultBlockState();
        }

    }
}
