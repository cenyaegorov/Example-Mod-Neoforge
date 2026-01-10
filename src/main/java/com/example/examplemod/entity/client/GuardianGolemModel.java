package com.example.examplemod.entity.client;

import com.example.examplemod.ExampleModNeoforge;
import com.example.examplemod.entity.GuardianGolem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class GuardianGolemModel extends GeoModel<GuardianGolem> {

    @Override
    public ResourceLocation getModelResource(GuardianGolem animatable) {
        return ResourceLocation.fromNamespaceAndPath(ExampleModNeoforge.MODID, "geo/guardian_golem.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GuardianGolem animatable) {
        return ResourceLocation.fromNamespaceAndPath(ExampleModNeoforge.MODID, "textures/entity/guardian_golem.png");
    }

    @Override
    public ResourceLocation getAnimationResource(GuardianGolem animatable) {
        return null;
    }
}
