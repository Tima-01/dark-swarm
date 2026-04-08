package ds;

import ds.client.MinionHud;
import ds.entity.ModEntities;
import ds.entity.client.MinionModel;
import ds.entity.client.MinionRenderer;
import ds.screen.ModScreenHandlers;
import ds.screen.custom.SummoningCauldronScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class DarkSwarmClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        HandledScreens.register(ModScreenHandlers.SUMMONING_CAULDRON_SCREEN_HANDLER, SummoningCauldronScreen::new);


        EntityModelLayerRegistry.registerModelLayer(MinionModel.LAYER_LOCATION, MinionModel::getTexturedModelData);


        EntityRendererRegistry.register(ModEntities.MINION, MinionRenderer::new);

        HudRenderCallback.EVENT.register(new MinionHud());

    }
}

