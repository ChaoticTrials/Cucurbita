package de.melanx.cucurbita.util;

import de.melanx.cucurbita.util.inventory.BaseItemStackHandler;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class Util {
    public static void withdrawFromInventory(BaseItemStackHandler inv, PlayerEntity player) {
        for (int i = inv.getSlots() - 1; i >= 0; i--) {
            ItemStack invStack = inv.getStackInSlot(i);
            if (!invStack.isEmpty()) {
                ItemStack stack = invStack.copy();
                World world = player.world;
                if (!player.inventory.addItemStackToInventory(stack)) {
                    world.addEntity(new ItemEntity(world, player.getPosX(), player.getPosY(), player.getPosZ(), stack));
                }
                inv.setStackInSlot(i, ItemStack.EMPTY);
                world.playSound(player, player.getPosition(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.NEUTRAL, 0.2F, ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                break;
            }
        }
    }
}
