package com.buuz135.hotornot.item;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;

import com.buuz135.hotornot.config.HotConfig;

public class ModItems
{
    public static List<Item> ITEMS = new ArrayList<>();

    public static Item WOODEN_TONGS = new HotItem("wooden_tongs", HotConfig.WOODEN_TONGS_DURABILITY);
    public static Item MITTS = new HotItem("mitts", HotConfig.MITTS_DURABILITY);
    public static Item IRON_TONGS = new HotItem("iron_tongs", HotConfig.IRON_TONGS_DURABILITY);
}