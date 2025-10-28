package timmychips.modefiteitemdefinitions;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.JsonOps;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import timmychips.modefiteitemdefinitions.property.registry.ConditionPropertyRegistry;
import timmychips.modefiteitemdefinitions.property.registry.RangePropertyRegistry;
import timmychips.modefiteitemdefinitions.property.registry.SelectPropertyRegistry;
import timmychips.modefiteitemdefinitions.property.type.codec.ItemModelDefinition;
import timmychips.modefiteitemdefinitions.property.type.codec.ItemModelRootDefinition;
import timmychips.modefiteitemdefinitions.property.type.ItemModelTypes;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;

public class ClientInitializer implements ClientModInitializer {

    public static final String MOD_NAMESPACE = "modefite";
	public static final Logger LOGGER = LogUtils.getLogger();
	public static Collection<Identifier> modelIds;

    // Extra optional fields in Items Model root
    private static final String HAND_ANIMATION_SWAP = "hand_animation_on_swap";
    private static final String OVERSIZED_IN_GUI = "oversized_in_gui"; // Not currently used/functioning
    private static final String SWAP_ANIMATION_SCALE = "swap_animation_scale"; // Not currently used/functioning

    private static void registerResources(String folderName, ResourceManager manager) {
        for (Identifier id : manager.findResources(folderName, path -> path.getPath().endsWith(".json")).keySet()) {
            try (InputStream stream = manager.getResource(id).get().getInputStream()) {
                JsonElement json = JsonParser.parseReader(new InputStreamReader(stream));

                JsonObject root = json.getAsJsonObject();

                // Optional fields
                boolean handAnimationOnSwap = !root.has(HAND_ANIMATION_SWAP) || root.get(HAND_ANIMATION_SWAP).getAsBoolean(); // True if not specified
                boolean oversizedInGui = root.has(OVERSIZED_IN_GUI) && root.get(OVERSIZED_IN_GUI).getAsBoolean();
                float swapAnimationScale = root.has(SWAP_ANIMATION_SCALE) ? root.get(SWAP_ANIMATION_SCALE).getAsFloat() : 1F; // 1.0 if not specified

                JsonElement modelElement = root.get("model");

                if (modelElement != null && modelElement.isJsonObject()) {
                    ItemModelTypes.CODEC.decode(JsonOps.INSTANCE, modelElement)
                            .resultOrPartial(error -> LOGGER.warn("Failed to decode model definition for {}: {}", id, error))
                            .ifPresent(pair -> {
                                // Clean up path to match item ID (remove "items/" and ".json")
                                String cleanPath = id.getPath().substring((folderName + "/").length(), id.getPath().length() - ".json".length());
                                Identifier itemId = Identifier.of(id.getNamespace(), cleanPath);

                                ItemModelDefinition definition = pair.getFirst();
                                ItemModelRootDefinition rootDef = new ItemModelRootDefinition(definition, handAnimationOnSwap, oversizedInGui, swapAnimationScale);

                                ItemModelTypes.Registry.putRoot(itemId, rootDef); // Register item and root definition
//                                LOGGER.info("Successfully decoded item model definition for: {}", itemId);
                            });
                } else {
                    LOGGER.warn("No 'model' field found in item JSON for {}", id);
                }

            } catch (Exception e) {
                LOGGER.warn("Failed to parse item definition for {}", id, e);
            }
        }
    }

	@Override
	public void onInitializeClient() {
		// Mod's Client Entrypoint

		// Register methods using items for the is_using predicate
		UseKeyTracker.receiveUseKeyPacket();
		UseKeyTracker.clientUseKey();
		UseKeyTracker.eventUseKeyPacket();

		ModelLoadingPlugin.register(pluginContext -> {

			ResourceManager manager = MinecraftClient.getInstance().getResourceManager();

			ItemModelTypes.Registry.clear();
			RangePropertyRegistry.init();
			ConditionPropertyRegistry.init();
			SelectPropertyRegistry.init();

			registerResources("items", manager);
            String overrideFolderName = MOD_NAMESPACE + "_items_override"; // modefite_items_override
            registerResources(overrideFolderName, manager); // The resource folder where you should use modded properties

			modelIds = ItemModelTypes.Registry.getAllModelDependencies();

			LOGGER.info("{}: loading models", MOD_NAMESPACE.toUpperCase());
			pluginContext.addModels(modelIds);
		});
	}
}