package timmychips.relignitemodeldefinitions.property.type.codec;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;

public record ModelDefinition(Identifier type, Identifier model) implements ItemModelDefinition {
    public static final MapCodec<ModelDefinition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Identifier.CODEC.fieldOf("type").forGetter(ModelDefinition::type),
            Identifier.CODEC.fieldOf("model").forGetter(ModelDefinition::model)
    ).apply(instance, ModelDefinition::new));

    public static final Identifier TYPE = Identifier.of("minecraft:model");

    @Override
    public MapCodec<? extends ItemModelDefinition> getCodec() {
        return CODEC;
    }

    @Override
    public Identifier expectedType() {
        return TYPE;
    }
}
