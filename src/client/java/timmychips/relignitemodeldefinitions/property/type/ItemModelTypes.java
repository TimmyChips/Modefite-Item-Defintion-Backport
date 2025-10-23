package timmychips.relignitemodeldefinitions.property.type;

import com.mojang.serialization.Codec;
import net.minecraft.util.Identifier;
import timmychips.relignitemodeldefinitions.property.helper.PommelIdMapper;
import timmychips.relignitemodeldefinitions.property.type.codec.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ItemModelTypes {
    public static final PommelIdMapper ID_MAPPER = new PommelIdMapper();
    public static final Codec<ItemModelDefinition> CODEC = Codec.lazyInitialized(() -> ID_MAPPER.getCodec(Identifier.CODEC));

    // Identifiers for item model types
    private static final Identifier MODEL =     Identifier.of("minecraft:model");
    private static final Identifier CONDITION = Identifier.of("minecraft:condition");
    private static final Identifier SELECT =    Identifier.of("minecraft:select");
    private static final Identifier RANGE =     Identifier.of("minecraft:range_dispatch");
    private static final Identifier COMPOSITE = Identifier.of("minecraft:composite");
    private static final Identifier EMPTY =     Identifier.of("minecraft:empty");

    static {
        // Place all items model types into mapper
        ID_MAPPER.put(MODEL,     ModelDefinition.CODEC);
        ID_MAPPER.put(CONDITION, ConditionDefinition.codec(CODEC));
        ID_MAPPER.put(SELECT,    SelectDefinition.Definition.codec(CODEC));
        ID_MAPPER.put(RANGE,     RangeDispatchDefinition.Definition.codec(CODEC));
        ID_MAPPER.put(COMPOSITE, CompositeModelDefinition.CODEC);
        ID_MAPPER.put(EMPTY,     EmptyModelDefinition.CODEC);
    }
}
