package ds.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import ds.DarkSwarm;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class SummoningCauldronScreen extends HandledScreen<SummoningCauldronScreenHandler> {
    private int shakeTicks = 0;
    private boolean previousNotEnoughHealth = false;
    public static final Identifier GUI_TEXTURE =
            Identifier.of(DarkSwarm.MOD_ID,
                    "textures/gui/summoning_cauldron/summoning_cauldron.png");

    public static final Identifier MINION_ICON =
            Identifier.of(DarkSwarm.MOD_ID,
                    "textures/gui/minion_icon.png");

    public SummoningCauldronScreen(SummoningCauldronScreenHandler handler,
                                   PlayerInventory inventory,
                                   Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();

        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        this.addDrawableChild(new IconButtonWidget(
                x + 120,
                y + 27,
                32,
                32,
                MINION_ICON,
                button -> {
                    assert client != null;
                    assert client.interactionManager != null;

                    client.interactionManager.clickButton(handler.syncId, 0);
                }
        ));
    }

    @Override
    protected void drawBackground(DrawContext context,
                                  float delta,
                                  int mouseX,
                                  int mouseY) {

        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);

        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        context.drawTexture(
                GUI_TEXTURE,
                x,
                y,
                0,
                0,
                backgroundWidth,
                backgroundHeight
        );
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        boolean currentState = handler.hasNotEnoughHealth();
        if (currentState && !previousNotEnoughHealth) {
            shakeTicks = 20;
        }
        previousNotEnoughHealth = currentState;
        if (shakeTicks > 0) {
            shakeTicks--;
        }

        assert client != null;

        float health = client.player.getHealth();
        float maxHealth = client.player.getMaxHealth();

        int hearts = (int)Math.ceil(maxHealth / 2f);
        int fullHearts = (int)(health / 2f);
        boolean halfHeart = health % 2 != 0;

        int heartX = x + 7;
        int heartY = y + 39;

        if (shakeTicks > 0) {
            heartX += client.player.getRandom().nextBetween(-1, 1);
            heartY += client.player.getRandom().nextBetween(-1, 1);
        }

        for (int i = 0; i < hearts; i++) {
            int drawX = heartX + (i * 8);
            context.drawGuiTexture(
                    Identifier.ofVanilla("hud/heart/container"),
                    drawX,
                    heartY,
                    9,
                    9
            );
            if (i < fullHearts) {
                context.drawGuiTexture(
                        Identifier.ofVanilla("hud/heart/full"),
                        drawX,
                        heartY,
                        9,
                        9
                );
            }
            else if (i == fullHearts && halfHeart) {
                context.drawGuiTexture(
                        Identifier.ofVanilla("hud/heart/half"),
                        drawX,
                        heartY,
                        9,
                        9
                );
            }
        }
        if (handler.hasNotEnoughHealth()) {

            context.drawText(
                    textRenderer,
                    Text.translatable(
                            "gui.dark-swarm.summoning_cauldron.not_enough_health"
                    ),
                    x + 42,
                    y + 32,
                    0xFF5555,
                    true
            );
        }

        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    public static class IconButtonWidget extends ButtonWidget {

        private final Identifier texture;

        public IconButtonWidget(int x, int y, int width, int height, Identifier texture, PressAction onPress) {
            super(x, y, width, height, Text.empty(), onPress, DEFAULT_NARRATION_SUPPLIER
            );
            this.texture = texture;
        }

        @Override
        protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
            RenderSystem.setShaderTexture(0, texture);

            context.drawTexture(texture, getX(), getY(), 0, 0, width, height, width, height);

            if (isHovered()) {
                context.drawBorder(
                        getX(),
                        getY(),
                        width,
                        height,
                        0xFFFFFFFF
                );
            }
        }
    }
}