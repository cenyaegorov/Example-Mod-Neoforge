package com.example.examplemod.entity.client;

import com.example.examplemod.ExampleModNeoforge;
import com.example.examplemod.entity.Bus;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BusModel extends GeoModel<Bus> {
    @Override
    public ResourceLocation getModelResource(Bus animatable) {
        return ResourceLocation.fromNamespaceAndPath(ExampleModNeoforge.MODID, "geo/bus.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Bus animatable) {
        return ResourceLocation.fromNamespaceAndPath(ExampleModNeoforge.MODID, "textures/entity/bus.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Bus animatable) {
        return null;
    }
}
