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

import java.beans.beancontext.BeanContextServiceRevokedListener;
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
	
	public OverworldRegion addBiome(ResourceLocation name, Consumer<ParameterPointListBuilder> consumer) {
		ResourceKey<Biome> biome = this.createBiomeKey(name);
		ParameterPointListBuilder builder = new ParameterPointListBuilder();
		consumer.accept(builder);
		List<ParameterPoint> pointList = builder.build();
		this.list.add(new Pair<>(pointList, biome));
		return this;
	}
	
	private boolean isValley(Weirdness weirdness) {
		return weirdness == Weirdness.VALLEY;
	}
	
	private boolean isLow(Weirdness weirdness) {
		return this.isLowNormal(weirdness) || this.isLowVariant(weirdness);
	}
	
	private boolean isLowNormal(Weirdness weirdness) {
		return weirdness == Weirdness.LOW_SLICE_NORMAL_DESCENDING;
	}
	
	private boolean isLowVariant(Weirdness weirdness) {
		return weirdness == Weirdness.LOW_SLICE_VARIANT_ASCENDING;
	}
	
	private boolean isMid(Weirdness weirdness) {
		return this.isMidNormal(weirdness) || this.isMidVariant(weirdness);
	}
	
	private boolean isMidNormal(Weirdness weirdness) {
		return weirdness == Weirdness.MID_SLICE_NORMAL_ASCENDING || weirdness == Weirdness.MID_SLICE_NORMAL_DESCENDING;
	}
	
	private boolean isMidVariant(Weirdness weirdness) {
		return weirdness == Weirdness.MID_SLICE_VARIANT_ASCENDING || weirdness == Weirdness.MID_SLICE_VARIANT_DESCENDING;
	}
	
	private boolean isHigh(Weirdness weirdness) {
		return this.isHighNormal(weirdness) || isHighVariant(weirdness);
	}
	
	private boolean isHighNormal(Weirdness weirdness) {
		return weirdness == Weirdness.HIGH_SLICE_NORMAL_ASCENDING || weirdness == Weirdness.HIGH_SLICE_NORMAL_DESCENDING;
	}
	
	private boolean isHighVariant(Weirdness weirdness) {
		return weirdness == Weirdness.HIGH_SLICE_VARIANT_ASCENDING || weirdness == Weirdness.HIGH_SLICE_VARIANT_DESCENDING;
	}
	
	private boolean isPeak(Weirdness weirdness) {
		return this.isPeakNormal(weirdness) || this.isPeakVariant(weirdness);
	}
	
	private boolean isPeakNormal(Weirdness weirdness) {
		return weirdness == Weirdness.PEAK_NORMAL;
	}
	
	private boolean isPeakVariant(Weirdness weirdness) {
		return weirdness == Weirdness.PEAK_VARIANT;
	}
	
	
	public OverworldRegion addMushroomFields(ResourceLocation name) {
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
	
	public OverworldRegion addDeepOcean(ResourceLocation name, Temperature temperature) {
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
	
	public OverworldRegion addOcean(ResourceLocation name, Temperature temperature) {
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
	
	private OverworldRegion addCoast(ResourceLocation name, Temperature temperature, Humidity humidity, Erosion erosion, Weirdness weirdness) {
		ParameterPointListBuilder builder = new ParameterPointListBuilder();
		builder.continentalness(Continentalness.COAST)
			   .temperature(temperature)
			   .humidity(humidity)
			   .erosion(erosion)
			   .weirdness(weirdness)
			   .depth(Depth.SURFACE);
		this.list.add(new Pair<>(builder.build(), this.createBiomeKey(name)));
		return this;
	}
	
	private OverworldRegion addNearInland(ResourceLocation name, Temperature temperature, Humidity humidity, Erosion erosion, Weirdness weirdness) {
		ParameterPointListBuilder builder = new ParameterPointListBuilder();
		builder.continentalness(Continentalness.NEAR_INLAND)
			   .temperature(temperature)
			   .humidity(humidity)
			   .erosion(erosion)
			   .weirdness(weirdness)
			   .depth(Depth.SURFACE);
		this.list.add(new Pair<>(builder.build(), this.createBiomeKey(name)));
		return this;
	}
	
	private OverworldRegion addMidInland(ResourceLocation name, Temperature temperature, Humidity humidity, Erosion erosion, Weirdness weirdness) {
		ParameterPointListBuilder builder = new ParameterPointListBuilder();
		builder.continentalness(Continentalness.MID_INLAND)
			   .temperature(temperature)
			   .humidity(humidity)
			   .erosion(erosion)
			   .weirdness(weirdness)
			   .depth(Depth.SURFACE);
		this.list.add(new Pair<>(builder.build(), this.createBiomeKey(name)));
		return this;
	}
	
	private OverworldRegion addFarInland(ResourceLocation name, Temperature temperature, Humidity humidity, Erosion erosion, Weirdness weirdness) {
		ParameterPointListBuilder builder = new ParameterPointListBuilder();
		builder.continentalness(Continentalness.FAR_INLAND)
			   .temperature(temperature)
			   .humidity(humidity)
			   .erosion(erosion)
			   .weirdness(weirdness)
			   .depth(Depth.SURFACE);
		this.list.add(new Pair<>(builder.build(), this.createBiomeKey(name)));
		return this;
	}
	
	private OverworldRegion addValley(ResourceLocation name, Continentalness continentalness, Temperature temperature, Humidity humidity, Erosion erosion) {
		ParameterPointListBuilder builder = new ParameterPointListBuilder();
		builder.continentalness(continentalness)
			   .temperature(temperature)
			   .humidity(humidity)
			   .erosion(erosion)
			   .weirdness(Weirdness.VALLEY)
			   .depth(Depth.SURFACE);
		this.list.add(new Pair<>(builder.build(), this.createBiomeKey(name)));
		return this;
	}
	
	private OverworldRegion addLow(ResourceLocation name, Continentalness continentalness, Temperature temperature, Humidity humidity, Erosion erosion) {
		ParameterPointListBuilder builder = new ParameterPointListBuilder();
		builder.continentalness(continentalness)
			   .temperature(temperature)
			   .humidity(humidity)
			   .erosion(erosion)
			   .weirdness(Weirdness.LOW_SLICE_VARIANT_ASCENDING, Weirdness.LOW_SLICE_NORMAL_DESCENDING)
			   .depth(Depth.SURFACE);
		this.list.add(new Pair<>(builder.build(), this.createBiomeKey(name)));
		return this;
	}
	
	private OverworldRegion addMid(ResourceLocation name, Continentalness continentalness, Temperature temperature, Humidity humidity, Erosion erosion) {
		ParameterPointListBuilder builder = new ParameterPointListBuilder();
		builder.continentalness(continentalness)
			   .temperature(temperature)
			   .humidity(humidity)
			   .erosion(erosion)
			   .weirdness(Weirdness.MID_SLICE_VARIANT_ASCENDING, Weirdness.MID_SLICE_VARIANT_DESCENDING, Weirdness.MID_SLICE_NORMAL_ASCENDING, Weirdness.MID_SLICE_NORMAL_DESCENDING)
			   .depth(Depth.SURFACE);
		this.list.add(new Pair<>(builder.build(), this.createBiomeKey(name)));
		return this;
	}
	
	private OverworldRegion addHigh(ResourceLocation name, Continentalness continentalness, Temperature temperature, Humidity humidity, Erosion erosion) {
		ParameterPointListBuilder builder = new ParameterPointListBuilder();
		builder.continentalness(continentalness)
			   .temperature(temperature)
			   .humidity(humidity)
			   .erosion(erosion)
			   .weirdness(Weirdness.HIGH_SLICE_NORMAL_ASCENDING, Weirdness.HIGH_SLICE_NORMAL_DESCENDING, Weirdness.HIGH_SLICE_VARIANT_ASCENDING, Weirdness.HIGH_SLICE_VARIANT_DESCENDING)
			   .depth(Depth.SURFACE);
		this.list.add(new Pair<>(builder.build(), this.createBiomeKey(name)));
		return this;
	}
	
	private OverworldRegion addPeak(ResourceLocation name, Continentalness continentalness, Temperature temperature, Humidity humidity, Erosion erosion) {
		ParameterPointListBuilder builder = new ParameterPointListBuilder();
		builder.continentalness(continentalness)
			   .temperature(temperature)
			   .humidity(humidity)
			   .erosion(erosion)
			   .weirdness(Weirdness.PEAK_NORMAL, Weirdness.PEAK_VARIANT)
			   .depth(Depth.SURFACE);
		this.list.add(new Pair<>(builder.build(), this.createBiomeKey(name)));
		return this;
	}
	
	/**
	 * 添加河流类生物群系
	 *
	 * @param name
	 * @param temperature
	 * @param humidity
	 * @param erosion
	 * @return
	 */
	private OverworldRegion addRiver(ResourceLocation name, Temperature temperature, Humidity humidity, Erosion erosion) {
		// 沿岸
		this.addValley(name, Continentalness.COAST, temperature, humidity, erosion);
		// 准内陆 E=6 T=0
		if (erosion == Erosion.EROSION_6 && temperature == Temperature.ICY) {
			this.addValley(name, Continentalness.NEAR_INLAND, temperature, humidity, erosion);
		} else {
			this.addValley(name, Continentalness.NEAR_INLAND, temperature, humidity, erosion);
		}
		// 内陆 深内陆
		if (erosion == Erosion.EROSION_6 && temperature == Temperature.ICY) {
			this.addValley(name, Continentalness.MID_INLAND, temperature, humidity, erosion);
			this.addValley(name, Continentalness.FAR_INLAND, temperature, humidity, erosion);
		} else if (erosion == Erosion.EROSION_2 || erosion == Erosion.EROSION_3 || erosion == Erosion.EROSION_4 || erosion == Erosion.EROSION_5) {
			this.addValley(name, Continentalness.MID_INLAND, temperature, humidity, erosion);
			this.addValley(name, Continentalness.FAR_INLAND, temperature, humidity, erosion);
		}
		return this;
	}
	
	/**
	 * 添加滩涂类生物群系
	 *
	 * @param name
	 * @param temperature
	 * @param humidity
	 * @param erosion
	 * @return
	 */
	public OverworldRegion addBeach(ResourceLocation name, Temperature temperature, Humidity humidity, Erosion erosion) {
		// 沿岸
		// 低地带 E=5 && W<0
		if (erosion == Erosion.EROSION_5) {
			this.addCoast(name, temperature, humidity, erosion, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
		} else {
			this.addCoast(name, temperature, humidity, erosion, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
			this.addCoast(name, temperature, humidity, erosion, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		}
		// 低山带
		// (E=4 && W<0) || (E=5 && W<0) || (E=6 && W<0)
		if (erosion == Erosion.EROSION_4 || erosion == Erosion.EROSION_5 || erosion == Erosion.EROSION_6) {
			this.addCoast(name, temperature, humidity, erosion, Weirdness.MID_SLICE_NORMAL_ASCENDING);
			this.addCoast(name, temperature, humidity, erosion, Weirdness.MID_SLICE_NORMAL_DESCENDING);
		} else {
			this.addCoast(name, temperature, humidity, erosion, Weirdness.MID_SLICE_NORMAL_ASCENDING);
			this.addCoast(name, temperature, humidity, erosion, Weirdness.MID_SLICE_NORMAL_DESCENDING);
			this.addCoast(name, temperature, humidity, erosion, Weirdness.MID_SLICE_VARIANT_DESCENDING);
			this.addCoast(name, temperature, humidity, erosion, Weirdness.MID_SLICE_VARIANT_DESCENDING);
		}
		return this;
	}
	
	/**
	 * 添加恶地类生物群系
	 *
	 * @param name
	 * @param humidity
	 * @return
	 */
	private OverworldRegion addBadland(ResourceLocation name, Humidity humidity) {
		// 沿岸
		// 山峰带 E=0 T=4
		this.addCoast(name, Temperature.HOT, humidity, Erosion.EROSION_0, Weirdness.PEAK_NORMAL);
		this.addCoast(name, Temperature.HOT, humidity, Erosion.EROSION_0, Weirdness.PEAK_VARIANT);
		// 山峰带 E=1 T=4
		this.addCoast(name, Temperature.HOT, humidity, Erosion.EROSION_1, Weirdness.PEAK_NORMAL);
		this.addCoast(name, Temperature.HOT, humidity, Erosion.EROSION_1, Weirdness.PEAK_VARIANT);
		// 准内陆
		// 低地带 E=[0, 1] T=4
		this.addNearInland(name, Temperature.HOT, humidity, Erosion.EROSION_0, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
		this.addNearInland(name, Temperature.HOT, humidity, Erosion.EROSION_0, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		this.addNearInland(name, Temperature.HOT, humidity, Erosion.EROSION_1, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
		this.addNearInland(name, Temperature.HOT, humidity, Erosion.EROSION_1, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		// 低山带 E=1 T=4
		this.addNearInland(name, Temperature.HOT, humidity, Erosion.EROSION_1, Weirdness.MID_SLICE_NORMAL_ASCENDING);
		this.addNearInland(name, Temperature.HOT, humidity, Erosion.EROSION_1, Weirdness.MID_SLICE_NORMAL_DESCENDING);
		this.addNearInland(name, Temperature.HOT, humidity, Erosion.EROSION_1, Weirdness.MID_SLICE_VARIANT_DESCENDING);
		this.addNearInland(name, Temperature.HOT, humidity, Erosion.EROSION_1, Weirdness.MID_SLICE_VARIANT_ASCENDING);
		// 高山带 E=1 T=4
		this.addNearInland(name, Temperature.HOT, humidity, Erosion.EROSION_1, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
		this.addNearInland(name, Temperature.HOT, humidity, Erosion.EROSION_1, Weirdness.HIGH_SLICE_NORMAL_DESCENDING);
		this.addNearInland(name, Temperature.HOT, humidity, Erosion.EROSION_1, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
		this.addNearInland(name, Temperature.HOT, humidity, Erosion.EROSION_1, Weirdness.HIGH_SLICE_VARIANT_DESCENDING);
		// 山峰带 E=0 T=4
		this.addNearInland(name, Temperature.HOT, humidity, Erosion.EROSION_0, Weirdness.PEAK_NORMAL);
		this.addNearInland(name, Temperature.HOT, humidity, Erosion.EROSION_0, Weirdness.PEAK_VARIANT);
		// 山峰带 E=1 T=4
		this.addNearInland(name, Temperature.HOT, humidity, Erosion.EROSION_1, Weirdness.PEAK_NORMAL);
		this.addNearInland(name, Temperature.HOT, humidity, Erosion.EROSION_1, Weirdness.PEAK_VARIANT);
		// 内陆
		// 谷地 E=[0, 1] T=4
		this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_0, Weirdness.VALLEY);
		this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_1, Weirdness.VALLEY);
		// 低地带 E=[0, 1, 2, 3] T=4
		this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_0, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_0, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
		this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_1, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_1, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
		this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_2, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_2, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
		this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_3, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_3, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
		// 低山带 E=[1, 2, 3] T=4
		this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_1, Weirdness.MID_SLICE_NORMAL_ASCENDING);
		this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_1, Weirdness.MID_SLICE_NORMAL_DESCENDING);
		this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_1, Weirdness.MID_SLICE_VARIANT_DESCENDING);
		this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_1, Weirdness.MID_SLICE_VARIANT_ASCENDING);
		
		this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_2, Weirdness.MID_SLICE_NORMAL_ASCENDING);
		this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_2, Weirdness.MID_SLICE_NORMAL_DESCENDING);
		this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_2, Weirdness.MID_SLICE_VARIANT_DESCENDING);
		this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_2, Weirdness.MID_SLICE_VARIANT_ASCENDING);
		
		this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_3, Weirdness.MID_SLICE_NORMAL_ASCENDING);
		this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_3, Weirdness.MID_SLICE_NORMAL_DESCENDING);
		this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_3, Weirdness.MID_SLICE_VARIANT_DESCENDING);
		this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_3, Weirdness.MID_SLICE_VARIANT_ASCENDING);
		// 高山带 E=0 T=4
		this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_0, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
		this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_0, Weirdness.HIGH_SLICE_NORMAL_DESCENDING);
		this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_0, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
		this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_0, Weirdness.HIGH_SLICE_VARIANT_DESCENDING);
		// 高山带 E=3 T=4
		this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_3, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
		this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_3, Weirdness.HIGH_SLICE_NORMAL_DESCENDING);
		this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_3, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
		this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_3, Weirdness.HIGH_SLICE_VARIANT_DESCENDING);
		// 山峰带 E=[0, 1] T=4
		this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_0, Weirdness.PEAK_NORMAL);
		this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_0, Weirdness.PEAK_VARIANT);
		this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_1, Weirdness.PEAK_NORMAL);
		this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_1, Weirdness.PEAK_VARIANT);
		// 山峰带 E=3 T=4
		this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_3, Weirdness.PEAK_NORMAL);
		this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_3, Weirdness.PEAK_VARIANT);
		// 深内陆
		// 谷地 E=[0, 1] T=4
		this.addFarInland(name, Temperature.HOT, humidity, Erosion.EROSION_0, Weirdness.VALLEY);
		this.addFarInland(name, Temperature.HOT, humidity, Erosion.EROSION_1, Weirdness.VALLEY);
		// 低地带 E=[0, 1, 2, 3] T=4
		this.addFarInland(name, Temperature.HOT, humidity, Erosion.EROSION_0, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
		this.addFarInland(name, Temperature.HOT, humidity, Erosion.EROSION_0, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		this.addFarInland(name, Temperature.HOT, humidity, Erosion.EROSION_1, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
		this.addFarInland(name, Temperature.HOT, humidity, Erosion.EROSION_1, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		this.addFarInland(name, Temperature.HOT, humidity, Erosion.EROSION_2, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
		this.addFarInland(name, Temperature.HOT, humidity, Erosion.EROSION_2, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		this.addFarInland(name, Temperature.HOT, humidity, Erosion.EROSION_3, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
		this.addFarInland(name, Temperature.HOT, humidity, Erosion.EROSION_3, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		this.addFarInland(name, Temperature.HOT, humidity, Erosion.EROSION_4, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
		this.addFarInland(name, Temperature.HOT, humidity, Erosion.EROSION_4, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		// 低山带 E=3 T=4
		this.addFarInland(name, Temperature.HOT, humidity, Erosion.EROSION_3, Weirdness.MID_SLICE_NORMAL_ASCENDING);
		this.addFarInland(name, Temperature.HOT, humidity, Erosion.EROSION_3, Weirdness.MID_SLICE_NORMAL_DESCENDING);
		this.addFarInland(name, Temperature.HOT, humidity, Erosion.EROSION_3, Weirdness.MID_SLICE_VARIANT_DESCENDING);
		this.addFarInland(name, Temperature.HOT, humidity, Erosion.EROSION_3, Weirdness.MID_SLICE_VARIANT_ASCENDING);
		// 高山带 E=0 T=4
		this.addFarInland(name, Temperature.HOT, humidity, Erosion.EROSION_0, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
		this.addFarInland(name, Temperature.HOT, humidity, Erosion.EROSION_0, Weirdness.HIGH_SLICE_NORMAL_DESCENDING);
		this.addFarInland(name, Temperature.HOT, humidity, Erosion.EROSION_0, Weirdness.HIGH_SLICE_VARIANT_DESCENDING);
		this.addFarInland(name, Temperature.HOT, humidity, Erosion.EROSION_0, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
		// 山峰带 E=[0, 1] T=4
		this.addFarInland(name, Temperature.HOT, humidity, Erosion.EROSION_0, Weirdness.PEAK_VARIANT);
		this.addFarInland(name, Temperature.HOT, humidity, Erosion.EROSION_0, Weirdness.PEAK_NORMAL);
		this.addFarInland(name, Temperature.HOT, humidity, Erosion.EROSION_1, Weirdness.PEAK_VARIANT);
		this.addFarInland(name, Temperature.HOT, humidity, Erosion.EROSION_1, Weirdness.PEAK_NORMAL);
		return this;
	}
	
	/**
	 * 添加低山带类生物群系
	 *
	 * @param name
	 * @param temperature
	 * @param humidity
	 * @param weirdness
	 * @return
	 */
	private OverworldRegion addMiddle(ResourceLocation name, Temperature temperature, Humidity humidity, Weirdness weirdness) {
		// 沿岸低地带1 E=5 T=[0, 1] W>0
		if (isLowVariant(weirdness) && (temperature == Temperature.ICY || temperature == Temperature.COOL)) {
			ParameterPointListBuilder coastLow1 = new ParameterPointListBuilder();
			coastLow1.continentalness(Continentalness.COAST)
					 .temperature(temperature)
					 .humidity(humidity)
					 .erosion(Erosion.EROSION_5)
					 .weirdness(weirdness)
					 .depth(Depth.SURFACE);
			this.list.add(new Pair<>(coastLow1.build(), this.createBiomeKey(name)));
		}
		// 沿岸低地带2 E=5 H=4
		if (isLow(weirdness) && (humidity == Humidity.HUMID)) {
			ParameterPointListBuilder coastLow2 = new ParameterPointListBuilder();
			coastLow2.continentalness(Continentalness.COAST)
					 .temperature(temperature)
					 .humidity(humidity)
					 .erosion(Erosion.EROSION_5)
					 .weirdness(weirdness)
					 .depth(Depth.SURFACE);
			this.list.add(new Pair<>(coastLow2.build(), this.createBiomeKey(name)));
		}
		// 沿岸低山带1 E=3
		if (isMid(weirdness)) {
			ParameterPointListBuilder coastMid1 = new ParameterPointListBuilder();
			coastMid1.continentalness(Continentalness.COAST)
					 .temperature(temperature)
					 .humidity(humidity)
					 .erosion(Erosion.EROSION_3)
					 .weirdness(weirdness)
					 .depth(Depth.SURFACE);
			this.list.add(new Pair<>(coastMid1.build(), this.createBiomeKey(name)));
		}
		// 沿岸低山带2 E=4 W>0
		if (isMidVariant(weirdness)) {
			ParameterPointListBuilder coastMid2 = new ParameterPointListBuilder();
			coastMid2.continentalness(Continentalness.COAST)
					 .temperature(temperature)
					 .humidity(humidity)
					 .erosion(Erosion.EROSION_4)
					 .weirdness(weirdness)
					 .depth(Depth.SURFACE);
			this.list.add(new Pair<>(coastMid2.build(), this.createBiomeKey(name)));
		}
		// 沿岸低山带3 E=5 W>0 T=[0, 1]
		if (isMidVariant(weirdness) && (temperature == Temperature.ICY || temperature == Temperature.COOL)) {
			ParameterPointListBuilder coastMid3 = new ParameterPointListBuilder();
			coastMid3.continentalness(Continentalness.COAST)
					 .temperature(temperature)
					 .humidity(humidity)
					 .erosion(Erosion.EROSION_5)
					 .weirdness(weirdness)
					 .depth(Depth.SURFACE);
			this.list.add(new Pair<>(coastMid3.build(), this.createBiomeKey(name)));
		}
		// 沿岸低山带4 E=5 H=4
		if (isMid(weirdness) && (humidity == Humidity.HUMID)) {
			ParameterPointListBuilder coastMid4 = new ParameterPointListBuilder();
			coastMid4.continentalness(Continentalness.COAST)
					 .temperature(temperature)
					 .humidity(humidity)
					 .erosion(Erosion.EROSION_5)
					 .weirdness(weirdness)
					 .depth(Depth.SURFACE);
			this.list.add(new Pair<>(coastMid4.build(), this.createBiomeKey(name)));
		}
		// 沿岸低山带5 E=6 W>0
		if (isMidVariant(weirdness)) {
			ParameterPointListBuilder coastMid5 = new ParameterPointListBuilder();
			coastMid5.continentalness(Continentalness.COAST)
					 .temperature(temperature)
					 .humidity(humidity)
					 .erosion(Erosion.EROSION_6)
					 .weirdness(weirdness)
					 .depth(Depth.SURFACE);
			this.list.add(new Pair<>(coastMid5.build(), this.createBiomeKey(name)));
		}
		// 沿岸高山带1 E=[0..4]
		if (isHigh(weirdness)) {
			ParameterPointListBuilder builder = new ParameterPointListBuilder();
			builder.continentalness(Continentalness.COAST)
				   .temperature(temperature)
				   .humidity(humidity)
				   .erosion(Erosion.EROSION_0, Erosion.EROSION_1, Erosion.EROSION_2, Erosion.EROSION_3, Erosion.EROSION_4)
				   .weirdness(weirdness)
				   .depth(Depth.SURFACE);
			this.list.add(new Pair<>(builder.build(), this.createBiomeKey(name)));
		}
		// 沿岸高山带2 E=5 W<0
		if (isHighNormal(weirdness)) {
			ParameterPointListBuilder coastHigh2 = new ParameterPointListBuilder();
			coastHigh2.continentalness(Continentalness.COAST)
					  .temperature(temperature)
					  .humidity(humidity)
					  .erosion(Erosion.EROSION_5)
					  .weirdness(weirdness)
					  .depth(Depth.SURFACE);
			this.list.add(new Pair<>(coastHigh2.build(), this.createBiomeKey(name)));
		}
		// 沿岸高山带3 E=5 T=[0, 1]
		if (isHigh(weirdness) && (temperature == Temperature.ICY || temperature == Temperature.COOL)) {
			ParameterPointListBuilder coastHigh3 = new ParameterPointListBuilder();
			coastHigh3.continentalness(Continentalness.COAST)
					  .temperature(temperature)
					  .humidity(humidity)
					  .erosion(Erosion.EROSION_5)
					  .weirdness(weirdness)
					  .depth(Depth.SURFACE);
			this.list.add(new Pair<>(coastHigh3.build(), this.createBiomeKey(name)));
		}
		// 沿岸高山带4 E=5 H=4
		if (isHigh(weirdness) && (humidity == Humidity.HUMID)) {
			ParameterPointListBuilder coastHigh4 = new ParameterPointListBuilder();
			coastHigh4.continentalness(Continentalness.COAST)
					  .temperature(temperature)
					  .humidity(humidity)
					  .erosion(Erosion.EROSION_5)
					  .weirdness(weirdness)
					  .depth(Depth.SURFACE);
			this.list.add(new Pair<>(coastHigh4.build(), this.createBiomeKey(name)));
		}
		// 沿岸高山带5 E=6
		if (isHigh(weirdness)) {
			ParameterPointListBuilder coastHigh5 = new ParameterPointListBuilder();
			coastHigh5.continentalness(Continentalness.COAST)
					  .temperature(temperature)
					  .humidity(humidity)
					  .erosion(Erosion.EROSION_6)
					  .weirdness(weirdness)
					  .depth(Depth.SURFACE);
			this.list.add(new Pair<>(coastHigh5.build(), this.createBiomeKey(name)));
		}
		// 沿岸山峰带1 E=[2..4, 6]
		if (isPeak(weirdness)) {
			ParameterPointListBuilder coastPeak1 = new ParameterPointListBuilder();
			coastPeak1.continentalness(Continentalness.COAST)
					  .temperature(temperature)
					  .humidity(humidity)
					  .erosion(Erosion.EROSION_2, Erosion.EROSION_3, Erosion.EROSION_4, Erosion.EROSION_6)
					  .weirdness(weirdness)
					  .depth(Depth.SURFACE);
			this.list.add(new Pair<>(coastPeak1.build(), this.createBiomeKey(name)));
		}
		// 准内陆低地带1 E=[0, 1] T<4
		if (isLow(weirdness) && (temperature == Temperature.ICY || temperature == Temperature.COOL || temperature == Temperature.NEUTRAL || temperature == Temperature.WARM)) {
			ParameterPointListBuilder nearInlandLow1 = new ParameterPointListBuilder();
			nearInlandLow1.continentalness(Continentalness.NEAR_INLAND)
						  .temperature(temperature)
						  .humidity(humidity)
						  .erosion(Erosion.EROSION_0, Erosion.EROSION_1)
						  .weirdness(weirdness)
						  .depth(Depth.SURFACE);
			this.list.add(new Pair<>(nearInlandLow1.build(), this.createBiomeKey(name)));
		}
		// 准内陆低地带2 E=[2..4]
		if (isLow(weirdness)) {
			ParameterPointListBuilder nearInlandLow1 = new ParameterPointListBuilder();
			nearInlandLow1.continentalness(Continentalness.NEAR_INLAND)
						  .temperature(temperature)
						  .humidity(humidity)
						  .erosion(Erosion.EROSION_2, Erosion.EROSION_3, Erosion.EROSION_4)
						  .weirdness(weirdness)
						  .depth(Depth.SURFACE);
			this.list.add(new Pair<>(nearInlandLow1.build(), this.createBiomeKey(name)));
		}
		// 准内陆低地带3 E=5 W<0
		if (isLowNormal(weirdness)) {
			ParameterPointListBuilder nearInlandLow1 = new ParameterPointListBuilder();
			nearInlandLow1.continentalness(Continentalness.NEAR_INLAND)
						  .temperature(temperature)
						  .humidity(humidity)
						  .erosion(Erosion.EROSION_5)
						  .weirdness(weirdness)
						  .depth(Depth.SURFACE);
			this.list.add(new Pair<>(nearInlandLow1.build(), this.createBiomeKey(name)));
		}
		// 准内陆低地带3 E=5 T=[0, 1]
		if (isLow(weirdness) && (temperature == Temperature.ICY || temperature == Temperature.COOL)) {
			ParameterPointListBuilder nearInlandLow2 = new ParameterPointListBuilder();
			nearInlandLow2.continentalness(Continentalness.NEAR_INLAND)
						  .temperature(temperature)
						  .humidity(humidity)
						  .erosion(Erosion.EROSION_5)
						  .weirdness(weirdness)
						  .depth(Depth.SURFACE);
			this.list.add(new Pair<>(nearInlandLow2.build(), this.createBiomeKey(name)));
		}
		// 准内陆低地带3 E=5 H=4
		if (isLow(weirdness) && (temperature == Temperature.HOT)) {
			ParameterPointListBuilder nearInlandLow3 = new ParameterPointListBuilder();
			nearInlandLow3.continentalness(Continentalness.NEAR_INLAND)
						  .temperature(temperature)
						  .humidity(humidity)
						  .erosion(Erosion.EROSION_2, Erosion.EROSION_3, Erosion.EROSION_4)
						  .weirdness(weirdness)
						  .depth(Depth.SURFACE);
			this.list.add(new Pair<>(nearInlandLow3.build(), this.createBiomeKey(name)));
		}
		// 准内陆低山带1 E=1 0<T<4
		if (isMid(weirdness) && (temperature == Temperature.COOL || temperature == Temperature.NEUTRAL || temperature == Temperature.WARM)) {
			ParameterPointListBuilder nearInlandMid1 = new ParameterPointListBuilder();
			nearInlandMid1.continentalness(Continentalness.NEAR_INLAND)
						  .temperature(temperature)
						  .humidity(humidity)
						  .erosion(Erosion.EROSION_1)
						  .weirdness(weirdness)
						  .depth(Depth.SURFACE);
			this.list.add(new Pair<>(nearInlandMid1.build(), this.createBiomeKey(name)));
		}
		// 准内陆低山带2 E=[2..4]
		if (isMid(weirdness)) {
			ParameterPointListBuilder nearInlandMid2 = new ParameterPointListBuilder();
			nearInlandMid2.continentalness(Continentalness.NEAR_INLAND)
						  .temperature(temperature)
						  .humidity(humidity)
						  .erosion(Erosion.EROSION_2, Erosion.EROSION_3, Erosion.EROSION_4)
						  .weirdness(weirdness)
						  .depth(Depth.SURFACE);
			this.list.add(new Pair<>(nearInlandMid2.build(), this.createBiomeKey(name)));
		}
		// 准内陆低山带3 E=5 W<0
		if (isMidNormal(weirdness)) {
			ParameterPointListBuilder nearInlandMid3 = new ParameterPointListBuilder();
			nearInlandMid3.continentalness(Continentalness.NEAR_INLAND)
						  .temperature(temperature)
						  .humidity(humidity)
						  .erosion(Erosion.EROSION_5)
						  .weirdness(weirdness)
						  .depth(Depth.SURFACE);
			this.list.add(new Pair<>(nearInlandMid3.build(), this.createBiomeKey(name)));
		}
		// 准内陆低山带4 E=5 T=[0, 1]
		if (isMid(weirdness) && (temperature == Temperature.ICY || temperature == Temperature.COOL)) {
			ParameterPointListBuilder nearInlandMid4 = new ParameterPointListBuilder();
			nearInlandMid4.continentalness(Continentalness.NEAR_INLAND)
						  .temperature(temperature)
						  .humidity(humidity)
						  .erosion(Erosion.EROSION_5)
						  .weirdness(weirdness)
						  .depth(Depth.SURFACE);
			this.list.add(new Pair<>(nearInlandMid4.build(), this.createBiomeKey(name)));
		}
		// 准内陆低山带5 E=5 H=4
		if (isMid(weirdness) && (humidity == Humidity.HUMID)) {
			ParameterPointListBuilder nearInlandMid5 = new ParameterPointListBuilder();
			nearInlandMid5.continentalness(Continentalness.NEAR_INLAND)
						  .temperature(temperature)
						  .humidity(humidity)
						  .erosion(Erosion.EROSION_5)
						  .weirdness(weirdness)
						  .depth(Depth.SURFACE);
			this.list.add(new Pair<>(nearInlandMid5.build(), this.createBiomeKey(name)));
		}
		// 准内陆低山带6 E=6 T=0
		if (isMid(weirdness) && (temperature == Temperature.ICY)) {
			ParameterPointListBuilder nearInlandMid6 = new ParameterPointListBuilder();
			nearInlandMid6.continentalness(Continentalness.NEAR_INLAND)
						  .temperature(temperature)
						  .humidity(humidity)
						  .erosion(Erosion.EROSION_6)
						  .weirdness(weirdness)
						  .depth(Depth.SURFACE);
			this.list.add(new Pair<>(nearInlandMid6.build(), this.createBiomeKey(name)));
		}
		// 准内陆高山带1 E=1 0<T<4
		if (isHigh(weirdness) && (temperature == Temperature.COOL || temperature == Temperature.NEUTRAL || temperature == Temperature.WARM)) {
			ParameterPointListBuilder nearInlandHigh1 = new ParameterPointListBuilder();
			nearInlandHigh1.continentalness(Continentalness.NEAR_INLAND)
						   .temperature(temperature)
						   .humidity(humidity)
						   .erosion(Erosion.EROSION_1)
						   .weirdness(weirdness)
						   .depth(Depth.SURFACE);
			this.list.add(new Pair<>(nearInlandHigh1.build(), this.createBiomeKey(name)));
		}
		// 准内陆高山带2 E=[2..4]
		if (isHigh(weirdness)) {
			ParameterPointListBuilder nearInlandHigh2 = new ParameterPointListBuilder();
			nearInlandHigh2.continentalness(Continentalness.NEAR_INLAND)
						   .temperature(temperature)
						   .humidity(humidity)
						   .erosion(Erosion.EROSION_2, Erosion.EROSION_3, Erosion.EROSION_4)
						   .weirdness(weirdness)
						   .depth(Depth.SURFACE);
			this.list.add(new Pair<>(nearInlandHigh2.build(), this.createBiomeKey(name)));
		}
		// 准内陆高山带3 E=5 W<0
		if (isHighNormal(weirdness)) {
			ParameterPointListBuilder nearInlandHigh3 = new ParameterPointListBuilder();
			nearInlandHigh3.continentalness(Continentalness.NEAR_INLAND)
						   .temperature(temperature)
						   .humidity(humidity)
						   .erosion(Erosion.EROSION_5)
						   .weirdness(weirdness)
						   .depth(Depth.SURFACE);
			this.list.add(new Pair<>(nearInlandHigh3.build(), this.createBiomeKey(name)));
		}
		// 准内陆高山带4 E=5 T=[0, 1]
		if (isHigh(weirdness) && (temperature == Temperature.ICY || temperature == Temperature.COOL)) {
			ParameterPointListBuilder nearInlandHigh4 = new ParameterPointListBuilder();
			nearInlandHigh4.continentalness(Continentalness.NEAR_INLAND)
						   .temperature(temperature)
						   .humidity(humidity)
						   .erosion(Erosion.EROSION_5)
						   .weirdness(weirdness)
						   .depth(Depth.SURFACE);
			this.list.add(new Pair<>(nearInlandHigh4.build(), this.createBiomeKey(name)));
		}
		// 准内陆高山带5 E=5 H=4
		if (isHigh(weirdness) && (humidity == Humidity.HUMID)) {
			ParameterPointListBuilder nearInlandHigh5 = new ParameterPointListBuilder();
			nearInlandHigh5.continentalness(Continentalness.NEAR_INLAND)
						   .temperature(temperature)
						   .humidity(humidity)
						   .erosion(Erosion.EROSION_5)
						   .weirdness(weirdness)
						   .depth(Depth.SURFACE);
			this.list.add(new Pair<>(nearInlandHigh5.build(), this.createBiomeKey(name)));
		}
		// 准内陆高山带6 E=6
		if (isHigh(weirdness)) {
			ParameterPointListBuilder nearInlandHigh6 = new ParameterPointListBuilder();
			nearInlandHigh6.continentalness(Continentalness.NEAR_INLAND)
						   .temperature(temperature)
						   .humidity(humidity)
						   .erosion(Erosion.EROSION_6)
						   .weirdness(weirdness)
						   .depth(Depth.SURFACE);
			this.list.add(new Pair<>(nearInlandHigh6.build(), this.createBiomeKey(name)));
		}
		// 准内陆山峰带1 E=1 0<T<4
		if (isPeak(weirdness) && (temperature == Temperature.COOL || temperature == Temperature.NEUTRAL || temperature == Temperature.WARM)) {
			ParameterPointListBuilder nearInlandPeak1 = new ParameterPointListBuilder();
			nearInlandPeak1.continentalness(Continentalness.NEAR_INLAND)
						   .temperature(temperature)
						   .humidity(humidity)
						   .erosion(Erosion.EROSION_1)
						   .weirdness(weirdness)
						   .depth(Depth.SURFACE);
			this.list.add(new Pair<>(nearInlandPeak1.build(), this.createBiomeKey(name)));
		}
		// 准内陆山峰带2 E=[2..4]
		if (isPeak(weirdness)) {
			ParameterPointListBuilder nearInlandPeak2 = new ParameterPointListBuilder();
			nearInlandPeak2.continentalness(Continentalness.NEAR_INLAND)
						   .temperature(temperature)
						   .humidity(humidity)
						   .erosion(Erosion.EROSION_2, Erosion.EROSION_3, Erosion.EROSION_4)
						   .weirdness(weirdness)
						   .depth(Depth.SURFACE);
			this.list.add(new Pair<>(nearInlandPeak2.build(), this.createBiomeKey(name)));
		}
		// 准内陆山峰带2 E=6
		if (isPeak(weirdness)) {
			ParameterPointListBuilder nearInlandPeak2 = new ParameterPointListBuilder();
			nearInlandPeak2.continentalness(Continentalness.NEAR_INLAND)
						   .temperature(temperature)
						   .humidity(humidity)
						   .erosion(Erosion.EROSION_6)
						   .weirdness(weirdness)
						   .depth(Depth.SURFACE);
			this.list.add(new Pair<>(nearInlandPeak2.build(), this.createBiomeKey(name)));
		}
		// 内陆谷地1 E=[0, 1] T<4
		if (isValley(weirdness) && (temperature == Temperature.ICY || temperature == Temperature.COOL || temperature == Temperature.NEUTRAL || temperature == Temperature.WARM)) {
			ParameterPointListBuilder midInlandValley = new ParameterPointListBuilder();
			midInlandValley.continentalness(Continentalness.MID_INLAND)
						   .temperature(temperature)
						   .humidity(humidity)
						   .erosion(Erosion.EROSION_0, Erosion.EROSION_1)
						   .weirdness(weirdness)
						   .depth(Depth.SURFACE);
			this.list.add(new Pair<>(midInlandValley.build(), this.createBiomeKey(name)));
		}
		// 内陆低地带1 E=[0, 1] 0<T<4
		if (isLow(weirdness) && (temperature == Temperature.COOL || temperature == Temperature.NEUTRAL || temperature == Temperature.WARM)) {
			ParameterPointListBuilder midInlandLow1 = new ParameterPointListBuilder();
			midInlandLow1.continentalness(Continentalness.MID_INLAND)
						 .temperature(temperature)
						 .humidity(humidity)
						 .erosion(Erosion.EROSION_0, Erosion.EROSION_1)
						 .weirdness(weirdness)
						 .depth(Depth.SURFACE);
			this.list.add(new Pair<>(midInlandLow1.build(), this.createBiomeKey(name)));
		}
		// 内陆低地带2 E=[2, 3] T<4
		if (isLow(weirdness) && (temperature == Temperature.ICY || temperature == Temperature.COOL || temperature == Temperature.NEUTRAL || temperature == Temperature.WARM)) {
			ParameterPointListBuilder midInlandLow2 = new ParameterPointListBuilder();
			midInlandLow2.continentalness(Continentalness.MID_INLAND)
						 .temperature(temperature)
						 .humidity(humidity)
						 .erosion(Erosion.EROSION_2, Erosion.EROSION_3)
						 .weirdness(weirdness)
						 .depth(Depth.SURFACE);
			this.list.add(new Pair<>(midInlandLow2.build(), this.createBiomeKey(name)));
		}
		// 内陆低地带3 E=[4, 5]
		if (isLow(weirdness) && (temperature == Temperature.ICY || temperature == Temperature.COOL || temperature == Temperature.NEUTRAL || temperature == Temperature.WARM)) {
			ParameterPointListBuilder midInlandLow3 = new ParameterPointListBuilder();
			midInlandLow3.continentalness(Continentalness.MID_INLAND)
						 .temperature(temperature)
						 .humidity(humidity)
						 .erosion(Erosion.EROSION_2, Erosion.EROSION_3)
						 .weirdness(weirdness)
						 .depth(Depth.SURFACE);
			this.list.add(new Pair<>(midInlandLow3.build(), this.createBiomeKey(name)));
		}
		// 内陆低地带4 E=6 T=0
		if (isLow(weirdness) && (temperature == Temperature.ICY)) {
			ParameterPointListBuilder midInlandLow3 = new ParameterPointListBuilder();
			midInlandLow3.continentalness(Continentalness.MID_INLAND)
						 .temperature(temperature)
						 .humidity(humidity)
						 .erosion(Erosion.EROSION_6)
						 .weirdness(weirdness)
						 .depth(Depth.SURFACE);
			this.list.add(new Pair<>(midInlandLow3.build(), this.createBiomeKey(name)));
		}
		// 内陆低山带1 E=1 0<T<4
		if (isMid(weirdness) && (temperature == Temperature.COOL || temperature == Temperature.NEUTRAL || temperature == Temperature.WARM)) {
			ParameterPointListBuilder midInlandMid1 = new ParameterPointListBuilder();
			midInlandMid1.continentalness(Continentalness.MID_INLAND)
						 .temperature(temperature)
						 .humidity(humidity)
						 .erosion(Erosion.EROSION_1)
						 .weirdness(weirdness)
						 .depth(Depth.SURFACE);
			this.list.add(new Pair<>(midInlandMid1.build(), this.createBiomeKey(name)));
		}
		// 内陆低山带2 E=[2, 3] T<4
		if (isMid(weirdness) && (temperature == Temperature.ICY || temperature == Temperature.COOL || temperature == Temperature.NEUTRAL || temperature == Temperature.WARM)) {
			ParameterPointListBuilder midInlandMid2 = new ParameterPointListBuilder();
			midInlandMid2.continentalness(Continentalness.MID_INLAND)
						 .temperature(temperature)
						 .humidity(humidity)
						 .erosion(Erosion.EROSION_2, Erosion.EROSION_3)
						 .weirdness(weirdness)
						 .depth(Depth.SURFACE);
			this.list.add(new Pair<>(midInlandMid2.build(), this.createBiomeKey(name)));
		}
		// 内陆低山带3 E=4
		if (isMid(weirdness)) {
			ParameterPointListBuilder midInlandMid3 = new ParameterPointListBuilder();
			midInlandMid3.continentalness(Continentalness.MID_INLAND)
						 .temperature(temperature)
						 .humidity(humidity)
						 .erosion(Erosion.EROSION_4)
						 .weirdness(weirdness)
						 .depth(Depth.SURFACE);
			this.list.add(new Pair<>(midInlandMid3.build(), this.createBiomeKey(name)));
		}
		// 内陆低山带4 E=6 T=0
		if (isMid(weirdness) && (temperature == Temperature.ICY)) {
			ParameterPointListBuilder midInlandMid4 = new ParameterPointListBuilder();
			midInlandMid4.continentalness(Continentalness.MID_INLAND)
						 .temperature(temperature)
						 .humidity(humidity)
						 .erosion(Erosion.EROSION_6)
						 .weirdness(weirdness)
						 .depth(Depth.SURFACE);
			this.list.add(new Pair<>(midInlandMid4.build(), this.createBiomeKey(name)));
		}
		// 内陆高山带1 E=3 T<4
		if (isMid(weirdness) && (temperature == Temperature.ICY || temperature == Temperature.COOL || temperature == Temperature.NEUTRAL || temperature == Temperature.WARM)) {
			ParameterPointListBuilder midInlandHigh1 = new ParameterPointListBuilder();
			midInlandHigh1.continentalness(Continentalness.MID_INLAND)
						  .temperature(temperature)
						  .humidity(humidity)
						  .erosion(Erosion.EROSION_3)
						  .weirdness(weirdness)
						  .depth(Depth.SURFACE);
			this.list.add(new Pair<>(midInlandHigh1.build(), this.createBiomeKey(name)));
		}
		// 内陆高山带2 E=4
		if (isMid(weirdness)) {
			ParameterPointListBuilder midInlandHigh2 = new ParameterPointListBuilder();
			midInlandHigh2.continentalness(Continentalness.MID_INLAND)
						  .temperature(temperature)
						  .humidity(humidity)
						  .erosion(Erosion.EROSION_4)
						  .weirdness(weirdness)
						  .depth(Depth.SURFACE);
			this.list.add(new Pair<>(midInlandHigh2.build(), this.createBiomeKey(name)));
		}
		// 内陆高山带3 E=6
		if (isMid(weirdness)) {
			ParameterPointListBuilder midInlandHigh3 = new ParameterPointListBuilder();
			midInlandHigh3.continentalness(Continentalness.MID_INLAND)
						  .temperature(temperature)
						  .humidity(humidity)
						  .erosion(Erosion.EROSION_6)
						  .weirdness(weirdness)
						  .depth(Depth.SURFACE);
			this.list.add(new Pair<>(midInlandHigh3.build(), this.createBiomeKey(name)));
		}
		// 内陆山峰带 E=3 T<4
		if (isMid(weirdness) && (temperature == Temperature.ICY || temperature == Temperature.COOL || temperature == Temperature.NEUTRAL || temperature == Temperature.WARM)) {
			ParameterPointListBuilder midInlandHigh3 = new ParameterPointListBuilder();
			midInlandHigh3.continentalness(Continentalness.MID_INLAND)
						  .temperature(temperature)
						  .humidity(humidity)
						  .erosion(Erosion.EROSION_3)
						  .weirdness(weirdness)
						  .depth(Depth.SURFACE);
			this.list.add(new Pair<>(midInlandHigh3.build(), this.createBiomeKey(name)));
		}
		// 内陆山峰带 E=6
		if (isMid(weirdness)) {
			ParameterPointListBuilder midInlandHigh3 = new ParameterPointListBuilder();
			midInlandHigh3.continentalness(Continentalness.MID_INLAND)
						  .temperature(temperature)
						  .humidity(humidity)
						  .erosion(Erosion.EROSION_6)
						  .weirdness(weirdness)
						  .depth(Depth.SURFACE);
			this.list.add(new Pair<>(midInlandHigh3.build(), this.createBiomeKey(name)));
		}
		// 深内陆谷地1 E=[0, 1] T<4
		if (isValley(weirdness) && (temperature == Temperature.ICY || temperature == Temperature.COOL || temperature == Temperature.NEUTRAL || temperature == Temperature.WARM)) {
			ParameterPointListBuilder farInlandValley = new ParameterPointListBuilder();
			farInlandValley.continentalness(Continentalness.FAR_INLAND)
						   .temperature(temperature)
						   .humidity(humidity)
						   .erosion(Erosion.EROSION_0, Erosion.EROSION_1)
						   .weirdness(weirdness)
						   .depth(Depth.SURFACE);
			this.list.add(new Pair<>(farInlandValley.build(), this.createBiomeKey(name)));
		}
		// 深内陆低地1 E=0 0<T<4
		if (isLow(weirdness) && (temperature == Temperature.COOL || temperature == Temperature.NEUTRAL || temperature == Temperature.WARM)) {
			ParameterPointListBuilder farInlandLow1 = new ParameterPointListBuilder();
			farInlandLow1.continentalness(Continentalness.FAR_INLAND)
						 .temperature(temperature)
						 .humidity(humidity)
						 .erosion(Erosion.EROSION_0, Erosion.EROSION_1)
						 .weirdness(weirdness)
						 .depth(Depth.SURFACE);
			this.list.add(new Pair<>(farInlandLow1.build(), this.createBiomeKey(name)));
		}
		// 深内陆低地2 E=[2, 3] T<4
		if (isLow(weirdness) && (temperature == Temperature.ICY || temperature == Temperature.COOL || temperature == Temperature.NEUTRAL || temperature == Temperature.WARM)) {
			ParameterPointListBuilder farInlandLow2 = new ParameterPointListBuilder();
			farInlandLow2.continentalness(Continentalness.FAR_INLAND)
						 .temperature(temperature)
						 .humidity(humidity)
						 .erosion(Erosion.EROSION_2, Erosion.EROSION_3)
						 .weirdness(weirdness)
						 .depth(Depth.SURFACE);
			this.list.add(new Pair<>(farInlandLow2.build(), this.createBiomeKey(name)));
		}
		// 深内陆低地3 E=[4, 5]
		if (isLow(weirdness)) {
			ParameterPointListBuilder farInlandLow3 = new ParameterPointListBuilder();
			farInlandLow3.continentalness(Continentalness.FAR_INLAND)
						 .temperature(temperature)
						 .humidity(humidity)
						 .erosion(Erosion.EROSION_4, Erosion.EROSION_5)
						 .weirdness(weirdness)
						 .depth(Depth.SURFACE);
			this.list.add(new Pair<>(farInlandLow3.build(), this.createBiomeKey(name)));
		}
		// 深内陆低地3 E=6 T=0
		if (isLow(weirdness) && (temperature == Temperature.ICY)) {
			ParameterPointListBuilder farInlandLow3 = new ParameterPointListBuilder();
			farInlandLow3.continentalness(Continentalness.FAR_INLAND)
						 .temperature(temperature)
						 .humidity(humidity)
						 .erosion(Erosion.EROSION_6)
						 .weirdness(weirdness)
						 .depth(Depth.SURFACE);
			this.list.add(new Pair<>(farInlandLow3.build(), this.createBiomeKey(name)));
		}
		// 深内陆低山带1 E=3 T<4
		if (isMid(weirdness) && (temperature == Temperature.ICY || temperature == Temperature.COOL || temperature == Temperature.NEUTRAL || temperature == Temperature.WARM)) {
			ParameterPointListBuilder farInlandMid1 = new ParameterPointListBuilder();
			farInlandMid1.continentalness(Continentalness.FAR_INLAND)
						 .temperature(temperature)
						 .humidity(humidity)
						 .erosion(Erosion.EROSION_3)
						 .weirdness(weirdness)
						 .depth(Depth.SURFACE);
			this.list.add(new Pair<>(farInlandMid1.build(), this.createBiomeKey(name)));
		}
		// 深内陆低山带 E=4
		if (isMid(weirdness)) {
			ParameterPointListBuilder farInlandMid1 = new ParameterPointListBuilder();
			farInlandMid1.continentalness(Continentalness.FAR_INLAND)
						 .temperature(temperature)
						 .humidity(humidity)
						 .erosion(Erosion.EROSION_4)
						 .weirdness(weirdness)
						 .depth(Depth.SURFACE);
			this.list.add(new Pair<>(farInlandMid1.build(), this.createBiomeKey(name)));
		}
		// 深内陆低山带 E=6 T=0
		if (isMid(weirdness) && (temperature == Temperature.ICY)) {
			ParameterPointListBuilder farInlandMid1 = new ParameterPointListBuilder();
			farInlandMid1.continentalness(Continentalness.FAR_INLAND)
						 .temperature(temperature)
						 .humidity(humidity)
						 .erosion(Erosion.EROSION_6)
						 .weirdness(weirdness)
						 .depth(Depth.SURFACE);
			this.list.add(new Pair<>(farInlandMid1.build(), this.createBiomeKey(name)));
		}
		// 深内陆高山带1 E=4
		if (isHigh(weirdness)) {
			ParameterPointListBuilder farInlandHigh1 = new ParameterPointListBuilder();
			farInlandHigh1.continentalness(Continentalness.FAR_INLAND)
						  .temperature(temperature)
						  .humidity(humidity)
						  .erosion(Erosion.EROSION_4)
						  .weirdness(weirdness)
						  .depth(Depth.SURFACE);
			this.list.add(new Pair<>(farInlandHigh1.build(), this.createBiomeKey(name)));
		}
		// 深内陆高山带2 E=6
		if (isHigh(weirdness)) {
			ParameterPointListBuilder farInlandHigh2 = new ParameterPointListBuilder();
			farInlandHigh2.continentalness(Continentalness.FAR_INLAND)
						  .temperature(temperature)
						  .humidity(humidity)
						  .erosion(Erosion.EROSION_6)
						  .weirdness(weirdness)
						  .depth(Depth.SURFACE);
			this.list.add(new Pair<>(farInlandHigh2.build(), this.createBiomeKey(name)));
		}
		// 深内陆山峰带1 E=4
		if (isPeak(weirdness)) {
			ParameterPointListBuilder farInlandHigh2 = new ParameterPointListBuilder();
			farInlandHigh2.continentalness(Continentalness.FAR_INLAND)
						  .temperature(temperature)
						  .humidity(humidity)
						  .erosion(Erosion.EROSION_4)
						  .weirdness(weirdness)
						  .depth(Depth.SURFACE);
			this.list.add(new Pair<>(farInlandHigh2.build(), this.createBiomeKey(name)));
		}
		// 深内陆山峰带2 E=6
		if (isPeak(weirdness)) {
			ParameterPointListBuilder farInlandHigh2 = new ParameterPointListBuilder();
			farInlandHigh2.continentalness(Continentalness.FAR_INLAND)
						  .temperature(temperature)
						  .humidity(humidity)
						  .erosion(Erosion.EROSION_6)
						  .weirdness(weirdness)
						  .depth(Depth.SURFACE);
			this.list.add(new Pair<>(farInlandHigh2.build(), this.createBiomeKey(name)));
		}
		return this;
	}
	
	public OverworldRegion addMiddle(ResourceLocation name, Temperature temperature, Humidity humidity, boolean isVariant) {
		// 条件
		// T=0
		boolean tem0 = temperature == Temperature.ICY;
		// T<4
		boolean tem_4 = (tem0 || temperature == Temperature.COOL || temperature == Temperature.NEUTRAL || temperature == Temperature.WARM);
		// T=[0, 1]
		boolean tem01 = (tem0 || temperature == Temperature.COOL);
		// 0<T<4
		boolean tem0_4 = (temperature == Temperature.COOL || temperature == Temperature.NEUTRAL || temperature == Temperature.WARM);
		// H=4
		boolean hum4 = (humidity == Humidity.HUMID);
		// 沿岸-谷地-null
		// 沿岸-低地
		//E=5: W>0, T=[0, 1] || H=4
		if((isVariant && tem01)){
			this.addCoast(name, temperature, humidity, Erosion.EROSION_5, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		}else if(hum4){
			this.addCoast(name, temperature, humidity, Erosion.EROSION_5, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_5, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		}
		// E=6
		this.addCoast(name, temperature, humidity, Erosion.EROSION_6, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
		this.addCoast(name, temperature, humidity, Erosion.EROSION_6, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		// 沿岸-低山
		// E=4: W>0
		if(isVariant){
			this.addCoast(name, temperature, humidity, Erosion.EROSION_4, Weirdness.MID_SLICE_NORMAL_ASCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_4, Weirdness.MID_SLICE_NORMAL_DESCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_4, Weirdness.MID_SLICE_VARIANT_DESCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_4, Weirdness.MID_SLICE_VARIANT_ASCENDING);
		}
		// E=5: W>0, T=[0, 1] || H=4
		if(isVariant && tem01){
			this.addCoast(name, temperature, humidity, Erosion.EROSION_5, Weirdness.MID_SLICE_VARIANT_DESCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_5, Weirdness.MID_SLICE_VARIANT_ASCENDING);
		}else if(hum4){
			this.addCoast(name, temperature, humidity, Erosion.EROSION_5, Weirdness.MID_SLICE_NORMAL_ASCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_5, Weirdness.MID_SLICE_NORMAL_DESCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_5, Weirdness.MID_SLICE_VARIANT_DESCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_5, Weirdness.MID_SLICE_VARIANT_ASCENDING);
		}
		// E=6: W>0
		if(isVariant){
			this.addCoast(name, temperature, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_NORMAL_ASCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_NORMAL_DESCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_VARIANT_DESCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_VARIANT_ASCENDING);
		}
		// 沿岸-高山
		// E=[0..4]
		this.addCoast(name, temperature, humidity, Erosion.EROSION_0, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
		this.addCoast(name, temperature, humidity, Erosion.EROSION_0, Weirdness.HIGH_SLICE_NORMAL_DESCENDING);
		this.addCoast(name, temperature, humidity, Erosion.EROSION_0, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
		this.addCoast(name, temperature, humidity, Erosion.EROSION_0, Weirdness.HIGH_SLICE_VARIANT_DESCENDING);
		
		this.addCoast(name, temperature, humidity, Erosion.EROSION_1, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
		this.addCoast(name, temperature, humidity, Erosion.EROSION_1, Weirdness.HIGH_SLICE_NORMAL_DESCENDING);
		this.addCoast(name, temperature, humidity, Erosion.EROSION_1, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
		this.addCoast(name, temperature, humidity, Erosion.EROSION_1, Weirdness.HIGH_SLICE_VARIANT_DESCENDING);
		
		this.addCoast(name, temperature, humidity, Erosion.EROSION_2, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
		this.addCoast(name, temperature, humidity, Erosion.EROSION_2, Weirdness.HIGH_SLICE_NORMAL_DESCENDING);
		this.addCoast(name, temperature, humidity, Erosion.EROSION_2, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
		this.addCoast(name, temperature, humidity, Erosion.EROSION_2, Weirdness.HIGH_SLICE_VARIANT_DESCENDING);
		
		this.addCoast(name, temperature, humidity, Erosion.EROSION_3, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
		this.addCoast(name, temperature, humidity, Erosion.EROSION_3, Weirdness.HIGH_SLICE_NORMAL_DESCENDING);
		this.addCoast(name, temperature, humidity, Erosion.EROSION_3, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
		this.addCoast(name, temperature, humidity, Erosion.EROSION_3, Weirdness.HIGH_SLICE_VARIANT_DESCENDING);
		
		this.addCoast(name, temperature, humidity, Erosion.EROSION_4, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
		this.addCoast(name, temperature, humidity, Erosion.EROSION_4, Weirdness.HIGH_SLICE_NORMAL_DESCENDING);
		this.addCoast(name, temperature, humidity, Erosion.EROSION_4, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
		this.addCoast(name, temperature, humidity, Erosion.EROSION_4, Weirdness.HIGH_SLICE_VARIANT_DESCENDING);
		// E=5: W<0, T=[0, 1] || H=4
		if(!isVariant && tem01){
			this.addCoast(name, temperature, humidity, Erosion.EROSION_5, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_5, Weirdness.HIGH_SLICE_NORMAL_DESCENDING);
		}else if(hum4){
			this.addCoast(name, temperature, humidity, Erosion.EROSION_5, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_5, Weirdness.HIGH_SLICE_NORMAL_DESCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_5, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_5, Weirdness.HIGH_SLICE_VARIANT_DESCENDING);
		}
		// E=6
		this.addCoast(name, temperature, humidity, Erosion.EROSION_6, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
		this.addCoast(name, temperature, humidity, Erosion.EROSION_6, Weirdness.HIGH_SLICE_NORMAL_DESCENDING);
		this.addCoast(name, temperature, humidity, Erosion.EROSION_6, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
		this.addCoast(name, temperature, humidity, Erosion.EROSION_6, Weirdness.HIGH_SLICE_VARIANT_DESCENDING);
		// 沿岸-山峰
		// E=1: 0<T<4
		if(tem0_4){
			this.addCoast(name, temperature, humidity, Erosion.EROSION_1, Weirdness.PEAK_NORMAL);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_1, Weirdness.PEAK_VARIANT);
		}
		// E=[2..4]
		this.addCoast(name, temperature, humidity, Erosion.EROSION_2, Weirdness.PEAK_NORMAL);
		this.addCoast(name, temperature, humidity, Erosion.EROSION_2, Weirdness.PEAK_VARIANT);
		
		this.addCoast(name, temperature, humidity, Erosion.EROSION_3, Weirdness.PEAK_NORMAL);
		this.addCoast(name, temperature, humidity, Erosion.EROSION_3, Weirdness.PEAK_VARIANT);
		
		this.addCoast(name, temperature, humidity, Erosion.EROSION_4, Weirdness.PEAK_NORMAL);
		this.addCoast(name, temperature, humidity, Erosion.EROSION_4, Weirdness.PEAK_VARIANT);
		// E=6
		this.addCoast(name, temperature, humidity, Erosion.EROSION_6, Weirdness.PEAK_NORMAL);
		this.addCoast(name, temperature, humidity, Erosion.EROSION_6, Weirdness.PEAK_VARIANT);
		// 准陆-谷地-null
		// 准陆-低地
		// E=[0, 1]: T<4
		if(tem_4){
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_0, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_0, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
			
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		}
		// E=[2..4]
		this.addNearInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
		this.addNearInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		
		this.addNearInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
		this.addNearInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		
		this.addNearInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
		this.addNearInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		// E=5: W<0 || T=[0, 1] || H=4
		if(!isVariant){
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		}else if(tem01 || hum4){
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		}
		// E=6: T=0
		if(tem0){
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		}
		// 准陆-低山
		// E=1: 0<T<4
		if(tem0_4){
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.MID_SLICE_NORMAL_ASCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.MID_SLICE_NORMAL_DESCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.MID_SLICE_VARIANT_ASCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.MID_SLICE_VARIANT_DESCENDING);
		}
		// E=[2..4]
		this.addNearInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.MID_SLICE_NORMAL_ASCENDING);
		this.addNearInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.MID_SLICE_NORMAL_DESCENDING);
		this.addNearInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.MID_SLICE_VARIANT_ASCENDING);
		this.addNearInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.MID_SLICE_VARIANT_DESCENDING);
		
		this.addNearInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.MID_SLICE_NORMAL_ASCENDING);
		this.addNearInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.MID_SLICE_NORMAL_DESCENDING);
		this.addNearInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.MID_SLICE_VARIANT_ASCENDING);
		this.addNearInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.MID_SLICE_VARIANT_DESCENDING);
		
		this.addNearInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.MID_SLICE_NORMAL_ASCENDING);
		this.addNearInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.MID_SLICE_NORMAL_DESCENDING);
		this.addNearInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.MID_SLICE_VARIANT_ASCENDING);
		this.addNearInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.MID_SLICE_VARIANT_DESCENDING);
		// E=5: W<0 || T=[0, 1] || H=4
		if(!isVariant){
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.MID_SLICE_NORMAL_ASCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.MID_SLICE_NORMAL_DESCENDING);
		}else if(tem01 || hum4){
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.MID_SLICE_NORMAL_ASCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.MID_SLICE_NORMAL_DESCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.MID_SLICE_VARIANT_ASCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.MID_SLICE_VARIANT_DESCENDING);
		}
		// E=6: T=0
		if(tem0){
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_NORMAL_ASCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_NORMAL_DESCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_VARIANT_ASCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_VARIANT_DESCENDING);
		}
		// 准陆-高山
		// E=1: 0<T<4
		if(tem0_4){
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.HIGH_SLICE_NORMAL_DESCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.HIGH_SLICE_VARIANT_DESCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
		}
		// E=[2..4]
		this.addNearInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
		this.addNearInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.HIGH_SLICE_NORMAL_DESCENDING);
		this.addNearInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.HIGH_SLICE_VARIANT_DESCENDING);
		this.addNearInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
		
		this.addNearInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
		this.addNearInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.HIGH_SLICE_NORMAL_DESCENDING);
		this.addNearInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.HIGH_SLICE_VARIANT_DESCENDING);
		this.addNearInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
		
		this.addNearInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
		this.addNearInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.HIGH_SLICE_NORMAL_DESCENDING);
		this.addNearInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.HIGH_SLICE_VARIANT_DESCENDING);
		this.addNearInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
		// E=5: W<0 || T=0 || H=4
		if(!isVariant){
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.HIGH_SLICE_NORMAL_DESCENDING);
		}else if(tem0 || hum4){
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.HIGH_SLICE_NORMAL_DESCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.HIGH_SLICE_VARIANT_DESCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
		}
		// E=6
		this.addNearInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
		this.addNearInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.HIGH_SLICE_NORMAL_DESCENDING);
		this.addNearInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.HIGH_SLICE_VARIANT_DESCENDING);
		this.addNearInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
		// 准陆-山峰
		// E=1: 0<T<4
		if(tem0_4){
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.PEAK_NORMAL);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.PEAK_VARIANT);
		}
		// E=[2..4]
		this.addNearInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.PEAK_NORMAL);
		this.addNearInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.PEAK_VARIANT);
		
		this.addNearInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.PEAK_NORMAL);
		this.addNearInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.PEAK_VARIANT);
		
		this.addNearInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.PEAK_NORMAL);
		this.addNearInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.PEAK_VARIANT);
		// E=5: W<0 || T=[0, 1] || H=4
		if(!isVariant){
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.PEAK_NORMAL);
		}else if(tem01 || hum4){
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.PEAK_NORMAL);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.PEAK_VARIANT);
		}
		// E=6
		this.addNearInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.PEAK_NORMAL);
		this.addNearInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.PEAK_VARIANT);
		// 内陆-谷地
		// E=[0, 1]: T<4
		if(tem_4){
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_0, Weirdness.VALLEY);
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.VALLEY);
		}
		// 内陆-低地
		// E=[0, 1]: 0<T<4
		if(tem0_4){
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_0, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_0, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
			
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		}
		// E=[2, 3]: T<4
		if(tem_4){
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
			
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		}
		// E=[4, 5]
		this.addMidInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
		this.addMidInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		
		this.addMidInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
		this.addMidInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		// E=6: T=0
		if(tem0){
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		}
		// 内陆-低山
		// E=1: 0<T<4
		if(tem0_4){
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.MID_SLICE_NORMAL_ASCENDING);
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.MID_SLICE_NORMAL_DESCENDING
			);
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.MID_SLICE_VARIANT_ASCENDING);
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.MID_SLICE_VARIANT_DESCENDING
			);
		}
		// E=[2, 3]: T<4
		if(tem_4){
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.MID_SLICE_NORMAL_ASCENDING);
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.MID_SLICE_NORMAL_DESCENDING
			);
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.MID_SLICE_VARIANT_ASCENDING);
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.MID_SLICE_VARIANT_DESCENDING
			);
			
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.MID_SLICE_NORMAL_ASCENDING);
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.MID_SLICE_NORMAL_DESCENDING
			);
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.MID_SLICE_VARIANT_ASCENDING);
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.MID_SLICE_VARIANT_DESCENDING
			);
		}
		// E=4
		this.addMidInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.MID_SLICE_NORMAL_ASCENDING);
		this.addMidInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.MID_SLICE_NORMAL_DESCENDING
		);
		this.addMidInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.MID_SLICE_VARIANT_ASCENDING);
		this.addMidInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.MID_SLICE_VARIANT_DESCENDING
		);
		// E=6: T=0
		if(tem0){
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_NORMAL_ASCENDING);
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_NORMAL_DESCENDING
			);
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_VARIANT_ASCENDING);
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_VARIANT_DESCENDING
			);
		}
		// 内陆-高山
		// E=3: T<4
		if(tem_4){
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.HIGH_SLICE_NORMAL_DESCENDING
			);
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.HIGH_SLICE_VARIANT_DESCENDING
			);
		}
		// E=4
		this.addMidInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
		this.addMidInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.HIGH_SLICE_NORMAL_DESCENDING
		);
		this.addMidInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
		this.addMidInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.HIGH_SLICE_VARIANT_DESCENDING
		);
		// E=6
		this.addMidInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
		this.addMidInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.HIGH_SLICE_NORMAL_DESCENDING
		);
		this.addMidInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
		this.addMidInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.HIGH_SLICE_VARIANT_DESCENDING
		);
		// 内陆-山峰
		// E=3: T<4
		if(tem_4){
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.PEAK_NORMAL);
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.PEAK_VARIANT);
		}
		// E=4
		this.addMidInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.PEAK_NORMAL);
		this.addMidInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.PEAK_VARIANT);
		// E=6
		this.addMidInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.PEAK_NORMAL);
		this.addMidInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.PEAK_VARIANT);
		// 深陆-谷地
		// E=[0, 1]: T<4
		if(tem_4){
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_0, Weirdness.VALLEY);
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.VALLEY);
		}
		// 深陆-低地
		// E=[0, 1]: 0<T<4
		if(tem0_4){
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_0, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_0, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
			
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		}
		// E=[2, 3]: T<4
		if(tem_4){
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
			
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		}
		// E=[4, 5]
		this.addFarInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
		this.addFarInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		
		this.addFarInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
		this.addFarInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		// E=6: T=0
		if(tem0){
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		}
		// 深陆-低山
		// E=3: T<4
		if(tem_4){
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.MID_SLICE_NORMAL_ASCENDING);
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.MID_SLICE_NORMAL_DESCENDING);
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.MID_SLICE_VARIANT_ASCENDING);
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.MID_SLICE_VARIANT_DESCENDING);
		}
		// E=4
		this.addFarInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.MID_SLICE_NORMAL_ASCENDING);
		this.addFarInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.MID_SLICE_NORMAL_DESCENDING);
		this.addFarInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.MID_SLICE_VARIANT_ASCENDING);
		this.addFarInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.MID_SLICE_VARIANT_DESCENDING);
		// E=6: T=0
		if(tem0){
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_NORMAL_ASCENDING);
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_NORMAL_DESCENDING);
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_VARIANT_ASCENDING);
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_VARIANT_DESCENDING);
		}
		// 深陆-高山
		// E=4
		this.addFarInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
		this.addFarInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.HIGH_SLICE_NORMAL_DESCENDING);
		this.addFarInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
		this.addFarInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.HIGH_SLICE_VARIANT_DESCENDING);
		// E=6
		this.addFarInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
		this.addFarInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.HIGH_SLICE_NORMAL_DESCENDING);
		this.addFarInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
		this.addFarInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.HIGH_SLICE_VARIANT_DESCENDING);
		// 深陆-山峰
		// E=4
		this.addFarInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.PEAK_NORMAL);
		this.addFarInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.PEAK_VARIANT);
		// E=6
		this.addFarInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.PEAK_NORMAL);
		this.addFarInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.PEAK_VARIANT);
		return this;
	}
	
	public OverworldRegion addPlateau(ResourceLocation name, Temperature temperature, Humidity humidity, boolean isVariant) {
		// 条件
		// T=[3, 4]
		boolean tem34 = temperature == Temperature.WARM || temperature == Temperature.HOT;
		// T>0
		boolean tem0_ = temperature == Temperature.COOL || temperature == Temperature.NEUTRAL || temperature == Temperature.WARM || temperature == Temperature.HOT;
		// 沿岸-谷地-null
		// 沿岸-低地-null
		// 沿岸-低山-null
		// 沿岸-高山-null
		// 沿岸-山峰-null
		
		// 准陆-谷地-null
		// 准陆-低地-null
		// 准陆-低山
		// E=0: T=[3, 4]
		if(tem34){
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_0, Weirdness.MID_SLICE_NORMAL_ASCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_0, Weirdness.MID_SLICE_NORMAL_DESCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_0, Weirdness.MID_SLICE_VARIANT_ASCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_0, Weirdness.MID_SLICE_VARIANT_ASCENDING);
		}
		// 准陆-高山
		// E=0: T=[3, 4]
		if(tem34){
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_0, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_0, Weirdness.HIGH_SLICE_NORMAL_DESCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_0, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_0, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
		}
		// 准陆-山峰-null
		// 内陆-谷地-null
		// 内陆-低地-null
		// 内陆-低山
		// E=0: T=[3, 4]
		if(tem34){
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_0, Weirdness.MID_SLICE_NORMAL_ASCENDING);
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_0, Weirdness.MID_SLICE_NORMAL_DESCENDING);
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_0, Weirdness.MID_SLICE_VARIANT_ASCENDING);
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_0, Weirdness.MID_SLICE_VARIANT_DESCENDING);
		}
		// 内陆-高山
		// E=1: T=[3, 4]
		if(tem34){
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.HIGH_SLICE_NORMAL_DESCENDING);
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.HIGH_SLICE_VARIANT_DESCENDING);
		}
		// E=2
		this.addMidInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
		this.addMidInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.HIGH_SLICE_NORMAL_DESCENDING);
		this.addMidInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
		this.addMidInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.HIGH_SLICE_VARIANT_DESCENDING);
		// 内陆-山峰
		// E=2
		this.addMidInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.PEAK_NORMAL);
		this.addMidInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.PEAK_VARIANT);
		// 深陆-谷地-null
		// 深陆-低地-null
		// 深陆-低山
		// E=0: T=[3, 4]
		if(tem34){
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_0, Weirdness.MID_SLICE_NORMAL_ASCENDING);
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_0, Weirdness.MID_SLICE_NORMAL_DESCENDING);
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_0, Weirdness.MID_SLICE_VARIANT_ASCENDING);
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_0, Weirdness.MID_SLICE_VARIANT_DESCENDING);
		}
		// E=1: T>0
		if(tem0_){
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.MID_SLICE_NORMAL_ASCENDING);
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.MID_SLICE_NORMAL_DESCENDING);
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.MID_SLICE_VARIANT_ASCENDING);
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.MID_SLICE_VARIANT_DESCENDING);
		}
		// E=2
		this.addFarInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.MID_SLICE_NORMAL_ASCENDING);
		this.addFarInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.MID_SLICE_NORMAL_DESCENDING);
		this.addFarInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.MID_SLICE_VARIANT_ASCENDING);
		this.addFarInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.MID_SLICE_VARIANT_DESCENDING);
		// 深陆-高山
		// E=1: T=[3, 4]
		if(tem34){
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.HIGH_SLICE_NORMAL_DESCENDING);
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.HIGH_SLICE_VARIANT_DESCENDING);
		}
		// E=[2, 3]
		this.addFarInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
		this.addFarInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.HIGH_SLICE_NORMAL_DESCENDING);
		this.addFarInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
		this.addFarInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.HIGH_SLICE_VARIANT_DESCENDING);
		
		this.addFarInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
		this.addFarInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.HIGH_SLICE_NORMAL_DESCENDING);
		this.addFarInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
		this.addFarInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.HIGH_SLICE_VARIANT_DESCENDING);
		// 深陆-山峰
		// E=[2, 3]
		this.addFarInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.PEAK_NORMAL);
		this.addFarInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.PEAK_VARIANT);
		
		this.addFarInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.PEAK_NORMAL);
		this.addFarInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.PEAK_VARIANT);
	}
	
	public OverworldRegion addShattered(ResourceLocation name) {
		
	}
	
	public OverworldRegion addUnderground(ResourceLocation name) {
		
	}
	
	/**
	 * 添加沼泽类生物群系
	 *
	 * @param name
	 * @param temperature
	 * @return this
	 */
	public OverworldRegion addSwamp(ResourceLocation name, Temperature temperature) {
		// 准内陆谷地
		ParameterPointListBuilder nearInlandValley = new ParameterPointListBuilder();
		nearInlandValley.continentalness(Continentalness.NEAR_INLAND)
						.temperature(temperature)
						.humidity(Humidity.FULL_RANGE)
						.erosion(Erosion.EROSION_6)
						.weirdness(Weirdness.VALLEY)
						.depth(Depth.SURFACE);
		this.list.add(new Pair<>(nearInlandValley.build(), this.createBiomeKey(name)));
		// 准内陆低地带
		ParameterPointListBuilder nearInlandLow = new ParameterPointListBuilder();
		nearInlandLow.continentalness(Continentalness.NEAR_INLAND)
					 .temperature(temperature)
					 .humidity(Humidity.FULL_RANGE)
					 .erosion(Erosion.EROSION_6)
					 .weirdness(Weirdness.LOW_SLICE_VARIANT_ASCENDING, Weirdness.LOW_SLICE_NORMAL_DESCENDING)
					 .depth(Depth.SURFACE);
		this.list.add(new Pair<>(nearInlandLow.build(), this.createBiomeKey(name)));
		// 准内陆低山带
		ParameterPointListBuilder nearInlandMid = new ParameterPointListBuilder();
		nearInlandMid.continentalness(Continentalness.NEAR_INLAND)
					 .temperature(temperature)
					 .humidity(Humidity.FULL_RANGE)
					 .erosion(Erosion.EROSION_6)
					 .weirdness(Weirdness.MID_SLICE_VARIANT_ASCENDING, Weirdness.MID_SLICE_VARIANT_DESCENDING, Weirdness.MID_SLICE_NORMAL_DESCENDING, Weirdness.MID_SLICE_NORMAL_ASCENDING)
					 .depth(Depth.SURFACE);
		this.list.add(new Pair<>(nearInlandMid.build(), this.createBiomeKey(name)));
		// 内陆低地带
		ParameterPointListBuilder midInlandLow = new ParameterPointListBuilder();
		midInlandLow.continentalness(Continentalness.MID_INLAND)
					.temperature(temperature)
					.humidity(Humidity.FULL_RANGE)
					.erosion(Erosion.EROSION_6)
					.weirdness(Weirdness.LOW_SLICE_VARIANT_ASCENDING, Weirdness.LOW_SLICE_NORMAL_DESCENDING)
					.depth(Depth.SURFACE);
		this.list.add(new Pair<>(midInlandLow.build(), this.createBiomeKey(name)));
		// 内陆低山带
		ParameterPointListBuilder midInlandMid = new ParameterPointListBuilder();
		midInlandMid.continentalness(Continentalness.MID_INLAND)
					.temperature(temperature)
					.humidity(Humidity.FULL_RANGE)
					.erosion(Erosion.EROSION_6)
					.weirdness(Weirdness.MID_SLICE_VARIANT_ASCENDING, Weirdness.MID_SLICE_VARIANT_DESCENDING, Weirdness.MID_SLICE_NORMAL_ASCENDING, Weirdness.MID_SLICE_NORMAL_DESCENDING)
					.depth(Depth.SURFACE);
		this.list.add(new Pair<>(midInlandMid.build(), this.createBiomeKey(name)));
		// 深内陆低地带
		ParameterPointListBuilder farInlandLow = new ParameterPointListBuilder();
		farInlandLow.continentalness(Continentalness.FAR_INLAND)
					.temperature(temperature)
					.humidity(Humidity.FULL_RANGE)
					.erosion(Erosion.EROSION_6)
					.weirdness(Weirdness.LOW_SLICE_VARIANT_ASCENDING, Weirdness.LOW_SLICE_NORMAL_DESCENDING)
					.depth(Depth.SURFACE);
		this.list.add(new Pair<>(farInlandLow.build(), this.createBiomeKey(name)));
		// 深内陆低山带
		ParameterPointListBuilder farInlandMid = new ParameterPointListBuilder();
		farInlandMid.continentalness(Continentalness.MID_INLAND)
					.temperature(temperature)
					.humidity(Humidity.FULL_RANGE)
					.erosion(Erosion.EROSION_6)
					.weirdness(Weirdness.MID_SLICE_VARIANT_ASCENDING, Weirdness.MID_SLICE_VARIANT_DESCENDING, Weirdness.MID_SLICE_NORMAL_DESCENDING, Weirdness.MID_SLICE_NORMAL_ASCENDING)
					.depth(Depth.SURFACE);
		this.list.add(new Pair<>(farInlandMid.build(), this.createBiomeKey(name)));
		return this;
	}
	
	private ResourceKey<Biome> createBiomeKey(ResourceLocation name) {
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
		 *
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