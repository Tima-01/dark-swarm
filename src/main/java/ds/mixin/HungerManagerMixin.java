package ds.mixin;

import ds.util.ArmorHealthManager;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(HungerManager.class)
public abstract class HungerManagerMixin {
    @Redirect(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;canFoodHeal()Z"))
    private boolean darkSwarm$canHealArmorHealth(PlayerEntity player) {
        return player.canFoodHeal() || player.isAlive() && ArmorHealthManager.getCurrentHealth(player) < ArmorHealthManager.getMaxHealth(player);
    }
}