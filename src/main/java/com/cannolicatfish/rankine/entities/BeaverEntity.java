package com.cannolicatfish.rankine.entities;

import com.cannolicatfish.rankine.init.RankineBlocks;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.FluidState;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class BeaverEntity extends AnimalEntity {
    private static final DataParameter<Optional<BlockState>> CARRIED_BLOCK = EntityDataManager.defineId(BeaverEntity.class, DataSerializers.BLOCK_STATE);
    public BeaverEntity(EntityType<? extends AnimalEntity> p_i50250_1_, World p_i50250_2_) {
        super(p_i50250_1_, p_i50250_2_);
        this.getNavigation().setCanFloat(true);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(CARRIED_BLOCK, Optional.empty());
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(2, new StripLogGoal((double)1.2F, 32, 2));
        this.goalSelector.addGoal(3, new PlaceSticksGoal((double)1.2F, 32, 2));
        this.goalSelector.addGoal(4, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new TemptGoal(this, 1.2D, Ingredient.of(Items.STICK), false));
        this.goalSelector.addGoal(6, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(7, new RandomWalkingGoal(this, 1.2D));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(9, new LookRandomlyGoal(this));
    }

    @Override
    @Nullable
    public AgeableEntity getBreedOffspring(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        return null;
    }

    public static AttributeModifierMap.MutableAttribute createBeaverAttributes() {
        return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 8.0D).add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    public void setHeldBlockState(@Nullable BlockState state) {
        this.entityData.set(CARRIED_BLOCK, Optional.ofNullable(state));
    }

    @Nullable
    public BlockState getHeldBlockState() {
        return this.entityData.get(CARRIED_BLOCK).orElse((BlockState)null);
    }



    protected SoundEvent getAmbientSound() {
        return SoundEvents.DISPENSER_FAIL;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.DISPENSER_LAUNCH;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.DISPENSER_DISPENSE;
    }



    class StripLogGoal extends MoveToBlockGoal {
        private final BeaverEntity beaver;

        public StripLogGoal(double speedIn, int length, int p_i50737_5_) {
            super(BeaverEntity.this, speedIn, length, p_i50737_5_);
            this.beaver = BeaverEntity.this;
        }

        public boolean shouldRecalculatePath() {
            return this.tryTicks % 40 == 0;
        }
        public double acceptedDistance() {
            return 2.0D;
        }

        @Override
        protected boolean isValidTarget(IWorldReader worldIn, BlockPos pos) {
            BlockState blockstate = worldIn.getBlockState(pos);
            ResourceLocation rs = blockstate.getBlock().getRegistryName();
            return blockstate.getBlock().getTags().contains(new ResourceLocation("minecraft:logs")) && rs != null && !rs.getPath().contains("stripped");
        }

        public void tick() {
            BlockPos blockpos = this.getMoveToTarget();
            if (!blockpos.closerThan(this.mob.position(), this.acceptedDistance())) {
                ++this.tryTicks;
                if (this.shouldRecalculatePath()) {
                    this.mob.getNavigation().moveTo((double)((float)blockpos.getX()) + 0.5D, (double)blockpos.getY(), (double)((float)blockpos.getZ()) + 0.5D, this.speedModifier);
                }
            } else {
                this.stripLog();
                --this.tryTicks;
            }
        }

        protected void stripLog() {
            if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(BeaverEntity.this.level, BeaverEntity.this)) {
                BlockState blockstate = BeaverEntity.this.level.getBlockState(this.blockPos);
                ResourceLocation rs = blockstate.getBlock().getRegistryName();
                if (rs != null) {
                    Block strippedLog = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(rs.getNamespace(),"stripped_"+blockstate.getBlock().getRegistryName().getPath()));
                    if (strippedLog != null && strippedLog != Blocks.AIR)
                    {
                        level.setBlock(this.blockPos, strippedLog.defaultBlockState(),3);
                        BeaverEntity.this.heal(1f);
                        this.beaver.setHeldBlockState(RankineBlocks.STICK_BLOCK.get().defaultBlockState());
                        BeaverEntity.this.playSound(SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, 1.0F, 1.0F);
                    }
                }

            }
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return !BeaverEntity.this.isSleeping() && this.beaver.getHeldBlockState() == null && super.canUse();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            super.start();
        }
    }

    class PlaceSticksGoal extends MoveToBlockGoal {
        private final BeaverEntity beaver;
        public PlaceSticksGoal(double p_i50737_2_, int p_i50737_4_, int p_i50737_5_) {
            super(BeaverEntity.this, p_i50737_2_, p_i50737_4_, p_i50737_5_);
            this.beaver = BeaverEntity.this;
        }

        public boolean shouldRecalculatePath() {
            return this.tryTicks % 100 == 0;
        }

        public double acceptedDistance() {
            return 2.0D;
        }

        public boolean canUse() {
            if (this.nextStartTick > 0) {
                --this.nextStartTick;
                return false;
            } else {
                this.nextStartTick = this.nextStartTick(this.mob);
                return this.findNearestBlock() && !BeaverEntity.this.isSleeping() && BeaverEntity.this.getHeldBlockState() == RankineBlocks.STICK_BLOCK.get().defaultBlockState() && super.canUse()
                        && ForgeEventFactory.getMobGriefingEvent(this.beaver.level, this.beaver);
            }
        }



        public void tick() {
            Random random = this.beaver.getRandom();
            World world = this.beaver.level;
            int i = MathHelper.floor(this.beaver.getX() - 1.0D + random.nextDouble() * 2.0D);
            int j = MathHelper.floor(this.beaver.getY() + random.nextDouble() * 2.0D);
            int k = MathHelper.floor(this.beaver.getZ() - 1.0D + random.nextDouble() * 2.0D);
            BlockPos blockpos = new BlockPos(i, j, k);
            BlockState blockstate = world.getBlockState(blockpos);
            BlockPos blockpos1 = blockpos.below();
            BlockState blockstate1 = world.getBlockState(blockpos1);
            BlockState blockstate2 = this.beaver.getHeldBlockState();
            if (blockstate2 != null) {
                blockstate2 = Block.updateFromNeighbourShapes(blockstate2, this.beaver.level, blockpos);
                if (this.canPlaceBlock(world, blockpos, blockstate2, blockstate, blockstate1, blockpos1) && !net.minecraftforge.event.ForgeEventFactory.onBlockPlace(beaver, net.minecraftforge.common.util.BlockSnapshot.create(world.dimension(), world, blockpos1), Direction.DOWN)) {
                    world.setBlock(blockpos, blockstate2, 3);
                    this.beaver.setHeldBlockState((BlockState)null);
                }
            }
        }
        @Override
        protected boolean isValidTarget(IWorldReader worldIn, BlockPos pos) {
            FluidState fluid = worldIn.getFluidState(pos);
            BlockState blockState = worldIn.getBlockState(pos);
            boolean flag = BlockPos.findClosestMatch(pos,1,1,blockPos -> worldIn.getBlockState(blockPos).canOcclude()).isPresent();
            return flag && (fluid.getType() == Fluids.WATER.getSource() || fluid.getType() == Fluids.WATER.getFlowing() || fluid.getType() == Fluids.WATER || blockState.getBlock() == Blocks.WATER);
        }

        private boolean canPlaceBlock(IWorldReader p_220836_1_, BlockPos p_220836_2_, BlockState p_220836_3_, BlockState p_220836_4_, BlockState p_220836_5_, BlockPos p_220836_6_) {
            return p_220836_4_.isAir(p_220836_1_, p_220836_2_) && !p_220836_5_.isAir(p_220836_1_, p_220836_6_) && !p_220836_5_.hasLargeCollisionShape() && p_220836_3_.canSurvive(p_220836_1_, p_220836_2_);
        }
    }

}
