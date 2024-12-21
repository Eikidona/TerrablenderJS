package com.forget_melody.terrablenderjs;

import com.forget_melody.terrablenderjs.kubejs.eventhandler.TerraBlenderEventHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import terrablender.api.Regions;

@Mod(TerraBlenderJS.ID)
public class TerraBlenderJS {

    // You really don't need any of the mumbo-jumbo found here in other mods. Just the ID and Logger.
    public static final String ID = "terrablenderjs";
    public static final Logger LOGGER = LogManager.getLogger();
    
    public TerraBlenderJS() {
        FMLJavaModLoadingContext ctx = FMLJavaModLoadingContext.get();
        IEventBus bus = ctx.getModEventBus();
        bus.addListener(this::onSetup);
    }
    
    public void onSetup(FMLCommonSetupEvent event){
        event.enqueueWork(() ->{
            TerraBlenderEventHandler.registerRegions().forEach(Regions::register);
        });
    }
}
