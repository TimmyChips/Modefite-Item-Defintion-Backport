package timmychips.modefiteitemdefinitions.property.handler;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import timmychips.modefiteitemdefinitions.property.type.codec.RangeDispatchDefinition;

@FunctionalInterface
public interface RangePropertyHandler {
    float getValue(ItemStack stack, LivingEntity entity, RangeDispatchDefinition.Definition definition);
}
