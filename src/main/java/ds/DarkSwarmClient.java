package ds;

import ds.block.entity.ModBlockEntities;
import ds.block.entity.renderer.IceSpikeEntityRenderer;
import ds.block.entity.renderer.NetherSpikeEntityRenderer;
import ds.entity.ModEntities;
import ds.entity.client.MinionModel;
import ds.entity.client.MinionRenderer;
import ds.entity.client.SoulEaterRenderer;
import ds.item.ModItems;
import ds.item.custom.ModArmorRenderer;
import ds.screen.ModScreenHandlers;
import ds.screen.custom.InlayTableScreen;
import ds.screen.custom.SummoningCauldronScreen;
import ds.util.ArmorUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
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

        ArmorUtil.registerEnhancementPredicate(ModItems.SOUL_CHESTPLATE, "fire_enhanced");
        ArmorUtil.registerEnhancementPredicate(ModItems.SOUL_CHESTPLATE, "ice_enhanced");

        ArmorUtil.registerEnhancementPredicate(ModItems.SUMMONER_IRON_CHESTPLATE, "fire_enhanced");
        ArmorUtil.registerEnhancementPredicate(ModItems.SUMMONER_IRON_CHESTPLATE, "ice_enhanced");

        ArmorUtil.registerEnhancementPredicate(ModItems.SUMMONER_GOLD_CHESTPLATE, "fire_enhanced");
        ArmorUtil.registerEnhancementPredicate(ModItems.SUMMONER_GOLD_CHESTPLATE, "ice_enhanced");

        ArmorUtil.registerEnhancementPredicate(ModItems.SUMMONER_DIAMOND_CHESTPLATE, "fire_enhanced");
        ArmorUtil.registerEnhancementPredicate(ModItems.SUMMONER_DIAMOND_CHESTPLATE, "ice_enhanced");

        ArmorRenderer.register(new ModArmorRenderer(), ModItems.SOUL_CHESTPLATE, ModItems.SUMMONER_DIAMOND_CHESTPLATE, ModItems.SUMMONER_GOLD_CHESTPLATE, ModItems.SUMMONER_IRON_CHESTPLATE);
    }
}
