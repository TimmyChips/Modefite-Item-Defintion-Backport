package timmychips.modefiteitemdefinitions.comp;

import net.minecraft.nbt.NbtCompound;

import java.util.HashMap;
import java.util.Map;
public class BlockStateComponent {
    private final Map<String, String> properties;
//consturcutor
    public BlockStateComponent(Map<String, String>properties) {
        this.properties = new HashMap<>(properties);
    }
//getter metjod
    public Map<String, String> properties() {
        return properties;
    }
    //static
    public static Builder builder() {
        return new Builder();

    }
//builder class
    public static class Builder {
        private final Map<String, String> properties = new HashMap<>();

        public Builder put(String key, String value) {
            properties.put(key, value);
            return this;
        }
        public BlockStateComponent build() {
            return new BlockStateComponent(properties);
        }
    }
//ntb parser
    public static BlockStateComponent fromNbt(NbtCompound nbt) {
        Map<String, String> properties = new HashMap<>();


        for (String key : nbt.getKeys()) {
            String value = nbt.getString(key);
            properties.put(key, value);
        }

        return new BlockStateComponent(properties);
    }
//optional covert back to nbt
    public NbtCompound toNbt() {
        NbtCompound nbt = new NbtCompound();
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            nbt.putString(entry.getKey(), entry.getValue());
        }
        return nbt;
    }

    //extras for equals, hashCode and tostring methods

    //equals method checks if two Blockstates objects are equal
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof BlockStateComponent)) return false;
        BlockStateComponent other = (BlockStateComponent) obj;
        return properties.equals(other.properties);
    }
//return hash based on property
    @Override
    public int hashCode() {
        return properties.hashCode();

    }

    @Override
    public String toString() {
        return "BlockStateComponent{properties=" + properties + "}";
    }
}
