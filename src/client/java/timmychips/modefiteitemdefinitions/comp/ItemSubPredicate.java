package timmychips.modefiteitemdefinitions.comp;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
//READ BELOW
//NAMED EVERYTHING TO testX for TESTING PURPOSES I HAVE NO IDEA IF THIS WHOLE BACKPORT SCRIPT WORK
// READ ABOVE

public class ItemSubPredicate {

    // Main Entry Prediactae
    public static boolean testPredicate(ItemStack stack, Identifier PId, JsonElement value) {
        if (stack == null || PId == null || value == null) {

            return false;
        }

        String PPath = PId.getPath();

        // case labels
        //changed PPath to PredicatePath and PId to predicateId
        //changed PredicatePath back to PPath since it's easiere to type and the it's back to PId
        return switch (PPath) {
            case "damage" -> testDamage(stack, value);
            case "custom_model_data" -> testCustomModelData(stack, value);
            case "enchantments" -> testEnchantments(stack, value);
            case "stored_enchantments" -> testStoredEnchantments(stack, value);
            case "trim" -> testTrim(stack, value);
            case "potion_contents" -> testPotion(stack, value);
            case "custom_data" -> testCustomData(stack, value);
            case "unbreakable" -> testUnbreakable(stack, value);
            case "repair_cost" -> testRepairCost(stack, value);
            case "attribute_modifiers" -> testAttributeModifiers(stack, value);
            case "can_break" -> testCanBreak(stack, value);
            case "can_place_on" -> testCanPlaceOn(stack, value);
            //NEW WIP
            case "special" -> testSpecial(stack, value);
            case "bundle/selected_item" -> testBundleSelectedItem(stack, value);
            case "model_tint_source" -> testModelTintSource(stack, value);
            //NEW WIP
            default -> {

                yield testGenericNbt(stack, PPath, value);
            }
        };
    }

    private static boolean testDamage(ItemStack stack, JsonElement value) {
        NbtCompound nbt = stack.getNbt();
        if (nbt == null) return false;

        int actualDamage = nbt.getInt(DataComponentTypes.DAMAGE);

        // compare
        if (value.isJsonPrimitive() && value.getAsJsonPrimitive().isNumber()) {
            return actualDamage == value.getAsInt();
        }

        // min/max
        if (value.isJsonObject()) {
            JsonObject obj = value.getAsJsonObject();
            if (obj.has("min") && actualDamage < obj.get("min").getAsInt()) return false;
            if (obj.has("max") && actualDamage > obj.get("max").getAsInt()) return false;
            return true;
        }

        return false;
    }

    private static boolean testCustomModelData(ItemStack stack, JsonElement value) {
        NbtCompound nbt = stack.getNbt();
        if (nbt == null || !nbt.contains(DataComponentTypes.CUSTOM_MODEL_DATA)) return false;

        if (value.isJsonPrimitive() && value.getAsJsonPrimitive().isNumber()) {
            return nbt.getInt(DataComponentTypes.CUSTOM_MODEL_DATA) == value.getAsInt();
        }

        return false;
    }

    private static boolean testEnchantments(ItemStack stack, JsonElement value) {
        NbtCompound nbt = stack.getNbt();
        if (nbt == null) return false;

        boolean hasEnchantments = nbt.contains(DataComponentTypes.ENCHANTMENTS);

        if (value.isJsonPrimitive() && value.getAsJsonPrimitive().isBoolean()) {
            return hasEnchantments == value.getAsBoolean();
        }

        return hasEnchantments;
    }

    private static boolean testStoredEnchantments(ItemStack stack, JsonElement value) {
        NbtCompound nbt = stack.getNbt();
        if (nbt == null) return false;

        boolean hasStoredEnchantments = nbt.contains(DataComponentTypes.STORED_ENCHANTMENTS);

        if (value.isJsonPrimitive() && value.getAsJsonPrimitive().isBoolean()) {
            return hasStoredEnchantments == value.getAsBoolean();
        }

        return hasStoredEnchantments;
    }

    private static boolean testTrim(ItemStack stack, JsonElement value) {
        NbtCompound nbt = stack.getNbt();
        if (nbt == null) return false;

        boolean hasTrim = nbt.contains(DataComponentTypes.TRIM);

        //  true/false check
        if (value.isJsonPrimitive() && value.getAsJsonPrimitive().isBoolean()) {
            return hasTrim == value.getAsBoolean();
        }

        //  trim comparison
        if (hasTrim && value.isJsonObject()) {
            NbtCompound trim = nbt.getCompound(DataComponentTypes.TRIM);
            JsonObject obj = value.getAsJsonObject();

            if (obj.has("material")) {
                if (!trim.getString("material").equals(obj.get("material").getAsString())) return false;
            }

            if (obj.has("pattern")) {
                if (!trim.getString("pattern").equals(obj.get("pattern").getAsString())) return false;
            }

            return true;
        }

        return hasTrim;
    }

    private static boolean testPotion(ItemStack stack, JsonElement value) {
        NbtCompound nbt = stack.getNbt();
        if (nbt == null) return false;


        if (value.isJsonPrimitive() && value.getAsJsonPrimitive().isString()) {
            return nbt.getString(DataComponentTypes.POTION_CONTENTS)
                    .equals(value.getAsString());
        }

        return false;
    }

    private static boolean testCustomData(ItemStack stack, JsonElement value) {
        NbtCompound nbt = stack.getNbt();
        if (nbt == null) return false;

        //general check
        if (value.isJsonPrimitive() && value.getAsJsonPrimitive().isBoolean()) {
            return (nbt.getSize() > 0) == value.getAsBoolean();
        }

        // default to true ----:/
        return true;
    }

