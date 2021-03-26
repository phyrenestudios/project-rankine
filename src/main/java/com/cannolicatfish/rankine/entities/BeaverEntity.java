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
    private static final DataParameter<Optional<BlockState>> CARRIED_BLOCK = EntityDataManager.createKey(BeaverEntity.class, DataSerializers.OPTIONAL_BLOCK_STATE);
    public BeaverEntity(EntityType<? extends AnimalEntity> p_i50250_1_, World p_i50250_2_) {
        super(p_i50250_1_, p_i50250_2_);
        this.getNavigator().setCanSwim(true);
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(CARRIED_BLOCK, Optional.empty());
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(2, new StripLogGoal((double)1.2F, 32, 2));
        this.goalSelector.addGoal(3, new PlaceSticksGoal((double)1.2F, 32, 2));
        this.goalSelector.addGoal(4, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new TemptGoal(this, 1.2D, Ingredient.fromItems(Items.STICK), false));
        this.goalSelector.addGoal(6, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(7, new RandomWalkingGoal(this, 1.2D));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(9, new LookRandomlyGoal(this));
    }

    @Override
    @Nullable
    public AgeableEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        return null;
    }

    public static AttributeModifierMap.MutableAttribute getAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 8.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    public void setHeldBlockState(@Nullable BlockState state) {
        this.dataManager.set(CARRIED_BLOCK, Optional.ofNullable(state));
    }

    @Nullable
    public BlockState getHeldBlockState() {
        return this.dataManager.get(CARRIED_BLOCK).orElse((BlockState)null);
    }



    protected SoundEvent getAmbientSound() {
        return SoundEvents.BLOCK_DISPENSER_FAIL;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.BLOCK_DISPENSER_LAUNCH;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.BLOCK_DISPENSER_DISPENSE;
    }



    class StripLogGoal extends MoveToBlockGoal {
        private final BeaverEntity beaver;

        public StripLogGoal(double speedIn, int length, int p_i50737_5_) {
            super(BeaverEntity.this, speedIn, length, p_i50737_5_);
            this.beaver = BeaverEntity.this;
        }

        public boolean shouldMove() {
            return this.timeoutCounter % 40 == 0;
        }
        public double getTargetDistanceSq() {
            return 2.0D;
        }

        @Override
        protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
            BlockState blockstate = worldIn.getBlockState(pos);
            ResourceLocation rs = blockstate.getBlock().getRegistryName();
            return blockstate.getBlock().getTags().contains(new ResourceLocation("minecraft:logs")) && rs != null && !rs.getPath().contains("stripped");
        }

        public void tick() {
            BlockPos blockpos = this.func_241846_j();
            if (!blockpos.withinDistance(this.creature.getPositionVec(), this.getTargetDistanceSq())) {
                ++this.timeoutCounter;
                if (this.shouldMove()) {
                    this.creature.getNavigator().tryMoveToXYZ((double)((float)blockpos.getX()) + 0.5D, (double)blockpos.getY(), (double)((float)blockpos.getZ()) + 0.5D, this.movementSpeed);
                }
            } else {
                this.stripLog();
                --this.timeoutCounter;
            }
        }

        protected void stripLog() {
            if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(BeaverEntity.this.world, BeaverEntity.this)) {
                BlockState blockstate = BeaverEntity.this.world.getBlockState(this.destinationBlock);
                ResourceLocation rs = blockstate.getBlock().getRegistryName();
                if (rs != null) {
                    Block strippedLog = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(rs.getNamespace(),"stripped_"+blockstate.getBlock().getRegistryName().getPath()));
                    if (strippedLog != null && strippedLog != Blocks.AIR)
                    {
                        world.setBlockState(this.destinationBlock, strippedLog.getDefaultState(),3);
                        BeaverEntity.this.heal(1f);
                        this.beaver.setHeldBlockState(RankineBlocks.STICK_BLOCK.get().getDefaultState());
                        BeaverEntity.this.playSound(SoundEvents.ITEM_SWEET_BERRIES_PICK_FROM_BUSH, 1.0F, 1.0F);
                    }
                }

            }
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean shouldExecute() {
            return !BeaverEntity.this.isSleeping() && this.beaver.getHeldBlockState() == null && super.shouldExecute();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            super.startExecuting();
        }
    }

    class PlaceSticksGoal extends MoveToBlockGoal {
        private final BeaverEntity beaver;
        public PlaceSticksGoal(double p_i50737_2_, int p_i50737_4_, int p_i50737_5_) {
            super(BeaverEntity.this, p_i50737_2_, p_i50737_4_, p_i50737_5_);
            this.beaver = BeaverEntity.this;
        }

        public boolean shouldMove() {
            return this.timeoutCounter % 100 == 0;
        }

        public double getTargetDistanceSq() {
            return 2.0D;
        }

        public boolean shouldExecute() {
            if (this.runDelay > 0) {
                --this.runDelay;
                return false;
            } else {
                this.runDelay = this.getRunDelay(this.creature);
                return this.searchForDestination() && !BeaverEntity.this.isSleeping() && BeaverEntity.this.getHeldBlockState() == RankineBlocks.STICK_BLOCK.get().getDefaultState() && super.shouldExecute()
                        && ForgeEventFactory.getMobGriefingEvent(this.beaver.world, this.beaver);
            }
        }



        public void tick() {
            Random random = this.beaver.getRNG();
            World world = this.beaver.world;
            int i = MathHelper.floor(this.beaver.getPosX() - 1.0D + random.nextDouble() * 2.0D);
            int j = MathHelper.floor(this.beaver.getPosY() + random.nextDouble() * 2.0D);
            int k = MathHelper.floor(this.beaver.getPosZ() - 1.0D + random.nextDouble() * 2.0D);
            BlockPos blockpos = new BlockPos(i, j, k);
            BlockState blockstate = world.getBlockState(blockpos);
            BlockPos blockpos1 = blockpos.down();
            BlockState blockstate1 = world.getBlockState(blockpos1);
            BlockState blockstate2 = this.beaver.getHeldBlockState();
            if (blockstate2 != null) {
                blockstate2 = Block.getValidBlockForPosition(blockstate2, this.beaver.world, blockpos);
                if (this.func_220836_a(world, blockpos, blockstate2, blockstate, blockstate1, blockpos1) && !net.minecraftforge.event.ForgeEventFactory.onBlockPlace(beaver, net.minecraftforge.common.util.BlockSnapshot.create(world.getDimensionKey(), world, blockpos1), Direction.DOWN)) {
                    world.setBlockState(blockpos, blockstate2, 3);
                    this.beaver.setHeldBlockState((BlockState)null);
                }
            }
        }
        @Override
        protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
            FluidState fluid = worldIn.getFluidState(pos);
            BlockState blockState = worldIn.getBlockState(pos);
            boolean flag = BlockPos.getClosestMatchingPosition(pos,1,1,blockPos -> worldIn.getBlockState(blockPos).isSolid()).isPresent();
            return flag && (fluid.getFluid() == Fluids.WATER.getStillFluid() || fluid.getFluid() == Fluids.WATER.getFlowingFluid() || fluid.getFluid() == Fluids.WATER || blockState.getBlock() == Blocks.WATER);
        }

        private boolean func_220836_a(IWorldReader p_220836_1_, BlockPos p_220836_2_, BlockState p_220836_3_, BlockState p_220836_4_, BlockState p_220836_5_, BlockPos p_220836_6_) {
            return p_220836_4_.isAir(p_220836_1_, p_220836_2_) && !p_220836_5_.isAir(p_220836_1_, p_220836_6_) && !p_220836_5_.isCollisionShapeLargerThanFullBlock() && p_220836_3_.isValidPosition(p_220836_1_, p_220836_2_);
        }
    }

}
