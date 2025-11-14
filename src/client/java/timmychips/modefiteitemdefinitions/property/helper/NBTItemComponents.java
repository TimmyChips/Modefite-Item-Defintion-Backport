package timmychips.modefiteitemdefinitions.property.helper;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class NBTItemComponents implements NBTComponentGetter {

    private static final String BANNER_PATTERNS = "minecraft:banner_patterns";

    @Override
    public NbtElement getNBTComponent(ItemStack stack, String key) {
        if (!stack.hasNbt()) return null;
        NbtCompound tag = stack.getNbt();
        if (tag == null) return null;
        String keyId = String.valueOf(Identifier.tryParse(key));

        switch (keyId) {
            case BANNER_PATTERNS -> {
                return tag.contains("Pattern") ? tag.getCompound("Pattern") : null;
            }
            default -> {
                return null;
            }
        }
    }
}
