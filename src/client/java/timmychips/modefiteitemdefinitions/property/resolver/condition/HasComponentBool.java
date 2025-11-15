package timmychips.modefiteitemdefinitions.property.resolver.condition;

import com.mojang.logging.LogUtils;
//import net.minecraft.component.ComponentChanges;
//import net.minecraft.component.ComponentType;
import net.minecraft.nbt.NbtCompound;
import timmychips.modefiteitemdefinitions.comp.ComponentType;

import timmychips.modefiteitemdefinitions.comp.ComponentChanges;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import timmychips.modefiteitemdefinitions.property.handler.ConditionPropertyHandler;
import timmychips.modefiteitemdefinitions.property.resolver.ResolveRecursive;
import timmychips.modefiteitemdefinitions.property.type.codec.ConditionDefinition;

import java.util.Set;

// Returns if item has specified component (and/or if it should ignore the default component value)
public class HasComponentBool implements ConditionPropertyHandler {

    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Set<String> WARNED_MODELS = ResolveRecursive.WARNED_MODELS;
    private static boolean hasComponent(ItemStack stack, ComponentType<?> componentType) {
        NbtCompound nbt = stack.getNbt();
        return nbt != null && componentType.isIn(nbt);
    }

    @Override
    public boolean getValue(ItemStack stack, LivingEntity entity, ConditionDefinition definition) {
        String component = definition.component(); // Specified component string from items definition
        boolean ignore_default = definition.ignore_default(); // If it should ignore the default item's component value

        if (component == null) return false;

        // Parse string to Identifier id
        Identifier componentId = Identifier.tryParse(component);
        if (componentId == null) {
            String key = stack.getItem().toString() + "|" + "minecraft:has_component";
            if (WARNED_MODELS.add(key)) LOGGER.warn("Invalid component predicate ID '{}'", component);
            return false;
        }

        // Get component type from Identifier
        ComponentType<?> componentType = ComponentType.get(componentId);
        if (componentType == null) {
            String key = stack.getItem().toString() + "|" + "minecraft:has_component";
            if (WARNED_MODELS.add(key)) LOGGER.warn("Unknown component predicate componentType '{}'", componentId);
            return false;
        }

        if (hasComponent(stack, componentType)) { // stack has component

            if (!ignore_default) return true;               // if ignore_default is false
            else return hasChanged(stack, componentType);   // if it's true
        }

        return false;
    }

    // Boolean if item component has had component changes
    private static Boolean hasChanged(ItemStack stack, ComponentType<?> componentType) {
        ComponentChanges changes = ComponentChanges.fromStack(stack);
        String nbtKey = componentType.getNbtKey();
        return changes.getAdditions().contains(nbtKey);
    }
}
//aaa