package timmychips.modefiteitemdefinitions.property.resolver.condition.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import timmychips.modefiteitemdefinitions.ClientInitializer;
import timmychips.modefiteitemdefinitions.property.handler.ConditionPropertyHandler;
import timmychips.modefiteitemdefinitions.property.type.codec.ConditionDefinition;

// Returns if player is in matching "fluid" field in items model definition
// "fluid" json field - optional; is id minecraft:water by default
public class SubmergedBool implements ConditionPropertyHandler {
    @Override
    public boolean getValue(ItemStack stack, LivingEntity entity, ConditionDefinition definition) {
        Identifier fluid = definition.submergedFluid(); // is minecraft:water by default
        return submergedInFluidCheck(entity, stack, fluid);
    }

    private static boolean submergedInFluidCheck(LivingEntity livingEntity, ItemStack stack, Identifier fluidIdToMatch) {

        Entity holder = livingEntity == null ? stack.getHolder() : livingEntity; // Get the stack holding entity (ItemEntity) if livingEntity is null

        if (holder != null) {
            Vec3d eyePos;
            if (holder instanceof LivingEntity) eyePos = holder.getEyePos();
            else eyePos = holder.getPos();

            BlockPos fluidBlock = BlockPos.ofFloored(eyePos);
            FluidState fluidState = holder.getWorld().getFluidState(fluidBlock); // Get fluidState entity is submerged in

            if (!fluidState.isEmpty()) {
                Fluid fluid = fluidState.getFluid();
                Fluid targetToMatch = Registries.FLUID.get(fluidIdToMatch); // Get identifier to match as Fluid object

                return fluid.matchesType(targetToMatch); // Matches specified fluid from json file
            }
        }
        return false;
    }
}
