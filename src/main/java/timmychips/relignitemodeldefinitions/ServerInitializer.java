package timmychips.relignitemodeldefinitions;

import net.fabricmc.api.ModInitializer;

public class ServerInitializer implements ModInitializer {
    public static final String MOD_ID = "relignite";

    @Override
    public void onInitialize() {
        ReligniteNetworking.registerPayloads();
        ReligniteNetworking.useKeyGlobalReceiver();
    }
}
