package timmychips.modefiteitemdefinitions.comp.util;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import java.lang.reflect.Field;
public class MathUtils {
    private static Field dimensionField;
    private static Field posField;
    private static boolean fieldsInitialized = false;
    private static void initializeFields() {
        if (fieldsInitialized) return;

        try {
            // Try to get the dimesinos
            dimensionField = GlobalPos.class.getDeclaredField("dimension");
            dimensionField.setAccessible(true);

            // Try to get the pos
            posField = GlobalPos.class.getDeclaredField("pos");
            posField.setAccessible(true);

            fieldsInitialized = true;
        } catch (NoSuchFieldException e) {
            // if don't work try diffrent mappings
            try {
                // try mcp
                dimensionField = GlobalPos.class.getDeclaredField("field_25064");
                dimensionField.setAccessible(true);

                posField = GlobalPos.class.getDeclaredField("field_25065");
                posField.setAccessible(true);

                fieldsInitialized = true;
            } catch (NoSuchFieldException ex) {
                System.err.println("Failed to find GlobalPos");
                ex.printStackTrace();
            }
        }

    }@Nullable //utility methods for global spos getpos returns blockpos from a globalpos if no then null
    public static BlockPos getPos(@Nullable GlobalPos globalPos) {
        if (globalPos == null) return null;

        initializeFields();

        if (posField == null) return null;

        try {
            return (BlockPos) posField.get(globalPos);
        } catch (IllegalAccessException e) {
            System.err.println("Failed to access GlobalPos.pos");
            e.printStackTrace();
            return null;
        }
    }@Nullable // returns dimesinpon RegistryKey<World> of a Globalpos or null if none
    @SuppressWarnings("unchecked")
    public static RegistryKey<World> getDimension(@Nullable GlobalPos globalPos) {
        if (globalPos == null) return null;

        initializeFields();

        if (dimensionField == null) return null;

        try {
            return (RegistryKey<World>) dimensionField.get(globalPos);
        } catch (IllegalAccessException e) {
            System.err.println("Failed to access GlobalPosdimension");
            e.printStackTrace();
            return null;
        }
        //checks if two positions are in the same positino/diemension

    }public static boolean isSameDimension(@Nullable GlobalPos pos1, @Nullable GlobalPos pos2, @Nullable RegistryKey<World> dimension2) {
        if (pos1 == null) return false;

        RegistryKey<World> dim1 = getDimension(pos1);
        RegistryKey<World> dim2 = pos2 != null ? getDimension(pos2) : dimension2;

        return dim1 != null && dim1.equals(dim2);
    }public static boolean isValid(@Nullable GlobalPos globalPos) {
        return globalPos != null && getDimension(globalPos) != null && getPos(globalPos) != null;
    }
    // i used reflection to get acces to mathpos since idk but minecraft said it's private :(

    // Since Java 21 added Math.clamp; compat with Java 17
    public static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(value, max));
    }
}