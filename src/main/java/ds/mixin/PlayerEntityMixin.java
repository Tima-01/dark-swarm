package ds.mixin;

import ds.network.ArmorHealthS2CPayload;
import ds.util.ArmorHealthAccess;
import ds.util.ArmorHealthManager;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements ArmorHealthAccess {
    @Unique
    private float armorHealth;
    @Unique
    private float lastMaxArmorHealth;

    @Override
    public float dark_swarm$getArmorHealth() {
        return armorHealth;
    }

    @Override
    public void dark_swarm$setArmorHealth(float health) {
        float value = Math.max(0.0F, health);
        if (armorHealth == value) return;

        armorHealth = value;

        PlayerEntity player = (PlayerEntity) (Object) this;
        if (player instanceof ServerPlayerEntity serverPlayer && serverPlayer.networkHandler != null)
            ServerPlayNetworking.send(serverPlayer, new ArmorHealthS2CPayload(value));
    }

    @Override
    public float dark_swarm$getLastMaxArmorHealth() {
        return lastMaxArmorHealth;
    }

    @Override
    public void dark_swarm$setLastMaxArmorHealth(float health) {
        lastMaxArmorHealth = Math.max(0.0F, health);
    }

    @ModifyVariable(method = "applyDamage", at = @At("HEAD"), argsOnly = true, ordinal = 0)
    private float darkSwarm$absorbArmorDamage(float amount) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        float armorHealth = ArmorHealthManager.getCurrentHealth(player);

        if (armorHealth <= 0) return amount;

        float absorbed = Math.min(armorHealth, amount);
        ArmorHealthManager.damage(player, absorbed);

        return amount - absorbed;
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void darkSwarm$writeArmorHealth(NbtCompound nbt, CallbackInfo ci) {
        nbt.putFloat("ArmorHealth", armorHealth);
        nbt.putFloat("DarkSwarmLastMaxArmorHealth", lastMaxArmorHealth);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void darkSwarm$readArmorHealth(NbtCompound nbt, CallbackInfo ci) {
        dark_swarm$setArmorHealth(nbt.getFloat("ArmorHealth"));
        lastMaxArmorHealth = nbt.getFloat("DarkSwarmLastMaxArmorHealth");
    }
}