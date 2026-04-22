package ds.effects.custom;

import ds.mixin.BlazingAuraFireImmunityMixin;
import ds.util.OwnerUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
/**
 * Represents the Blazing Aura status effect.
 *
 * <p>This effect is used as a trigger for combat and defensive mechanics
 * applied to entities controlled by players.
 *
 * <p>Unlike traditional status effects, this effect does not directly apply
 * buffs or damage. Instead, it acts as a logical flag used by mixins and
 * gameplay systems to enable special behaviors such as:
 * Fire-based attack effects for owned entities
 * Fire damage immunity for controlled mobs
 *
 * @see BlazingAuraFireImmunityMixin for fire immunity behavior
 * @see OwnerUtil for effect propagation to owned entities
 */
public class BlazingAura extends StatusEffect {
    public BlazingAura(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        return true;
    }
}
