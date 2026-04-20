package ds.screen;

import ds.DarkSwarm;
import ds.screen.custom.InlayTableScreenHandler;
import ds.screen.custom.SummoningCauldronScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class ModScreenHandlers {
    public static final ScreenHandlerType<SummoningCauldronScreenHandler> SUMMONING_CAULDRON_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(DarkSwarm.MOD_ID, "summoning_cauldron_screen_handler"),
                    new ExtendedScreenHandlerType<>(SummoningCauldronScreenHandler::new, BlockPos.PACKET_CODEC));

    public static final ScreenHandlerType<InlayTableScreenHandler> INLAY_TABLE_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(DarkSwarm.MOD_ID, "inlay_table_screen_handler"),
                    new ExtendedScreenHandlerType<>(InlayTableScreenHandler::new, BlockPos.PACKET_CODEC));

    public static void registerScreenHandlers() {}
}
