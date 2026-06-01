package ds.item.custom;

import com.google.common.collect.ImmutableMap;
import ds.effects.ModEffects;
import ds.item.ModArmorMaterials;
import ds.util.ArmorUtil;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.List;
import java.util.Map;
/**
 * Handles custom armor buffs, that applies on the player*/
public class ModArmorItem extends ArmorItem {
    public ModArmorItem(RegistryEntry<ArmorMaterial> material, Type type, float bonusHealth, Settings settings) {
        super(material, type, settings.component(DataComponentTypes.ATTRIBUTE_MODIFIERS, createHealthModifier(type, bonusHealth)));
    }

    private static AttributeModifiersComponent createHealthModifier(Type type, float health) {
        return AttributeModifiersComponent.builder().add(
            EntityAttributes.GENERIC_MAX_HEALTH,
            new EntityAttributeModifier(
                    Identifier.of("dark-swarm","armor_health_" + type.getName()),
                    health,
                    EntityAttributeModifier.Operation.ADD_VALUE
            ),
            AttributeModifierSlot.forEquipmentSlot(type.getEquipmentSlot())).build();
    }

    public static final Map<RegistryEntry<ArmorMaterial>, List<StatusEffectInstance>> MATERIAL_TO_EFFECT_MAP =
            (new ImmutableMap.Builder<RegistryEntry<ArmorMaterial>, List<StatusEffectInstance>>())
                    .put(ModArmorMaterials.SOUL_ARMOR_MATERIAL,
                            List.of(
                                    new StatusEffectInstance(ModEffects.BLAZING_AURA, 40, 0, false, false),
                                    new StatusEffectInstance(ModEffects.FREEZING_PRESENCE, 40, 0, false, false)
                            )).build();
    /**Check for armor every Tick*/
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isClient()) {
            if (entity instanceof PlayerEntity player) {
                if (ArmorUtil.hasFullSetOfArmorOn(player)) {
                    evaluateArmorEffects(player);
                }
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    private void evaluateArmorEffects(PlayerEntity player) {
        for (Map.Entry<RegistryEntry<ArmorMaterial>, List<StatusEffectInstance>> entry : MATERIAL_TO_EFFECT_MAP.entrySet()) {
            RegistryEntry<ArmorMaterial> mapArmorMaterial = entry.getKey();
            List<StatusEffectInstance> mapStatusEffects = entry.getValue();

            if (ArmorUtil.hasCorrectArmorOn(mapArmorMaterial, player)) {
                addStatusEffectForMaterial(player, mapArmorMaterial, mapStatusEffects);
            }
        }
    }

    private void addStatusEffectForMaterial(PlayerEntity player, RegistryEntry<ArmorMaterial> mapArmorMaterial,
                                            List<StatusEffectInstance> mapStatusEffect) {
        if (!ArmorUtil.hasCorrectArmorOn(mapArmorMaterial, player)) return;

        boolean fireEnhanced = mapArmorMaterial == ModArmorMaterials.SOUL_ARMOR_MATERIAL && ArmorUtil.hasEnhancement(player, EquipmentSlot.CHEST, ArmorEnhancement.FIRE.getId());
        boolean iceEnhanced = mapArmorMaterial == ModArmorMaterials.SOUL_ARMOR_MATERIAL && ArmorUtil.hasEnhancement(player, EquipmentSlot.CHEST, ArmorEnhancement.ICE.getId());

        for (StatusEffectInstance instance : mapStatusEffect) {
            if (instance.getEffectType() == ModEffects.BLAZING_AURA && !fireEnhanced) continue;
            if (instance.getEffectType() == ModEffects.FREEZING_PRESENCE && !iceEnhanced) continue;
            if (player.hasStatusEffect(instance.getEffectType())) continue;

            player.addStatusEffect(new StatusEffectInstance(
                    instance.getEffectType(),
                    instance.getDuration(),
                    instance.getAmplifier(),
                    instance.isAmbient(),
                    instance.shouldShowParticles()
            ));
        }
    }
}
