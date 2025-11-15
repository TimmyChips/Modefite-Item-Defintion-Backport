package timmychips.modefiteitemdefinitions.property.helper;


import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntityVariantHelper {

    // TODO: add rest of entity variants from 1.21.5
    //  https://minecraft.wiki/w/Data_component_format#Entity_variant_components

    /// Not components in this version, but are in 1.21.5
    static final List<Identifier> ENTITY_VARIANTS = List.of(
            new Identifier("minecraft", "axolotl/variant"),
            new Identifier("minecraft", "frog/variant"),
            new Identifier("minecraft", "cat/variant"),

        new Identifier("minecraft", "fox/variant"),

        new Identifier("minecraft", "wolf/variant"),
        new Identifier("minecraft", "rabbit/variant"),

            new Identifier("minecraft", "parrot/variant"),
            new Identifier("minecraft", "painting/variant"),
        new Identifier("minecraft", "mooshroom/variant"),
            new Identifier("minecraft", "horse/variant"),
        new Identifier("minecraft", "llama/variant")

    );

    // Axolotl variant names
    static final ArrayList<Identifier> AXOLOTL_VARIANT_LIST = new ArrayList<>(
            Arrays.asList(
                    new Identifier("minecraft", "lucy"),
                    new Identifier("minecraft", "wild"),
                    new Identifier("minecraft", "gold"),
                    new Identifier("minecraft", "cyan"),
                    new Identifier("minecraft", "blue")
            )
    );

    static final ArrayList<Identifier> FOX_VARIANT_LIST = new ArrayList<>(
            Arrays.asList(
                    new Identifier("minecraft", "lucy"),
                    new Identifier("minecraft", "lucy")
            )
    );


    /**
     *
     * @param component Component identifier to check if it's a type of entity variant
     * @return If component is in list
     */
    public static boolean isEntityVariant(Identifier component) {
        return ENTITY_VARIANTS.contains(component);
    }


    /**
     * Casts the component data to its identifier version from 1.21.5
     * @param stack ItemStack to cast component data to string
     * @param component The component to cast from
     * @return String of entity variant
     */
    public static String castEntityVariantComponents(ItemStack stack, String component) {
        switch (component) {
            case "minecraft:axolotl/variant" -> {
                String axolotlVariant = getBucketEntityVariant(stack);
                if (axolotlVariant != null) return axolotlVariant;
            }
        }
        return null;
    }


    /**
     * Get the bucket_entity_data component as a string like for 1.21.5+
     * <p>E.g. a minecraft:axolotl_bucket item that has the gold variant will return variant int 3, and will be cast to its identifier equivalent, "minecraft:gold"
     * @param stack ItemStack to get the variant data from
     * @return String of the entity variant id from the bucket
     */
    public static String getBucketEntityVariant(ItemStack stack) {

        NbtCompound nbt = stack.getNbt();

        if (nbt == null) {
            return null;
        }

        if (nbt.contains("BucketVariantTag", 10)) {
            NbtCompound bucketData = nbt.getCompound("BucketVariantTag");

            //the variants
            if (bucketData.contains("Variant", 3)) {
                int value = bucketData.getInt("Variant");

                // Axolotl Variants if it's within bounds
                if (value >= 0 && value < AXOLOTL_VARIANT_LIST.size()) {
                    return AXOLOTL_VARIANT_LIST.get(value).toString();
                }
            }
        }
            return null;
        }
    }

