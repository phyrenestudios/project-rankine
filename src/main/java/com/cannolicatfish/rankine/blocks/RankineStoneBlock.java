package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.AbstractMap;
import java.util.Random;

public class RankineStoneBlock extends Block {

    private boolean isPlacedByWorld;
    int type;
    public RankineStoneBlock(Properties properties) {
        super(properties);
        this.isPlacedByWorld = true;
    }

    public RankineStoneBlock(Properties properties, boolean isPlacedByWorld) {
        super(properties);
        this.isPlacedByWorld = isPlacedByWorld;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (placer != null)
        {
            this.isPlacedByWorld = false;
        }
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        boolean oreFound = false;
        Random random = new Random();
        AbstractMap.SimpleEntry<Block, BlockPos> checker = nuggetCheck(worldIn,pos);
        if (checker.getKey() != Blocks.AIR) {
            oreFound = true;
        }
        if (oreFound && random.nextFloat() < Config.NUGGET_CHANCE.get() && isPlacedByWorld && !worldIn.isRemote && worldIn.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && !worldIn.restoringBlockSnapshots && !player.abilities.isCreativeMode)
        {
            float f = 0.5F;
            double d0 = (double)(worldIn.rand.nextFloat() * 0.5F) + 0.25D;
            double d1 = (double)(worldIn.rand.nextFloat() * 0.5F) + 0.25D;
            double d2 = (double)(worldIn.rand.nextFloat() * 0.5F) + 0.25D;
            ItemEntity itementity = new ItemEntity(worldIn, (double)pos.getX() + d0, (double)pos.getY() + d1, (double)pos.getZ() + d2, getNugget(checker));
            itementity.setDefaultPickupDelay();
            worldIn.addEntity(itementity);

        }
        worldIn.playEvent(player, 2001, pos, getStateId(state));
    }


    private AbstractMap.SimpleEntry<Block,BlockPos> nuggetCheck(World worldIn, BlockPos pos)
    {
        BlockPos foundPos = null;
        for (int x = 1; x < Config.NUGGET_DISTANCE.get(); x++) {
            if (worldIn.getBlockState(pos.down(x)).getBlock() instanceof RankineOreBlock) {
                foundPos = pos.down(x);
            } else if (worldIn.getBlockState(pos.up(x)).getBlock() instanceof RankineOreBlock) {
                foundPos = pos.up(x);
            } else if (worldIn.getBlockState(pos.south(x)).getBlock() instanceof RankineOreBlock) {
                foundPos = pos.south(x);
            } else if (worldIn.getBlockState(pos.north(x)).getBlock() instanceof RankineOreBlock) {
                foundPos = pos.north(x);
            } else if (worldIn.getBlockState(pos.east(x)).getBlock() instanceof RankineOreBlock) {
                foundPos = pos.east(x);
            } else if (worldIn.getBlockState(pos.west(x)).getBlock() instanceof RankineOreBlock) {
                foundPos = pos.west(x);
            }

            if (foundPos != null)
            {
                if ((worldIn.getBlockState(foundPos).get(RankineOreBlock.TYPE) != 0)) {
                    return new AbstractMap.SimpleEntry<>(worldIn.getBlockState(foundPos).getBlock(), foundPos);
                } else
                {
                    foundPos = null;
                }
            }
        }
        return new AbstractMap.SimpleEntry<>(Blocks.AIR,pos);
    }

