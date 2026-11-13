package ds;

import ds.block.entity.ModBlockEntities;
import ds.effects.ModEffects;
import ds.entity.ModEntities;
import ds.events.ModEvents;
import ds.item.ModItemGroups;
import ds.item.ModItems;
import ds.network.ArmorHealthS2CPayload;
import ds.recipe.ModRecipes;
import ds.screen.ModScreenHandlers;
import ds.util.ArmorHealthManager;
import ds.util.ModLootTableModifiers;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DarkSwarm implements ModInitializer {
	public static final String MOD_ID = "dark-swarm";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


	@Override
	public void onInitialize() {
		LOGGER.info("SBR Episode 2!");

		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();
		ModLootTableModifiers.modifyLootTables();
		ModBlockEntities.registerBlockEntities();
		ModScreenHandlers.registerScreenHandlers();
		ModEvents.registerEvents();
		ModEntities.registerModEntities();
        ModRecipes.registerRecipes();
        ModEffects.registerEffects();
        PayloadTypeRegistry.playS2C().register(ArmorHealthS2CPayload.ID, ArmorHealthS2CPayload.CODEC);
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            PlayerEntity player = handler.player;
            float health = ArmorHealthManager.getCurrentHealth(player);
            ServerPlayNetworking.send(handler.player, new ArmorHealthS2CPayload(health));
        });
	}
}