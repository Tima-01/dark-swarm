package ds.util;

import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

import java.util.UUID;

public class SoulManager {

    private static final float SOUL_HEALTH_COST = 2.0f;
    private static final float MIN_MAX_HEALTH = 2.0f;

    public static final String SOUL_MODIFIER_PREFIX = "soul_";

    /**
     * Проверяет, может ли игрок пожертвовать одним сердцем.
     */
    public static boolean canExtractSoul(PlayerEntity player) {
        return player.getMaxHealth() - SOUL_HEALTH_COST >= MIN_MAX_HEALTH;
    }

    /**
     * Забирает у игрока 1 сердце максимального здоровья
     * и возвращает true, если операция прошла успешно.
     */
    public static boolean extractSoul(PlayerEntity player) {
        if (!canExtractSoul(player)) return false;


        EntityAttributeInstance maxHealth = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);

        if (maxHealth == null) return false;

        Identifier modifierId = Identifier.of(
                "dark-swarm",
                SOUL_MODIFIER_PREFIX + UUID.randomUUID()
        );

        EntityAttributeModifier modifier = new EntityAttributeModifier(
            modifierId,
            -SOUL_HEALTH_COST,
            EntityAttributeModifier.Operation.ADD_VALUE
        );

        maxHealth.addPersistentModifier(modifier);
        /*
         * Если после уменьшения максимального здоровья
         * текущее здоровье стало больше нового максимума,
         * корректируем его.
         */
        if (player.getHealth() > player.getMaxHealth()) {
            player.setHealth(player.getMaxHealth());
        }

        return true;
    }


    public static boolean isSoulModifier(EntityAttributeModifier modifier) {
        Identifier id = modifier.id();
        return id.getNamespace().equals("dark-swarm") && id.getPath().startsWith(SOUL_MODIFIER_PREFIX);
    }
}