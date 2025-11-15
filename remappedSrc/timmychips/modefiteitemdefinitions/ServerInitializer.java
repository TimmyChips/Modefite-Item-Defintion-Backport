package timmychips.modefiteitemdefinitions;

import net.fabricmc.api.ModInitializer;

public class ServerInitializer implements ModInitializer {
    public static final String MOD_ID = "modefite";

    @Override
    public void onInitialize() {
        ModefiteNetworking.registerPayloads();
        ModefiteNetworking.useKeyGlobalReceiver();
    }
}
