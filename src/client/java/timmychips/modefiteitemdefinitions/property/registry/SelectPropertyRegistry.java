package timmychips.modefiteitemdefinitions.property.registry;

import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import timmychips.modefiteitemdefinitions.property.handler.SelectPropertyHandler;
import timmychips.modefiteitemdefinitions.property.resolver.selectcase.*;
import timmychips.modefiteitemdefinitions.property.type.codec.SelectDefinition;

import java.util.HashMap;
import java.util.Map;

public class SelectPropertyRegistry {
    private static final Map<Identifier, SelectPropertyHandler> HANDLERS = new HashMap<>();

    public static void init() {
        register(new Identifier("minecraft:block_state"), new BlockStateCase());
        register(new Identifier("minecraft:charge_type"), new ChargeTypeCase());
        register(new Identifier("minecraft:component"), new ComponentCase());
        register(new Identifier("minecraft:context_dimension"), new ContextDimensionCase());
        register(new Identifier("minecraft:context_entity_type"), new ContextEntityTypeCase());
        register(new Identifier("minecraft:custom_model_data"), new CustomModelDataCase());
        register(new Identifier("minecraft:display_context"), new DisplayContextCase());
        register(new Identifier("minecraft:main_hand"), new MainHandCase());
        register(new Identifier("minecraft:trim_material"), new TrimMaterialCase());
    }

    private static void register(Identifier id, SelectPropertyHandler handler) {
        HANDLERS.put(id, handler);
    }

    public static String resolve(Identifier id, ItemStack stack, LivingEntity entity, ModelTransformationMode mode, SelectDefinition.Definition definition) {
        SelectPropertyHandler handler = HANDLERS.get(id);
        if (handler == null) return null;
        return handler.getValue(stack, entity, mode, definition);
    }
}
