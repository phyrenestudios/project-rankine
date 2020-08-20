package com.cannolicatfish.rankine.entities;

import com.cannolicatfish.rankine.init.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.FluidState;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;

public class BeaverEntity extends AnimalEntity {
    public BeaverEntity(EntityType<? extends AnimalEntity> p_i50250_1_, World p_i50250_2_) {
        super(p_i50250_1_, p_i50250_2_);
        this.getNavigator().setCanSwim(true);
    }



    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(2, new StripLogGoal((double)1.2F, 32, 2));
        this.goalSelector.addGoal(3, new PlaceSticksGoal((double)1.2F, 32, 2));
        this.goalSelector.addGoal(4, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new TemptGoal(this, 1.2D, Ingredient.fromItems(new IItemProvider[]{Items.STICK}), false));
        this.goalSelector.addGoal(6, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(7, new RandomWalkingGoal(this, 1.2D));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(9, new LookRandomlyGoal(this));
    }

    @Nullable
    @Override
    public AgeableEntity createChild(AgeableEntity ageableEntity) {
        return null;
    }

    public static AttributeModifierMap.MutableAttribute getAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 8.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D);
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
        protected int field_220731_g;
        List<Block> LOGS = Arrays.asList(Blocks.ACACIA_LOG,Blocks.BIRCH_LOG,Blocks.DARK_OAK_LOG,Blocks.JUNGLE_LOG,Blocks.OAK_LOG,Blocks.SPRUCE_LOG,ModBlocks.CEDAR_LOG,ModBlocks.PINYON_PINE_LOG,
                ModBlocks.JUNIPER_LOG,ModBlocks.COCONUT_PALM_LOG,ModBlocks.BALSAM_FIR_LOG);
        public StripLogGoal(double p_i50737_2_, int p_i50737_4_, int p_i50737_5_) {
            super(BeaverEntity.this, p_i50737_2_, p_i50737_4_, p_i50737_5_);
        }

        public boolean shouldMove() {
            return this.timeoutCounter % 100 == 0;
        }
        public double getTargetDistanceSq() {
            return 2.0D;
        }
        @Override
        protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
            BlockState blockstate = worldIn.getBlockState(pos);
            return LOGS.contains(blockstate.getBlock());
        }

        public void tick() {
            if (this.getIsAboveDestination()) {
                if (this.field_220731_g >= 40) {
                    this.stripLog();
                } else {
                    ++this.field_220731_g;
                }
            } else if (!this.getIsAboveDestination() && BeaverEntity.this.rand.nextFloat() < 0.05F) {
                BeaverEntity.this.playSound(SoundEvents.ENTITY_FOX_SNIFF, 1.0F, 1.0F);
            }

            super.tick();
        }
        protected void stripLog() {
            if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(BeaverEntity.this.world, BeaverEntity.this)) {
                //System.out.println("Attempting to strip log");
                BlockState blockstate = BeaverEntity.this.world.getBlockState(this.destinationBlock);
                boolean completed = false;
                if (blockstate.getBlock() == Blocks.ACACIA_LOG)
                {
                    world.setBlockState(this.destinationBlock, Blocks.STRIPPED_ACACIA_LOG.getDefaultState(),2);
                    BeaverEntity.this.heal(1f);
                    completed = true;
                }
                if (blockstate.getBlock() == Blocks.BIRCH_LOG)
                {
                    world.setBlockState(this.destinationBlock, Blocks.STRIPPED_BIRCH_LOG.getDefaultState(),2);
                    BeaverEntity.this.heal(1f);
                    completed = true;
                }
                if (blockstate.getBlock() == Blocks.DARK_OAK_LOG)
                {
                    world.setBlockState(this.destinationBlock, Blocks.STRIPPED_DARK_OAK_LOG.getDefaultState(),2);
                    BeaverEntity.this.heal(1f);
                    completed = true;
                }
                if (blockstate.getBlock() == Blocks.JUNGLE_LOG)
                {
                    world.setBlockState(this.destinationBlock, Blocks.STRIPPED_JUNGLE_LOG.getDefaultState(),2);
                    BeaverEntity.this.heal(1f);
                    completed = true;
                }
                if (blockstate.getBlock() == Blocks.OAK_LOG)
                {
                    world.setBlockState(this.destinationBlock, Blocks.STRIPPED_OAK_LOG.getDefaultState(),2);
                    BeaverEntity.this.heal(1f);
                    completed = true;
                }
                if (blockstate.getBlock() == Blocks.SPRUCE_LOG)
                {
                    world.setBlockState(this.destinationBlock, Blocks.STRIPPED_SPRUCE_LOG.getDefaultState(),2);
                    BeaverEntity.this.heal(1f);
                    completed = true;
                }
                if (blockstate.getBlock() == ModBlocks.CEDAR_LOG)
                {
                    world.setBlockState(this.destinationBlock, ModBlocks.STRIPPED_CEDAR_LOG.getDefaultState(),2);
                    BeaverEntity.this.heal(1f);
                    completed = true;
                }
                if (blockstate.getBlock() == ModBlocks.PINYON_PINE_LOG)
                {
                    world.setBlockState(this.destinationBlock, ModBlocks.STRIPPED_PINYON_PINE_LOG.getDefaultState(),2);
                    BeaverEntity.this.heal(1f);
                    completed = true;
                }
                if (blockstate.getBlock() == ModBlocks.JUNIPER_LOG)
                {
                    world.setBlockState(this.destinationBlock, ModBlocks.STRIPPED_JUNIPER_LOG.getDefaultState(),2);
                    BeaverEntity.this.heal(1f);
                    completed = true;
                }
                if (blockstate.getBlock() == ModBlocks.COCONUT_PALM_LOG)
                {
                    world.setBlockState(this.destinationBlock, ModBlocks.STRIPPED_COCONUT_PALM_LOG.getDefaultState(),2);
                    BeaverEntity.this.heal(1f);
                    completed = true;
                }
                if (blockstate.getBlock() == ModBlocks.BALSAM_FIR_LOG)
                {
                    world.setBlockState(this.destinationBlock, ModBlocks.STRIPPED_BALSAM_FIR_LOG.getDefaultState(),2);
                    BeaverEntity.this.heal(1f);
                    completed = true;
                }
                if (blockstate.getBlock() == ModBlocks.MAGNOLIA_LOG)
                {
                    world.setBlockState(this.destinationBlock, ModBlocks.STRIPPED_MAGNOLIA_LOG.getDefaultState(),2);
                    BeaverEntity.this.heal(1f);
                    completed = true;
                }
                if (blockstate.getBlock() == ModBlocks.EASTERN_HEMLOCK_LOG)
                {
                    world.setBlockState(this.destinationBlock, ModBlocks.STRIPPED_EASTERN_HEMLOCK_LOG.getDefaultState(),2);
                    BeaverEntity.this.heal(1f);
                    completed = true;
                }
                if (blockstate.getBlock() == ModBlocks.YELLOW_BIRCH_LOG)
                {
                    world.setBlockState(this.destinationBlock, Blocks.BIRCH_LOG.getDefaultState(),2);
                    BeaverEntity.this.heal(1f);
                    completed = true;
                }
                if (completed) {
                    if (BeaverEntity.this.getItemStackFromSlot(EquipmentSlotType.MAINHAND).isEmpty())
                    {
                        BeaverEntity.this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(ModBlocks.STICK_BLOCK));
                    } else
                    {
                        BeaverEntity.this.getItemStackFromSlot(EquipmentSlotType.MAINHAND).grow(1);
                    }

                }
                if (blockstate.getBlock().getTags().contains(new ResourceLocation("minecraft/logs"))) {
                    BeaverEntity.this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(ModBlocks.STICK_BLOCK));
                    //System.out.println("Beaver should now have stick block");
                    //System.out.println(BeaverEntity.this.getItemStackFromSlot(EquipmentSlotType.MAINHAND));
                    BeaverEntity.this.playSound(SoundEvents.ITEM_SWEET_BERRIES_PICK_FROM_BUSH, 1.0F, 1.0F);
                }
            }
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean shouldExecute() {
            return !BeaverEntity.this.isSleeping() && BeaverEntity.this.getItemStackFromSlot(EquipmentSlotType.MAINHAND).getCount() <= 16 && super.shouldExecute();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            this.field_220731_g = 0;
            super.startExecuting();
        }
    }
    class PlaceSticksGoal extends MoveToBlockGoal {
        private final BeaverEntity beaver;
        protected int field_220731_g;
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
            if (!BeaverEntity.this.isSleeping() && BeaverEntity.this.getItemStackFromSlot(EquipmentSlotType.MAINHAND).getItem() == new ItemStack(ModBlocks.STICK_BLOCK).getItem() && super.shouldExecute()
            && ForgeEventFactory.getMobGriefingEvent(this.beaver.world, this.beaver)){
                //System.out.println("CAN PLACE STICKS");
                return true;
            } else
            {
                return false;
            }
        }

        public void tick() {
            if (this.getIsAboveDestination()) {
                if (this.field_220731_g >= 40) {
                    this.placeSticks();
                } else {
                    ++this.field_220731_g;
                }
            } else if (!this.getIsAboveDestination() && BeaverEntity.this.rand.nextFloat() < 0.05F) {
                BeaverEntity.this.playSound(SoundEvents.ENTITY_FOX_SNIFF, 1.0F, 1.0F);
            }

            super.tick();


        }

        protected void placeSticks()
        {

            Random random = this.beaver.getRNG();
            IWorld iworld = this.beaver.world;
            int i = MathHelper.floor(this.beaver.getPosX() - 1.0D + random.nextDouble() * 2.0D);
            int j = MathHelper.floor(this.beaver.getPosY() + random.nextDouble() * 2.0D);
            int k = MathHelper.floor(this.beaver.getPosZ() - 1.0D + random.nextDouble() * 2.0D);
            BlockPos blockpos = new BlockPos(i, j, k);
            BlockState blockstate = iworld.getBlockState(blockpos);
            BlockPos blockpos1 = blockpos.down();
            BlockState blockstate1 = iworld.getBlockState(blockpos1);
            BlockState blockstate2 = ModBlocks.STICK_BLOCK.getDefaultState();
            if ((iworld.getFluidState(blockpos).getFluid() == Fluids.WATER || iworld.getBlockState(blockpos).getBlock() == Blocks.WATER) && blockpos != this.beaver.getOnPosition() &&
                    !ForgeEventFactory.onBlockPlace(this.beaver, BlockSnapshot.create(iworld, blockpos), Direction.UP)) {
                iworld.setBlockState(blockpos, blockstate2, 3);
                //System.out.println("STICK BLOCK PLACED");
                BeaverEntity.this.getItemStackFromSlot(EquipmentSlotType.MAINHAND).shrink(1);
            }
        }
        @Override
        protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
            FluidState fluid = worldIn.getFluidState(pos);
            BlockState blockState = worldIn.getBlockState(pos);
            return fluid.getFluid() == Fluids.WATER || blockState.getBlock() == Blocks.WATER;
        }

        private boolean func_220836_a(IWorldReader p_220836_1_, BlockPos p_220836_2_, BlockState p_220836_3_, BlockState p_220836_4_, BlockState p_220836_5_, BlockPos p_220836_6_) {
            return p_220836_4_.isAir(p_220836_1_, p_220836_2_) && !p_220836_5_.isAir(p_220836_1_, p_220836_6_) && !p_220836_5_.isCollisionShapeLargerThanFullBlock() && p_220836_3_.isValidPosition(p_220836_1_, p_220836_2_);
        }
    }

}
