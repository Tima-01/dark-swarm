package ds.item;

import ds.DarkSwarm;
import ds.item.custom.ModArmorItem;
import ds.entity.ModEntities;
import ds.item.custom.OverlordSwordItem;
import ds.item.custom.WhipItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ArmorItem;
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
    public static final Item WHIP = registerItem("whip",
            new WhipItem(ModToolMaterials.IRON, new Item.Settings().attributeModifiers(WhipItem.createAttributeModifiers(ModToolMaterials.IRON, 1, -2.0f))));

    public static final Item OVERLORD_SWORD = registerItem("overlord_sword",
            new OverlordSwordItem(ModToolMaterials.IRON, new Item.Settings().attributeModifiers(OverlordSwordItem.createAttributeModifiers(ModToolMaterials.IRON, 7, -1.0f))));

    //предметы брони
    public static final Item SOUL_HELMET = registerItem("soul_helmet",
            new ModArmorItem(ModArmorMaterials.SOUL_ARMOR_MATERIAL, ArmorItem.Type.HELMET, new Item.Settings()
                    .maxDamage(ArmorItem.Type.HELMET.getMaxDamage(15))));

    public static final Item SOUL_CHESTPLATE = registerItem("soul_chestplate",
            new ArmorItem(ModArmorMaterials.SOUL_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(15))));

    public static final Item SOUL_BOOTS = registerItem("soul_boots",
            new ArmorItem(ModArmorMaterials.SOUL_ARMOR_MATERIAL, ArmorItem.Type.BOOTS, new Item.Settings()
                    .maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(15))));

    public static final Item SOUL_LEGGINGS = registerItem("soul_leggings",
            new ArmorItem(ModArmorMaterials.SOUL_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS, new Item.Settings()
                    .maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(15))));



    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(DarkSwarm.MOD_ID, name), item);
    }

    public static void registerModItems() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(SOUL);

            //добавление предметов брони в игру
            entries.add(SOUL_HELMET);
            entries.add(SOUL_CHESTPLATE);
            entries.add(SOUL_BOOTS);
            entries.add(SOUL_LEGGINGS);

            entries.add(OVERLORD_SWORD);
        });
    }
}