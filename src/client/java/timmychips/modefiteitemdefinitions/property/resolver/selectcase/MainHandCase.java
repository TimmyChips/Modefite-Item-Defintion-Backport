package timmychips.modefiteitemdefinitions.property.resolver.selectcase;

import net.minecraft.client.MinecraftClient;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.util.Arm;
import timmychips.modefiteitemdefinitions.property.handler.SelectPropertyHandler;
import timmychips.modefiteitemdefinitions.property.type.codec.SelectDefinition;

/**
 * Return main hand of player
 * <p>Values: left or right
 */
public class MainHandCase implements SelectPropertyHandler {
    @Override
    public String getValue(ItemStack stack, LivingEntity entity, ModelTransformationMode mode, SelectDefinition.Definition definition) {
        Arm mainArm = MinecraftClient.getInstance().options.getSyncedOptions().mainArm();
        if (entity != null) mainArm = entity.getMainArm();

        return mainArm.toString().toLowerCase();
    }
}
