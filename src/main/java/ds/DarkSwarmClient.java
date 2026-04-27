package ds;

import ds.block.entity.ModBlockEntities;
import ds.block.entity.custom.IceSpikeEntity;
import ds.block.entity.renderer.IceSpikeEntityRenderer;
import ds.block.entity.renderer.NetherSpikeEntityRenderer;
import ds.entity.ModEntities;
import ds.entity.client.MinionModel;
import ds.entity.client.MinionRenderer;
import ds.entity.client.SoulEaterRenderer;
import ds.screen.ModScreenHandlers;
import ds.screen.custom.InlayTableScreen;
import ds.screen.custom.SummoningCauldronScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class DarkSwarmClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        HandledScreens.register(ModScreenHandlers.SUMMONING_CAULDRON_SCREEN_HANDLER, SummoningCauldronScreen::new);
        HandledScreens.register(ModScreenHandlers.INLAY_TABLE_SCREEN_HANDLER, InlayTableScreen::new);

        EntityModelLayerRegistry.registerModelLayer(MinionModel.LAYER_LOCATION, MinionModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.MINION, MinionRenderer::new);

        EntityRendererRegistry.register(ModEntities.SOUL_EATER, SoulEaterRenderer::new);

        BlockEntityRendererFactories.register(ModBlockEntities.NETHER_SPIKE_ENTITY_BE, NetherSpikeEntityRenderer::new);

        BlockEntityRendererFactories.register(ModBlockEntities.ICE_SPIKE_ENTITY_BE, IceSpikeEntityRenderer::new);
    }
}