package com.forget_melody.terrablenderjs;

import com.forget_melody.terrablenderjs.kubejs.event.KubeJSEventFactory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;

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
//            List<Pair<ParameterUtils.ParameterPointListBuilder, ResourceKey<Biome>>>list = new ArrayList<>();
//
//            ParameterUtils.ParameterPointListBuilder builder1 = new ParameterUtils.ParameterPointListBuilder()
//                                                                        .continentalness(ParameterUtils.Continentalness.COAST)
//                                                                        .temperature(ParameterUtils.Temperature.FULL_RANGE)
//                                                                        .humidity(ParameterUtils.Humidity.FULL_RANGE)
//                                                                        .weirdness(ParameterUtils.Weirdness.LOW_SLICE_NORMAL_DESCENDING, ParameterUtils.Weirdness.LOW_SLICE_VARIANT_ASCENDING)
//                                                                        .depth(ParameterUtils.Depth.SURFACE)
//                                                                        .erosion(ParameterUtils.Erosion.FULL_RANGE);
//
//            ResourceKey<Biome> biome1 = ResourceKey.register(Registries.BIOME, new ResourceLocation("wythers:coastal_mangroves"));
//
//            ParameterUtils.ParameterPointListBuilder builder2 = new ParameterUtils.ParameterPointListBuilder()
//                                                                        .continentalness(ParameterUtils.Continentalness.INLAND)
//                                                                        .temperature(ParameterUtils.Temperature.FULL_RANGE)
//                                                                        .humidity(ParameterUtils.Humidity.FULL_RANGE)
//                                                                        .weirdness(ParameterUtils.Weirdness.LOW_SLICE_NORMAL_DESCENDING, ParameterUtils.Weirdness.LOW_SLICE_VARIANT_ASCENDING)
//                                                                        .depth(ParameterUtils.Depth.SURFACE)
//                                                                        .erosion(ParameterUtils.Erosion.FULL_RANGE);
//
//            ResourceKey<Biome> biome2 = ResourceKey.register(Registries.BIOME, new ResourceLocation("wythers:fen"));
//
//            list.add(new Pair<>(builder1, biome1));
//            list.add(new Pair<>(builder2, biome2));
//
//            Regions.register(new VanillaOverworldRegion(new ResourceLocation(ID, "overworld"), 10, list));
            
            KubeJSEventFactory.registerRegions().forEach(region -> Regions.register(region));
            KubeJSEventFactory.registerSurfaceRules().forEach(builder -> SurfaceRuleManager.addSurfaceRules(builder.category(), builder.name(), builder.ruleSource()));
        });
    }
}
