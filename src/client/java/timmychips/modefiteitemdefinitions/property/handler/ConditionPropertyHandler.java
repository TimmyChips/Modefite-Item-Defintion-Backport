package timmychips.modefiteitemdefinitions.property.handler;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import timmychips.modefiteitemdefinitions.property.type.codec.ConditionDefinition;

@FunctionalInterface
public interface ConditionPropertyHandler {
    boolean getValue(ItemStack stack, LivingEntity entity, ConditionDefinition definition);
}
