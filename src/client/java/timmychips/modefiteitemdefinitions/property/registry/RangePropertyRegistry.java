package timmychips.modefiteitemdefinitions.property.registry;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import timmychips.modefiteitemdefinitions.property.handler.RangePropertyHandler;
import timmychips.modefiteitemdefinitions.property.resolver.rangeentry.*;
import timmychips.modefiteitemdefinitions.property.type.codec.RangeDispatchDefinition;

import java.util.HashMap;
import java.util.Map;

public class RangePropertyRegistry {
    private static final Map<Identifier, RangePropertyHandler> HANDLERS = new HashMap<>();

    // Register each property
    public static void init() {
        register(new Identifier("minecraft:bundle/fullness"), new BundleFullnessFloat());
        register(new Identifier("minecraft:compass"), new CompassFloat());
        register(new Identifier("minecraft:cooldown"), new CooldownFloat());
        register(new Identifier("minecraft:count"), new CountFloat());
        register(new Identifier("minecraft:crossbow/pull"), new CrossbowPullFloat());
        register(new Identifier("minecraft:damage"), new DamageFloat());
        register(new Identifier("minecraft:time"), new ClockTimeFloat());
        register(new Identifier("minecraft:use_cycle"), new UseCycleFloat());
        register(new Identifier("minecraft:use_duration"), new UseDurationFloat());
        register(new Identifier("minecraft:custom_model_data"), new CustomModelDataFloat());
    }

    private static void register(Identifier id, RangePropertyHandler handler) {
        HANDLERS.put(id, handler);
    }

    public static float resolve(Identifier id, ItemStack stack, LivingEntity entity, RangeDispatchDefinition.Definition def) {
        RangePropertyHandler handler = HANDLERS.get(id);
        if (handler == null) return 0f;
        return handler.getValue(stack, entity, def);
    }
}
