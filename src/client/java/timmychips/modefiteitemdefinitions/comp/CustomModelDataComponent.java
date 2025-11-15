package timmychips.modefiteitemdefinitions.comp;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.Nullable;
//net.minecraft.component.type.CustomModelDataComponent rpleacment

public class CustomModelDataComponent {

    private final int value;

    public CustomModelDataComponent(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }

    @Nullable
    public static CustomModelDataComponent get(ItemStack stack) {
        NbtCompound nbt = stack.getNbt();
        if (nbt == null || !nbt.contains(DataComponentTypes.CUSTOM_MODEL_DATA)) {
            return null;
      }
        int value = nbt.getInt(DataComponentTypes.CUSTOM_MODEL_DATA);
        return new CustomModelDataComponent(value);
    }

    public static void set(ItemStack stack, int value) {
        NbtCompound nbt = stack.getOrCreateNbt();
        nbt.putInt(DataComponentTypes.CUSTOM_MODEL_DATA, value);
    }

  public static void set(ItemStack stack, CustomModelDataComponent data) {
        set(stack, data.value());
    }

    public static boolean has(ItemStack stack) {
        NbtCompound nbt = stack.getNbt();
        return nbt != null && nbt.contains(DataComponentTypes.CUSTOM_MODEL_DATA);
    }

    public static void remove(ItemStack stack) {
        NbtCompound nbt = stack.getNbt();
      if (nbt != null) {
            nbt.remove(DataComponentTypes.CUSTOM_MODEL_DATA);
        }
    }

    public static int getValue(ItemStack stack) {
        CustomModelDataComponent helper = get(stack);
        return helper != null ? helper.value() : 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CustomModelDataComponent other)) return false;
        return this.value == other.value;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(this.value);
    }

  @Override
    public String toString() {
        return "CustomModelData{value=" + this.value + "}";
    }
}