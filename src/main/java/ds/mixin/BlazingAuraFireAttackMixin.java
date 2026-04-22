package ds.mixin;

import ds.effects.ModEffects;
import ds.util.OwnerUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/**
 * Injects additional combat behavior into {@link MobEntity} to support the Blazing Aura effect.
 *
 * <p>This mixin hooks into the {@code tryAttack} method, which is called whenever a mob
 * attempts to attack a target. It allows owned entities (such as wolves or minions)
 * to apply fire effects based on their owner's status effects.
 *
 * <p>Injection point: {@code @At("HEAD")} ensures the logic runs before the base attack logic.
 *
 * @see ModEffects#BLAZING_AURA for the effect enabling this behavior
 * @see OwnerUtil for ownership and alliance resolution
 */
@Mixin(MobEntity.class)
public class BlazingAuraFireAttackMixin{
    @Inject(method = "tryAttack", at = @At("HEAD"))
    private void blazingAuraAttack(Entity target, CallbackInfoReturnable<Boolean> cir) {
        if (!(target instanceof LivingEntity targetEntity)) return;
        MobEntity attacker = (MobEntity)(Object)this;
        LivingEntity owner = OwnerUtil.getOwner(attacker);
        if (!owner.hasStatusEffect(ModEffects.BLAZING_AURA) || owner == attacker || OwnerUtil.isAllied(attacker, targetEntity)) return;
        targetEntity.setOnFireFor(4);
    }
}
