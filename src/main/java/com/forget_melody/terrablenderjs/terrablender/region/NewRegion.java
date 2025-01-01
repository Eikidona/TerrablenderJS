package com.forget_melody.terrablenderjs.terrablender.region;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate.*;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import terrablender.api.ParameterUtils.*;
import terrablender.api.Region;
import terrablender.api.RegionType;
import terrablender.api.VanillaParameterOverlayBuilder;

import java.util.List;
import java.util.function.Consumer;

public class NewRegion extends Region {
	private final OverworldBiomeBuilder BIOME_BUILDER = new OverworldBiomeBuilder();
	
	public NewRegion() {
		super(new ResourceLocation("test"), RegionType.OVERWORLD, 10);
	}
	
	@Override
	public void addBiomes(Registry<Biome> registry, Consumer<Pair<ParameterPoint, ResourceKey<Biome>>> mapper) {
		
		VanillaParameterOverlayBuilder overlayBuilder = new VanillaParameterOverlayBuilder();
		
		ResourceKey<Biome> coastal_mangroves = ResourceKey.create(Registries.BIOME, new ResourceLocation("wythers:coastal_mangroves"));
		new ParameterPointListBuilder()
				.temperature(Temperature.FULL_RANGE)
				.humidity(Humidity.FULL_RANGE)
				.continentalness(Continentalness.COAST)
				.erosion(Erosion.FULL_RANGE)
				.weirdness(Weirdness.FULL_RANGE)
				.depth(Depth.SURFACE, Depth.FLOOR)
				.build().forEach(parameterPoint -> overlayBuilder.add(parameterPoint, coastal_mangroves));
		
		ResourceKey<Biome> fen = ResourceKey.create(Registries.BIOME, new ResourceLocation("wythers:fen"));
		new ParameterPointListBuilder()
				.temperature(Temperature.FULL_RANGE)
				.humidity(Humidity.FULL_RANGE)
				.continentalness(Continentalness.FULL_RANGE)
				.erosion(Erosion.FULL_RANGE)
				.weirdness(Weirdness.FULL_RANGE)
				.depth(Depth.FULL_RANGE)
				.build().forEach(parameterPoint -> overlayBuilder.add(parameterPoint, DEFERRED_PLACEHOLDER));
		
		overlayBuilder.build().forEach(mapper);
		
//		addModifiedVanillaOverworldBiomes(mapper, modified -> {
//			pointList.forEach(point -> {
//				modified.replaceBiome(point, biome);
//			});
//		});
	}
}
