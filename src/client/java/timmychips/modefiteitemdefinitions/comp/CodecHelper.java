package timmychips.modefiteitemdefinitions.comp;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;

import java.util.function.Supplier;


public class CodecHelper {

//made this for lazyInitialized i think it was added 1.20.2+ it's a remake of that vanilalt hing
    public static <A> Codec<A> lazyInitialized(Supplier<Codec<A>> codecSupplier) {

        return new LazyCodec<>(codecSupplier);
    }


    private static class LazyCodec<A> implements Codec<A> {
        private final Supplier<Codec<A>> codecSupplier;
        private volatile Codec<A> codec; // Lazy-loaded codec

        public LazyCodec(Supplier<Codec<A>> codecSupplier) {
            this.codecSupplier = codecSupplier;
        }

        private Codec<A> getCodec() {
            if (codec == null) {
                synchronized (this) {
                    if (codec == null) {
                        codec = codecSupplier.get();
                    }
                }
            }
            return codec;
        }

        @Override
        public <T> DataResult<T> encode(A input, DynamicOps<T> ops, T prefix) {
            return getCodec().encode(input, ops, prefix);
        }

        @Override
        public <T> DataResult<com.mojang.datafixers.util.Pair<A, T>> decode(DynamicOps<T> ops, T input) {
            return getCodec().decode(ops, input);
        }

        @Override
        public String toString() {
            return "LazyCodec[" + (codec != null ? codec.toString() : "uninitialized") + "]";
        }
    }
}