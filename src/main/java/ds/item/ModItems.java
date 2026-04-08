package ds.item;

import ds.DarkSwarm;
import ds.entity.ModEntities;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item SOUL = registerItem("soul", new Item(new Item.Settings()));
    public static final Item MINION_SPAWN_EGG = registerItem("minion_spawn_egg",
            new SpawnEggItem(ModEntities.MINION, 0xa16228, 0x198717, new Item.Settings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(DarkSwarm.MOD_ID, name), item);
    }

    public static void registerModItems() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(SOUL);
        });
    }
}