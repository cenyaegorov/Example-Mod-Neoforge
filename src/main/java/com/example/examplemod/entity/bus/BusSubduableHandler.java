package com.example.examplemod.entity.bus;

import com.example.examplemod.ModDataComponents;
import com.example.examplemod.entity.ISubduableMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;
import java.util.UUID;

public class BusSubduableHandler implements ISubduableMob {
    private final Bus mob;
    private UUID ownerId;
    private boolean isSubdued;

    public BusSubduableHandler(Bus mob) {
        this.mob = mob;
        this.isSubdued = false;
    }

    @Override
    public boolean canBeSubdued(Player player, ItemStack item) {
        return !isSubdued() && mob.getHealth() < mob.getMaxHealth() * 0.5 && item.getOrDefault(ModDataComponents.ITEM_LEVEL, 0) >= 1;
    }

    @Override
    public boolean trySubdue(Player player, ItemStack item) {
        if (!canBeSubdued(player, item)) return false;
        
        this.ownerId = player.getUUID();
        this.isSubdued = true;
        
        updateMobAI();
        
        if (this.mob.level().isClientSide()){

        }
        return true;
    }

    private void updateMobAI() {
        this.mob.subdued();
    }

    @Override
    public void release() {

    }

    @Override
    public boolean isSubdued() {
        return isSubdued;
    }

    @Override
    public Optional<UUID> getOwnerId() {
        return Optional.of(ownerId);
    }
}
