package timmychips.modefiteitemdefinitions.property.helper;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtElement;
import org.jetbrains.annotations.Nullable;

public interface NBTComponentGetter {
    @Nullable NbtElement getNBTComponent(ItemStack stack, String key);
}
