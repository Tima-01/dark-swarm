package ds;

import ds.block.entity.ModBlockEntities;
import ds.datagen.ModLootTableProvider;
import ds.entity.ModEntities;
import ds.item.ModItemGroups;
import ds.item.ModItems;
import ds.screen.ModScreenHandlers;
import ds.util.ArmorBuffHandler;
import net.fabricmc.api.ModInitializer;

import ds.util.ModLootTableModifiers;

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

		ModEntities.registerModEntities();
		ArmorBuffHandler.registerArmorBuffsEvent();
	}
}