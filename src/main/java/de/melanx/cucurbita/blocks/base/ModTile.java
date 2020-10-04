package de.melanx.cucurbita.blocks.base;

import de.melanx.cucurbita.util.VanillaPacketDispatcher;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ModTile extends TileEntity implements ITickableTileEntity {
    private boolean sendPacket;

    public ModTile(TileEntityType<?> type) {
        super(type);
    }

    @Override
    public void tick() {
        if (this.world != null && this.sendPacket) {
            VanillaPacketDispatcher.dispatchTEToNearbyPlayers(this);
            this.sendPacket = false;
        }
    }

    @Nonnull
    @Override
    public CompoundNBT write(@Nonnull CompoundNBT tag) {
        CompoundNBT ret = super.write(tag);
        writePacketNBT(ret);
        return super.write(tag);
    }

    @Nonnull
    @Override
    public CompoundNBT getUpdateTag() {
        return write(new CompoundNBT());
    }

    @Override
    public void read(@Nonnull BlockState state, @Nonnull CompoundNBT nbt) {
        super.read(state, nbt);
        readPacketNBT(nbt);
    }

    public void writePacketNBT(CompoundNBT cmp) {}

    public void readPacketNBT(CompoundNBT cmp) {}

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT tag = new CompoundNBT();
        writePacketNBT(tag);
        return new SUpdateTileEntityPacket(this.pos, -999, tag);
    }

    @Override
    public void onDataPacket(NetworkManager network, SUpdateTileEntityPacket packet) {
        super.onDataPacket(network, packet);
        readPacketNBT(packet.getNbtCompound());
    }

    public void markDispatchable() {
        this.sendPacket = true;
    }
}
