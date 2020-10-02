package de.melanx.spookyjam2020.core;

import de.melanx.spookyjam2020.SpookyJam2020;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class CreativeTab extends ItemGroup {
    public CreativeTab() {
        super(SpookyJam2020.MODID);
    }

    @Nonnull
    @Override
    public ItemStack createIcon() {
        return new ItemStack(Registration.ITEM_HOLLOWED_PUMPKIN.get());
    }
}
