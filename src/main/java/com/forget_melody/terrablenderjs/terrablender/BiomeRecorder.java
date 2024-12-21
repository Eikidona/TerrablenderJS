package com.forget_melody.terrablenderjs.terrablender;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Climate.Parameter;
import java.lang.reflect.Array;

public class BiomeRecorder {
	private ResourceLocation name;
	private Parameter temperature;
	private Parameter humidity;
	private Parameter continentalness;
	private Parameter erosion;
	private Parameter depth;
	private Parameter weirdness;
	
	public BiomeRecorder(ResourceLocation name, Parameter temperature, Parameter humidity, Parameter continentalness, Parameter erosion, Parameter depth, Parameter weirdness) {
		this.name = name;
		this.temperature = temperature;
		this.humidity = humidity;
		this.continentalness = continentalness;
		this.erosion = erosion;
		this.depth = depth;
		this.weirdness = weirdness;
	}
	
	public ResourceLocation getName() {
		return name;
	}
	
	public Parameter getTemperature() {
		return temperature;
	}
	
	public Parameter getHumidity() {
		return humidity;
	}
	
	public Parameter getContinentalness() {
		return continentalness;
	}
	
	public Parameter getErosion() {
		return erosion;
	}
	
	public Parameter getDepth() {
		return depth;
	}
	
	public Parameter getWeirdness() {
		return weirdness;
	}
}
