package ds.client;

import ds.DarkSwarm;
import ds.util.IEntityDataSaver;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;

public class MinionHud implements HudRenderCallback {

    private static final Identifier MINION_ICON_TEXTURE = Identifier.of(DarkSwarm.MOD_ID, "textures/gui/minion_icon.png");

    @Override    public void onHudRender(DrawContext drawContext, RenderTickCounter tickCounter) {
        drawContext.drawText(MinecraftClient.getInstance().textRenderer,
                "ЮНСОП ПРИВЕТ ХИИХХИХИХ", 100, 100, 0xFF0000, true);
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.player == null) return;

        if (client.player instanceof IEntityDataSaver saver) {
            int count = saver.darkswarm$getMinionCount();


            int x = 10;
            int y = 10;


            int iconSize = 32;


            drawContext.drawTexture(MINION_ICON_TEXTURE,
                    x, y,
                    0, 0,
                    iconSize, iconSize,
                    iconSize, iconSize);


            String countText = String.valueOf(count);

            int textWidth = client.textRenderer.getWidth(countText);

            int textX = x + (iconSize / 2) - (textWidth / 2);

            int textY = y + iconSize + 2;

            drawContext.drawText(client.textRenderer, countText, textX, textY, 0xFFFFFF, true);
        }
    }
}