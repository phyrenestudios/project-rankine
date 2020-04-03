package com.cannolicatfish.rankine.entities;

import com.cannolicatfish.rankine.blocks.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.*;
import com.cannolicatfish.rankine.entities.BeaverEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nullable;
import java.util.Random;

public class BeaverEntity extends AnimalEntity {
    public BeaverEntity(EntityType<? extends AnimalEntity> p_i50250_1_, World p_i50250_2_) {
        super(p_i50250_1_, p_i50250_2_);
    }



    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        if (BeaverEntity.this.getItemStackFromSlot(EquipmentSlotType.MAINHAND).isEmpty()){
            this.goalSelector.addGoal(2, new StripLogGoal((double)1.2F, 12, 2));
        }
        else
        {
            this.goalSelector.addGoal(2, new PlaceSticksGoal(this));
        }
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.fromItems(new IItemProvider[]{Items.STICK}), false));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
    }

    @Nullable
    @Override
    public AgeableEntity createChild(AgeableEntity ageableEntity) {
        return null;
    }

    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
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

    /*
    static class StripLogGoal extends Goal {
        private final BeaverEntity beaver;

        public StripLogGoal(BeaverEntity p_i45841_1_) {
            this.beaver = p_i45841_1_;
        }

        public boolean shouldExecute() {
            if (!ForgeEventFactory.getMobGriefingEvent(this.beaver.world, this.beaver)) {
                return false;
            } if (beaver.getHealth() != beaver.getMaxHealth()) {
                return this.beaver.getRNG().nextInt(10) == 0;
            } else {
                return this.beaver.getRNG().nextInt(50) == 0;
            }
        }

        public void tick() {
            Random random = this.beaver.getRNG();
            World world = this.beaver.world;
            int i = MathHelper.floor(this.beaver.getPosX() - 2.0D + random.nextDouble() * 1.0D);
            int j = MathHelper.floor(this.beaver.getPosY() + random.nextDouble() * 2.0D);
            int k = MathHelper.floor(this.beaver.getPosZ() - 2.0D + random.nextDouble() * 1.0D);
            BlockPos blockpos = new BlockPos(i, j, k);
            BlockState blockstate = world.getBlockState(blockpos);
            Block block = blockstate.getBlock();
            Vec3d vec3d = new Vec3d((double)MathHelper.floor(this.beaver.getPosX()) + 0.5D, (double)j + 0.5D, (double)MathHelper.floor(this.beaver.getPosZ()) + 0.5D);
            Vec3d vec3d1 = new Vec3d((double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D);
            BlockRayTraceResult blockraytraceresult = world.rayTraceBlocks(new RayTraceContext(vec3d, vec3d1, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, this.beaver));
            boolean flag = blockraytraceresult.getPos().equals(blockpos);
            if (block.isIn(BlockTags.LOGS) && flag) {
                if (block == Blocks.ACACIA_LOG)
                {
                    world.setBlockState(blockpos, Blocks.STRIPPED_ACACIA_LOG.getDefaultState(),2);
                    beaver.heal(1f);
                }
                if (block == Blocks.BIRCH_LOG)
                {
                    world.setBlockState(blockpos, Blocks.STRIPPED_BIRCH_LOG.getDefaultState(),2);
                    beaver.heal(1f);
                }
                if (block == Blocks.DARK_OAK_LOG)
                {
                    world.setBlockState(blockpos, Blocks.STRIPPED_DARK_OAK_LOG.getDefaultState(),2);
                    beaver.heal(1f);
                }
                if (block == Blocks.JUNGLE_LOG)
                {
                    world.setBlockState(blockpos, Blocks.STRIPPED_JUNGLE_LOG.getDefaultState(),2);
                    beaver.heal(1f);
                }
                if (block == Blocks.OAK_LOG)
                {
                    world.setBlockState(blockpos, Blocks.STRIPPED_OAK_LOG.getDefaultState(),2);
                    beaver.heal(1f);
                }
                if (block == Blocks.SPRUCE_LOG)
                {
                    world.setBlockState(blockpos, Blocks.STRIPPED_SPRUCE_LOG.getDefaultState(),2);
                    beaver.heal(1f);
                }
            }

        }
    }

     */

    class StripLogGoal extends MoveToBlockGoal {
        protected int field_220731_g;
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
            return blockstate.getBlock() instanceof LogBlock;
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
            if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(BeaverEntity.this.world, BeaverEntity.this) && BeaverEntity.this.getItemStackFromSlot(EquipmentSlotType.MAINHAND).isEmpty()) {
                BlockState blockstate = BeaverEntity.this.world.getBlockState(this.destinationBlock);
                if (blockstate.getBlock().isIn(BlockTags.LOGS)) {
                    if (blockstate.getBlock() == Blocks.ACACIA_LOG)
                    {
                        world.setBlockState(this.destinationBlock, Blocks.STRIPPED_ACACIA_LOG.getDefaultState(),2);
                        BeaverEntity.this.heal(1f);
                    }
                    if (blockstate.getBlock() == Blocks.BIRCH_LOG)
                    {
                        world.setBlockState(this.destinationBlock, Blocks.STRIPPED_BIRCH_LOG.getDefaultState(),2);
                        BeaverEntity.this.heal(1f);
                    }
                    if (blockstate.getBlock() == Blocks.DARK_OAK_LOG)
                    {
                        world.setBlockState(this.destinationBlock, Blocks.STRIPPED_DARK_OAK_LOG.getDefaultState(),2);
                        BeaverEntity.this.heal(1f);
                    }
                    if (blockstate.getBlock() == Blocks.JUNGLE_LOG)
                    {
                        world.setBlockState(this.destinationBlock, Blocks.STRIPPED_JUNGLE_LOG.getDefaultState(),2);
                        BeaverEntity.this.heal(1f);
                    }
                    if (blockstate.getBlock() == Blocks.OAK_LOG)
                    {
                        world.setBlockState(this.destinationBlock, Blocks.STRIPPED_OAK_LOG.getDefaultState(),2);
                        BeaverEntity.this.heal(1f);
                    }
                    if (blockstate.getBlock() == Blocks.SPRUCE_LOG)
                    {
                        world.setBlockState(this.destinationBlock, Blocks.STRIPPED_SPRUCE_LOG.getDefaultState(),2);
                        BeaverEntity.this.heal(1f);
                    }
                }
                if (blockstate.getBlock() instanceof LogBlock) {
                    BeaverEntity.this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(ModBlocks.STICK_BLOCK));
                    System.out.println("Beaver should now have stick block");
                    System.out.println(BeaverEntity.this.getItemStackFromSlot(EquipmentSlotType.MAINHAND));
                    BeaverEntity.this.playSound(SoundEvents.ITEM_SWEET_BERRIES_PICK_FROM_BUSH, 1.0F, 1.0F);
                }
            }
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean shouldExecute() {
            return !BeaverEntity.this.isSleeping() && super.shouldExecute();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            this.field_220731_g = 0;
            super.startExecuting();
        }
    }
    class PlaceSticksGoal extends Goal {
        private final BeaverEntity beaver;
        ItemStack itemstack = BeaverEntity.this.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
        public PlaceSticksGoal(BeaverEntity p_i45843_1_) {
            this.beaver = p_i45843_1_;
        }

        public boolean shouldExecute() {
            if (!ForgeEventFactory.getMobGriefingEvent(this.beaver.world, this.beaver)) {
                return false;
            } else {
                return itemstack.getItem() == new ItemStack(ModBlocks.STICK_BLOCK).getItem();
            }
        }

        public void tick() {
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
            if ( this.func_220836_a(iworld, blockpos, blockstate2, blockstate, blockstate1, blockpos1) && blockpos != this.beaver.getPosition() && !ForgeEventFactory.onBlockPlace(this.beaver, new BlockSnapshot(iworld, blockpos, blockstate1), Direction.UP)) {
                iworld.setBlockState(blockpos, blockstate2, 3);
            }

        }

        private boolean func_220836_a(IWorldReader p_220836_1_, BlockPos p_220836_2_, BlockState p_220836_3_, BlockState p_220836_4_, BlockState p_220836_5_, BlockPos p_220836_6_) {
            return p_220836_4_.isAir(p_220836_1_, p_220836_2_) && !p_220836_5_.isAir(p_220836_1_, p_220836_6_) && p_220836_5_.isCollisionShapeOpaque(p_220836_1_, p_220836_6_) && p_220836_3_.isValidPosition(p_220836_1_, p_220836_2_);
        }
    }
}
