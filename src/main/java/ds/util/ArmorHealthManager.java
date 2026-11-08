package ds.util;

import ds.effects.ModEffects;
import ds.item.custom.ModArmorItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class ArmorHealthManager {

    private static final int MAX_AMPLIFIER = 255;

    public static void tick(PlayerEntity player) {
        float maxArmorHealth = calculateArmorHealth(player);
        StatusEffectInstance effect = player.getStatusEffect(ModEffects.ARMOR_HEALTH);

        if (maxArmorHealth <= 0.0f) {
            if (effect != null) {
                player.removeStatusEffect(ModEffects.ARMOR_HEALTH);
            }
            return;
        }

        float currentArmorHealth = getCurrentArmorHealth(player);

        if (currentArmorHealth > maxArmorHealth) {
            currentArmorHealth = maxArmorHealth;
        }

        if (currentArmorHealth <= 0.0f) {
            currentArmorHealth = maxArmorHealth;
        }

        setArmorHealth(player, currentArmorHealth);
    }

    public static float calculateArmorHealth(PlayerEntity player) {
        float total = 0.0f;

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (!slot.isArmorSlot()) continue;

            ItemStack stack = player.getEquippedStack(slot);

            if (stack.getItem() instanceof ModArmorItem armorItem) {
                total += armorItem.getBonusHealth();
            }
        }

        return total;
    }

    public static float getCurrentArmorHealth(PlayerEntity player) {
        StatusEffectInstance effect = player.getStatusEffect(ModEffects.ARMOR_HEALTH);

        if (effect == null) {
            return 0.0f;
        }

        return (effect.getAmplifier() + 1) / 2.0f;
    }

    public static void setArmorHealth(PlayerEntity player, float health) {
        float maxArmorHealth = calculateArmorHealth(player);

        if (maxArmorHealth <= 0.0f) {
            player.removeStatusEffect(ModEffects.ARMOR_HEALTH);
            return;
        }

        health = Math.max(0.0f, Math.min(health, maxArmorHealth));

        if (health <= 0.0f) {
            player.removeStatusEffect(ModEffects.ARMOR_HEALTH);
            return;
        }

        int amplifier = Math.min(MAX_AMPLIFIER, Math.max(0, Math.round(health * 2.0f) - 1));

        player.addStatusEffect(new StatusEffectInstance(
                ModEffects.ARMOR_HEALTH,
                -1,
                amplifier,
                false,
                false,
                false
        ));
    }

    public static void damageArmorHealth(PlayerEntity player, float amount) {
        if (amount <= 0.0f) return;

        float current = getCurrentArmorHealth(player);

        if (current <= 0.0f) return;

        setArmorHealth(player, current - amount);
    }

    public static float healArmorHealth(PlayerEntity player, float amount) {
        if (amount <= 0.0f) return 0.0f;

        float max = calculateArmorHealth(player);
        float current = getCurrentArmorHealth(player);

        if (max <= current) return 0.0f;

        float healed = Math.min(amount, max - current);

        setArmorHealth(player, current + healed);

        return healed;
    }

    public static void remove(PlayerEntity player) {
        player.removeStatusEffect(ModEffects.ARMOR_HEALTH);
    }
}