package com.example.examplemod.entity;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;
import java.util.UUID;

public interface ISubduableMob {
    boolean canBeSubdued(Player player, ItemStack item);
    boolean trySubdue(Player player, ItemStack item);
    void release();
    boolean isSubdued();
    Optional<UUID> getOwnerId();
}
