package timmychips.modefiteitemdefinitions.property.resolver.selectcase;

import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.trim.ArmorTrim;
import timmychips.modefiteitemdefinitions.property.handler.SelectPropertyHandler;
import timmychips.modefiteitemdefinitions.property.type.codec.SelectDefinition;

/**
 * Return the material id of the armor trim as string
 */
public class TrimMaterialCase implements SelectPropertyHandler {
    @Override
    public String getValue(ItemStack stack, LivingEntity entity, ModelTransformationMode mode, SelectDefinition.Definition definition) {
        ArmorTrim armorTrim = stack.get(timmychips.modefiteitemdefinitions.comp.DataComponentTypes.TRIM);
        if (armorTrim == null) return null;
        return armorTrim.getMaterial().getIdAsString();
    }
}
