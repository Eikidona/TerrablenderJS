package com.forget_melody.terrablenderjs.terrablender.region;

import com.forget_melody.terrablenderjs.terrablender.BiomeRecorder;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate.ParameterPoint;
import terrablender.api.RegionType;
import terrablender.api.Region;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class CustomRegion extends Region {
	private Map<ResourceLocation, ParameterPoint> biomes;
	public CustomRegion(ResourceLocation name, RegionType type, int weight, Map<ResourceLocation, ParameterPoint> biomes) {
		super(name, type, weight);
		this.biomes = biomes;
	}
	
	@Override
	public void addBiomes(Registry<Biome> registry, Consumer<Pair<ParameterPoint, ResourceKey<Biome>>> mapper) {
		super.addBiomes(registry, mapper);
		
		this.biomes.forEach((id, parameter) ->{
			this.addBiome(mapper, parameter.temperature(), parameter.humidity(), parameter.continentalness(), parameter.erosion(), parameter.weirdness(), parameter.depth(), parameter.offset(), ResourceKey.create(Registries.BIOME, id));
		});
		
	}
	public static class CustomRegionBuilder{
		private ResourceLocation name;
		private int weight = 10;
		private RegionType regionType;
		private Map<ResourceLocation, ParameterPoint> biomes;
		
		public CustomRegionBuilder(ResourceLocation name, RegionType regionType) {
			this.name = name;
			this.regionType = regionType;
		}
		
		public CustomRegionBuilder SetWeight(int weight){
			this.weight = weight;
			return this;
		}
		
		public void addBiome(ResourceLocation name, ParameterPoint parameterPoint){
			this.biomes.put(name, parameterPoint);
		}
		
		public static CustomRegion build(CustomRegionBuilder builder){
			return new CustomRegion(builder.name, builder.regionType, builder.weight, builder.biomes);
		}
	}
}