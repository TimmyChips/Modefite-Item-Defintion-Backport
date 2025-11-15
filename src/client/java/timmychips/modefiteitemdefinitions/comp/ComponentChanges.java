package timmychips.modefiteitemdefinitions.comp;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtInt;
import net.minecraft.nbt.NbtString;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ComponentChanges {

    private final Map<String, NbtElement> additions; //thing to asdd
    private final Set<String> removals; //things to remove

    private ComponentChanges(Map<String, NbtElement> additions, Set<String> removals) {
        this.additions = additions;
        this.removals = removals;
    }

    public Set<String> getAdditions() {
        return additions.keySet();
    }

    public Set<String> getRemovals() {
        return removals;
    }

    @Nullable
    public NbtElement get(String key) {
        return additions.get(key);
    }

    public boolean hasAddition(String key) {
        return additions.containsKey(key);
    }

    public boolean hasRemoval(String key) {
        return removals.contains(key);
    }

    public boolean isEmpty() {
        return additions.isEmpty() && removals.isEmpty();
    }

    public Set<Map.Entry<String, NbtElement>> entrySet() {
        return additions.entrySet();
    }

    public void applyTo(ItemStack stack) {
        NbtCompound nbt = stack.getOrCreateNbt();

        for (Map.Entry<String, NbtElement> entry : additions.entrySet()) {
            String key = entry.getKey();
            NbtElement value = entry.getValue();

            if (key.contains(".")) {
                String[] parts = key.split("\\.", 2);
                NbtCompound nested = nbt.getCompound(parts[0]);
                if (nested.isEmpty() && !nbt.contains(parts[0])) {
                    nbt.put(parts[0], nested);
                }
                nested.put(parts[1], value);
            } else {
                nbt.put(key, value);
            }
        }

        for (String key : removals) {
            if (key.contains(".")) {
                String[] parts = key.split("\\.", 2);
                if (nbt.contains(parts[0], 10)) {
                    NbtCompound nested = nbt.getCompound(parts[0]);
                    nested.remove(parts[1]);
                }
            } else {
                nbt.remove(key);
            }
        }
    }

    public static ComponentChanges fromStack(ItemStack stack) {
        NbtCompound nbt = stack.getNbt();
        Map<String, NbtElement> additions = new HashMap<>();

        if (nbt != null) {
            for (String key : nbt.getKeys()) {
                additions.put(key, nbt.get(key).copy());
            }

            if (nbt.contains("display", 10)) {
                NbtCompound display = nbt.getCompound("display");
                for (String key : display.getKeys()) {
                    additions.put("display." + key, display.get(key).copy());
                }
            }
        }

        return new ComponentChanges(additions, new HashSet<>());
    }

    public static ComponentChanges empty() {
        return new ComponentChanges(new HashMap<>(), new HashSet<>());
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final Map<String, NbtElement> additions = new HashMap<>();
        private final Set<String> removals = new HashSet<>();

        // TYPE THINGIES

        // Add with ComponentType
        public Builder add(ComponentType<Integer> type, int value) {
            additions.put(type.getNbtKey(), NbtInt.of(value));
            removals.remove(type.getNbtKey());
            return this;
        }

        public Builder add(ComponentType<String> type, String value) {
            additions.put(type.getNbtKey(), NbtString.of(value));
            removals.remove(type.getNbtKey());
            return this;
        }

        public Builder add(ComponentType<Boolean> type, boolean value) {
            NbtCompound temp = new NbtCompound();
            temp.putBoolean("temp", value);
            additions.put(type.getNbtKey(), temp.get("temp"));
            removals.remove(type.getNbtKey());
            return this;
        }

        // For Text components
        public Builder add(ComponentType<String> type, Text text) {
            additions.put(type.getNbtKey(), NbtString.of(Text.Serializer.toJson(text)));
            removals.remove(type.getNbtKey());
            return this;
        }

        // Generic add for complex types fıor klike NbtElement and NbtCompound
        public <T extends NbtElement> Builder add(ComponentType<T> type, T value) {
            additions.put(type.getNbtKey(), value);
            removals.remove(type.getNbtKey());
            return this;
        }

        // Remove with ComponentType
        public Builder remove(ComponentType<?> type) {
            removals.add(type.getNbtKey());
            additions.remove(type.getNbtKey());
            return this;
        }

        //string based legadcy

        public Builder add(String key, NbtElement value) {
            additions.put(key, value);
            removals.remove(key);
            return this;
        }

        public Builder addInt(String key, int value) {
            additions.put(key, NbtInt.of(value));
            removals.remove(key);
            return this;
        }

        public Builder addString(String key, String value) {
            additions.put(key, NbtString.of(value));
            removals.remove(key);
            return this;
        }

        public Builder addBoolean(String key, boolean value) {
            NbtCompound temp = new NbtCompound();
            temp.putBoolean("temp", value);
            additions.put(key, temp.get("temp"));
            removals.remove(key);
            return this;
        }

        public Builder remove(String key) {
            removals.add(key);
            additions.remove(key);
            return this;
        }

        public ComponentChanges build() {
            return new ComponentChanges(new HashMap<>(additions), new HashSet<>(removals));
        }
    }
// STRING BASED LEGACY
        @Override
        public String toString() {
            return "ComponentChanges{additions=" + additions.keySet() + ", removals=" + removals + "}";
        }
    }
