package timmychips.modefiteitemdefinitions.comp;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtInt;
import net.minecraft.nbt.NbtString;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;


public class ComponentType<T> {

    private final Identifier id; // exaamoke for this woıuld be minecraft:custom_model_data
    private final String nbtKey; //example for this would be is CustomModelDAta
    private final int nbtType; // what ype it is 3= int 8= string or 10=compınd


    private static final Map<Identifier, ComponentType<?>> REGISTRY = new HashMap<>();

    private ComponentType(Identifier id, String nbtKey, int nbtType) {

        this.id = id;
        this.nbtKey = nbtKey;
        this.nbtType = nbtType;
    }
//everything above is fort Registieries.DATA_COMPONENT_TYPE.get()
    //exampel of 1.21.1 ComponentType<?> type = Registries.DATA_COMPONENT_TYPE.get(id);
    // now üexample fo 1.20.1 ComponentType<?> type = ComponentType.get(id);



    public Identifier getId() {
        return this.id;
    }

    public String getNbtKey() {
        return this.nbtKey;
    }

    public int getNbtType() {
        return this.nbtType;
    }

//WIP
    @Nullable
    public T get(ItemStack stack) {
        if (stack == null) return null;
        NbtCompound nbt = stack.getNbt();
        if (nbt == null) return null;

        NbtElement element = get(nbt);
        if (element == null) return null;

        switch (nbtType) {
            case 3 -> { // int
                if (element instanceof NbtInt nbtInt) return (T) (Integer) nbtInt.intValue();
            }
            case 8 -> { // string
                if (element instanceof NbtString nbtString) return (T) nbtString.asString();
            }
            case 10 -> { // compound
                if (element instanceof NbtCompound nbtCompound) return (T) nbtCompound;
            }
            case 1 -> { // byte / boolean
                if (element instanceof NbtInt nbtInt) return (T) (Boolean) (nbtInt.intValue() != 0);
            }
        }

        return null;
    }
//WIP

    public boolean isIn(NbtCompound nbt) {
        if (nbtKey.contains(".")) {

            String[] parts = nbtKey.split("\\.", 2);
            if (!nbt.contains(parts[0], 10)) return false;
            NbtCompound nested = nbt.getCompound(parts[0]);
            return nested.contains(parts[1]);
        }
        return nbt.contains(nbtKey, nbtType);
    }
    @Nullable
    public NbtElement get(NbtCompound nbt) {
        if (nbtKey.contains(".")) {
            String[] parts = nbtKey.split("\\.", 2);
            if (!nbt.contains(parts[0], 10)) return null;
            NbtCompound nested = nbt.getCompound(parts[0]);
            return nested.get(parts[1]);
        }
        return nbt.get(nbtKey);
    }
    private static <T> ComponentType<T> register(String id, String nbtKey, int nbtType) {
        Identifier identifier = new Identifier(id);
        ComponentType<T> type = new ComponentType<>(identifier, nbtKey, nbtType);
        REGISTRY.put(identifier, type);
        return type;
    }

    @Nullable
    public static ComponentType<?> get(Identifier id) {
        return REGISTRY.get(id);
    }
//all taken from https://minecraft.wiki/w/Data_component_format
    public static final ComponentType<Integer> CUSTOM_MODEL_DATA = register("minecraft:custom_model_data", DataComponentTypes.CUSTOM_MODEL_DATA, 3); //as an integer //example if (ComponentType.CUSTOM_MODEL_DATA.isIn(nbt)) {
    public static final ComponentType<String> CUSTOM_NAME = register("minecraft:custom_name", DataComponentTypes.CUSTOM_NAME, 8);
    public static final ComponentType<NbtElement> LORE = register("minecraft:lore", DataComponentTypes.LORE, 9);
    public static final ComponentType<Integer> COLOR = register("minecraft:dyed_color", DataComponentTypes.COLOR, 3);

