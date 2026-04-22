package ds.mixin;

import ds.effects.ModEffects;
import ds.util.OwnerUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/**
 * Injects fire damage immunity behavior into {@link LivingEntity} for entities controlled by
 * players affected by the Blazing Aura effect.
 *
 * <p>This mixin hooks into the {@code damage} method, which is the central entry point for
 * all types of entity damage in Minecraft. It allows owned entities (such as wolves or minions)
 * to become immune to fire-based damage when their owner has {@link ModEffects#BLAZING_AURA}.
 *
 * <p>This ensures complete immunity to fire damage sources such as lava, fire, and fire-based projectiles
 * for all controlled entities.
 *
 * <p>Injection point: {@code @At("HEAD")} ensures damage is cancelled before any processing occurs.
 *
 * @see ModEffects#BLAZING_AURA for the effect enabling fire immunity
 * @see OwnerUtil for ownership resolution and entity delegation
 */
@Mixin(LivingEntity.class)
public class BlazingAuraFireImmunityMixin {
    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void fireDamageImmunity(net.minecraft.entity.damage.DamageSource source,
                                    float amount,
                                    CallbackInfoReturnable<Boolean> cir) {

        LivingEntity entity = (LivingEntity)(Object)this;
        if (!(entity instanceof MobEntity mob)) return;
        LivingEntity owner = OwnerUtil.getOwner(mob);
        if (owner == null || owner == mob || !owner.hasStatusEffect(ModEffects.BLAZING_AURA)) return;
        if (source.isIn(net.minecraft.registry.tag.DamageTypeTags.IS_FIRE)) {
            cir.setReturnValue(false);
        }
    }
}