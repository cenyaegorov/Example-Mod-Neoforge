package com.example.examplemod.entity.client;

import com.example.examplemod.ExampleModNeoforge;
import com.example.examplemod.entity.GuardianGolem;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class GuardianGolemRenderer extends GeoEntityRenderer<GuardianGolem> {

    public GuardianGolemRenderer(EntityRendererProvider.Context context, EntityType<? extends GuardianGolem> entityType) {
        super(context, entityType);
    }
    public GuardianGolemRenderer(EntityRendererProvider.Context context){
        super(context, new GuardianGolemModel());

        this.shadowRadius = 0.5f;
    }
}
