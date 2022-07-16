package com.buuz135.hotornot.config;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class HotLists
{
    public static boolean isHotFluid(FluidStack fluidStack)
    {
        return HotConfig.HOT_FLUIDS && fluidStack.getFluid().getTemperature(fluidStack) >= HotConfig.TEMP_HOT_FLUID + 273;
    }

    public static boolean isColdFluid(FluidStack fluidStack)
    {
        return HotConfig.COLD_FLUIDS && fluidStack.getFluid().getTemperature(fluidStack) <= HotConfig.TEMP_COLD_FLUID + 273;
    }

    public static boolean isGaseousFluid(FluidStack fluidStack)
    {
        return HotConfig.GASEOUS_FLUIDS && fluidStack.getFluid().isGaseous(fluidStack);
    }

    public static boolean isRemovedItem(ItemStack stack)
    {
        String regName = stack.getItem().getRegistryName().toString();
        for (String s : HotConfig.CUSTOM_REMOVALS)
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
        if (HotConfig.HOT_ITEMS)
        {
            String regName = stack.getItem().getRegistryName().toString();
            for (String s : HotConfig.CUSTOM_HOT_ITEMS)
            {
                if (regName.equals(s))
                {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isColdItem(ItemStack stack)
    {
        if (HotConfig.COLD_ITEMS)
        {
            String regName = stack.getItem().getRegistryName().toString();
            for (String s : HotConfig.CUSTOM_COLD_ITEMS)
            {
                if (regName.equals(s))
                {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isGaseousItem(ItemStack stack)
    {
        if (HotConfig.GASEOUS_ITEMS)
        {
            String regName = stack.getItem().getRegistryName().toString();
            for (String s : HotConfig.CUSTOM_GASEOUS_ITEMS)
            {
                if (regName.equals(s))
                {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isCustomProtectionItem(ItemStack stack)
    {
        String regName = stack.getItem().getRegistryName().toString();
        for (String s : HotConfig.CUSTOM_PROTECTION_ITEM)
        {
            if (regName.equals(s))
            {
                return true;
            }
        }
        return false;
    }
}