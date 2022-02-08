package com.buuz135.hotornot.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.buuz135.hotornot.HotOrNot;

@Config(modid = HotOrNot.MOD_ID, name = "HotOrNotPlus")
public class HotConfig
{
    @Config.Name("Hot items")
    @Config.Comment("If true, hot effects for items will be enabled")
    public static boolean HOT_ITEMS = true;

    @Config.Name("Cold items")
    @Config.Comment("If true, cold effects for items will be enabled")
    public static boolean COLD_ITEMS = true;

    @Config.Name("Gaseous items")
    @Config.Comment("If true, gaseous effects for items will be enabled")
    public static boolean GASEOUS_ITEMS = true;

    @Config.Name("Hot fluids")
    @Config.Comment("If true, hot effects for fluids will be enabled")
    public static boolean HOT_FLUIDS = true;

    @Config.Name("Cold fluids")
    @Config.Comment("If true, cold effects for fluids will be enabled")
    public static boolean COLD_FLUIDS = true;

    @Config.Name("Gaseous fluids")
    @Config.Comment("If true, gaseous effects for fluids will be enabled")
    public static boolean GASEOUS_FLUIDS = true;

    @Config.Name("Tooltips")
    @Config.Comment("If true, items causing effects will get a tooltip")
    public static boolean TOOLTIP = true;

    @Config.Name("Item throwing")
    @Config.Comment("If true, hot items make the player yeet them")
    public static boolean YEET = true;

    @Config.Name("Hot fluid temperature")
    @Config.Comment("How hot a fluid should be to start burning the player (in Celsius)")
    public static int HOT_FLUID_TEMP = 480;

    @Config.Name("Cold fluid temperature")
    @Config.Comment("How cold a fluid should be to start adding effects the player (in Celsius)")
    public static int COLD_FLUID_TEMP = 0;

    @Config.Name("Hot item temperature")
    @Config.Comment("How hot an item should be to start burning the player (in Celsius)")
    public static int HOT_ITEM_TEMP = 480;

    @Config.RequiresMcRestart()
    @Config.Name("Wooden Tongs durability")
    @Config.Comment("Max durability of Wooden Tongs, 0 for infinite durability")
    public static int WOODEN_TONGS_DURABILITY = 1200;

    @Config.RequiresMcRestart()
    @Config.Name("Mitts durability")
    @Config.Comment("Max durability of Mitts, 0 for infinite durability")
    public static int MITTS_DURABILITY = 12000;

    @Config.RequiresMcRestart()
    @Config.Name("Iron Tongs durability")
    @Config.Comment("Max durability of Iron Tongs, 0 for infinite durability")
    public static int IRON_TONGS_DURABILITY = 0;

    @Config.Name("Custom hot items")
    @Config.Comment("Hot items that are included manually")
    public static String[] HOT_ITEM_ADDITIONS = new String[] {"minecraft:blaze_rod"};

    @Config.Name("Custom cold items")
    @Config.Comment("Cold items that are included manually")
    public static String[] COLD_ITEM_ADDITIONS = new String[] {"minecraft:ice", "minecraft:packed_ice"};

    @Config.Name("Custom gaseous items")
    @Config.Comment("Gaseous items that are included manually")
    public static String[] GASEOUS_ITEM_ADDITIONS = new String[] {"mod_id:item"};

    @Config.Name("Excluded items")
    @Config.Comment("Items that are exempt from effects")
    public static String[] ITEM_REMOVALS = new String[] {"immersiveengineering:drill", "immersiveengineering:chemthrower", "immersivepetroleum:fluid_diesel", "immersivepetroleum:fluid_gasoline"};

    @SuppressWarnings("unused")
    @Mod.EventBusSubscriber(modid = HotOrNot.MOD_ID)
    private static class EventHandler
    {
        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
        {
            if (event.getModID().equals(HotOrNot.MOD_ID))
            {
                ConfigManager.sync(HotOrNot.MOD_ID, Config.Type.INSTANCE);
            }
        }
    }
}