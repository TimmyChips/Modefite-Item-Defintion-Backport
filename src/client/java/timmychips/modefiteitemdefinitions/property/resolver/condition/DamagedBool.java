package timmychips.modefiteitemdefinitions.property.resolver.condition;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import timmychips.modefiteitemdefinitions.property.handler.ConditionPropertyHandler;
import timmychips.modefiteitemdefinitions.property.type.codec.ConditionDefinition;

// Returns if item is damaged
public class DamagedBool implements ConditionPropertyHandler {
    @Override
    public boolean getValue(ItemStack stack, LivingEntity entity, ConditionDefinition definition) {
        return stack.isDamaged();
    }
}
