package timmychips.modefiteitemdefinitions.property.type.codec;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;

import java.util.List;

public class CodecUtils {
    // Return
    public static <T> Codec<List<T>> ofValueOrList(Codec<T> valueCodec) {
        return Codec.either(
                valueCodec,
                valueCodec.listOf()
        ).xmap(
                either -> either.map(List::of, list -> list),
                list -> list.size() == 1
                        ? Either.left(list.get(0))
                        : Either.right(list)
        );
    }
}
