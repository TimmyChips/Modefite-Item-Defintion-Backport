package timmychips.modefiteitemdefinitions;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.UUID;

public class ModefiteNetworking {
    public static void registerPayloads() {

    }

    public static void useKeyGlobalReceiver() {
        ServerPlayNetworking.registerGlobalReceiver(UseKeyC2SPayload.TYPE, (packet, player, responseSender) -> {
            UUID senderUuid = packet.playerUuid();
            ItemStack stack = packet.itemStack();
            boolean isUsing = packet.isUsing();

            UseKeyC2SPayload broadcastPacket = new UseKeyC2SPayload(senderUuid, stack, isUsing);

            for (ServerPlayerEntity otherPlayer : player.server.getPlayerManager().getPlayerList()) {
                if (!otherPlayer.getUuid().equals(senderUuid)) {
                    ServerPlayNetworking.send(otherPlayer, broadcastPacket);
                }
            }
        });
    }
}