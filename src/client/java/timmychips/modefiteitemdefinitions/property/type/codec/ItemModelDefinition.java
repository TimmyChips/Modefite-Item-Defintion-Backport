package timmychips.modefiteitemdefinitions.property.type.codec;

import com.mojang.serialization.MapCodec;
import net.minecraft.util.Identifier;

// Entry point to item model definition types
public sealed interface ItemModelDefinition
        permits CompositeModelDefinition, ConditionDefinition, EmptyModelDefinition, ModelDefinition, RangeDispatchDefinition.Definition, SelectDefinition.Definition {

    /**
     * Every subtype must return its own codec.
     */
    MapCodec<? extends ItemModelDefinition> getCodec();

    /**
     *
     * @return The actual type declared in the JSON type field
     */
    Identifier type();

    /**
     *
     * @return The type that is expected from the definition object (minecraft:composite, minecraft:condition, etc.)
     */
    Identifier expectedType();
}
