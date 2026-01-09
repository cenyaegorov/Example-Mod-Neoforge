package com.example.examplemod.entity.client;

import com.example.examplemod.ExampleModNeoforge;
import com.example.examplemod.entity.GuardianGolem;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class GuardianGolemRenderer extends MobRenderer<GuardianGolem, GuardianGolemModel> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(ExampleModNeoforge.MODID, "textures/entity/guardian_golem.png");

    public GuardianGolemRenderer(EntityRendererProvider.Context context){
        super(context, new GuardianGolemModel(context.bakeLayer(GuardianGolemModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(GuardianGolem guardianGolem) {
        return TEXTURE;
    }
}
