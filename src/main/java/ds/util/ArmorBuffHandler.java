package ds.util;

import ds.entity.custom.MinionEntity;
import ds.item.ModArmorMaterials;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;
/**
 * Manages armor-based buff effects for tamed entities.
 *
 * <p>The buff area extends
 * 8 blocks from the player and refreshes every server tick. Also tracks the count of currently
 * buffed entities for UI display purposes.
 *
 * <p>Registration: {@link #registerArmorBuffsEvent()} must be called during mod initialization.
 *
 * @see ArmorUtil for armor validation
 * @see MinionEntity for custom minion entities
 */
public class ArmorBuffHandler {

    private static boolean eventRegistered = false;
    private static int lastBuffesEntitiesCount = 0;

    public static void registerArmorBuffsEvent() {
        if (eventRegistered) return;
        eventRegistered = true;

        ServerTickEvents.END_SERVER_TICK.register(ArmorBuffHandler::onServerTick);
    }

    private static void onServerTick(MinecraftServer server) {
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            if (ArmorUtil.hasCorrectArmorOn(ModArmorMaterials.SOUL_ARMOR_MATERIAL, player)) {
                applyBuffsToNearbyTamedEntities(player);
            }
        }
    }

    private static void applyBuffsToNearbyTamedEntities(PlayerEntity player) {
        World world = player.getWorld();
        Box playerArea = player.getBoundingBox().expand(8);

        List<Entity> nearbyEntities = world.getOtherEntities(player, playerArea,
            entity -> entity instanceof WolfEntity || entity instanceof MinionEntity);

        int buffedCount = 0;

        for (Entity entity : nearbyEntities) {
            // Condition check for wolfes
            if (entity instanceof WolfEntity wolf) {
                if (wolf.getOwnerUuid() != null && wolf.getOwnerUuid().equals(player.getUuid())) {
                    addStrengthBuff(wolf);
                    buffedCount++;
                }
            }

            // Condition check for minions
            if (entity instanceof MinionEntity minion) {
                if (minion.getOwner() == player) {
                    addStrengthBuff(minion);
                    buffedCount++;
                }
            }
        }

        // Refresh every tick
        lastBuffesEntitiesCount = buffedCount;
    }

    private static void addStrengthBuff(TameableEntity entity) {
        entity.addStatusEffect(new StatusEffectInstance(
                StatusEffects.JUMP_BOOST,
                200,
                5,
                false,
                false
        ));
        entity.addStatusEffect(new StatusEffectInstance(
                StatusEffects.GLOWING,
                200,
                2,
                false,
                false
        ));
    }

    public static int getLastBuffesEntitiesCount() {
        return lastBuffesEntitiesCount;
    }
}
