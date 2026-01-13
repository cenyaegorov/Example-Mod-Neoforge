package com.example.examplemod;

import com.example.examplemod.entity.ISubduableMob;
import com.example.examplemod.entity.ModEntities;
import com.example.examplemod.entity.bus.Bus;
import com.example.examplemod.entity.bus.BusSubduableHandler;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

public class ModCapabilities {
    public static final EntityCapability<ISubduableMob, Void> SUBDUABLE_MOB = EntityCapability.createVoid(
            ResourceLocation.fromNamespaceAndPath(ExampleModNeoforge.MODID, "subduable_mob"),
            ISubduableMob.class
    );

    public static void registerCapabilities(RegisterCapabilitiesEvent event){
        event.registerEntity(SUBDUABLE_MOB, ModEntities.BUS.get(), (entity, context) -> new BusSubduableHandler((Bus) entity));
    }
}
