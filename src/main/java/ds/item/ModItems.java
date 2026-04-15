package ds.item;

import ds.DarkSwarm;
import ds.entity.ModEntities;
import ds.item.custom.OverlordWhipItem;
import ds.item.custom.WhipItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item SOUL = registerItem("soul", new Item(new Item.Settings()));
    public static final Item MINION_SPAWN_EGG = registerItem("minion_spawn_egg",
            new SpawnEggItem(ModEntities.MINION, 0xa16228, 0x198717, new Item.Settings()));
    public static final Item WHIP = registerItem("whip",
            new WhipItem(ToolMaterials.IRON, new Item.Settings().attributeModifiers(WhipItem.createAttributeModifiers(ToolMaterials.IRON, 2, -2.0f))));


    public static final Item OVERLOD_WHIP = registerItem("overlord_whip",
            new OverlordWhipItem(ToolMaterials.IRON, new Item.Settings().attributeModifiers(WhipItem.createAttributeModifiers(ToolMaterials.IRON, 2, -2.0f))));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(DarkSwarm.MOD_ID, name), item);
    }

    public static void registerModItems() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(SOUL);
        });
    }


}