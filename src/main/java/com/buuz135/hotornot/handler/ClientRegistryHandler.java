package com.buuz135.hotornot.handler;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import com.buuz135.hotornot.HotOrNot;
import com.buuz135.hotornot.item.ModItems;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = HotOrNot.MOD_ID)
public class ClientRegistryHandler
{
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event)
    {
        registerModel(ModItems.WOODEN_TONGS, 0);
        registerModel(ModItems.MITTS, 0);
        registerModel(ModItems.IRON_TONGS, 0);
    }

    private static void registerModel(Item item, int meta)
    {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
}