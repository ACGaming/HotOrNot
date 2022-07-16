package com.buuz135.hotornot.item;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;

import com.buuz135.hotornot.config.HotConfig;

public class ModItems
{
    public static List<Item> ITEMS = new ArrayList<>();

    public static Item WOODEN_TONGS = new HotItem("wooden_tongs", HotConfig.DURABILITY_WOODEN_TONGS);
    public static Item MITTS = new HotItem("mitts", HotConfig.DURABILITY_MITTS);
    public static Item IRON_TONGS = new HotItem("iron_tongs", HotConfig.DURABILITY_IRON_TONGS);
}