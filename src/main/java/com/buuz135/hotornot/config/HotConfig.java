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

    @Config.Name("Throw entire stack")
    @Config.Comment("If true, the player yeets the entire stack of items")
    public static boolean YEET_STACK = true;

    @Config.Name("Hot fluid temperature")
    @Config.Comment("How hot a fluid should be to start burning the player (in Celsius)")
    public static int TEMP_HOT_FLUID = 480;

    @Config.Name("Cold fluid temperature")
    @Config.Comment("How cold a fluid should be to start adding effects the player (in Celsius)")
    public static int TEMP_COLD_FLUID = 0;

    @Config.Name("Hot item temperature (TFC)")
    @Config.Comment("How hot an item should be to start burning the player (in Celsius)")
    public static int TEMP_HOT_ITEM = 480;

    @Config.RequiresMcRestart()
    @Config.Name("Wooden Tongs durability")
    @Config.Comment("Max durability of Wooden Tongs, 0 for infinite durability")
    public static int DURABILITY_WOODEN_TONGS = 1200;

    @Config.RequiresMcRestart()
    @Config.Name("Mitts durability")
    @Config.Comment("Max durability of Mitts, 0 for infinite durability")
    public static int DURABILITY_MITTS = 12000;

    @Config.RequiresMcRestart()
    @Config.Name("Iron Tongs durability")
    @Config.Comment("Max durability of Iron Tongs, 0 for infinite durability")
    public static int DURABILITY_IRON_TONGS = 0;

    @Config.Name("Effect tick rate")
    @Config.Comment("How frequently to check for and apply effects in ticks (performance sensitive)")
    public static int TICK_RATE = 20;

    @Config.Name("Protection item damage")
    @Config.Comment("How much damage gets applied to the protection item per check")
    public static int ITEM_DAMAGE = 1;

    @Config.Name("Custom hot items")
    @Config.Comment("Hot items that are included manually")
    public static String[] CUSTOM_HOT_ITEMS = new String[] {"minecraft:blaze_rod"};

    @Config.Name("Custom cold items")
    @Config.Comment("Cold items that are included manually")
    public static String[] CUSTOM_COLD_ITEMS = new String[] {"minecraft:ice", "minecraft:packed_ice"};

    @Config.Name("Custom gaseous items")
    @Config.Comment("Gaseous items that are included manually")
    public static String[] CUSTOM_GASEOUS_ITEMS = new String[] {"mod_id:item"};

    @Config.Name("Excluded items")
    @Config.Comment("Items that are exempt from effects")
    public static String[] CUSTOM_REMOVALS = new String[] {"immersiveengineering:drill", "immersiveengineering:chemthrower", "immersivepetroleum:fluid_diesel", "immersivepetroleum:fluid_gasoline"};

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