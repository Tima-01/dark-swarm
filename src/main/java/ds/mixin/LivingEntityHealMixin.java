package ds.mixin;

import ds.util.ArmorHealthManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityHealMixin {
    @Inject(method = "heal", at = @At("HEAD"), cancellable = true)
    private void darkSwarm$healHealth(float amount, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (!(entity instanceof ServerPlayerEntity player) || amount <= 0 || !player.isAlive()) return;

        float normalMissing = Math.max(0, player.getMaxHealth() - player.getHealth());
        float normalHealed = Math.min(amount, normalMissing);
        float remaining = amount - normalHealed;

        if (normalHealed > 0)
            player.setHealth(player.getHealth() + normalHealed);

        if (remaining > 0)
            ArmorHealthManager.heal(player, remaining);

        ci.cancel();
    }
}