package timmychips.modefiteitemdefinitions.property.resolver;

import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.NamespaceResourceManager;
import net.minecraft.resource.ResourceFactory;
import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.resource.ResourceType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Builds a vanilla-only fallback ResourceFactory, for retrying a shader compile without any active resourcepack's shaders/core interfering.
 */
public class VanillaShaderFactory {
    private static final Map<String, ResourceFactory> CACHE = new ConcurrentHashMap<>();

    public static void clearCache() {
        CACHE.clear();
    }

    public static ResourceFactory get(String namespace) {
        return CACHE.computeIfAbsent(namespace, VanillaShaderFactory::build);
    }

    private static ResourceFactory build(String namespace) {
        ResourcePackProfile vanilla = MinecraftClient.getInstance().getResourcePackManager().getProfile("vanilla");
        NamespaceResourceManager manager = new NamespaceResourceManager(ResourceType.CLIENT_RESOURCES, namespace);
        manager.addPack(vanilla.createResourcePack());
        return manager;
    }
}
