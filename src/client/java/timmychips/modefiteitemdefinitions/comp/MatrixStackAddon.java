package timmychips.modefiteitemdefinitions.comp;

import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix3f;
import org.joml.Matrix4f;


//only for Entry.copy()
public class MatrixStackAddon {








    public static MatrixStack.Entry copy(MatrixStack.Entry entry) {
        MatrixStack tempStack = new MatrixStack(); // temporaty MatrixStack
        Matrix4f positionMatrix = entry.getPositionMatrix();
        Matrix3f normalMatrix = entry.getNormalMatrix();
        MatrixStack.Entry tempEntry = tempStack.peek(); // copy matrix valeuas

        tempEntry.getPositionMatrix().set(positionMatrix); //4x4 copy position
        tempEntry.getNormalMatrix().set(normalMatrix); //3x3 copy normal

        return tempEntry;

    }
    public static MatrixStack.Entry copyFromStack(MatrixStack matrixStack) {
        return copy(matrixStack.peek());
    }
    public static MatrixStack.Entry copyUsingPush(MatrixStack matrixStack) {
        matrixStack.push(); //creates acopy internaly
        return matrixStack.peek();
    }
}
