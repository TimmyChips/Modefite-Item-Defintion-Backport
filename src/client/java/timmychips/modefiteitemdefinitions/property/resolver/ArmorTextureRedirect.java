package timmychips.modefiteitemdefinitions.property.resolver;

import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Redirects a legacy armor texture lookup to a pack's 1.21.2+ equipment texture, if it ships one.
 */
public class ArmorTextureRedirect {
    private static final Map<Identifier, Identifier> CACHE = new ConcurrentHashMap<>();
    // Vanilla material ids that changed for the new equipment asset format; others kept their legacy id
    private static final Map<String, String> RENAMED_ASSETS = Map.of(
            "turtle", "turtle_scute",
            "armadillo", "armadillo_scute"
    );
    private static final Pattern LEGACY_PATTERN = Pattern.compile("textures/models/armor/(.+)_layer_(1|2)(_overlay)?\\.png");

    public static void clearCache() {
        CACHE.clear();
    }

    public static Identifier redirect(Identifier legacy) {
        return CACHE.computeIfAbsent(legacy, ArmorTextureRedirect::compute);
    }

    private static Identifier compute(Identifier legacy) {
        Matcher matcher = LEGACY_PATTERN.matcher(legacy.getPath());
        if (!matcher.matches()) return legacy;

        String assetName = RENAMED_ASSETS.getOrDefault(matcher.group(1), matcher.group(1));
        String folder = matcher.group(2).equals("2") ? "humanoid_leggings" : "humanoid";
        boolean overlay = matcher.group(3) != null;

        Identifier candidate = Identifier.of(legacy.getNamespace(),
                "textures/entity/equipment/" + folder + "/" + assetName + (overlay ? "_overlay" : "") + ".png");

        ResourceManager manager = MinecraftClient.getInstance().getResourceManager();
        return manager.getResource(candidate).isPresent() ? candidate : legacy;
    }
}
