package ds.effects.custom;

import ds.mixin.FreezingPresenceFreezeAttackMixin;
import ds.util.OwnerUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

/**
 * Logical "flag" effect used to enable special combat behaviors for owned allies.
 *
 * @see FreezingPresenceFreezeAttackMixin for freeze/slow on-hit behavior
 * @see OwnerUtil for ownership and alliance resolution
 */
public class FreezingPresence extends StatusEffect {
    public FreezingPresence(StatusEffectCategory category, int color) {
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

