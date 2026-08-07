package timmychips.modefiteitemdefinitions.mixin.client;

import com.mojang.logging.LogUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.resource.ResourceFactory;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import timmychips.modefiteitemdefinitions.property.resolver.VanillaShaderFactory;

import java.io.IOException;

/**
 * Keeps a single incompatible core shader (e.g. from a pack built for 1.21.4+'s shader format) from aborting the whole shader reload.
 */
@Environment(EnvType.CLIENT)
@Mixin(GameRenderer.class)
public abstract class GameRendererShaderResilienceMixin {
    private static final Logger LOGGER = LogUtils.getLogger();

    @Redirect(method = "loadPrograms(Lnet/minecraft/resource/ResourceFactory;)V",
            at = @At(value = "NEW", target = "Lnet/minecraft/client/gl/ShaderProgram;"))
    private ShaderProgram modefite$safeConstructShaderProgram(ResourceFactory factory, String name, VertexFormat format) throws IOException {
        try {
            return new ShaderProgram(factory, name, format);
        } catch (IOException e) {
            LOGGER.warn("MODEFITE: shader '{}' failed to compile from active resourcepacks, falling back to vanilla ({})", name, e.getMessage());
            return new ShaderProgram(VanillaShaderFactory.get("minecraft"), name, format);
        }
    }
}
