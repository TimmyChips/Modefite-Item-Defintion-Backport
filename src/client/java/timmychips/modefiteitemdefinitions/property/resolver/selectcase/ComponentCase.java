package timmychips.modefiteitemdefinitions.property.resolver.selectcase;

import com.mojang.logging.LogUtils;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.component.ComponentType;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import timmychips.modefiteitemdefinitions.property.handler.SelectPropertyHandler;
import timmychips.modefiteitemdefinitions.property.helper.EntityVariantHelper;
import timmychips.modefiteitemdefinitions.property.resolver.ResolveRecursive;
import timmychips.modefiteitemdefinitions.property.type.codec.SelectDefinition;
import java.util.*;
import static timmychips.modefiteitemdefinitions.property.helper.EntityVariantHelper.castEntityVariantComponents;

/**
 * Returns a string for specified component's value
 * <p>{@code component:} ID of the component type
 */
public class ComponentCase implements SelectPropertyHandler {

    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Set<String> WARNED_MODELS = ResolveRecursive.WARNED_MODELS;

    @Override
    public String getValue(ItemStack stack, LivingEntity entity, ModelTransformationMode mode, SelectDefinition.Definition definition) {
        String component = definition.component(); // Retrieve specified component to check for
        if (component == null) return null;

        Identifier componentId = Identifier.tryParse(component); // Parse string to id
        if (componentId == null) {
            String key = stack.getItem().toString() + "|" + "minecraft:component";
            if (WARNED_MODELS.add(key)) LOGGER.warn("Invalid component predicate ID '{}'", component);
            return null;
        }

        ComponentType<?> componentType = Registries.DATA_COMPONENT_TYPE.get(componentId); // Retrieve component type from id
        if (componentType == null) {

            // For entity variants; ignore warned models
            if (EntityVariantHelper.isEntityVariant(Identifier.tryParse(component))) {
                // Test if it can get entity variant from item stack
                String entityVariant = castEntityVariantComponents(stack, component);
                if (entityVariant != null) return castEntityVariantComponents(stack, component);
            }
            else {
                String key = stack.getItem().toString() + "|" + "minecraft:component";
                if (WARNED_MODELS.add(key)) LOGGER.warn("Unknown component predicate componentType: '{}'", componentId);
                return null;
            }
        }

        String str;

        Object componentValue = stack.get(componentType);
        if (componentValue instanceof Text textValue) {
            str = textValue.getString(); // Get the string without the surrounding literal from Text component types, and with string as is
            return str; // Return just the string for text
        }
        else str = String.valueOf(componentValue).toLowerCase(); // Convert non-text values to lower case string

        return String.valueOf(Identifier.tryParse(str)); // Return component non-text value as string in identifier format
    }

    // Reads a component's enchantment map, for matching against a select case's map "when"
    public static Optional<Map<String, Integer>> getComponentEntries(ItemStack stack, String component) {
        Identifier componentId = Identifier.tryParse(component);
        if (componentId == null) return Optional.empty();

        ComponentType<?> componentType = Registries.DATA_COMPONENT_TYPE.get(componentId);
        if (componentType == null) return Optional.empty();

        Object value = stack.get(componentType);
        if (!(value instanceof ItemEnchantmentsComponent enchantments)) return Optional.empty();

        Map<String, Integer> entries = new HashMap<>();
        for (RegistryEntry<net.minecraft.enchantment.Enchantment> entry : enchantments.getEnchantments()) {
            Identifier id = entry.getKey().map(RegistryKey::getValue).orElse(null);
            if (id != null) entries.put(id.toString(), enchantments.getLevel(entry));
        }
        return Optional.of(entries);
    }

    public static boolean matchesAll(Map<String, Integer> want, Map<String, Integer> actual) {
        for (Map.Entry<String, Integer> entry : want.entrySet()) {
            if (!entry.getValue().equals(actual.get(entry.getKey()))) return false;
        }
        return true;
    }
}
