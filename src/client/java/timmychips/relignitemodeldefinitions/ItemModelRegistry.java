// In a new file: ItemModelRegistry.java
package timmychips.relignitemodeldefinitions;

import net.minecraft.util.Identifier;
import timmychips.relignitemodeldefinitions.property.type.codec.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ItemModelRegistry {
    private static final Map<Identifier, ItemModelDefinition> definitions = new HashMap<>();
    private static final Map<Identifier, ItemModelRootDefinition> rootDefinitions = new HashMap<>();

    public static Set<Identifier> INVALID_MODEL_TYPES = new HashSet<>();

    // Identifiers for item model types
    private static final Identifier MODEL = Identifier.of("minecraft:model");
    private static final Identifier CONDITION = Identifier.of("minecraft:condition");
    private static final Identifier SELECT = Identifier.of("minecraft:select");
    private static final Identifier RANGE = Identifier.of("minecraft:range_dispatch");
    private static final Identifier COMPOSITE = Identifier.of("minecraft:composite");
    private static final Identifier EMPTY = Identifier.of("minecraft:empty");

    public static void putRoot(Identifier id, ItemModelRootDefinition root) {
        if (root.model() != null && validateType(id, root.model())) {
            definitions.put(id, root.model()); // Put the id of the item and the ItemModelDefinition into Map
            rootDefinitions.put(id, root);
        }
    }

    // For retrieving the item's root json fields (get_animation_swap, etc.)
    public static ItemModelRootDefinition getRoot(Identifier id) {
        return rootDefinitions.get(id);
    }

    /**
     *
     * @param id Item identifier
     * @param definition The item model definition object associated with the item to verify
     * @return True if the definition's actual type matches what it's expecting
     */
    public static boolean validateType(Identifier id, ItemModelDefinition definition) {
        if (!definition.type().equals(definition.expectedType())) {
            INVALID_MODEL_TYPES.add(id);
            ClientInitializer.LOGGER.error("Couldn't parse item '{}': Unknown item model type id: {}", id, definition.type());
            return false;
        }
        return true;
    }

    public static ItemModelDefinition get(Identifier id) {
        return definitions.get(id);
    }

    public static boolean hasDefinition(Identifier id) {
        return definitions.containsKey(id);
    }

    public static void clear() {
        INVALID_MODEL_TYPES.clear();
        definitions.clear();
    }

    public static Set<Identifier> getAllModelDependencies() {
        Set<Identifier> dependencies = new HashSet<>();
        for (ItemModelDefinition definition : definitions.values()) {
            collectModelsFromDefinition(definition, dependencies);
        }
        return dependencies;
    }

    private static void collectModelsFromDefinition(ItemModelDefinition def, Set<Identifier> out) {
        if (def instanceof ModelDefinition model) {
            out.add(model.model());

        } else if (def instanceof SelectDefinition.Definition select) {
            for (SelectDefinition.Case<String> c : select.cases()) {
                collectModelsFromDefinition(c.model(), out);
            }

            collectModelsFromDefinition(select.fallback(), out);
        } else if (def instanceof ConditionDefinition condition) {
            collectModelsFromDefinition(condition.on_true(), out);
            collectModelsFromDefinition(condition.on_false(), out);

        } else if (def instanceof RangeDispatchDefinition.Definition range) {
            for (RangeDispatchDefinition.ThresholdEntry entry : range.entries()) {
                collectModelsFromDefinition(entry.model(), out);
            }
            collectModelsFromDefinition(range.fallback(), out);
        }
        // Extend here for other custom model types if needed
    }
}

