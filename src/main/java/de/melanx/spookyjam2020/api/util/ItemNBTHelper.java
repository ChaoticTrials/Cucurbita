package de.melanx.spookyjam2020.api.util;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;

public class ItemNBTHelper {
    public static void renameTag(CompoundNBT nbt, String oldName, String newName) {
        INBT tag = nbt.get(oldName);
        if (tag != null) {
            nbt.remove(oldName);
            nbt.put(newName, tag);
        }
    }
}
