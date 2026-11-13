package ds.mixin;

import ds.util.ArmorHealthManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "net.minecraft.entity.effect.RegenerationStatusEffect")
public abstract class RegenerationStatusEffectMixin {
    @Redirect(method = "applyUpdateEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getMaxHealth()F"))
    private float darkSwarm$includeArmorHealth(LivingEntity entity) {
        float max = entity.getMaxHealth();

        if (!(entity instanceof PlayerEntity player)) return max;

        float missing = ArmorHealthManager.getMaxHealth(player) - ArmorHealthManager.getCurrentHealth(player);

        return max + Math.max(0, missing);
    }
}