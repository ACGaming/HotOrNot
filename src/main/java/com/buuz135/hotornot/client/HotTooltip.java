package com.buuz135.hotornot.client;

import java.util.function.Predicate;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import com.buuz135.hotornot.config.HotConfig;
import com.buuz135.hotornot.config.HotLists;
import net.dries007.tfc.api.capability.heat.CapabilityItemHeat;
import net.dries007.tfc.api.capability.heat.IItemHeat;

@Mod.EventBusSubscriber(value = Side.CLIENT)
public class HotTooltip
{
    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event)
    {
        ItemStack stack = event.getItemStack();
        if (HotConfig.TOOLTIP && !stack.isEmpty() && !HotLists.isRemovedItem(stack))
        {
            if (stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null))
            {
                IFluidHandlerItem fluidHandlerItem = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
                FluidStack fluidStack = fluidHandlerItem.drain(1000, false);
                if (fluidStack != null)
                {
                    for (FluidEffect effect : FluidEffect.values())
                    {
                        if (effect.isValid.test(fluidStack))
                        {
                            event.getToolTip().add(effect.color + new TextComponentTranslation(effect.tooltip).getUnformattedText());
                        }
                    }
                }
            }
            else if (HotLists.isHotItem(stack))
            {
                event.getToolTip().add(FluidEffect.HOT.color + new TextComponentTranslation(FluidEffect.HOT.tooltip).getUnformattedText());
            }
            else if (HotLists.isColdItem(stack))
            {
                event.getToolTip().add(FluidEffect.COLD.color + new TextComponentTranslation(FluidEffect.COLD.tooltip).getUnformattedText());
            }
            else if (HotLists.isGaseousItem(stack))
            {
                event.getToolTip().add(FluidEffect.GAS.color + new TextComponentTranslation(FluidEffect.GAS.tooltip).getUnformattedText());
            }
            else if (Loader.isModLoaded("tfc"))
            {
                if (stack.hasCapability(CapabilityItemHeat.ITEM_HEAT_CAPABILITY, null))
                {
                    IItemHeat heat = stack.getCapability(CapabilityItemHeat.ITEM_HEAT_CAPABILITY, null);
                    if (heat.getTemperature() >= HotConfig.HOT_ITEM)
                    {
                        event.getToolTip().add(FluidEffect.HOT.color + new TextComponentTranslation(FluidEffect.HOT.tooltip).getUnformattedText());
                    }
                }
            }
        }
    }

    public enum FluidEffect
    {
        HOT(HotLists::isHotFluid, TextFormatting.RED, "tooltip.hotornot.toohot"),
        COLD(HotLists::isColdFluid, TextFormatting.AQUA, "tooltip.hotornot.toocold"),
        GAS(HotLists::isGaseousFluid, TextFormatting.YELLOW, "tooltip.hotornot.toolight");

        public final Predicate<FluidStack> isValid;
        public final TextFormatting color;
        public final String tooltip;

        FluidEffect(Predicate<FluidStack> isValid, TextFormatting color, String tooltip)
        {
            this.isValid = isValid;
            this.color = color;
            this.tooltip = tooltip;
        }
    }
}