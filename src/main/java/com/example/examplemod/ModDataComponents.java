package com.example.examplemod;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModDataComponents {
    public static final DeferredRegister.DataComponents REGISTRAR = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, ExampleModNeoforge.MODID);

    // 2. Регистрируем компонент
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> ITEM_LEVEL = REGISTRAR.registerComponentType(
            "item_level",
            builder -> builder
                    .persistent(Codec.INT)
                    .networkSynchronized(ByteBufCodecs.INT)
    );
    public static void register(IEventBus bus){
        REGISTRAR.register(bus);
    }
}
