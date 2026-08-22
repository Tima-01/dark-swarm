package ds.item;

import ds.DarkSwarm;
import ds.effects.ModEffects;
import ds.util.OwnerUtil;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;

import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class ModArmorMaterials {
    /**
     * Тут ты регистрируешь тир брони
     * @see net.minecraft.item.ArmorMaterials для референсов параметров брони
     */
    // Когда текстуру для брони добавляешь название должно быть таким: "name_layer_1.png" так же и для второго слоя.
    public static final RegistryEntry<ArmorMaterial> SOUL_ARMOR_MATERIAL = registerArmorMaterial("soul_armor",
            () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS,1);
                map.put(ArmorItem.Type.LEGGINGS,2);
                map.put(ArmorItem.Type.CHESTPLATE,3);
                map.put(ArmorItem.Type.HELMET,1);
                map.put(ArmorItem.Type.BODY,3);
            }), 20, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,() -> Ingredient.ofItems(ModItems.SOUL),
                    List.of(new ArmorMaterial.Layer(Identifier.of(DarkSwarm.MOD_ID,"soul_armor"))),0,0));

    public static final RegistryEntry<ArmorMaterial> SUMMONER_IRON_ARMOR_MATERIAL = registerArmorMaterial("summoner_iron_armor",
            () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS,2);
                map.put(ArmorItem.Type.LEGGINGS,5);
                map.put(ArmorItem.Type.CHESTPLATE,6);
                map.put(ArmorItem.Type.HELMET,2);
                map.put(ArmorItem.Type.BODY,5);
            }), 20, SoundEvents.ITEM_ARMOR_EQUIP_IRON,() -> Ingredient.ofItems(ModItems.SOUL),
                    List.of(new ArmorMaterial.Layer(Identifier.of(DarkSwarm.MOD_ID,"summoner_iron"))),0,0));

    public static final RegistryEntry<ArmorMaterial> SUMMONER_GOLD_ARMOR_MATERIAL = registerArmorMaterial("summoner_gold_armor",
            () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS,1);
                map.put(ArmorItem.Type.LEGGINGS,3);
                map.put(ArmorItem.Type.CHESTPLATE,5);
                map.put(ArmorItem.Type.HELMET,2);
                map.put(ArmorItem.Type.BODY,7);
            }), 20, SoundEvents.ITEM_ARMOR_EQUIP_GOLD,() -> Ingredient.ofItems(ModItems.SOUL),
                    List.of(new ArmorMaterial.Layer(Identifier.of(DarkSwarm.MOD_ID,"summoner_gold"))),0,0));

    public static final RegistryEntry<ArmorMaterial> SUMMONER_DIAMOND_ARMOR_MATERIAL = registerArmorMaterial("summoner_diamond_armor",
            () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS,3);
                map.put(ArmorItem.Type.LEGGINGS,6);
                map.put(ArmorItem.Type.CHESTPLATE,8);
                map.put(ArmorItem.Type.HELMET,4);
                map.put(ArmorItem.Type.BODY, 11);
            }), 20, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND ,() -> Ingredient.ofItems(ModItems.SOUL),
                    List.of(new ArmorMaterial.Layer(Identifier.of(DarkSwarm.MOD_ID,"summoner_diamond"))),2.0f,0));

    public static RegistryEntry<ArmorMaterial> registerArmorMaterial(String name, Supplier<ArmorMaterial> material){
        return Registry.registerReference(Registries.ARMOR_MATERIAL, Identifier.of(DarkSwarm.MOD_ID, name), material.get());
    }
}
