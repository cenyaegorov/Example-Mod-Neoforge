package com.example.examplemod.entity;

import com.example.examplemod.ExampleModNeoforge;
import com.example.examplemod.entity.bus.Bus;
import com.example.examplemod.entity.bus.BusState;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegisterEvent;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, ExampleModNeoforge.MODID);
    public static final EntityDataSerializer<BusState> BUS_STATE_ENTITY_DATA_SERIALIZER = EntityDataSerializer.forValueType(
            ByteBufCodecs.STRING_UTF8.map(BusState::byName, BusState::getSerializedName)
    );

    public static final DeferredHolder<EntityType<?>, EntityType<GuardianGolem>> GUARDIAN_GOLEM =
            ENTITY_TYPES.register("guardian_golem",
                    () -> EntityType.Builder.of(GuardianGolem::new, MobCategory.MONSTER)
                            .sized(5f, 3f)
                            .clientTrackingRange(10)
                            .build("guardian_golem"));
    public static final DeferredHolder<EntityType<?>, EntityType<Bus>> BUS =
            ENTITY_TYPES.register("bus",
                    () -> EntityType.Builder.of(Bus::new, MobCategory.MONSTER)
                            .sized(2f, 1f)
                            .clientTrackingRange(10)
                            .build("bus"));

    public static void register(IEventBus bus){
        ENTITY_TYPES.register(bus);
    }
    public static void registerSerializers(RegisterEvent event){
        event.register(NeoForgeRegistries.Keys.ENTITY_DATA_SERIALIZERS, helper -> {
            helper.register(ResourceLocation.fromNamespaceAndPath(ExampleModNeoforge.MODID, "bus_state"), BUS_STATE_ENTITY_DATA_SERIALIZER);
        });
    }
}
