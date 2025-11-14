package timmychips.modefiteitemdefinitions;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.util.UUID;

import static timmychips.modefiteitemdefinitions.ServerInitializer.MOD_ID;

public record UseKeyC2SPayload(UUID playerUuid, ItemStack itemStack, boolean isUsing) implements FabricPacket {
    public static final Identifier ID = new Identifier(MOD_ID, "use_key");
    public static final PacketType<UseKeyC2SPayload> TYPE = PacketType.create(ID, UseKeyC2SPayload::new);

    public UseKeyC2SPayload(PacketByteBuf buf) {
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