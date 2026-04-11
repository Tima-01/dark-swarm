package ds.client;

import ds.DarkSwarm;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;

public class MinionHud implements HudRenderCallback {

    //private static final Identifier MINION_ICON_TEXTURE = Identifier.of(DarkSwarm.MOD_ID, "textures/gui/minion_icon.png");

    @Override    public void onHudRender(DrawContext drawContext, RenderTickCounter tickCounter) {
        drawContext.drawText(MinecraftClient.getInstance().textRenderer,
                "АМИНА", 100, 100, 0xFF0000, true);
        MinecraftClient client = MinecraftClient.getInstance();

    }}