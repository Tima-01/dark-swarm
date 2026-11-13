package ds.util;

import ds.effects.ModEffects;
import ds.item.custom.ModArmorItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class ArmorHealthManager {

    public static float calculateMaxHealth(PlayerEntity player) {
        float total = 0.0f;

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (!slot.isArmorSlot()) continue;

            ItemStack stack = player.getEquippedStack(slot);

            if (stack.getItem() instanceof ModArmorItem armor) {
                total += armor.getBonusHealth();
            }
        }
        return total;
    }

    public static float getMaxHealth(PlayerEntity player) {
        return calculateMaxHealth(player);
    }

    public static float getCurrentHealth(PlayerEntity player) {
        return ((ArmorHealthAccess) player).dark_swarm$getArmorHealth();
    }

    public static void setHealth(PlayerEntity player, float health) {
        float max = calculateMaxHealth(player);
        ((ArmorHealthAccess) player).dark_swarm$setArmorHealth(Math.min(health, max));
    }

    public static void damage(PlayerEntity player, float amount) {
        if (amount <= 0) return;

        setHealth(player, getCurrentHealth(player) - amount);
    }

    public static float heal(PlayerEntity player, float amount) {
        if (amount <= 0) return 0;

        float current = getCurrentHealth(player);
        float max = calculateMaxHealth(player);

        System.out.println("[ArmorHealth] Heal amount: " + amount);
        System.out.println("[ArmorHealth] Current: " + current);
        System.out.println("[ArmorHealth] Max: " + max);
        System.out.println("[ArmorHealth] Missing: " + (max - current));

        float healed = Math.min(amount, Math.max(0, max - current));

        if (healed > 0)
            setHealth(player, current + healed);

        return healed;
    }

    public static void tick(PlayerEntity player) {
        ArmorHealthAccess access = (ArmorHealthAccess) player;

        float max = calculateMaxHealth(player);
        float current = getCurrentHealth(player);
        float previousMax = access.dark_swarm$getLastMaxArmorHealth();

        if (max > previousMax)
            setHealth(player, current + (max - previousMax));
        else if (current > max)
            setHealth(player, max);

        access.dark_swarm$setLastMaxArmorHealth(max);

        if (max <= 0)
            remove(player);
        else if (!player.hasStatusEffect(ModEffects.ARMOR_HEALTH))
            player.addStatusEffect(new StatusEffectInstance(ModEffects.ARMOR_HEALTH, -1, 0, false, false, false));
    }
    public static void remove(PlayerEntity player) {
        player.removeStatusEffect(ModEffects.ARMOR_HEALTH);
    }

}