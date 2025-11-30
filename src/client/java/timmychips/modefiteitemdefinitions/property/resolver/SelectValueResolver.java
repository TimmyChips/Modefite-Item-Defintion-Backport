package timmychips.modefiteitemdefinitions.property.resolver;


import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.util.Identifier;
import timmychips.modefiteitemdefinitions.property.registry.SelectPropertyRegistry;
import timmychips.modefiteitemdefinitions.property.type.codec.SelectDefinition;

public class SelectValueResolver {

    public static String evaluate(
            Identifier property,
            ModelTransformationMode renderMode,
            SelectDefinition.Definition def,
            ItemStack stack,
            LivingEntity entity) {

        // TODO case "minecraft:local_time" class
        return SelectPropertyRegistry.resolve(property, stack, entity, renderMode, def);
    }
}
