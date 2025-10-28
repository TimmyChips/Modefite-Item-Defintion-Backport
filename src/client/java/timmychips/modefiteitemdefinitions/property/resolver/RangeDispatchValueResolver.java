package timmychips.modefiteitemdefinitions.property.resolver;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import timmychips.modefiteitemdefinitions.property.registry.RangePropertyRegistry;
import timmychips.modefiteitemdefinitions.property.type.codec.RangeDispatchDefinition;

public class RangeDispatchValueResolver {
    public static float evaluate(
            Identifier property, Float scale,
            ItemStack stack, LivingEntity entity,
            RangeDispatchDefinition.Definition def) {

        return RangePropertyRegistry.resolve(property, stack, entity, def) * scale;
    }
}
