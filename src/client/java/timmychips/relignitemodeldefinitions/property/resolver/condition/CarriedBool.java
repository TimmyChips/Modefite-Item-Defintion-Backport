package timmychips.relignitemodeldefinitions.property.resolver.condition;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import timmychips.relignitemodeldefinitions.property.handler.ConditionPropertyHandler;
import timmychips.relignitemodeldefinitions.property.type.codec.ConditionDefinition;

// Returns if cursor is holding item
public class CarriedBool implements ConditionPropertyHandler {
    // Checks if current stack matches the cursor's stack
    @Override
    public boolean getValue(ItemStack stack, LivingEntity entity, ConditionDefinition definition) {
        if (entity instanceof ClientPlayerEntity clientPlayer) {
            return clientPlayer.currentScreenHandler.getCursorStack() == stack;
        }

        return false;
    }
}
