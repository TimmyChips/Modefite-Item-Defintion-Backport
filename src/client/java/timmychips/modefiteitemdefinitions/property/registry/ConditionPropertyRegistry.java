package timmychips.modefiteitemdefinitions.property.registry;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import timmychips.modefiteitemdefinitions.ClientInitializer;
import timmychips.modefiteitemdefinitions.property.handler.ConditionPropertyHandler;
import timmychips.modefiteitemdefinitions.property.resolver.condition.*;
import timmychips.modefiteitemdefinitions.property.resolver.condition.custom.HoveredItemBool;
import timmychips.modefiteitemdefinitions.property.resolver.condition.custom.SubmergedBool;
import timmychips.modefiteitemdefinitions.property.type.codec.ConditionDefinition;

import java.util.HashMap;
import java.util.Map;

public class ConditionPropertyRegistry {
    private static final Map<Identifier, ConditionPropertyHandler> HANDLERS = new HashMap<>();
    private static final String MOD_ID = ClientInitializer.MOD_ID;

    public static void init() {
        register(new Identifier("minecraft:broken"), new BrokenBool());
        register(new Identifier("minecraft:carried"), new CarriedBool());
        register(new Identifier("minecraft:component"), new ComponentBool());
        register(new Identifier("minecraft:custom_model_data"), new CustomModelDataBool());
        register(new Identifier("minecraft:damaged"), new DamagedBool());
        register(new Identifier("minecraft:extended_view"), new ExtendedViewBool());
        register(new Identifier("minecraft:fishing_rod/cast"), new FishingRodCastBool());
        register(new Identifier("minecraft:has_component"), new HasComponentBool());
        register(new Identifier("minecraft:keybind_down"), new KeybindDownBool());
        register(new Identifier("minecraft:selected"), new SelectedBool());
        register(new Identifier("minecraft:using_item"), new UsingItemBool());
        register( new Identifier("minecraft:view_entity"), new ViewEntityBool());

        // Custom, modded Properties
        register(new Identifier(MOD_ID,"hovered_item"), new HoveredItemBool());
        register(new Identifier(MOD_ID,"submerged"), new SubmergedBool());
    }

    private static void register(Identifier id, ConditionPropertyHandler handler) {
        HANDLERS.put(id, handler);
    }

    public static boolean resolve(Identifier id, ItemStack stack, LivingEntity entity, ConditionDefinition def) {
        ConditionPropertyHandler handler = HANDLERS.get(id);
        if (handler == null) return false;
        return handler.getValue(stack, entity, def);
    }
}
