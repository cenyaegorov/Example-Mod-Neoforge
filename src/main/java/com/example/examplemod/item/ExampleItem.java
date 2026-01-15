package com.example.examplemod.item;

import com.example.examplemod.ModCapabilities;
import com.example.examplemod.ModDataComponents;
import com.example.examplemod.entity.ISubduableMob;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.jetbrains.annotations.NotNull;

public class ExampleItem extends Item{
    public ExampleItem(){
        super(new Item.Properties().stacksTo(1).durability(100).rarity(Rarity.UNCOMMON).component(ModDataComponents.ITEM_LEVEL, 1));
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(ItemStack item, Player player, LivingEntity target, InteractionHand hand){
        if (!player.level().isClientSide() && target instanceof Monster){
            ISubduableMob subduable = target.getCapability(ModCapabilities.SUBDUABLE_MOB);
            if (subduable != null && subduable.trySubdue(player, item)) {
                //item.hurtAndBreak(10, player, p -> p.broadcastEvent(hand));
                player.displayClientMessage(
                        Component.literal("mob is subdued"),
                        true
                );
                return InteractionResult.SUCCESS;
            } else if (subduable == null) {
                player.displayClientMessage(
                        Component.literal("null"),
                        true
                );
            } else {
                player.displayClientMessage(
                        Component.literal("No success"),
                        true
                );
            }
        }

        return InteractionResult.PASS;
    }
}
