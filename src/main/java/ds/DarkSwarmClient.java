package ds;

import ds.screen.ModScreenHandlers;
import ds.screen.custom.SummoningCauldronScreen;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class DarkSwarmClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(ModScreenHandlers.SUMMONING_CAULDRON_SCREEN_HANDLER, SummoningCauldronScreen::new);
    }
}
