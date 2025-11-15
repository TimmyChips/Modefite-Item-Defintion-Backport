package timmychips.modefiteitemdefinitions.property.resolver.selectcase;

import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.nbt.NbtCompound;
import timmychips.modefiteitemdefinitions.comp.BlockStateComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import timmychips.modefiteitemdefinitions.comp.ComponentType;
import timmychips.modefiteitemdefinitions.comp.DataComponentTypes;
import timmychips.modefiteitemdefinitions.property.handler.SelectPropertyHandler;
import timmychips.modefiteitemdefinitions.property.type.codec.SelectDefinition;

// Returns Block State String specified
public class BlockStateCase implements SelectPropertyHandler {
    @Override
    public String getValue(ItemStack stack, LivingEntity entity, ModelTransformationMode mode, SelectDefinition.Definition definition) {
        String block_state_property = definition.blockStateProperty(); // Get specified block state from items model definition

        NbtCompound blockStateNbt = ComponentType.BLOCK_STATE.get(stack);
        if (blockStateNbt == null) return null;
        BlockStateComponent block_state = BlockStateComponent.fromNbt(blockStateNbt); // retrieves value from string
        return block_state.properties().get(block_state_property);
    }
}
//ComponentType.CUSTOM_MODEL_DATA.get(stack)