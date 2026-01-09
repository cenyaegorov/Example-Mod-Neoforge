package com.example.examplemod;

import com.example.examplemod.entity.GuardianGolem;
import com.example.examplemod.entity.ModEntities;
import com.example.examplemod.entity.client.GuardianGolemModel;
import com.example.examplemod.entity.client.GuardianGolemRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.PigRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = ExampleModNeoforge.MODID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = ExampleModNeoforge.MODID, value = Dist.CLIENT)
public class ExampleModNeoforgeClient {
    public ExampleModNeoforgeClient(ModContainer container) {
        // Allows NeoForge to create a config screen for this mod's configs.
        // The config screen is accessed by going to the Mods screen > clicking on your mod > clicking on config.
        // Do not forget to add translations for your config options to the en_us.json file.
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        // Some client setup code
        EntityRenderers.register(ModEntities.GUARDIAN_GOLEM.get(), GuardianGolemRenderer::new);
        ExampleModNeoforge.LOGGER.info("HELLO FROM CLIENT SETUP");
        ExampleModNeoforge.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }
    @SubscribeEvent
    public static void onRegisterLayer(EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(GuardianGolemModel.LAYER_LOCATION, GuardianGolemModel::createBodyLayer);
    }
}
