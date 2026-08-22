package ds.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import ds.DarkSwarm;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.IconWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class SummoningCauldronScreen extends HandledScreen<SummoningCauldronScreenHandler> {
    private static final Identifier GUI_TEXTURE =
            Identifier.of(DarkSwarm.MOD_ID, "textures/gui/summoning_cauldron/summoning_cauldron.png");
    public static final Identifier MINION_ICON =
            Identifier.of(DarkSwarm.MOD_ID, "textures/gui/minion_icon.png");
    public SummoningCauldronScreen(SummoningCauldronScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();

        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        this.addDrawableChild(new IconButtonWidget( x + 120, y + 27, 32, 32, MINION_ICON, button -> { assert client != null; assert client.interactionManager != null; client.interactionManager.clickButton(handler.syncId, 0); } ));
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);

        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        context.drawTexture(GUI_TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);

        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

//        drawMinionDescription(context, x + 14, y + 14);

        drawMouseoverTooltip(context, mouseX, mouseY);
    }

//    private void drawMinionDescription(DrawContext context, int x, int y) {
//        int width = 68;
//        int height = 58;
//
//        int textX = x + 4;
//        int textY = y + 4;
//
//        Text title = Text.translatable("gui.dark-swarm.summoning_cauldron.minion_title");
//        Text description = Text.translatable("gui.dark-swarm.summoning_cauldron.minion_description");
//
//        context.drawText(
//                textRenderer,
//                title,
//                textX,
//                textY,
//                0xFFFFFF,
//                true
//        );
//
//        var lines = textRenderer.wrapLines(
//                description,
//                width - 8
//        );
//
//        int lineY = textY + 12;
//
//        for (OrderedText line : lines) {
//            if (lineY >= y + height - 8) {
//                break;
//            }
//
//            context.drawText(
//                    textRenderer,
//                    line,
//                    textX,
//                    lineY,
//                    0xAAAAAA,
//                    false
//            );
//
//            lineY += 9;
//        }
//    }
public static class IconButtonWidget extends ButtonWidget {
        private final Identifier texture; public IconButtonWidget(int x, int y, int width, int height, Identifier texture, PressAction onPress) {
            super(x, y, width, height, Text.empty(), onPress, DEFAULT_NARRATION_SUPPLIER );
            this.texture = texture; }

    @Override protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
            RenderSystem.setShaderTexture(0, texture);
            context.drawTexture(texture, getX(), getY(), 0, 0, width, height, width, height);
            if (isHovered()) {
                context.drawBorder( getX(), getY(), width, height, 0xFFFFFFFF );
            }
        }
    }
}