    public static final ComponentType<Integer> DAMAGE = register("minecraft:damage", DataComponentTypes.DAMAGE, 3);
    public static final ComponentType<Integer> MAX_DAMAGE = register("minecraft:max_damage", DataComponentTypes.MAX_DAMAGE, 3);
    public static final ComponentType<Boolean> UNBREAKABLE = register("minecraft:unbreakable", DataComponentTypes.UNBREAKABLE, 1);
    public static final ComponentType<NbtCompound> BLOCK_STATE = register("minecraft:block_state", DataComponentTypes.BLOCK_STATE, 10);
    public static final ComponentType<NbtElement> ENCHANTMENTS = register("minecraft:enchantments", DataComponentTypes.ENCHANTMENTS, 9);
    public static final ComponentType<NbtElement> STORED_ENCHANTMENTS = register("minecraft:stored_enchantments", DataComponentTypes.STORED_ENCHANTMENTS, 9);

    public static final ComponentType<NbtCompound> TRIM = register("minecraft:trim", DataComponentTypes.TRIM, 10);

   public static final ComponentType<NbtCompound> LODESTONE_TRACKER = register("minecraft:lodestone_tracker", DataComponentTypes.LODESTONE_TRACKER, 10);
    public static final ComponentType<NbtCompound> BUCKET_ENTITY_DATA = register("minecraft:bucket_entity_data", DataComponentTypes.BUCKET_ENTITY_DATA, 10);
    public static final ComponentType<String> POTION_CONTENTS = register("minecraft:potion_contents", DataComponentTypes.POTION_CONTENTS, 8);
    public static final ComponentType<Integer> CUSTOM_POTION_COLOR = register("minecraft:custom_potion_color", DataComponentTypes.CUSTOM_POTION_COLOR, 3);
    public static final ComponentType<NbtElement> WRITTEN_BOOK_CONTENT = register("minecraft:written_book_content", DataComponentTypes.WRITTEN_BOOK_CONTENT, 9);
    public static final ComponentType<NbtCompound> FIREWORKS = register("minecraft:fireworks", DataComponentTypes.FIREWORKS, 10);
    public static final ComponentType<Integer> MAP_ID = register("minecraft:map_id", DataComponentTypes.MAP_ID, 3);
    public static final ComponentType<NbtCompound> PROFILE = register("minecraft:profile", DataComponentTypes.PROFILE, 10);
    public static final ComponentType<Integer> HIDE_FLAGS = register("minecraft:hide_additional_tooltip", DataComponentTypes.HIDE_FLAGS, 3);
    public static final ComponentType<Integer> REPAIR_COST = register("minecraft:repair_cost", DataComponentTypes.REPAIR_COST, 3);
    public static final ComponentType<NbtElement> ATTRIBUTE_MODIFIERS = register("minecraft:attribute_modifiers", DataComponentTypes.ATTRIBUTE_MODIFIERS, 9);
    public static final ComponentType<NbtElement> CAN_DESTROY = register("minecraft:can_break", DataComponentTypes.CAN_DESTROY, 9);
    public static final ComponentType<NbtElement> CAN_PLACE_ON = register("minecraft:can_place_on", DataComponentTypes.CAN_PLACE_ON, 9);
    public static final ComponentType<NbtCompound> BLOCK_ENTITY_DATA = register("minecraft:block_entity_data", DataComponentTypes.BLOCK_ENTITY_DATA, 10);
    public static final ComponentType<NbtCompound> ENTITY_DATA = register("minecraft:entity_data", DataComponentTypes.ENTITY_DATA, 10);
    public static final ComponentType<NbtElement> CHARGED_PROJECTILES = register("minecraft:charged_projectiles", DataComponentTypes.CHARGED_PROJECTILES, 9);
    public static final ComponentType<NbtElement> BUNDLE_CONTENTS = register("minecraft:bundle_contents", DataComponentTypes.BUNDLE_CONTENTS, 9);
    @Override
    public String toString() {
        return "ComponentType{" + id + " -> " + nbtKey + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ComponentType<?> other)) return false;
        return this.id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

}
