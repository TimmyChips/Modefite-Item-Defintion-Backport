package timmychips.modefiteitemdefinitions.property.resolver.rangeentry;

import com.mojang.logging.LogUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import org.slf4j.Logger;
import timmychips.modefiteitemdefinitions.property.handler.RangePropertyHandler;
import timmychips.modefiteitemdefinitions.property.resolver.ResolveRecursive;
import timmychips.modefiteitemdefinitions.property.type.codec.RangeDispatchDefinition;

import java.util.Set;

public class CustomModelDataFloat implements RangePropertyHandler {

    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Set<String> WARNED_MODELS = ResolveRecursive.WARNED_MODELS;

    @Override
    public float getValue(ItemStack stack, LivingEntity entity, RangeDispatchDefinition.Definition definition) {
        // Warning that custom_model_data is only an integer in versions below 1.21.4
        String key = stack.getItem().toString() + "|" + "minecraft:custom_model_data";
        if (WARNED_MODELS.add(key)) LOGGER.warn("Unable to read 'custom_model_data' for type: 'minecraft:range_dispatch' since component is an integer in this version. Defaulting to use integer values.");

        //if customdmodeldata exists on nbt if it missing return 0
        NbtCompound nbt = stack.getNbt();
        if (nbt == null || !nbt.contains("CustomModelData")) {
            return 0F;
        }
        //reads a integer value from ntbt using that .geitng converts it into a float and returns
        int customModelData = nbt.getInt("CustomModelData");
        return (float) customModelData;
    }
}
