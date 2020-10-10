package de.melanx.cucurbita.core;

import de.melanx.cucurbita.Cucurbita;
import de.melanx.cucurbita.core.registration.Registration;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class CreativeTab extends ItemGroup {
    public CreativeTab() {
        super(Cucurbita.MODID);
    }

    @Nonnull
    @Override
    public ItemStack createIcon() {
        return new ItemStack(Registration.ITEM_HOLLOWED_PUMPKIN.get());
    }
}
