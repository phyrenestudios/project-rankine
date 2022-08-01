package com.cannolicatfish.rankine.entities;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.DirectionalPlaceContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.List;

public class BalloonEntity extends Entity {
    private static final Logger LOGGER = LogUtils.getLogger();
    private BlockState blockState = RankineBlocks.VULCANIZED_RUBBER_BLOCK.get().defaultBlockState();
    public int time;
    private boolean cancelDrop;
    public float yMovement = 0.2f;
    @Nullable
    public CompoundTag blockData;
    protected static final EntityDataAccessor<BlockPos> DATA_START_POS = SynchedEntityData.defineId(BalloonEntity.class, EntityDataSerializers.BLOCK_POS);

    public BalloonEntity(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_START_POS, BlockPos.ZERO);
    }


    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public BlockState getBlockState() {
        return this.blockState;
    }

    public void setStartPos(BlockPos p_31960_) {
        this.entityData.set(DATA_START_POS, p_31960_);
    }

    public BlockPos getStartPos() {
        return this.entityData.get(DATA_START_POS);
    }

    public boolean isPickable() {
        return false;
    }

    public void tick() {
        if (this.blockState.isAir()) {
            this.discard();
        } else {
            Block block = this.blockState.getBlock();
            ++this.time;
            if (!this.isNoGravity()) {
                this.setDeltaMovement(new Vec3(0.0D, yMovement, 0.0D));
                List<LivingEntity> entitiesOnBlock = this.level.getEntitiesOfClass(LivingEntity.class, new AABB(this.blockPosition(), this.blockPosition().above()).expandTowards(1, 1, 1), (e) -> e instanceof Mob || e instanceof Player);
                for (LivingEntity i : entitiesOnBlock) {
                    i.setDeltaMovement(new Vec3(i.getDeltaMovement().x(), yMovement, i.getDeltaMovement().z()));
                }
            }

            this.move(MoverType.SELF, this.getDeltaMovement());
            if (!this.level.isClientSide) {
                BlockPos blockpos = this.blockPosition();
                if (((blockpos.getY() <= this.level.getMinBuildHeight() || blockpos.getY() > this.level.getMaxBuildHeight()) || this.time > 600) ||
                        ((this.level.getBlockState(this.blockPosition().above())).isFaceSturdy(this.level,this.blockPosition().above(),Direction.DOWN) && this.yMovement > 0) ||
                        ((this.level.getBlockState(this.blockPosition().below())).isFaceSturdy(this.level,this.blockPosition().above(),Direction.UP) && this.yMovement < 0)) {
                    this.discard();
                }
            }
        }
    }

    protected void addAdditionalSaveData(CompoundTag p_31973_) {
        p_31973_.put("BlockState", NbtUtils.writeBlockState(this.blockState));
        p_31973_.putInt("Time", this.time);
        p_31973_.putFloat("YMovement",this.yMovement);

    }

    protected void readAdditionalSaveData(CompoundTag p_31964_) {
        this.blockState = NbtUtils.readBlockState(p_31964_.getCompound("BlockState"));
        this.time = p_31964_.getInt("Time");
        this.yMovement = p_31964_.getInt("YMovement");

        if (this.blockState.isAir()) {
            this.blockState = RankineBlocks.VULCANIZED_RUBBER_BLOCK.get().defaultBlockState();
        }

    }
}
