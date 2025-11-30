package timmychips.modefiteitemdefinitions.property.handler;


import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ModelTransformationMode;
import timmychips.modefiteitemdefinitions.property.type.codec.SelectDefinition;

@FunctionalInterface
public interface SelectPropertyHandler {
    String getValue(ItemStack stack, LivingEntity entity, ModelTransformationMode mode, SelectDefinition.Definition definition);
}
