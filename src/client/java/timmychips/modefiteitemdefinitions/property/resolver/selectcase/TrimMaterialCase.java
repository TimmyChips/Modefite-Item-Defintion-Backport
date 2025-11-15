package timmychips.modefiteitemdefinitions.property.resolver.selectcase;

import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.trim.ArmorTrim;
import net.minecraft.nbt.NbtCompound;
import timmychips.modefiteitemdefinitions.comp.ComponentType;
import timmychips.modefiteitemdefinitions.comp.DataComponentTypes;
import timmychips.modefiteitemdefinitions.property.handler.SelectPropertyHandler;
import timmychips.modefiteitemdefinitions.property.type.codec.SelectDefinition;

/**
 * Return the material id of the armor trim as string
 */
public class TrimMaterialCase implements SelectPropertyHandler {
    @Override
    public String getValue(ItemStack stack, LivingEntity entity, ModelTransformationMode mode, SelectDefinition.Definition definition) {
        NbtCompound trimNbt = ComponentType.TRIM.get(stack);
        if (trimNbt == null) return null;
        return trimNbt.getString("material");
    }
}
