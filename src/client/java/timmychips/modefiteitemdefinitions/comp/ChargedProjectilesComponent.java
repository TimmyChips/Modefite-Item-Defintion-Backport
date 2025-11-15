package timmychips.modefiteitemdefinitions.comp;

//WIP FOR CHARGETYPECASE//
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import java.util.List;
public class ChargedProjectilesComponent {
    private final List<ItemStack> projectiles;

    //consturcotr
    public ChargedProjectilesComponent(List<ItemStack> projectiles) {
        this.projectiles = new ArrayList<>(projectiles);
    }

    //parse nbt check if tje nbt is a lşst
    //loops thoruh each item then only adds non empty item stacks tl the şist returns null if none
    @Nullable
    public static ChargedProjectilesComponent fromNbt(NbtElement nbtElement) {
        if (!(nbtElement instanceof NbtList nbtList)) return null;

        List<ItemStack> projectiles = new ArrayList<>();

        for (int i = 0; i < nbtList.size(); i++) {
            NbtCompound projectileNbt = nbtList.getCompound(i);
            ItemStack projectile = ItemStack.fromNbt(projectileNbt);
            if (!projectile.isEmpty()) {
                projectiles.add(projectile);
            }
        }

        return projectiles.isEmpty() ? null : new ChargedProjectilesComponent(projectiles);
    }
 //return a copy list
    public List<ItemStack> getProjectiles() {
        return new ArrayList<>(projectiles);
    }
// see if any of te projectiles is loaded
    public boolean isEmpty() {
        return projectiles.isEmpty();
    }
//convert back to nbt
    public NbtList toNbt() {
        NbtList nbtList = new NbtList();
        for (ItemStack projectile : projectiles) {
            NbtCompound projectileNbt = new NbtCompound();
            projectile.writeNbt(projectileNbt);
            nbtList.add(projectileNbt);
        }
        return nbtList;
    }

    @Override
    public String toString() {
        return "ChargedProjectilesComponent{projectiles=" + projectiles + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ChargedProjectilesComponent)) return false;
        ChargedProjectilesComponent other = (ChargedProjectilesComponent) obj;
        return projectiles.equals(other.projectiles);
    }

    @Override
    public int hashCode() {
        return projectiles.hashCode();
    }
}
