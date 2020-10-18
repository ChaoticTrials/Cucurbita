package de.melanx.cucurbita.api.util;

import com.google.gson.JsonObject;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import io.github.noeppi_noeppi.libx.util.NbtToJson;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NBTDynamicOps;

public class ItemNBTHelper {
    public static void renameTag(CompoundNBT nbt, String oldName, String newName) {
        INBT tag = nbt.get(oldName);
        if (tag != null) {
            nbt.remove(oldName);
            nbt.put(newName, tag);
        }
    }

    public static JsonObject serializeStack(ItemStack stack) {
        CompoundNBT nbt = stack.write(new CompoundNBT());
        byte c = nbt.getByte("Count");
        if (c != 1) {
            nbt.putByte("count", c);
        }
        nbt.remove("Count");
        renameTag(nbt, "id", "item");
        renameTag(nbt, "tag", "nbt");
        Dynamic<INBT> dyn = new Dynamic<>(NBTDynamicOps.INSTANCE, nbt);
        return dyn.convert(JsonOps.INSTANCE).getValue().getAsJsonObject();
    }
}
