package timmychips.modefiteitemdefinitions.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import timmychips.modefiteitemdefinitions.property.resolver.ArmorTextureRedirect;

/**
 * Redirects legacy worn-armor texture lookups to a pack's 1.21.2+ equipment texture when present.
 */
@Environment(EnvType.CLIENT)
@Mixin(ArmorMaterial.Layer.class)
public abstract class ArmorTextureRedirectMixin {
    @Inject(method = "getTexture", at = @At("RETURN"), cancellable = true)
    private void modefite$redirectToNewEquipmentTexture(boolean secondLayer, CallbackInfoReturnable<Identifier> cir) {
        Identifier legacy = cir.getReturnValue();
        Identifier redirected = ArmorTextureRedirect.redirect(legacy);
        if (redirected != legacy) cir.setReturnValue(redirected);
    }
}
