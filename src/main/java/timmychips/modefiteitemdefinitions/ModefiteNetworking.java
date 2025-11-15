package timmychips.modefiteitemdefinitions;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.UUID;

import static timmychips.modefiteitemdefinitions.ServerInitializer.MOD_ID;

public class ModefiteNetworking {
    public static final Identifier USE_KEY_S2C_ID = new Identifier(MOD_ID, "use_key_sync");

    public static void registerPayloads() {
        //WIP NOT DONE REMOVED ERRORS FOR TEST PURPOSES
    }

    public static void useKeyGlobalReceiver() {
        ServerPlayNetworking.registerGlobalReceiver(UseKeyC2SPayload.TYPE, (packet, player, responseSender) -> {
            UUID senderUuid = packet.playerUuid();
            ItemStack stack = packet.itemStack();
            boolean isUsing = packet.isUsing();

            UseKeyS2CPayload broadcastPacket = new UseKeyS2CPayload(senderUuid, stack, isUsing);

            for (ServerPlayerEntity otherPlayer : player.server.getPlayerManager().getPlayerList()) {
                if (!otherPlayer.getUuid().equals(senderUuid)) {
                    ServerPlayNetworking.send(otherPlayer, broadcastPacket);
                }
            }
        });
    }
}