    private static boolean testUnbreakable(ItemStack stack, JsonElement value) {
        NbtCompound nbt = stack.getNbt();
        if (nbt == null) return false;

        boolean isUnbreakable =
                nbt.contains(DataComponentTypes.UNBREAKABLE)
                        && nbt.getBoolean(DataComponentTypes.UNBREAKABLE);

        if (value.isJsonPrimitive() && value.getAsJsonPrimitive().isBoolean()) {
            return isUnbreakable == value.getAsBoolean();
        }

        return isUnbreakable;
    }

    private static boolean testRepairCost(ItemStack stack, JsonElement value) {
        NbtCompound nbt = stack.getNbt();
        if (nbt == null || !nbt.contains(DataComponentTypes.REPAIR_COST)) return false;

        int actual = nbt.getInt(DataComponentTypes.REPAIR_COST);

        if (value.isJsonPrimitive() && value.getAsJsonPrimitive().isNumber()) {
            return actual == value.getAsInt();
        }

        if (value.isJsonObject()) {
            JsonObject obj = value.getAsJsonObject();
            if (obj.has("min") && actual < obj.get("min").getAsInt()) return false;
            if (obj.has("max") && actual > obj.get("max").getAsInt()) return false;
            return true;
        }

        return false;
    }

    private static boolean testAttributeModifiers(ItemStack stack, JsonElement value) {
        NbtCompound nbt = stack.getNbt();
        if (nbt == null) return false;

        boolean hasAttributeModifiers = nbt.contains(DataComponentTypes.ATTRIBUTE_MODIFIERS);

        if (value.isJsonPrimitive() && value.getAsJsonPrimitive().isBoolean()) {
            return hasAttributeModifiers == value.getAsBoolean();
        }

        return hasAttributeModifiers;
    }

    private static boolean testCanBreak(ItemStack stack, JsonElement value) {
        NbtCompound nbt = stack.getNbt();
        if (nbt == null) return false;

        boolean hasCanBreak = nbt.contains(DataComponentTypes.CAN_DESTROY);

        if (value.isJsonPrimitive() && value.getAsJsonPrimitive().isBoolean()) {
            return hasCanBreak == value.getAsBoolean();
        }

        return hasCanBreak;
    }

    private static boolean testCanPlaceOn(ItemStack stack, JsonElement value) {
        NbtCompound nbt = stack.getNbt();
        if (nbt == null) return false;

        boolean hasCanPlaceOn = nbt.contains(DataComponentTypes.CAN_PLACE_ON);

        if (value.isJsonPrimitive() && value.getAsJsonPrimitive().isBoolean()) {
            return hasCanPlaceOn == value.getAsBoolean();
        }

        return hasCanPlaceOn;
    }
//WIP
    //for things like

    //{
    //  "predicate": { "special": 1 },
    //  "model": "item/custom_special_item"
    //}
    private static boolean testSpecial(ItemStack stack, JsonElement value) {
        NbtCompound nbt = stack.getNbt();
        if (nbt == null) return false;
        boolean hasSpecial = nbt.contains("special");
        if (value.isJsonPrimitive() && value.getAsJsonPrimitive().isBoolean()) {
            return hasSpecial == value.getAsBoolean();
        }
        return hasSpecial;
    }

    //looks for bundle
    //tracks what's inside the bundle that is sleteced
    //this will allow for you to see what's active and selecrted
    //{
    //  "predicate": { "bundle/selected_item": 0 },
    //  "model": "item/bundle_empty"
    //}
    private static boolean testBundleSelectedItem(ItemStack stack, JsonElement value) {
        NbtCompound nbt = stack.getNbt();
        if (nbt == null) return false;
        if (!stack.getItem().getName().getString().contains("bundle")) return false;
        int selectedIndex = nbt.contains("selected_index") ? nbt.getInt("selected_index") : -1;
        if (value.isJsonPrimitive() && value.getAsJsonPrimitive().isNumber()) {
            return selectedIndex == value.getAsInt();
        }
        return selectedIndex != -1;
    }

    //model tint source looks for model_tint_source
    //this detemrnies how an item is tinted
    // used on item and block models
    //example
    //{
    //  "predicate": { "model_tint_source": "layer0" },
    //  "model": "item/colored_item_layer0"
    //}
    private static boolean testModelTintSource(ItemStack stack, JsonElement value) {
        NbtCompound nbt = stack.getNbt();
        if (nbt == null) return false;

        if (!nbt.contains("model_tint_source")) return false;

        String tint = nbt.getString("model_tint_source");

        if (value.isJsonPrimitive() && value.getAsJsonPrimitive().isString()) {
            return tint.equals(value.getAsString());
        }

        return true;
    }

  //WIP

    // fallback logic for any generic Nbtr
    private static boolean testGenericNbt(ItemStack stack, String key, JsonElement value) {
        NbtCompound nbt = stack.getNbt();
        if (nbt == null) return false;

        boolean hasKey = nbt.contains(key);

        if (value.isJsonPrimitive() && value.getAsJsonPrimitive().isBoolean()) {
            return hasKey == value.getAsBoolean();
        }

        if (hasKey) {
            if (value.isJsonPrimitive()) {
                var prim = value.getAsJsonPrimitive();

                if (prim.isNumber()) return nbt.getInt(key) == prim.getAsInt();
                if (prim.isString()) return nbt.getString(key).equals(prim.getAsString());
            }
        }

        return false;
    }


    //WIP  special`, `bundle/selected_item`, and the `model tint source might not work proceed with caution


}
