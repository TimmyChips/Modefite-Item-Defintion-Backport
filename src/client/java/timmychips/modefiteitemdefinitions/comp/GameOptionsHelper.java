package timmychips.modefiteitemdefinitions.comp;

import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.util.Arm;
import timmychips.modefiteitemdefinitions.ClientInitializer;

import java.lang.reflect.Field;


public class GameOptionsHelper {

    private static Field mainArmField;

    static {
        try {
            mainArmField = GameOptions.class.getDeclaredField("mainArm");
            mainArmField.setAccessible(true);
            ClientInitializer.LOGGER.debug("Successfully loaded mainArm field ");
        } catch (Exception e) {
            ClientInitializer.LOGGER.warn("Failed to load and get main arm : {}", e.getMessage());
            mainArmField = null;
        }
    }


    public static Arm getMainArm(GameOptions options) {
        try {
            if (mainArmField != null) {

                SimpleOption<Arm> mainArmOption = (SimpleOption<Arm>) mainArmField.get(options);
                return mainArmOption.getValue();

            }
        } catch (Exception e) {

        }

        try {

            Object syncedOptions = options.getClass().getMethod("getSyncedOptions").invoke(options);
            return (Arm) syncedOptions.getClass().getMethod("mainArm").invoke(syncedOptions);
        } catch (Exception e) {
            ClientInitializer.LOGGER.warn("Failed to load and get main arm: {}", e.getMessage());
        }

        return Arm.RIGHT;
    }
}