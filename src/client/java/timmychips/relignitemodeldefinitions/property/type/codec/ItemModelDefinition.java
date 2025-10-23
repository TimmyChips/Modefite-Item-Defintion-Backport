package timmychips.relignitemodeldefinitions.property.type.codec;

import com.mojang.serialization.MapCodec;

// Entry point to item model definition types
public sealed interface ItemModelDefinition
        permits CompositeModelDefinition, ConditionDefinition, EmptyModelDefinition, ModelDefinition, RangeDispatchDefinition.Definition, SelectDefinition.Definition {

    /**
     * Every subtype must return its own codec.
     */
    MapCodec<? extends ItemModelDefinition> getCodec();
}
