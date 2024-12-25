package com.forget_melody.terrablenderjs.terrablender.region;

import com.forget_melody.terrablenderjs.kubejs.wrapper.ParameterPointListBuilderJS;
import com.forget_melody.terrablenderjs.kubejs.wrapper.ParameterPointListBuilderJS.*;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate.*;
import terrablender.api.ParameterUtils.*;
import terrablender.api.RegionType;
import terrablender.api.Region;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class OverworldRegion extends Region {
	private List<Pair<List<ParameterPoint>, ResourceKey<Biome>>> list = new ArrayList<>();
	
	public OverworldRegion(ResourceLocation name, int weight, List<Pair<List<ParameterPoint>, ResourceKey<Biome>>> list) {
		super(name, RegionType.OVERWORLD, weight);
		this.list = list;
	}
	
	public OverworldRegion(ResourceLocation name, int weight) {
		super(name, RegionType.OVERWORLD, weight);
	}
	
	@Override
	public void addBiomes(Registry<Biome> registry, Consumer<Pair<ParameterPoint, ResourceKey<Biome>>> mapper) {
		this.list.forEach(pointListAndBiome -> {
			pointListAndBiome.getFirst().forEach(point -> mapper.accept(new Pair<>(point, pointListAndBiome.getSecond())));
		});
	}
	
	public OverworldRegion addBiome(ResourceLocation name, Consumer<ParameterPointListBuilder> consumer){
		ResourceKey<Biome> biome = this.createBiomeKey(name);
		ParameterPointListBuilder builder = new ParameterPointListBuilder();
		consumer.accept(builder);
		List<ParameterPoint> pointList = builder.build();
		this.list.add(new Pair<>(pointList, biome));
		return this;
	}
	
	public OverworldRegion addMushroomFields(ResourceLocation name){
		ParameterPointListBuilder builder = new ParameterPointListBuilder();
		builder.continentalness(Continentalness.MUSHROOM_FIELDS)
				.temperature(Temperature.FULL_RANGE)
				.humidity(Humidity.FULL_RANGE)
				.erosion(Erosion.FULL_RANGE)
				.weirdness(Weirdness.FULL_RANGE)
				.depth(Depth.SURFACE);
		this.list.add(new Pair<>(builder.build(), this.createBiomeKey(name)));
		return this;
	}
	
	public OverworldRegion addDeepOcean(ResourceLocation name, Temperature temperature){
		ParameterPointListBuilder builder = new ParameterPointListBuilder();
		builder.continentalness(Continentalness.DEEP_OCEAN)
			   .temperature(temperature)
			   .humidity(Humidity.FULL_RANGE)
			   .erosion(Erosion.FULL_RANGE)
			   .weirdness(Weirdness.FULL_RANGE)
			   .depth(Depth.SURFACE);
		this.list.add(new Pair<>(builder.build(), this.createBiomeKey(name)));
		return this;
	}
	
	public OverworldRegion addOcean(ResourceLocation name, Temperature temperature){
		ParameterPointListBuilder builder = new ParameterPointListBuilder();
		builder.continentalness(Continentalness.OCEAN)
			   .temperature(temperature)
			   .humidity(Humidity.FULL_RANGE)
			   .erosion(Erosion.FULL_RANGE)
			   .weirdness(Weirdness.FULL_RANGE)
			   .depth(Depth.SURFACE);
		this.list.add(new Pair<>(builder.build(), this.createBiomeKey(name)));
		return this;
	}
	
	public OverworldRegion addRiver(ResourceLocation name, Temperature temperature){
		ParameterPointListBuilder coast = new ParameterPointListBuilder();
		coast.continentalness(Continentalness.COAST)
			   .temperature(temperature)
			   .humidity(Humidity.FULL_RANGE)
			   .erosion(Erosion.FULL_RANGE)
			   .weirdness(Weirdness.VALLEY)
			   .depth(Depth.SURFACE);
		this.list.add(new Pair<>(coast.build(), this.createBiomeKey(name)));
		
		boolean isFrozen = temperature == Temperature.ICY ||temperature == Temperature.FROZEN;
		
		if(isFrozen){
			ParameterPointListBuilder nearInland = new ParameterPointListBuilder();
			nearInland.continentalness(Continentalness.NEAR_INLAND)
					  .temperature(temperature)
					  .humidity(Humidity.FULL_RANGE)
					  .erosion(Erosion.EROSION_0, Erosion.EROSION_1, Erosion.EROSION_2, Erosion.EROSION_3, Erosion.EROSION_4, Erosion.EROSION_5, Erosion.EROSION_6)
					  .weirdness(Weirdness.VALLEY)
					  .depth(Depth.SURFACE);
			this.list.add(new Pair<>(nearInland.build(), this.createBiomeKey(name)));
		}else {
			ParameterPointListBuilder nearInland = new ParameterPointListBuilder();
			nearInland.continentalness(Continentalness.NEAR_INLAND)
					  .temperature(temperature)
					  .humidity(Humidity.FULL_RANGE)
					  .erosion(Erosion.EROSION_0, Erosion.EROSION_1, Erosion.EROSION_2, Erosion.EROSION_3, Erosion.EROSION_4, Erosion.EROSION_5)
					  .weirdness(Weirdness.VALLEY)
					  .depth(Depth.SURFACE);
			this.list.add(new Pair<>(nearInland.build(), this.createBiomeKey(name)));
		}
		
		if(isFrozen){
			ParameterPointListBuilder midInland = new ParameterPointListBuilder();
			midInland.continentalness(Continentalness.MID_INLAND)
					 .temperature(temperature)
					 .humidity(Humidity.FULL_RANGE)
					 .erosion(Erosion.EROSION_2, Erosion.EROSION_3, Erosion.EROSION_4, Erosion.EROSION_5, Erosion.EROSION_6)
					 .weirdness(Weirdness.VALLEY)
					 .depth(Depth.SURFACE);
			this.list.add(new Pair<>(midInland.build(), this.createBiomeKey(name)));
		}else {
			ParameterPointListBuilder midInland = new ParameterPointListBuilder();
			midInland.continentalness(Continentalness.MID_INLAND)
					 .temperature(temperature)
					 .humidity(Humidity.FULL_RANGE)
					 .erosion(Erosion.EROSION_2, Erosion.EROSION_3, Erosion.EROSION_4, Erosion.EROSION_5)
					 .weirdness(Weirdness.VALLEY)
					 .depth(Depth.SURFACE);
			this.list.add(new Pair<>(midInland.build(), this.createBiomeKey(name)));
		}
		return this;
	}
	
	public OverworldRegion addSwamp(ResourceLocation name, Temperature temperature, boolean isVariant){
		ParameterPointListBuilder nearInlandValley = new ParameterPointListBuilder();
		nearInlandValley.continentalness(Continentalness.NEAR_INLAND)
				 .temperature(temperature)
				 .humidity(Humidity.FULL_RANGE)
				 .erosion(Erosion.EROSION_6)
				 .weirdness(Weirdness.VALLEY)
				 .depth(Depth.SURFACE);
		this.list.add(new Pair<>(nearInlandValley.build(), this.createBiomeKey(name)));
		
		if(isVariant){
			// 生物群系变体
			
			// 准内陆低地带
			ParameterPointListBuilder nearInlandLow = new ParameterPointListBuilder();
			nearInlandLow.continentalness(Continentalness.NEAR_INLAND)
						 .temperature(temperature)
						 .humidity(Humidity.FULL_RANGE)
						 .erosion(Erosion.EROSION_6)
						 .weirdness(Weirdness.LOW_SLICE_VARIANT_ASCENDING)
						 .depth(Depth.SURFACE);
			this.list.add(new Pair<>(nearInlandLow.build(), this.createBiomeKey(name)));
			// 准内陆低山带
			ParameterPointListBuilder nearInlandMid = new ParameterPointListBuilder();
			nearInlandMid.continentalness(Continentalness.NEAR_INLAND)
						 .temperature(temperature)
						 .humidity(Humidity.FULL_RANGE)
						 .erosion(Erosion.EROSION_6)
						 .weirdness(Weirdness.MID_SLICE_VARIANT_ASCENDING, Weirdness.MID_SLICE_VARIANT_DESCENDING)
						 .depth(Depth.SURFACE);
			this.list.add(new Pair<>(nearInlandMid.build(), this.createBiomeKey(name)));
			// 内陆低地带
			ParameterPointListBuilder midInlandLow = new ParameterPointListBuilder();
			midInlandLow.continentalness(Continentalness.MID_INLAND)
						 .temperature(temperature)
						 .humidity(Humidity.FULL_RANGE)
						 .erosion(Erosion.EROSION_6)
						 .weirdness(Weirdness.LOW_SLICE_VARIANT_ASCENDING)
						 .depth(Depth.SURFACE);
			this.list.add(new Pair<>(midInlandLow.build(), this.createBiomeKey(name)));
			// 内陆低山带
			ParameterPointListBuilder midInlandMid = new ParameterPointListBuilder();
			midInlandMid.continentalness(Continentalness.MID_INLAND)
						 .temperature(temperature)
						 .humidity(Humidity.FULL_RANGE)
						 .erosion(Erosion.EROSION_6)
						 .weirdness(Weirdness.MID_SLICE_VARIANT_ASCENDING, Weirdness.MID_SLICE_VARIANT_DESCENDING)
						 .depth(Depth.SURFACE);
			this.list.add(new Pair<>(midInlandMid.build(), this.createBiomeKey(name)));
			// 深内陆低地带
			ParameterPointListBuilder farInlandLow = new ParameterPointListBuilder();
			farInlandLow.continentalness(Continentalness.FAR_INLAND)
						.temperature(temperature)
						.humidity(Humidity.FULL_RANGE)
						.erosion(Erosion.EROSION_6)
						.weirdness(Weirdness.LOW_SLICE_VARIANT_ASCENDING)
						.depth(Depth.SURFACE);
			this.list.add(new Pair<>(farInlandLow.build(), this.createBiomeKey(name)));
			// 深内陆低山带
			ParameterPointListBuilder farInlandMid = new ParameterPointListBuilder();
			farInlandMid.continentalness(Continentalness.MID_INLAND)
						.temperature(temperature)
						.humidity(Humidity.FULL_RANGE)
						.erosion(Erosion.EROSION_6)
						.weirdness(Weirdness.MID_SLICE_VARIANT_ASCENDING, Weirdness.MID_SLICE_VARIANT_DESCENDING)
						.depth(Depth.SURFACE);
			this.list.add(new Pair<>(farInlandMid.build(), this.createBiomeKey(name)));
		}else {
			// 并非变体
			
			// 准内陆低地带
			ParameterPointListBuilder nearInlandLow = new ParameterPointListBuilder();
			nearInlandLow.continentalness(Continentalness.NEAR_INLAND)
						 .temperature(temperature)
						 .humidity(Humidity.FULL_RANGE)
						 .erosion(Erosion.EROSION_6)
						 .weirdness(Weirdness.LOW_SLICE_NORMAL_DESCENDING)
						 .depth(Depth.SURFACE);
			this.list.add(new Pair<>(nearInlandLow.build(), this.createBiomeKey(name)));
			// 准内陆低山带
			ParameterPointListBuilder nearInlandMid = new ParameterPointListBuilder();
			nearInlandMid.continentalness(Continentalness.NEAR_INLAND)
						 .temperature(temperature)
						 .humidity(Humidity.FULL_RANGE)
						 .erosion(Erosion.EROSION_6)
						 .weirdness(Weirdness.MID_SLICE_NORMAL_ASCENDING, Weirdness.MID_SLICE_NORMAL_DESCENDING)
						 .depth(Depth.SURFACE);
			this.list.add(new Pair<>(nearInlandMid.build(), this.createBiomeKey(name)));
			// 内陆低地带
			ParameterPointListBuilder midInlandLow = new ParameterPointListBuilder();
			midInlandLow.continentalness(Continentalness.MID_INLAND)
						 .temperature(temperature)
						 .humidity(Humidity.FULL_RANGE)
						 .erosion(Erosion.EROSION_6)
						 .weirdness(Weirdness.LOW_SLICE_NORMAL_DESCENDING)
						 .depth(Depth.SURFACE);
			this.list.add(new Pair<>(midInlandLow.build(), this.createBiomeKey(name)));
			// 内陆低山带
			ParameterPointListBuilder midInlandMid = new ParameterPointListBuilder();
			midInlandMid.continentalness(Continentalness.MID_INLAND)
						 .temperature(temperature)
						 .humidity(Humidity.FULL_RANGE)
						 .erosion(Erosion.EROSION_6)
						 .weirdness(Weirdness.MID_SLICE_NORMAL_ASCENDING, Weirdness.MID_SLICE_NORMAL_DESCENDING)
						 .depth(Depth.SURFACE);
			this.list.add(new Pair<>(midInlandMid.build(), this.createBiomeKey(name)));
			// 深内陆低地带
			ParameterPointListBuilder farInlandLow = new ParameterPointListBuilder();
			farInlandLow.continentalness(Continentalness.FAR_INLAND)
						.temperature(temperature)
						.humidity(Humidity.FULL_RANGE)
						.erosion(Erosion.EROSION_6)
						.weirdness(Weirdness.LOW_SLICE_NORMAL_DESCENDING)
						.depth(Depth.SURFACE);
			this.list.add(new Pair<>(farInlandLow.build(), this.createBiomeKey(name)));
			// 深内陆低山带
			ParameterPointListBuilder farInlandMid = new ParameterPointListBuilder();
			farInlandMid.continentalness(Continentalness.FAR_INLAND)
						.temperature(temperature)
						.humidity(Humidity.FULL_RANGE)
						.erosion(Erosion.EROSION_6)
						.weirdness(Weirdness.MID_SLICE_NORMAL_ASCENDING, Weirdness.MID_SLICE_NORMAL_DESCENDING)
						.depth(Depth.SURFACE);
			this.list.add(new Pair<>(farInlandMid.build(), this.createBiomeKey(name)));
		}
		return this;
	}
	
	private ResourceKey<Biome> createBiomeKey(ResourceLocation name){
		return ResourceKey.create(Registries.BIOME, name);
	}
	
	public static class OverworldRegionJS {
		private final ResourceLocation name;
		private final int weight;
		private final List<Pair<List<ParameterPoint>, ResourceKey<Biome>>> list = new ArrayList<>();
		
		public OverworldRegionJS(ResourceLocation name) {
			this.name = name;
			this.weight = 10;
		}
		public OverworldRegionJS(ResourceLocation name, int weight) {
			this.name = name;
			this.weight = weight;
		}
		
		/**
		 * 向Region添加生物群系
		 * @param biome
		 * @param consumer
		 * @return
		 */
		public OverworldRegionJS addBiome(ResourceLocation biome, Consumer<VanillaParameterPointListBuilder> consumer) {
			VanillaParameterPointListBuilder builder = new ParameterPointListBuilderJS.VanillaParameterPointListBuilder();
			consumer.accept(builder);
			this.list.add(new Pair<>(builder.build(), ResourceKey.create(Registries.BIOME, biome)));
			return this;
		}
		
		public OverworldRegion build() {
			return new OverworldRegion(this.name, this.weight);
		}
	}
}