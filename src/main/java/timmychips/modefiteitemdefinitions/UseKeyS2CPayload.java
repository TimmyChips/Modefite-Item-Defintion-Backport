package timmychips.modefiteitemdefinitions;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.util.UUID;

public record UseKeyS2CPayload(UUID playerUuid, ItemStack itemStack, boolean isUsing) implements FabricPacket {
    public static final Identifier ID = new Identifier(ServerInitializer.MOD_ID, "use_key_sync");
    public static final PacketType<UseKeyS2CPayload> TYPE = PacketType.create(ID, UseKeyS2CPayload::new);

    public UseKeyS2CPayload(PacketByteBuf buf) {
        this(buf.readUuid(), buf.readItemStack(), buf.readBoolean());
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeUuid(playerUuid);
        buf.writeItemStack(itemStack);
        buf.writeBoolean(isUsing);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}