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

public class GuardianGolemModel extends EntityModel<GuardianGolem> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(ExampleModNeoforge.MODID, "guardian_golem"),
            "main"
    );

    private final ModelPart root;

    public GuardianGolemModel(ModelPart root) {
        this.root = root;
    }

    public static LayerDefinition createBodyLayer(){
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        partDefinition.addOrReplaceChild("body",
                CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshDefinition, 32, 32);
    }
    @Override
    public void setupAnim(GuardianGolem guardianGolem, float v, float v1, float v2, float v3, float v4) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int i1, int i2) {
        root.render(poseStack, vertexConsumer, i, i1);
    }
}
