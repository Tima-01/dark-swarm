package ds;

import ds.block.entity.ModBlockEntities;
import ds.datagen.ModLootTableProvider;
import ds.item.ModItemGroups;
import ds.item.ModItems;
import ds.screen.ModScreenHandlers;
import net.fabricmc.api.ModInitializer;

import ds.util.ModLootTableModifiers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DarkSwarm implements ModInitializer {
	public static final String MOD_ID = "dark-swarm";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");

		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();
		ModLootTableModifiers.modifyLootTables();
		ModBlockEntities.registerBlockEntities();
		ModScreenHandlers.registerScreenHandlers();
	}
}