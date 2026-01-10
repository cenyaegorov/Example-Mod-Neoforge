package com.example.examplemod.entity.client;

import com.example.examplemod.entity.Bus;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BusRenderer extends GeoEntityRenderer<Bus> {
    public BusRenderer(EntityRendererProvider.Context renderManager, GeoModel<Bus> model) {
        super(renderManager, model);

    }
    public BusRenderer(EntityRendererProvider.Context context){
        super(context, new BusModel());

        this.shadowRadius = 0.5f;
        this.withScale(0.3f);
    }
}
