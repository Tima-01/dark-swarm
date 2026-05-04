package ds.mixin;

import ds.effects.ModEffects;
import ds.util.OwnerUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEntity.class)
public class FreezingPresenceFreezeAttackMixin {
    @Inject(method = "tryAttack", at = @At("HEAD"))
    private void freezingPresenceAttack(Entity target, CallbackInfoReturnable<Boolean> cir) {
        if (!(target instanceof LivingEntity targetEntity)) return;

        MobEntity attacker = (MobEntity)(Object)this;
        LivingEntity owner = OwnerUtil.getOwner(attacker);

        if (owner == null || owner == attacker) return;
        if (!owner.hasStatusEffect(ModEffects.FREEZING_PRESENCE)) return;
        if (OwnerUtil.isAllied(attacker, targetEntity)) return;

        // Approximate powder-snow frostbite: increase frozen ticks on hit.
        int newFrozenTicks = Math.min(targetEntity.getFrozenTicks() + 80, 200);
        targetEntity.setFrozenTicks(newFrozenTicks);

        // Extra explicit slow, as frozen ticks alone don't guarantee strong slow on all targets.
        targetEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 60, 1, false, true));
    }
}

