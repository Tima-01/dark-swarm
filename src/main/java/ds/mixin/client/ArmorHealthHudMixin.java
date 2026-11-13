package ds.mixin.client;

import ds.util.ArmorHealthManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class ArmorHealthHudMixin {
    @Unique
    private static final Identifier FULL_HEART = Identifier.of("dark-swarm", "textures/gui/heart/armor_heart_full.png");
    @Unique
    private static final Identifier HALF_HEART = Identifier.of("dark-swarm", "textures/gui/heart/armor_heart_half.png");
    @Unique
    private static final Identifier EMPTY_HEART = Identifier.of("dark-swarm", "textures/gui/heart/armor_heart_container.png");

    @ModifyVariable(method = "renderHealthBar", at = @At("HEAD"), argsOnly = true, ordinal = 0)
    private float darkSwarm$extendHealthBar(float maxHealth) {
        PlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) return maxHealth;

        return maxHealth + ArmorHealthManager.getMaxHealth(player);
    }

    @ModifyArg(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderArmor(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/entity/player/PlayerEntity;IIII)V"), index = 3)
    private int darkSwarm$moveArmorIcons(int y) {
        PlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) return y;

        int normalHearts = MathHelper.ceil(player.getMaxHealth() / 2.0F);
        int absorptionHearts = MathHelper.ceil(player.getAbsorptionAmount() / 2.0F);
        int armorHearts = MathHelper.ceil(ArmorHealthManager.getMaxHealth(player) / 2.0F);

        int normalRows = (normalHearts + absorptionHearts + 9) / 10;
        int totalRows = (normalHearts + armorHearts + absorptionHearts + 9) / 10;

        return y - (totalRows - normalRows) * 10;
    }

    @Inject(method = "renderHealthBar", at = @At("TAIL"))
    private void darkSwarm$renderArmorHealth(DrawContext context, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking, CallbackInfo ci) {
        float armorMax = ArmorHealthManager.getMaxHealth(player);
        float armorCurrent = ArmorHealthManager.getCurrentHealth(player);

        if (armorMax <= 0) return;

        int normalHearts = MathHelper.ceil(player.getMaxHealth() / 2.0F);
        int armorHearts = MathHelper.ceil(armorMax / 2.0F);

        for (int i = 0; i < armorHearts; i++) {
            int index = normalHearts + i;
            int heartX = x + index % 10 * 8;
            int heartY = y - index / 10 * lines;

            float remaining = armorCurrent - i * 2;

            context.drawTexture(EMPTY_HEART, heartX, heartY, 0, 0, 9, 9, 9, 9);

            if (remaining >= 2)
                context.drawTexture(FULL_HEART, heartX, heartY, 0, 0, 9, 9, 9, 9);
            else if (remaining > 0)
                context.drawTexture(HALF_HEART, heartX, heartY, 0, 0, 9, 9, 9, 9);
        }
    }
}