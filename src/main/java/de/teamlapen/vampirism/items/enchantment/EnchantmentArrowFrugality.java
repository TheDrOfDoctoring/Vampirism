package de.teamlapen.vampirism.items.enchantment;

import de.teamlapen.vampirism.REFERENCE;
import de.teamlapen.vampirism.api.VReference;
import de.teamlapen.vampirism.items.VampirismItemCrossbow;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

/**
 * Makes tech crossbows use less arrows
 */
public class EnchantmentArrowFrugality extends Enchantment {
    public EnchantmentArrowFrugality(Rarity rarityIn) {
        super(rarityIn, VReference.CROSSBOW_ENCHANTMENT, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND});
        this.setRegistryName(REFERENCE.MODID, "crossbowfrugality");

    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return !stack.isEmpty() && stack.getItem() instanceof VampirismItemCrossbow;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return 50;
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 10 + enchantmentLevel * 5;
    }

    @Override
    protected boolean canApplyTogether(Enchantment ench) {
        return super.canApplyTogether(ench) && ench != Enchantments.INFINITY;
    }

    @Override
    protected String getDefaultTranslationKey() {
        return "enchantment.vampirism.arrow_frugality";
    }
}
