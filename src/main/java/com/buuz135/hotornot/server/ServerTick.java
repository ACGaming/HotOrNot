package com.buuz135.hotornot.server;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fml.common.FMLCommonHandler;
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
    public static void onTick(TickEvent.WorldTickEvent event)
    {
        if (event.phase == TickEvent.Phase.START)
        {
            for (EntityPlayerMP entityPlayerMP : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers())
            {
                if (!entityPlayerMP.isBurning() && !entityPlayerMP.isCreative() && entityPlayerMP.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null))
                {
                    IItemHandler handler = entityPlayerMP.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
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
                                        ItemStack offHand = entityPlayerMP.getHeldItemOffhand();
                                        if (offHand.getItem().equals(ModItems.MITTS))
                                        {
                                            if (HotConfig.MITTS_DURABILITY != 0)
                                            {
                                                offHand.damageItem(1, entityPlayerMP);
                                            }
                                        }
                                        else if (offHand.getItem().equals(ModItems.WOODEN_TONGS))
                                        {
                                            if (HotConfig.WOODEN_TONGS_DURABILITY != 0)
                                            {
                                                offHand.damageItem(1, entityPlayerMP);
                                            }
                                        }
                                        else if (offHand.getItem().equals(ModItems.IRON_TONGS))
                                        {
                                            if (HotConfig.IRON_TONGS_DURABILITY != 0)
                                            {
                                                offHand.damageItem(1, entityPlayerMP);
                                            }
                                        }
                                        else if (event.world.getTotalWorldTime() % 20 == 0)
                                        {
                                            if (HotLists.isHotFluid(fluidStack))
                                            {
                                                entityPlayerMP.setFire(1);
                                                if (HotConfig.YEET)
                                                {
                                                    entityPlayerMP.dropItem(stack, false, true);
                                                    entityPlayerMP.inventory.decrStackSize(i, 1);
                                                }
                                            }
                                            else if (HotLists.isColdFluid(fluidStack))
                                            {
                                                entityPlayerMP.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 21, 1));
                                                entityPlayerMP.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 21, 1));
                                            }
                                            else if (HotLists.isGaseousFluid(fluidStack))
                                            {
                                                entityPlayerMP.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 21, 1));
                                            }
                                        }
                                    }
                                }
                            }
                            // ITEMS
                            if (HotConfig.ITEM_EFFECTS)
                            {
                                // TFC ITEMS
                                if (Loader.isModLoaded("tfc"))
                                {
                                    if (stack.hasCapability(CapabilityItemHeat.ITEM_HEAT_CAPABILITY, null))
                                    {
                                        IItemHeat heatHandlerItem = stack.getCapability(CapabilityItemHeat.ITEM_HEAT_CAPABILITY, null);
                                        if (heatHandlerItem.getTemperature() >= HotConfig.HOT_ITEM)
                                        {
                                            ItemStack offHand = entityPlayerMP.getHeldItemOffhand();
                                            if (offHand.getItem().equals(ModItems.MITTS))
                                            {
                                                if (HotConfig.MITTS_DURABILITY != 0)
                                                {
                                                    offHand.damageItem(1, entityPlayerMP);
                                                }
                                            }
                                            else if (offHand.getItem().equals(ModItems.WOODEN_TONGS))
                                            {
                                                if (HotConfig.WOODEN_TONGS_DURABILITY != 0)
                                                {
                                                    offHand.damageItem(1, entityPlayerMP);
                                                }
                                            }
                                            else if (offHand.getItem().equals(ModItems.IRON_TONGS))
                                            {
                                                if (HotConfig.IRON_TONGS_DURABILITY != 0)
                                                {
                                                    offHand.damageItem(1, entityPlayerMP);
                                                }
                                            }
                                            else if (event.world.getTotalWorldTime() % 10 == 0)
                                            {
                                                entityPlayerMP.setFire(1);
                                                if (HotConfig.YEET)
                                                {
                                                    entityPlayerMP.dropItem(stack, false, true);
                                                    entityPlayerMP.inventory.decrStackSize(i, 1);
                                                }
                                            }
                                        }
                                    }
                                }
                                // MANUALLY ADDED ITEMS
                                if (HotLists.isHotItem(stack) || HotLists.isColdItem(stack) || HotLists.isGaseousItem(stack))
                                {
                                    ItemStack offHand = entityPlayerMP.getHeldItemOffhand();
                                    if (offHand.getItem().equals(ModItems.MITTS))
                                    {
                                        if (HotConfig.MITTS_DURABILITY != 0)
                                        {
                                            offHand.damageItem(1, entityPlayerMP);
                                        }
                                    }
                                    else if (offHand.getItem().equals(ModItems.WOODEN_TONGS))
                                    {
                                        if (HotConfig.WOODEN_TONGS_DURABILITY != 0)
                                        {
                                            offHand.damageItem(1, entityPlayerMP);
                                        }
                                    }
                                    else if (offHand.getItem().equals(ModItems.IRON_TONGS))
                                    {
                                        if (HotConfig.IRON_TONGS_DURABILITY != 0)
                                        {
                                            offHand.damageItem(1, entityPlayerMP);
                                        }
                                    }
                                    else if (event.world.getTotalWorldTime() % 10 == 0)
                                    {
                                        if (HotLists.isHotItem(stack))
                                        {
                                            entityPlayerMP.setFire(1);
                                            if (HotConfig.YEET)
                                            {
                                                entityPlayerMP.dropItem(stack, false, true);
                                                entityPlayerMP.inventory.decrStackSize(i, 1);
                                            }
                                        }
                                        else if (HotLists.isColdItem(stack))
                                        {
                                            entityPlayerMP.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 21, 1));
                                            entityPlayerMP.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 21, 1));
                                        }
                                        else if (HotLists.isGaseousItem(stack))
                                        {
                                            entityPlayerMP.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 21, 1));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}