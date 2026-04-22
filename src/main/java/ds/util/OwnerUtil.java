package ds.util;

import ds.entity.custom.MinionEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.TameableEntity;
/**
 * Utility class for resolving ownership and alliance relationships between entities.
 * <p>
 * Provides a unified way to determine the "true owner" of an entity, supporting both
 * vanilla tameable entities (e.g., wolves) and custom entities such as {@link MinionEntity}.
 *
 * @see MinionEntity for custom owned entities
 * @see net.minecraft.entity.passive.TameableEntity for vanilla tameable entities
 */
public class OwnerUtil {
    public static LivingEntity getOwner(LivingEntity entity) {
        LivingEntity owner = null;
        if (entity instanceof TameableEntity tameable) owner = tameable.getOwner() instanceof LivingEntity o ? o : null;
        if (entity instanceof MinionEntity minion) owner = minion.getOwner();
        return owner != null ? owner : entity;
    }
    // Determines whether two entities are allied based on shared ownership
    public static boolean isAllied(LivingEntity a, LivingEntity b) {
        return getOwner(a) == getOwner(b);
    }
}