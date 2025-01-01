package com.forget_melody.terrablenderjs;

import com.forget_melody.terrablenderjs.kubejs.event.KubeJSEventFactory;
import com.forget_melody.terrablenderjs.terrablender.region.NewRegion;
import com.forget_melody.terrablenderjs.terrablender.region.OverworldRegion;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import dev.architectury.event.events.client.ClientSystemMessageEvent;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import terrablender.api.ParameterUtils;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
	
	public void onSetup(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			OverworldRegion overworldRegion = new OverworldRegion(new ResourceLocation(ID, "overworld"), 10);
			overworldRegion.addBeachBiome(new ResourceLocation("wythers:coastal_mangroves"), ParameterUtils.Temperature.WARM, ParameterUtils.Humidity.WET, false);
			overworldRegion.addBeachBiome(new ResourceLocation("wythers:coastal_mangroves"), ParameterUtils.Temperature.WARM, ParameterUtils.Humidity.WET, true);
//            Regions.register(overworldRegion);
//
//            List<Pair<List<Climate.ParameterPoint>, ResourceKey<Biome>>> list = new ArrayList<>();
//
//            ParameterUtils.ParameterPointListBuilder builder1 = new ParameterUtils.ParameterPointListBuilder()
//                                                                        .continentalness(ParameterUtils.Continentalness.COAST)
//                                                                        .temperature(ParameterUtils.Temperature.WARM)
//                                                                        .humidity(ParameterUtils.Humidity.WET)
//                                                                        .weirdness(ParameterUtils.Weirdness.LOW_SLICE_NORMAL_DESCENDING, ParameterUtils.Weirdness.LOW_SLICE_VARIANT_ASCENDING)
//                                                                        .depth(ParameterUtils.Depth.SURFACE)
//                                                                        .erosion(ParameterUtils.Erosion.FULL_RANGE);
//
//            ResourceKey<Biome> biome1 = ResourceKey.create(Registries.BIOME, new ResourceLocation("wythers:coastal_mangroves"));
//
//            ParameterUtils.ParameterPointListBuilder builder3 = new ParameterUtils.ParameterPointListBuilder()
//                                                                        .continentalness(ParameterUtils.Continentalness.COAST)
//                                                                        .temperature(ParameterUtils.Temperature.WARM)
//                                                                        .humidity(ParameterUtils.Humidity.WET)
//                                                                        .weirdness(ParameterUtils.Weirdness.MID_SLICE_VARIANT_ASCENDING, ParameterUtils.Weirdness.MID_SLICE_VARIANT_DESCENDING, ParameterUtils.Weirdness.MID_SLICE_NORMAL_DESCENDING, ParameterUtils.Weirdness.MID_SLICE_NORMAL_ASCENDING)
//                                                                        .depth(ParameterUtils.Depth.SURFACE)
//                                                                        .erosion(ParameterUtils.Erosion.FULL_RANGE);
//
//            ResourceKey<Biome> biome3 = ResourceKey.create(Registries.BIOME, new ResourceLocation("wythers:coastal_mangroves"));
//
//            ParameterUtils.ParameterPointListBuilder builder2 = new ParameterUtils.ParameterPointListBuilder()
//                                                                        .continentalness(ParameterUtils.Continentalness.INLAND)
//                                                                        .temperature(ParameterUtils.Temperature.FULL_RANGE)
//                                                                        .humidity(ParameterUtils.Humidity.FULL_RANGE)
//                                                                        .weirdness(ParameterUtils.Weirdness.LOW_SLICE_NORMAL_DESCENDING, ParameterUtils.Weirdness.LOW_SLICE_VARIANT_ASCENDING)
//                                                                        .depth(ParameterUtils.Depth.SURFACE)
//                                                                        .erosion(ParameterUtils.Erosion.FULL_RANGE);
//
//            ResourceKey<Biome> biome2 = ResourceKey.create(Registries.BIOME, new ResourceLocation("wythers:fen"));
//
//            list.add(new Pair<>(builder1.build(), biome1));
//            list.add(new Pair<>(builder2.build(), biome2));
//            list.add(new Pair<>(builder3.build(), biome3));
			
			Regions.register(new NewRegion());
			
			KubeJSEventFactory.registerRegions().forEach(region -> Regions.register(region));
			KubeJSEventFactory.registerSurfaceRules().forEach(builder -> SurfaceRuleManager.addSurfaceRules(builder.category(), builder.name(), builder.ruleSource()));
		});
	}
}
