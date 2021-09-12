package com.buuz135.hotornot.handler;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.buuz135.hotornot.HotOrNot;
import com.buuz135.hotornot.item.ModItems;

@Mod.EventBusSubscriber(modid = HotOrNot.MOD_ID)
public class RegistryHandler
{
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().registerAll(ModItems.ITEMS.toArray(new Item[0]));
    }
}