package timmychips.modefiteitemdefinitions.property.resolver.selectcase;


import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ModelTransformationMode;
import timmychips.modefiteitemdefinitions.property.handler.SelectPropertyHandler;
import timmychips.modefiteitemdefinitions.property.type.codec.SelectDefinition;

/**
 * Return custom model data int component value on item as string
 * <p>(Closest to custom_model_data predicate, pre-1.21.4)
 */
public class CustomModelDataCase implements SelectPropertyHandler {
    @Override
    public String getValue(ItemStack stack, LivingEntity entity, ModelTransformationMode mode, SelectDefinition.Definition definition) {
        CustomModelDataComponent custom_model_data = stack.get(DataComponentTypes.CUSTOM_MODEL_DATA);
        if (custom_model_data == null) return null;
        return String.valueOf(custom_model_data.value());
    }
}
