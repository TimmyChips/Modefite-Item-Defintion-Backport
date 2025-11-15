package timmychips.modefiteitemdefinitions.comp;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import timmychips.modefiteitemdefinitions.comp.util.*;
import timmychips.modefiteitemdefinitions.comp.*;

import java.util.Optional;
public class LodestoneTrackerComponent {
    private final GlobalPos target;
    private final boolean tracked;

    public LodestoneTrackerComponent(@Nullable GlobalPos target, boolean tracked) {
        this.target = target;
        this.tracked = tracked;
    }
    public Optional<GlobalPos> target() {
        return Optional.ofNullable(this.target);
    }

    public boolean isTracked() {
        return this.tracked;
    }


    @Nullable
    public static LodestoneTrackerComponent fromNbt(NbtCompound nbt) {
        if (!nbt.contains("LodestoneDimension") || !nbt.contains("LodestonePos")) {
            return null;
        }

        try {
            // Read dimension
            String dimensionId = nbt.getString("LodestoneDimension");
            RegistryKey<World> dimension = RegistryKey.of(
                    RegistryKeys.WORLD,
                    new Identifier(dimensionId)
            );

            // Read position
            NbtCompound posNbt = nbt.getCompound("LodestonePos");
            BlockPos pos = new BlockPos(
                    posNbt.getInt("X"),
                    posNbt.getInt("Y"),
                    posNbt.getInt("Z")
            );

            // Read tracked flag
            boolean tracked = !nbt.contains("LodestoneTracked") || nbt.getBoolean("LodestoneTracked");

            GlobalPos globalPos = GlobalPos.create(dimension, pos);
            return new LodestoneTrackerComponent(globalPos, tracked);
        } catch (Exception e) {
            return null;
        }
    }


   // Writes lodestone data to NBT

    public NbtCompound toNbt() {
        NbtCompound nbt = new NbtCompound();

        if (this.target != null) {

            RegistryKey<World> dimension = MathUtils.getDimension(this.target);
            BlockPos pos = MathUtils.getPos(this.target);

            if (dimension != null && pos != null) {
                nbt.putString("LodestoneDimension", dimension.getValue().toString());

                NbtCompound posNbt = new NbtCompound();
                posNbt.putInt("X", pos.getX());
                posNbt.putInt("Y", pos.getY());
                posNbt.putInt("Z", pos.getZ());
                nbt.put("LodestonePos", posNbt);

                nbt.putBoolean("LodestoneTracked", this.tracked);
            }
        }

        return nbt;
    }
}