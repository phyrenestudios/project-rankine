package com.cannolicatfish.rankine.util.alloys;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.text.TextFormatting;

public interface AlloyUtils {

        default int getDurabilityBonus(){
                return 0;
        }

        default float getMiningSpeedBonus(){
                return 0;
        }

        default int getMiningLevelBonus(){
                return 0;
        }

        default int getEnchantabilityBonus(){
                return 0;
        }

        default float getCorrResistBonus(){
                return 0;
        }

        default float getHeatResistBonus(){
                return 0;
        }

        default float getAttackSpeedBonus(){
                return 0;
        }

        default float getAttackDamageBonus(){
                return 0;
        }

        default float getToughnessBonus(){
                return 0;
        }

        default Enchantment getEnchantmentBonus(Item item){
                return null;
        }

        default int getEnchantmentLevel(Enchantment en, int enchantability){
                if (enchantability >= 50 && en.getMaxLevel() >= 5)
                {
                        return 5;
                }
                else if (enchantability >= 40 && en.getMaxLevel() >= 4)
                {
                        return 4;
                }
                else if (enchantability >= 30 && en.getMaxLevel() >= 3)
                {
                        return 3;
                }
                else if (enchantability >= 20 && en.getMaxLevel() >= 2)
                {
                        return 2;
                }
                return 1;
        }

        public String getDefComposition();

        default TextFormatting getAlloyGroupColor() {
                return TextFormatting.WHITE;
        }


}
