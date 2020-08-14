package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.ModBlocks;
import com.cannolicatfish.rankine.init.ModItems;
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

public class RankineStone extends Block {

    private boolean isPlacedByWorld;
    int type;
    public RankineStone(Properties properties) {
        super(properties);
        this.isPlacedByWorld = true;
    }

    public RankineStone(Properties properties, boolean isPlacedByWorld) {
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
        //System.out.println(checker);
        if (checker.getKey() != Blocks.AIR) {
            oreFound = true;
        }
        if (oreFound && random.nextFloat() < 0.2f && isPlacedByWorld && !worldIn.isRemote && worldIn.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && !worldIn.restoringBlockSnapshots && !player.abilities.isCreativeMode)
        {
            float f = 0.5F;
            double d0 = (double)(worldIn.rand.nextFloat() * 0.5F) + 0.25D;
            double d1 = (double)(worldIn.rand.nextFloat() * 0.5F) + 0.25D;
            double d2 = (double)(worldIn.rand.nextFloat() * 0.5F) + 0.25D;
            ItemEntity itementity = new ItemEntity(worldIn, (double)pos.getX() + d0, (double)pos.getY() + d1, (double)pos.getZ() + d2, getNugget(checker));
            //System.out.println(itementity);
            itementity.setDefaultPickupDelay();
            worldIn.addEntity(itementity);

        }
        worldIn.playEvent(player, 2001, pos, getStateId(state));
    }


    private AbstractMap.SimpleEntry<Block,BlockPos> nuggetCheck(World worldIn, BlockPos pos)
    {
        BlockPos foundPos = null;
        for (int x = 1; x < 7; x++) {
            if (worldIn.getBlockState(pos.down(x)).getBlock() instanceof RankineOre) {
                foundPos = pos.down(x);
            } else if (worldIn.getBlockState(pos.up(x)).getBlock() instanceof RankineOre) {
                foundPos = pos.up(x);
            } else if (worldIn.getBlockState(pos.south(x)).getBlock() instanceof RankineOre) {
                foundPos = pos.south(x);
            } else if (worldIn.getBlockState(pos.north(x)).getBlock() instanceof RankineOre) {
                foundPos = pos.north(x);
            } else if (worldIn.getBlockState(pos.east(x)).getBlock() instanceof RankineOre) {
                foundPos = pos.east(x);
            } else if (worldIn.getBlockState(pos.west(x)).getBlock() instanceof RankineOre) {
                foundPos = pos.west(x);
            }

            if (foundPos != null)
            {
                if ((worldIn.getBlockState(foundPos).get(RankineOre.TYPE) != 0)) {
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
        Random random = new Random();
        if (ore.getKey() == ModBlocks.MAGNETITE_ORE) { return new ItemStack(Items.IRON_NUGGET); }
        if (ore.getKey() == ModBlocks.MALACHITE_ORE || ore.getKey() == ModBlocks.NATIVE_COPPER_ORE) { return new ItemStack(ModItems.COPPER_NUGGET); }
        if (ore.getKey() == ModBlocks.BAUXITE_ORE || ore.getKey() == ModBlocks.NATIVE_ALUMINUM_ORE) { return new ItemStack(ModItems.ALUMINUM_NUGGET); }
        if (ore.getKey() == ModBlocks.NATIVE_GOLD_ORE) { return new ItemStack(Items.GOLD_NUGGET); }
        if (ore.getKey() == ModBlocks.CASSITERITE_ORE || ore.getKey() == ModBlocks.NATIVE_TIN_ORE) { return new ItemStack(ModItems.TIN_NUGGET); }
        if (ore.getKey() == ModBlocks.SPHALERITE_ORE) { return new ItemStack(ModItems.ZINC_NUGGET); }
        if (ore.getKey() == ModBlocks.PENTLANDITE_ORE) { return new ItemStack(ModItems.NICKEL_NUGGET); }
        if (ore.getKey() == ModBlocks.MAGNESITE_ORE) { return new ItemStack(ModItems.MAGNESIUM_NUGGET); }
        if (ore.getKey() == ModBlocks.ILMENITE_ORE) { return new ItemStack(ModItems.TITANIUM_NUGGET); }
        if (ore.getKey() == ModBlocks.GALENA_ORE || ore.getKey() == ModBlocks.NATIVE_LEAD_ORE) { return new ItemStack(ModItems.LEAD_NUGGET); }
        if (ore.getKey() == ModBlocks.BISMUTHINITE_ORE || ore.getKey() == ModBlocks.NATIVE_BISMUTH_ORE) { return new ItemStack(ModItems.BISMUTH_NUGGET); }
        if (ore.getKey() == ModBlocks.ACANTHITE_ORE || ore.getKey() == ModBlocks.NATIVE_SILVER_ORE) { return new ItemStack(ModItems.SILVER_NUGGET); }
        if (ore.getKey() == ModBlocks.MOLYBDENITE_ORE) { return new ItemStack(ModItems.MOLYBDENUM_NUGGET); }
        if (ore.getKey() == ModBlocks.PYROLUSITE_ORE) { return new ItemStack(ModItems.MANGANESE_NUGGET); }
        if (ore.getKey() == ModBlocks.CHROMITE_ORE) { return new ItemStack(ModItems.CHROMIUM_NUGGET); }
        if (ore.getKey() == ModBlocks.COLUMBITE_ORE) { return new ItemStack(ModItems.NIOBIUM_NUGGET); }
        if (ore.getKey() == ModBlocks.TANTALITE_ORE) { return new ItemStack(ModItems.TANTALUM_NUGGET); }
        if (ore.getKey() == ModBlocks.WOLFRAMITE_ORE) { return new ItemStack(ModItems.TUNGSTEN_NUGGET); }
        if (ore.getKey() == ModBlocks.NATIVE_ARSENIC_ORE) { return new ItemStack(ModItems.ARSENIC_NUGGET); }
        if (ore.getKey() == ModBlocks.GREENOCKITE_ORE) { return new ItemStack(ModItems.CADMIUM_NUGGET); }
        if (ore.getKey() == ModBlocks.VANADINITE_ORE) { return new ItemStack(ModItems.VANADIUM_NUGGET); }
        if (ore.getKey() == ModBlocks.NATIVE_GALLIUM_ORE) { return new ItemStack(ModItems.GALLIUM_NUGGET); }
        if (ore.getKey() == ModBlocks.NATIVE_SELENIUM_ORE) { return new ItemStack(ModItems.SELENIUM_NUGGET); }
        if (ore.getKey() == ModBlocks.NATIVE_TELLURIUM_ORE) { return new ItemStack(ModItems.TELLURIUM_NUGGET); }
        if (ore.getKey() == ModBlocks.NATIVE_INDIUM_ORE) { return new ItemStack(ModItems.INDIUM_NUGGET); }
        if (ore.getKey() == ModBlocks.CINNABAR_ORE) { return new ItemStack(Items.REDSTONE); }
        return ItemStack.EMPTY;
    }

}
