package timmychips.modefiteitemdefinitions.property.helper;

import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntityVariantLists {

    public static final List<Identifier> ENTITY_VARIANTS = List.of(
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
            new Identifier("minecraft", "llama/variant"),
            new Identifier("minecraft", "tropical_fish/pattern")
    );

    //axolotl
    public static final ArrayList<Identifier> AXOLOTL = new ArrayList<>(
            Arrays.asList(
                    new Identifier("minecraft", "lucy"),
                    new Identifier("minecraft", "wild"),
                    new Identifier("minecraft", "gold"),
                    new Identifier("minecraft", "cyan"),
                    new Identifier("minecraft", "blue")
            )
    );

    //frog
    public static final ArrayList<Identifier> FROG = new ArrayList<>(
            Arrays.asList(
                    new Identifier("minecraft", "temperate"),
                    new Identifier("minecraft", "warm"),
                    new Identifier("minecraft", "cold")
            )
    );


    //cat
    public static final ArrayList<Identifier> CAT = new ArrayList<>(
            Arrays.asList(
                    new Identifier("minecraft", "tabby"),
                    new Identifier("minecraft", "black"),
                    new Identifier("minecraft", "red"),
                    new Identifier("minecraft", "siamese"),
                    new Identifier("minecraft", "british_shorthair"),
                    new Identifier("minecraft", "calico"),
                    new Identifier("minecraft", "persian"),
                    new Identifier("minecraft", "ragdoll"),
                    new Identifier("minecraft", "white"),
                    new Identifier("minecraft", "jellie"),
                    new Identifier("minecraft", "all_black")
            )
    );

    //fox
    public static final ArrayList<Identifier> FOX = new ArrayList<>(
            Arrays.asList(
                    new Identifier("minecraft", "red"),
                    new Identifier("minecraft", "snow")
            )
    );

    //wolf

    public static final ArrayList<Identifier> WOLF = new ArrayList<>(
            Arrays.asList(
                    new Identifier("minecraft", "pale"),
                    new Identifier("minecraft", "spotted"),
                    new Identifier("minecraft", "snowy"),
                    new Identifier("minecraft", "black"),
                    new Identifier("minecraft", "ashen"),
                    new Identifier("minecraft", "rusty"),
                    new Identifier("minecraft", "woods"),
                    new Identifier("minecraft", "chestnut"),
                    new Identifier("minecraft", "striped")
            )
    );


    //rabit

    public static final ArrayList<Identifier> RABBIT = new ArrayList<>(
            Arrays.asList(
                    new Identifier("minecraft", "brown"),
                    new Identifier("minecraft", "white"),
                    new Identifier("minecraft", "black"),
                    new Identifier("minecraft", "black_and_white"),
                    new Identifier("minecraft", "gold"),
                    new Identifier("minecraft", "salt_and_pepper"),
                    new Identifier("minecraft", "toast"),
                    new Identifier("minecraft", "caerbannog")
            )
    );



    //parrot

    public static final ArrayList<Identifier> PARROT = new ArrayList<>(
            Arrays.asList(
                    new Identifier("minecraft", "red"),
                    new Identifier("minecraft", "blue"),
                    new Identifier("minecraft", "green"),
                    new Identifier("minecraft", "cyan"),
                    new Identifier("minecraft", "gray")
            )
    );



    //mooshroom

    public static final ArrayList<Identifier> MOOSHROOM = new ArrayList<>(
            Arrays.asList(
                    new Identifier("minecraft", "red"),
                    new Identifier("minecraft", "brown")
            )
    );

    //horse
    public static final ArrayList<Identifier> HORSE = new ArrayList<>(
            Arrays.asList(
                    new Identifier("minecraft", "white"),
                    new Identifier("minecraft", "creamy"),
                    new Identifier("minecraft", "chestnut"),
                    new Identifier("minecraft", "brown"),
                    new Identifier("minecraft", "black"),
                    new Identifier("minecraft", "gray"),
                    new Identifier("minecraft", "dark_brown")
            )
    );

    //lama
    public static final ArrayList<Identifier> LLAMA = new ArrayList<>(
            Arrays.asList(
                    new Identifier("minecraft", "creamy"),
                    new Identifier("minecraft", "white"),
                    new Identifier("minecraft", "brown"),
                    new Identifier("minecraft", "gray")
            )
    );
//fishy
    public static final ArrayList<Identifier> TROPICAL_FISH = new ArrayList<>(
            Arrays.asList(
                    new Identifier("minecraft", "kob"),
                    new Identifier("minecraft", "sunstreak"),
                    new Identifier("minecraft", "snooper"),
                    new Identifier("minecraft", "dasher"),
                    new Identifier("minecraft", "brinely"),
                    new Identifier("minecraft", "spotty"),
                    new Identifier("minecraft", "flopper"),
                    new Identifier("minecraft", "stripey"),
                    new Identifier("minecraft", "glitter"),
                    new Identifier("minecraft", "blockfish"),
                    new Identifier("minecraft", "betty"),
                    new Identifier("minecraft", "clayfish")
            )
    );

    private EntityVariantLists() {
        throw new UnsupportedOperationException("");
    }
}
