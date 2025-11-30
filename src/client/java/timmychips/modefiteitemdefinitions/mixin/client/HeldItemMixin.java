package timmychips.modefiteitemdefinitions.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MatrixUtil;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import timmychips.modefiteitemdefinitions.bakedmodels.CompositeItemModel;
import timmychips.modefiteitemdefinitions.property.type.ItemModelTypes;

import java.util.List;
import java.util.Optional;

import static timmychips.modefiteitemdefinitions.property.resolver.ItemModelResolver.resolveModel;

// Mixin injects into target ItemRenderer vanilla class
@Environment(EnvType.CLIENT)
@Mixin(ItemRenderer.class)
public abstract class HeldItemMixin {

    @Unique
    private static final ThreadLocal<Boolean> RENDERING_LIVING_ENTITY = ThreadLocal.withInitial(() -> false);

    @Shadow
    protected abstract void renderBakedItemModel(BakedModel model, ItemStack stack, int light, int overlay, MatrixStack matrices, VertexConsumer vertices);

    @Shadow
    @Final
    private BuiltinModelItemRenderer builtinModelItemRenderer;

    // Gets custom model for GUI model mode so the item model changes for the GUI
    @Inject(method = "getModel", at = @At("HEAD"), cancellable = true)
    private void modefite$overrideGUIModel(ItemStack stack, World world, LivingEntity entity, int seed, CallbackInfoReturnable<BakedModel> cir) {
        BakedModel gui_model = getCustomModel(stack, entity, ModelTransformationMode.GUI);
        if (gui_model != null) {
            cir.setReturnValue(gui_model);
        }
    }

    /**
     * Performs item renderer methods for each baked model if baked model is a composite item model
     * <p> Code is mostly from vanilla target method with the major difference being it performs the method for each modelPart of the composite item model
     */
    @Inject(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V",
            at = @At(value = "HEAD"),
            cancellable = true)
    private void modefite$renderCompositeOrItemEntityModel(ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model, CallbackInfo ci) {
        if (!stack.isEmpty()) {
            ///  Composite model types
            if (model instanceof CompositeItemModel compositeModel) {
                // Retrieve list of baked models from CompositeItemModel object
                List<BakedModel> models = compositeModel.getModels();

                if (models != null) {
                    for (BakedModel modelPart : models) {
                        // Code is from vanilla method with minor tweaks
                        matrices.push();

                        boolean bl = renderMode == ModelTransformationMode.GUI || renderMode == ModelTransformationMode.GROUND || renderMode == ModelTransformationMode.FIXED;
                        modelPart.getTransformation().getTransformation(renderMode).apply(leftHanded, matrices);
                        matrices.translate(-0.5F, -0.5F, -0.5F);

                        if (!model.isBuiltin() || bl) {

                            RenderLayer renderLayer = RenderLayers.getItemLayer(stack);
                            VertexConsumer vertexConsumer;


                            vertexConsumer = ItemRenderer.getItemGlintConsumer(vertexConsumers, renderLayer, true, stack.hasGlint());

                            MatrixStack.Entry entry = matrices.peek().copy();
                            if (renderMode == ModelTransformationMode.GUI) {
                                MatrixUtil.scale(entry.getPositionMatrix(), 0.5F);
                            } else if (renderMode.isFirstPerson()) {
                                MatrixUtil.scale(entry.getPositionMatrix(), 0.75F);
                            }

                            this.renderBakedItemModel(modelPart, stack, light, overlay, matrices, vertexConsumer);
                        }
                        else {
                            this.builtinModelItemRenderer.render(stack, renderMode, matrices, vertexConsumers, light, overlay);
                        }
                        matrices.pop(); // Pop matrix to prevent render errors for next element in list
                    }
                }
                ci.cancel(); // Cancel rest of method for composite item models
            }

            else {
                ///  For Ground or null LivingEntity types (such as ItemEntities)
                if (RENDERING_LIVING_ENTITY.get()) return;

                if (renderMode == ModelTransformationMode.GROUND) {
                    // For null LivingEntities (e.g. renders the ground render mode for items thrown onto ground)
                    BakedModel customModel = getCustomModel(stack, null, renderMode);
                    if (customModel != null && customModel != model) {
                        ItemRenderer self = (ItemRenderer) (Object) this;
                        self.renderItem(stack, renderMode, leftHanded, matrices, vertexConsumers, light, overlay, customModel);
                        ci.cancel();
                    }
                }
            }
        }
    }

    // Replaces entity item render with our custom model
    @Inject(method = "renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/world/World;III)V",
            at = @At(value = "HEAD"),
            cancellable = true)
    private void modefite$interceptRender(LivingEntity entity, ItemStack item, ModelTransformationMode renderMode, boolean leftHanded,
                                          MatrixStack matrices, VertexConsumerProvider vertexConsumers, World world,
                                          int light, int overlay, int seed, CallbackInfo ci) {

        if (RENDERING_LIVING_ENTITY.get()) {
            // Already in recursive rendering call — skip this render method call
            return;
        }

        RENDERING_LIVING_ENTITY.set(true); // Set if it's rendering model for a valid LivingEntity

        try {
            BakedModel model = getCustomModel(item, entity, renderMode);

            if (model != null) {
                ItemRenderer self = (ItemRenderer)(Object) this;
                // manually call vanilla rendering method with overridden model
                self.renderItem(item, renderMode, leftHanded, matrices, vertexConsumers, light, overlay, model);
                ci.cancel(); // skip original call
            }
        } finally {
            RENDERING_LIVING_ENTITY.set(false); // Set boolean back to false if LivingEntity is null or finished rendering in this method call
        }
    }

    @Inject(
            method = "renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/world/World;III)V",
            at = @At("RETURN")
    )
    private void modefite$markEntityRenderEnd(LivingEntity entity, ItemStack stack, ModelTransformationMode mode, boolean leftHanded,
                                              MatrixStack matrices, VertexConsumerProvider vertexConsumers, World world,
                                              int light, int overlay, int seed, CallbackInfo ci) {
        RENDERING_LIVING_ENTITY.set(false);
    }


    // Get custom model from BakedModelManger's getModel from id (which is needed since we loaded the models with ModelLoadingPlugin)
    @Unique
    private static BakedModel getCustomModel(ItemStack stack, LivingEntity entity, ModelTransformationMode mode) {

        BakedModelManager missingModelManager = MinecraftClient.getInstance().getBakedModelManager();

        // If item's items model definition has an invalid model type, returns missing item model
        for (Identifier id : ItemModelTypes.Registry.INVALID_MODEL_TYPES) {
            if (Registries.ITEM.getId(stack.getItem()).equals(id)) { // Checks if INVALID_TYPES Set contains item id
                return missingModelManager.getMissingModel(); // Item renders as Missing Model
            }
        }

        if (mode == null) mode = ModelTransformationMode.GUI;

        Optional<BakedModel> maybeModel = resolveModel(Registries.ITEM.getId(stack.getItem()), mode, stack, entity);
        if (maybeModel != null && maybeModel.isPresent()) {
            return maybeModel.get();
        }
        return null;
    }
}