package de.melanx.cucurbita.items;

import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.ItemBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;

public class ContainerItem extends ItemBase {
    private final ItemStack container;

    public ContainerItem(ModX mod, Properties properties, IItemProvider item) {
        this(mod, properties, new ItemStack(item));
    }

    public ContainerItem(ModX mod, Properties properties, ItemStack item) {
        super(mod, properties);
        this.container = item;
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack stack) {
        return this.container;
    }
}
