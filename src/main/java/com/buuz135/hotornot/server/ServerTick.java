package com.buuz135.hotornot.server;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import com.buuz135.hotornot.config.HotConfig;
import com.buuz135.hotornot.config.HotLists;
import com.buuz135.hotornot.item.ModItems;
import net.dries007.tfc.api.capability.heat.CapabilityItemHeat;
import net.dries007.tfc.api.capability.heat.IItemHeat;

@Mod.EventBusSubscriber
public class ServerTick
{
    @SubscribeEvent
    public static void onTick(TickEvent.PlayerTickEvent event)
    {
        World world = event.player.world;
        EntityPlayer player = event.player;

        if (event.phase == TickEvent.Phase.START && !world.isRemote && world.getTotalWorldTime() % HotConfig.TICK_RATE == 0)
        {
            if (!player.isBurning() && !player.isCreative() && player.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null))
            {
                IItemHandler handler = player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
                for (int i = 0; i < handler.getSlots(); i++)
                {
                    ItemStack stack = handler.extractItem(i, 1, true);
                    if (!stack.isEmpty() && !HotLists.isRemovedItem(stack))
                    {
                        // FLUIDS
                        if (stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null))
                        {
                            IFluidHandlerItem fluidHandlerItem = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
                            FluidStack fluidStack = fluidHandlerItem.drain(1000, false);
                            if (fluidStack != null)
                            {
                                if (HotLists.isHotFluid(fluidStack) || HotLists.isColdFluid(fluidStack) || HotLists.isGaseousFluid(fluidStack))
                                {
                                    if (!damageProtectionItem(player))
                                    {
                                        if (HotLists.isHotFluid(fluidStack))
                                        {
                                            applyHotEffect(player, i);
                                        }
                                        else if (HotLists.isColdFluid(fluidStack))
                                        {
                                            applyColdEffect(player);
                                        }
                                        else if (HotLists.isGaseousFluid(fluidStack))
                                        {
                                            applyGaseousEffect(player);
                                        }
                                    }
                                }
                            }
                        }
                        // CONFIG-ADDED ITEMS
                        else if (HotLists.isHotItem(stack) || HotLists.isColdItem(stack) || HotLists.isGaseousItem(stack))
                        {
                            if (!damageProtectionItem(player))
                            {
                                if (HotLists.isHotItem(stack))
                                {
                                    applyHotEffect(player, i);
                                }
                                else if (HotLists.isColdItem(stack))
                                {
                                    applyColdEffect(player);
                                }
                                else if (HotLists.isGaseousItem(stack))
                                {
                                    applyGaseousEffect(player);
                                }
                            }
                        }
                        // TFC ITEMS
                        else if (Loader.isModLoaded("tfc") && HotConfig.HOT_ITEMS)
                        {
                            if (stack.hasCapability(CapabilityItemHeat.ITEM_HEAT_CAPABILITY, null))
                            {
                                IItemHeat heatHandlerItem = stack.getCapability(CapabilityItemHeat.ITEM_HEAT_CAPABILITY, null);
                                if (heatHandlerItem.getTemperature() >= HotConfig.TEMP_HOT_ITEM)
                                {
                                    if (!damageProtectionItem(player))
                                    {
                                        applyHotEffect(player, i);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static boolean damageProtectionItem(EntityPlayer player)
    {
        ItemStack offHand = player.getHeldItemOffhand();
        if (offHand.getItem().equals(ModItems.MITTS))
        {
            if (HotConfig.DURABILITY_MITTS != 0)
            {
                offHand.damageItem(HotConfig.ITEM_DAMAGE, player);
            }
            return true;
        }
        else if (offHand.getItem().equals(ModItems.WOODEN_TONGS))
        {
            if (HotConfig.DURABILITY_WOODEN_TONGS != 0)
            {
                offHand.damageItem(HotConfig.ITEM_DAMAGE, player);
            }
            return true;
        }
        else if (offHand.getItem().equals(ModItems.IRON_TONGS))
        {
            if (HotConfig.DURABILITY_IRON_TONGS != 0)
            {
                offHand.damageItem(HotConfig.ITEM_DAMAGE, player);
            }
            return true;
        }
        return false;
    }

    public static void applyHotEffect(EntityPlayer player, int index)
    {
        player.setFire(1);
        if (HotConfig.YEET)
        {
            if (HotConfig.YEET_STACK)
            {
                ForgeHooks.onPlayerTossEvent(player, player.inventory.decrStackSize(index, player.inventory.getStackInSlot(index).getCount()), true);
            }
            else
            {
                ForgeHooks.onPlayerTossEvent(player, player.inventory.decrStackSize(index, 1), true);
            }
        }
    }

    public static void applyColdEffect(EntityPlayer player)
    {
        player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 21, 1));
        player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 21, 1));
    }

    public static void applyGaseousEffect(EntityPlayer player)
    {
        player.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 21, 1));
    }
}