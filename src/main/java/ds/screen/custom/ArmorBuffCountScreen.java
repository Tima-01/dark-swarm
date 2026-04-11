package ds.screen.custom;

import ds.util.ArmorBuffHandler;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;


/**
 * HUD overlay renderer that displays the count of entities currently affected by armor buffs.
 *
 * This class registers a callback to the HUD render event and displays a counter showing
 * how many nearby tamed entities (minions, wolves, etc.) are currently receiving strength buffs
 * from the Soul Armor set. The counter updates every tick and shows in the top-right corner
 * of the screen with a semi-transparent background.
 *
 * @see ArmorBuffHandler for the buff logic and counter updates
 * @see HudRenderCallback for the render event registration
 */

public class ArmorBuffCountScreen {

    public static void registerArmorBuffCounter() {
        HudRenderCallback.EVENT.register(ArmorBuffCountScreen:: onHudRender);
    }

    public static void onHudRender(DrawContext drawContext, RenderTickCounter tickCount) {
        MinecraftClient client = MinecraftClient.getInstance();

        //Always displayed
        int buffedCount = ArmorBuffHandler.getLastBuffesEntitiesCount();

        //Right top corner coordinates
        int x = drawContext.getScaledWindowWidth() - 150;
        int y = 10;

        // Background rectangle
        drawContext.fill(x, y, x + 140, y + 20, 0x80000000); // Полупрозрачный черный

        // Draw text
        String text = "Affected minions: " + buffedCount;
        drawContext.drawText(
                client.textRenderer,
                text,
                x + 5,
                y + 6,
                0xFFFFFF,  // Белый цвет
                false
        );
    }
}
