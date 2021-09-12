package com.buuz135.hotornot.client;

import java.util.function.Consumer;
import java.util.function.Predicate;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
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
        if (HotConfig.TOOLTIP && !stack.isEmpty() && !HotLists.isRemoved(stack))
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
            else if (HotLists.isHot(stack))
            {
                event.getToolTip().add(FluidEffect.HOT.color + new TextComponentTranslation(FluidEffect.HOT.tooltip).getUnformattedText());
            }
            else if (HotLists.isCold(stack))
            {
                event.getToolTip().add(FluidEffect.COLD.color + new TextComponentTranslation(FluidEffect.COLD.tooltip).getUnformattedText());
            }
            else if (HotLists.isGaseous(stack))
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
        HOT(fluidStack -> fluidStack.getFluid().getTemperature(fluidStack) >= HotConfig.HOT_FLUID + 273 && HotConfig.HOT_FLUIDS, entityPlayerMP -> entityPlayerMP.setFire(1), TextFormatting.RED, "tooltip.hotornot.toohot"),
        COLD(fluidStack -> fluidStack.getFluid().getTemperature(fluidStack) <= HotConfig.COLD_FLUID + 273 && HotConfig.COLD_FLUIDS, entityPlayerMP ->
        {
            entityPlayerMP.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 21, 1));
            entityPlayerMP.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 21, 1));
        }, TextFormatting.AQUA, "tooltip.hotornot.toocold"),
        GAS(fluidStack -> fluidStack.getFluid().isGaseous(fluidStack) && HotConfig.GASEOUS_FLUIDS, entityPlayerMP -> entityPlayerMP.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 21, 1)), TextFormatting.YELLOW, "tooltip.hotornot.toolight");

        public final Predicate<FluidStack> isValid;
        public final Consumer<EntityPlayerMP> interactPlayer;
        public final TextFormatting color;
        public final String tooltip;

        FluidEffect(Predicate<FluidStack> isValid, Consumer<EntityPlayerMP> interactPlayer, TextFormatting color, String tooltip)
        {
            this.isValid = isValid;
            this.interactPlayer = interactPlayer;
            this.color = color;
            this.tooltip = tooltip;
        }
    }
}