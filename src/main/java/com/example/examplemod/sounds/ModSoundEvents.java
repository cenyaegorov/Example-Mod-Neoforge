package com.example.examplemod.sounds;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, "examplemodneoforge");

    public static final DeferredHolder<SoundEvent, SoundEvent> BUS_IDLE = SOUND_EVENTS.register(
            "entity.bus.idle",
            SoundEvent::createVariableRangeEvent
    );
    public static final DeferredHolder<SoundEvent, SoundEvent> BUS_ANGRY = SOUND_EVENTS.register(
            "entity.bus.angry",
            SoundEvent::createVariableRangeEvent
    );

    public static void register(IEventBus bus){
        SOUND_EVENTS.register(bus);
    }
}