    public ItemStack getNugget(AbstractMap.SimpleEntry<Block,BlockPos> ore)
    {
        if (ore.getKey() == RankineBlocks.MAGNETITE_ORE.get()) { return new ItemStack(Items.IRON_NUGGET); }
        if (ore.getKey() == RankineBlocks.MALACHITE_ORE.get() || ore.getKey() == RankineBlocks.NATIVE_COPPER_ORE.get()) { return new ItemStack(RankineItems.COPPER_NUGGET.get()); }
        if (ore.getKey() == RankineBlocks.BAUXITE_ORE.get() || ore.getKey() == RankineBlocks.NATIVE_ALUMINUM_ORE.get()) { return new ItemStack(RankineItems.ALUMINUM_NUGGET.get()); }
        if (ore.getKey() == RankineBlocks.NATIVE_GOLD_ORE.get()) { return new ItemStack(Items.GOLD_NUGGET); }
        if (ore.getKey() == RankineBlocks.CASSITERITE_ORE.get() || ore.getKey() == RankineBlocks.NATIVE_TIN_ORE.get()) { return new ItemStack(RankineItems.TIN_NUGGET.get()); }
        if (ore.getKey() == RankineBlocks.SPHALERITE_ORE.get()) { return new ItemStack(RankineItems.ZINC_NUGGET.get()); }
        if (ore.getKey() == RankineBlocks.PENTLANDITE_ORE.get()) { return new ItemStack(RankineItems.NICKEL_NUGGET.get()); }
        if (ore.getKey() == RankineBlocks.INTERSPINIFEX_ORE.get()) { return new ItemStack(RankineItems.NICKEL_NUGGET.get()); }
        if (ore.getKey() == RankineBlocks.MAGNESITE_ORE.get()) { return new ItemStack(RankineItems.MAGNESIUM_NUGGET.get()); }
        if (ore.getKey() == RankineBlocks.ILMENITE_ORE.get()) { return new ItemStack(RankineItems.TITANIUM_NUGGET.get()); }
        if (ore.getKey() == RankineBlocks.GALENA_ORE.get() || ore.getKey() == RankineBlocks.NATIVE_LEAD_ORE.get()) { return new ItemStack(RankineItems.LEAD_NUGGET.get()); }
        if (ore.getKey() == RankineBlocks.BISMUTHINITE_ORE.get() || ore.getKey() == RankineBlocks.NATIVE_BISMUTH_ORE.get()) { return new ItemStack(RankineItems.BISMUTH_NUGGET.get()); }
        if (ore.getKey() == RankineBlocks.ACANTHITE_ORE.get() || ore.getKey() == RankineBlocks.NATIVE_SILVER_ORE.get()) { return new ItemStack(RankineItems.SILVER_NUGGET.get()); }
        if (ore.getKey() == RankineBlocks.MOLYBDENITE_ORE.get()) { return new ItemStack(RankineItems.MOLYBDENUM_NUGGET.get()); }
        if (ore.getKey() == RankineBlocks.PYROLUSITE_ORE.get()) { return new ItemStack(RankineItems.MANGANESE_NUGGET.get()); }
        if (ore.getKey() == RankineBlocks.CHROMITE_ORE.get()) { return new ItemStack(RankineItems.CHROMIUM_NUGGET.get()); }
        if (ore.getKey() == RankineBlocks.COLUMBITE_ORE.get()) { return new ItemStack(RankineItems.NIOBIUM_NUGGET.get()); }
        if (ore.getKey() == RankineBlocks.TANTALITE_ORE.get()) { return new ItemStack(RankineItems.TANTALUM_NUGGET.get()); }
        if (ore.getKey() == RankineBlocks.WOLFRAMITE_ORE.get()) { return new ItemStack(RankineItems.TUNGSTEN_NUGGET.get()); }
        if (ore.getKey() == RankineBlocks.NATIVE_ARSENIC_ORE.get()) { return new ItemStack(RankineItems.ARSENIC_NUGGET.get()); }
        if (ore.getKey() == RankineBlocks.GREENOCKITE_ORE.get()) { return new ItemStack(RankineItems.CADMIUM_NUGGET.get()); }
        if (ore.getKey() == RankineBlocks.VANADINITE_ORE.get()) { return new ItemStack(RankineItems.VANADIUM_NUGGET.get()); }
        if (ore.getKey() == RankineBlocks.NATIVE_GALLIUM_ORE.get()) { return new ItemStack(RankineItems.GALLIUM_NUGGET.get()); }
        if (ore.getKey() == RankineBlocks.NATIVE_SELENIUM_ORE.get()) { return new ItemStack(RankineItems.SELENIUM_NUGGET.get()); }
        if (ore.getKey() == RankineBlocks.NATIVE_TELLURIUM_ORE.get()) { return new ItemStack(RankineItems.TELLURIUM_NUGGET.get()); }
        if (ore.getKey() == RankineBlocks.NATIVE_INDIUM_ORE.get()) { return new ItemStack(RankineItems.INDIUM_NUGGET.get()); }
        if (ore.getKey() == RankineBlocks.XENOTIME_ORE.get()) { return new ItemStack(RankineItems.CERIUM_NUGGET.get()); }
        if (ore.getKey() == RankineBlocks.STIBNITE_ORE.get()) { return new ItemStack(RankineItems.ANTIMONY_NUGGET.get()); }
        if (ore.getKey() == RankineBlocks.URANINITE_ORE.get()) { return new ItemStack(RankineItems.URANIUM_NUGGET.get()); }

        return ItemStack.EMPTY;
    }

}
