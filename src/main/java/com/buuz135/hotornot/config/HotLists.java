package com.buuz135.hotornot.config;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class HotLists
{
    public static boolean isHotFluid(FluidStack fluidStack)
    {
        return HotConfig.HOT_FLUIDS && fluidStack.getFluid().getTemperature(fluidStack) >= HotConfig.HOT_FLUID + 273;
    }

    public static boolean isColdFluid(FluidStack fluidStack)
    {
        return HotConfig.COLD_FLUIDS && fluidStack.getFluid().getTemperature(fluidStack) <= HotConfig.COLD_FLUID + 273;
    }

    public static boolean isGaseousFluid(FluidStack fluidStack)
    {
        return HotConfig.GASEOUS_FLUIDS && fluidStack.getFluid().isGaseous(fluidStack);
    }

    public static boolean isRemovedItem(ItemStack stack)
    {
        String regName = stack.getItem().getRegistryName().toString();
        for (String s : HotConfig.ITEM_REMOVALS)
        {
            if (regName.equals(s))
            {
                return true;
            }
        }
        return false;
    }

    public static boolean isHotItem(ItemStack stack)
    {
        String regName = stack.getItem().getRegistryName().toString();
        for (String s : HotConfig.HOT_ITEM_ADDITIONS)
        {
            if (regName.equals(s))
            {
                return true;
            }
        }
        return false;
    }

    public static boolean isColdItem(ItemStack stack)
    {
        String regName = stack.getItem().getRegistryName().toString();
        for (String s : HotConfig.COLD_ITEM_ADDITIONS)
        {
            if (regName.equals(s))
            {
                return true;
            }
        }
        return false;
    }

    public static boolean isGaseousItem(ItemStack stack)
    {
        String regName = stack.getItem().getRegistryName().toString();
        for (String s : HotConfig.GASEOUS_ITEM_ADDITIONS)
        {
            if (regName.equals(s))
            {
                return true;
            }
        }
        return false;
    }
}