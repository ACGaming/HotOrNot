package com.buuz135.hotornot.item;

import java.util.List;
import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.buuz135.hotornot.HotOrNot;

public class HotItem extends Item
{
    public HotItem(String name, int durability)
    {
        super();
        this.setRegistryName(HotOrNot.MOD_ID, name);
        this.setTranslationKey(HotOrNot.MOD_ID + "." + name);
        this.setMaxStackSize(1);
        if (durability != 0)
        {
            this.setMaxDamage(durability);
        }
        this.setCreativeTab(HotOrNot.HOTORNOT_TAB);

        ModItems.ITEMS.add(this);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TextComponentTranslation("item.hotornot.hot_item.tooltip").getUnformattedComponentText());
    }

    @Override
    public boolean doesSneakBypassUse(ItemStack stack, IBlockAccess world, BlockPos pos, EntityPlayer player)
    {
        return true;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged)
    {
        return false;
    }
}