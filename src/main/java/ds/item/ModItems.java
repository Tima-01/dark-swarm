package ds.item;

import ds.DarkSwarm;
import ds.entity.ModEntities;
import ds.item.custom.ModArmorItem;
import ds.item.custom.OverlordSwordItem;
import ds.item.custom.OverlordWhipItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    //материалы
    public static final Item SOUL = registerItem("soul", new Item(new Item.Settings()));
    public static final Item PROFANED_SOUL = registerItem("profaned_soul", new Item(new Item.Settings()));
    public static final Item HANDLE = registerItem("handle", new Item(new Item.Settings()));
    public static final Item FIRE_STONE = registerItem("fire_stone", new Item(new Item.Settings()));
    public static final Item ICE_STONE = registerItem("ice_stone", new Item(new Item.Settings()));
    public static final Item FIRE_ESSENCE = registerItem("fire_essence", new Item(new Item.Settings()));
    public static final Item ICE_ESSENCE = registerItem("ice_essence", new Item(new Item.Settings()));

    //инструменты
    public static final Item OVERLORD_SWORD = registerItem("overlord_sword",
            new OverlordSwordItem(ModToolMaterials.IRON, new Item.Settings().attributeModifiers(OverlordSwordItem.createAttributeModifiers(ModToolMaterials.IRON, 7, -1.0f))));

    public static final Item OVERLORD_WHIP = registerItem("overlord_whip",
            new OverlordWhipItem(ModToolMaterials.IRON, new Item.Settings().attributeModifiers(OverlordWhipItem.createAttributeModifiers(ModToolMaterials.IRON, 1, -2.0f))));

    //предметы брони
    public static final Item SOUL_HELMET = registerItem("soul_helmet",
            new ModArmorItem(ModArmorMaterials.SOUL_ARMOR_MATERIAL, ArmorItem.Type.HELMET, 1f, new Item.Settings()
                    .maxDamage(ArmorItem.Type.HELMET.getMaxDamage(11))));
    public static final Item SOUL_CHESTPLATE = registerItem("soul_chestplate",
            new ModArmorItem(ModArmorMaterials.SOUL_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE, 2f, new Item.Settings()
                    .maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(16))));
    public static final Item SOUL_BOOTS = registerItem("soul_boots",
            new ModArmorItem(ModArmorMaterials.SOUL_ARMOR_MATERIAL, ArmorItem.Type.BOOTS, 1f, new Item.Settings()
                    .maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(13))));
    public static final Item SOUL_LEGGINGS = registerItem("soul_leggings",
            new ModArmorItem(ModArmorMaterials.SOUL_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS, 1f, new Item.Settings()
                    .maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(15))));

    //iron tier
    public static final Item SUMMONER_IRON_HELMET = registerItem("summoner_iron_helmet",
            new ModArmorItem(ModArmorMaterials.SUMMONER_IRON_ARMOR_MATERIAL, ArmorItem.Type.HELMET, 1f, new Item.Settings()
                    .maxDamage(ArmorItem.Type.HELMET.getMaxDamage(11))));
    public static final Item SUMMONER_IRON_CHESTPLATE = registerItem("summoner_iron_chestplate",
            new ModArmorItem(ModArmorMaterials.SUMMONER_IRON_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE, 2f, new Item.Settings()
                    .maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(16))));
    public static final Item SUMMONER_IRON_BOOTS = registerItem("summoner_iron_boots",
            new ModArmorItem(ModArmorMaterials.SUMMONER_IRON_ARMOR_MATERIAL, ArmorItem.Type.BOOTS, 2f, new Item.Settings()
                    .maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(13))));
    public static final Item SUMMONER_IRON_LEGGINGS = registerItem("summoner_iron_leggings",
            new ModArmorItem(ModArmorMaterials.SUMMONER_IRON_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS, 1f, new Item.Settings()
                    .maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(15))));

    //gold tier
    public static final Item SUMMONER_GOLD_HELMET = registerItem("summoner_gold_helmet",
            new ModArmorItem(ModArmorMaterials.SUMMONER_GOLD_ARMOR_MATERIAL, ArmorItem.Type.HELMET, 2f, new Item.Settings()
                    .maxDamage(ArmorItem.Type.HELMET.getMaxDamage(11))));
    public static final Item SUMMONER_GOLD_CHESTPLATE = registerItem("summoner_gold_chestplate",
            new ModArmorItem(ModArmorMaterials.SUMMONER_GOLD_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE, 4f, new Item.Settings()
                    .maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(16))));
    public static final Item SUMMONER_GOLD_BOOTS = registerItem("summoner_gold_boots",
            new ModArmorItem(ModArmorMaterials.SUMMONER_GOLD_ARMOR_MATERIAL, ArmorItem.Type.BOOTS, 2f, new Item.Settings()
                    .maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(13))));
    public static final Item SUMMONER_GOLD_LEGGINGS = registerItem("summoner_gold_leggings",
            new ModArmorItem(ModArmorMaterials.SUMMONER_GOLD_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS, 1f, new Item.Settings()
                    .maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(15))));

    //diamond tier
    public static final Item SUMMONER_DIAMOND_HELMET = registerItem("summoner_diamond_helmet",
            new ModArmorItem(ModArmorMaterials.SUMMONER_DIAMOND_ARMOR_MATERIAL, ArmorItem.Type.HELMET, 2f, new Item.Settings()
                    .maxDamage(ArmorItem.Type.HELMET.getMaxDamage(11))));
    public static final Item SUMMONER_DIAMOND_CHESTPLATE = registerItem("summoner_diamond_chestplate",
            new ModArmorItem(ModArmorMaterials.SUMMONER_DIAMOND_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE, 6f, new Item.Settings()
                    .maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(16))));
    public static final Item SUMMONER_DIAMOND_BOOTS = registerItem("summoner_diamond_boots",
            new ModArmorItem(ModArmorMaterials.SUMMONER_DIAMOND_ARMOR_MATERIAL, ArmorItem.Type.BOOTS, 4f, new Item.Settings()
                    .maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(13))));
    public static final Item SUMMONER_DIAMOND_LEGGINGS = registerItem("summoner_diamond_leggings",
            new ModArmorItem(ModArmorMaterials.SUMMONER_DIAMOND_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS, 2f, new Item.Settings()
                    .maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(15))));

    //misc
    public static final Item MINION_SPAWN_EGG = registerItem("minion_spawn_egg",
            new SpawnEggItem(ModEntities.MINION, 0xa16228, 0x198717, new Item.Settings()));
    public static final Item SOUL_EATER_SPAWN_EGG = registerItem("soul_eater_spawn_egg",
            new SpawnEggItem(ModEntities.SOUL_EATER, 0x541507, 0xbfb034, new Item.Settings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(DarkSwarm.MOD_ID, name), item);
    }

    // Есчо этот метод нужен для добавления предметов в ванильные категории, я сюда душу добавил потому что она как бы материал
    public static void registerModItems() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(SOUL);
            entries.add(PROFANED_SOUL);
            entries.add(FIRE_STONE);
            entries.add(ICE_STONE);
        });
        // Это боевая категория
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
            entries.add(SOUL_HELMET);
            entries.add(SOUL_CHESTPLATE);
            entries.add(SOUL_BOOTS);
            entries.add(SOUL_LEGGINGS);
            entries.add(OVERLORD_WHIP);
            entries.add(OVERLORD_SWORD);
        });
    }
}