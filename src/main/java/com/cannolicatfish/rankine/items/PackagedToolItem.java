package com.cannolicatfish.rankine.items;

import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.items.alloys.AlloyData;
import com.cannolicatfish.rankine.items.alloys.AlloyItem;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PackagedToolItem extends Item {
    public PackagedToolItem(Properties properties) {
        super(properties);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add((new StringTextComponent("Contains a completely random tool. Results may vary.")).mergeStyle(TextFormatting.RED));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        playerIn.getHeldItem(handIn).shrink(1);
        playerIn.addItemStackToInventory(genRandomTool(worldIn.getRandom()));
        return super.onItemRightClick(worldIn, playerIn, handIn);

    }

    public ItemStack genRandomTool(Random random){
        ItemStack ret;
        PeriodicTableUtils utils = new PeriodicTableUtils();
        int mode = random.nextInt(2);
        switch(random.nextInt(7))
        {
            case 0:
                ret = new ItemStack(RankineItems.ALLOY_AXE.get());
                break;
            case 1:
                ret = new ItemStack(RankineItems.ALLOY_SHOVEL.get());
                break;
            case 2:
                ret = new ItemStack(RankineItems.ALLOY_HAMMER.get());
                break;
            case 3:
                ret = new ItemStack(RankineItems.ALLOY_SWORD.get());
                break;
            case 4:
                ret = new ItemStack(RankineItems.ALLOY_SPEAR.get());
                break;
            case 5:
            default:
                ret = new ItemStack(RankineItems.ALLOY_PICKAXE.get());
                break;
            case 6:
                ret = new ItemStack(RankineItems.ALLOY_HOE.get());
                break;
        }
        List<PeriodicTableUtils.Element> elements = Arrays.asList(PeriodicTableUtils.Element.values());

        String one = elements.get(random.nextInt(elements.size() - 1)).getSymbol();
        String two = elements.get(random.nextInt(elements.size() - 1)).getSymbol();
        int x1 = random.nextInt(49 - mode) + 51;
        int x2 = 100 - x1;
        String alloy;
        if (mode == 1)
        {
            String three = elements.get(random.nextInt(elements.size() - 1)).getSymbol();
            x2 = x2 - random.nextInt(Math.round(x2/2f - 1)) - 1;
            int x3 = 100 - x1 - x2;
            alloy = x1 + one + "-" + x2 + two + "-" + x3 + three;

        } else {
            alloy = x1 + one + "-" + x2 + two;
        }




        AlloyItem.addAlloy(ret,new AlloyData(alloy));

        return ret;

    }
}
