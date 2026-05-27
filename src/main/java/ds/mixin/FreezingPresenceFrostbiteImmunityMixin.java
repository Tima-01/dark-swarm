package ds.mixin;

import ds.effects.ModEffects;
import ds.util.OwnerUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.registry.tag.DamageTypeTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class FreezingPresenceFrostbiteImmunityMixin {
    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void freezingDamageImmunity(net.minecraft.entity.damage.DamageSource source,
                                    float amount,
                                    CallbackInfoReturnable<Boolean> cir) {

        LivingEntity entity = (LivingEntity)(Object)this;
        if (!(entity instanceof MobEntity mob)) return;
        LivingEntity owner = OwnerUtil.getOwner(mob);
        if (!owner.hasStatusEffect(ModEffects.FREEZING_PRESENCE)) return;
        if (source.isIn(DamageTypeTags.IS_FREEZING)) {
            cir.setReturnValue(false);
        }
    }
}