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
import terrablender.api.VanillaParameterOverlayBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class OverworldRegion extends Region {
	// 大陆性: 溶洞
	public static final Parameter C_DRIPSTONE_CAVES = Parameter.span(0.8F, 1.0F);
//	// 大陆性: 繁茂洞穴
//	public static final Parameter C_LUSH_CAVES = Parameter.span(-1.0F, 0.8F);
	// 湿度值: 繁茂洞穴
	public static final Parameter H_LUSH_CAVES = Parameter.span(0.7F, 1.0F);
	// 湿度值: 洞穴
	public static final Parameter H_CAVES = Parameter.span(-1.0F, 0.7F);
	// 深度值: 地表
	public static final Parameter D_SURFACE = Parameter.span(-1.0F, 0.0F);
	// 深度值: 深暗之域
	public static final Parameter D_DEEP_DARK = Parameter.point(1.1F);
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
		VanillaParameterOverlayBuilder builder = new VanillaParameterOverlayBuilder();
		this.list.forEach(pointListAndBiome -> {
			pointListAndBiome.getFirst().forEach(point -> {
				ResourceKey<Biome> biome = pointListAndBiome.getSecond();
				builder.add(point, biome);
			});
		});
		builder.build().forEach(mapper);
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
			   .depth(D_SURFACE);
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
	
	private void addCoast(ResourceLocation name, Temperature temperature, Humidity humidity, Erosion erosion, Weirdness weirdness) {
		ParameterPointListBuilder builder = new ParameterPointListBuilder();
		builder.continentalness(Continentalness.COAST)
			   .temperature(temperature)
			   .humidity(humidity)
			   .erosion(erosion)
			   .weirdness(weirdness)
			   .depth(Depth.SURFACE);
		this.list.add(new Pair<>(builder.build(), this.createBiomeKey(name)));
	}
	
	private void addNearInland(ResourceLocation name, Temperature temperature, Humidity humidity, Erosion erosion, Weirdness weirdness) {
		ParameterPointListBuilder builder = new ParameterPointListBuilder();
		builder.continentalness(Continentalness.NEAR_INLAND)
			   .temperature(temperature)
			   .humidity(humidity)
			   .erosion(erosion)
			   .weirdness(weirdness)
			   .depth(Depth.SURFACE);
		this.list.add(new Pair<>(builder.build(), this.createBiomeKey(name)));
	}
	
	private void addMidInland(ResourceLocation name, Temperature temperature, Humidity humidity, Erosion erosion, Weirdness weirdness) {
		ParameterPointListBuilder builder = new ParameterPointListBuilder();
		builder.continentalness(Continentalness.MID_INLAND)
			   .temperature(temperature)
			   .humidity(humidity)
			   .erosion(erosion)
			   .weirdness(weirdness)
			   .depth(Depth.SURFACE);
		this.list.add(new Pair<>(builder.build(), this.createBiomeKey(name)));
	}
	
	private void addFarInland(ResourceLocation name, Temperature temperature, Humidity humidity, Erosion erosion, Weirdness weirdness) {
		ParameterPointListBuilder builder = new ParameterPointListBuilder();
		builder.continentalness(Continentalness.FAR_INLAND)
			   .temperature(temperature)
			   .humidity(humidity)
			   .erosion(erosion)
			   .weirdness(weirdness)
			   .depth(Depth.SURFACE);
		this.list.add(new Pair<>(builder.build(), this.createBiomeKey(name)));
	}
	
	private void addValley(ResourceLocation name, Continentalness continentalness, Temperature temperature, Humidity humidity, Erosion erosion) {
		ParameterPointListBuilder builder = new ParameterPointListBuilder();
		builder.continentalness(continentalness)
			   .temperature(temperature)
			   .humidity(humidity)
			   .erosion(erosion)
			   .weirdness(Weirdness.VALLEY)
			   .depth(Depth.SURFACE);
		this.list.add(new Pair<>(builder.build(), this.createBiomeKey(name)));
	}
	
	private void addLow(ResourceLocation name, Continentalness continentalness, Temperature temperature, Humidity humidity, Erosion erosion, boolean isVariant) {
		ParameterPointListBuilder builder = new ParameterPointListBuilder();
		if (isVariant) {
			builder.continentalness(continentalness)
				   .temperature(temperature)
				   .humidity(humidity)
				   .erosion(erosion)
				   .weirdness(Weirdness.LOW_SLICE_VARIANT_ASCENDING)
				   .depth(Depth.SURFACE);
			this.list.add(new Pair<>(builder.build(), this.createBiomeKey(name)));
		} else {
			builder.continentalness(continentalness)
				   .temperature(temperature)
				   .humidity(humidity)
				   .erosion(erosion)
				   .weirdness(Weirdness.LOW_SLICE_NORMAL_DESCENDING)
				   .depth(Depth.SURFACE);
			this.list.add(new Pair<>(builder.build(), this.createBiomeKey(name)));
		}
		
	}
	
	private void addMid(ResourceLocation name, Continentalness continentalness, Temperature temperature, Humidity humidity, Erosion erosion, boolean isVariant) {
		ParameterPointListBuilder builder = new ParameterPointListBuilder();
		if (isVariant) {
			builder.continentalness(continentalness)
				   .temperature(temperature)
				   .humidity(humidity)
				   .erosion(erosion)
				   .weirdness(Weirdness.MID_SLICE_VARIANT_ASCENDING, Weirdness.MID_SLICE_VARIANT_DESCENDING)
				   .depth(Depth.SURFACE);
			this.list.add(new Pair<>(builder.build(), this.createBiomeKey(name)));
		} else {
			builder.continentalness(continentalness)
				   .temperature(temperature)
				   .humidity(humidity)
				   .erosion(erosion)
				   .weirdness(Weirdness.MID_SLICE_NORMAL_ASCENDING, Weirdness.MID_SLICE_NORMAL_DESCENDING)
				   .depth(Depth.SURFACE);
			this.list.add(new Pair<>(builder.build(), this.createBiomeKey(name)));
		}
	}
	
	private void addHigh(ResourceLocation name, Continentalness continentalness, Temperature temperature, Humidity humidity, Erosion erosion, boolean isVariant) {
		ParameterPointListBuilder builder = new ParameterPointListBuilder();
		if (isVariant) {
			builder.continentalness(continentalness)
				   .temperature(temperature)
				   .humidity(humidity)
				   .erosion(erosion)
				   .weirdness(Weirdness.HIGH_SLICE_VARIANT_ASCENDING, Weirdness.HIGH_SLICE_VARIANT_DESCENDING)
				   .depth(Depth.SURFACE);
			this.list.add(new Pair<>(builder.build(), this.createBiomeKey(name)));
		} else {
			builder.continentalness(continentalness)
				   .temperature(temperature)
				   .humidity(humidity)
				   .erosion(erosion)
				   .weirdness(Weirdness.HIGH_SLICE_NORMAL_ASCENDING, Weirdness.HIGH_SLICE_NORMAL_DESCENDING)
				   .depth(Depth.SURFACE);
			this.list.add(new Pair<>(builder.build(), this.createBiomeKey(name)));
		}
	}
	
	private void addPeak(ResourceLocation name, Continentalness continentalness, Temperature temperature, Humidity humidity, Erosion erosion, boolean isVariant) {
		ParameterPointListBuilder builder = new ParameterPointListBuilder();
		if (isVariant) {
			builder.continentalness(continentalness)
				   .temperature(temperature)
				   .humidity(humidity)
				   .erosion(erosion)
				   .weirdness(Weirdness.PEAK_VARIANT)
				   .depth(Depth.SURFACE);
		} else {
			builder.continentalness(continentalness)
				   .temperature(temperature)
				   .humidity(humidity)
				   .erosion(erosion)
				   .weirdness(Weirdness.PEAK_NORMAL)
				   .depth(Depth.SURFACE);
		}
		this.list.add(new Pair<>(builder.build(), this.createBiomeKey(name)));
	}
	
	/**
	 * 添加河流类生物群系
	 *
	 * @param name
	 * @param temperature
	 * @param humidity
	 * @return
	 */
	private OverworldRegion addRiverBiome(ResourceLocation name, Temperature temperature, Humidity humidity) {
		// 条件
		boolean tem0 = (temperature == Temperature.ICY || temperature == Temperature.FROZEN);
		// Only Valley
		// 沿岸
		this.addValley(name, Continentalness.COAST, temperature, humidity, Erosion.FULL_RANGE);
		// 准陆 E=[0, 5]  |  E=6, T=0
		this.addValley(name, Continentalness.NEAR_INLAND, temperature, humidity, Erosion.EROSION_0);
		this.addValley(name, Continentalness.NEAR_INLAND, temperature, humidity, Erosion.EROSION_1);
		this.addValley(name, Continentalness.NEAR_INLAND, temperature, humidity, Erosion.EROSION_2);
		this.addValley(name, Continentalness.NEAR_INLAND, temperature, humidity, Erosion.EROSION_3);
		this.addValley(name, Continentalness.NEAR_INLAND, temperature, humidity, Erosion.EROSION_4);
		this.addValley(name, Continentalness.NEAR_INLAND, temperature, humidity, Erosion.EROSION_5);
		if (tem0) {
			this.addValley(name, Continentalness.NEAR_INLAND, temperature, humidity, Erosion.EROSION_6);
		}
		// 内陆 E=[2..5]  |  E=6, T=0
		this.addValley(name, Continentalness.MID_INLAND, temperature, humidity, Erosion.EROSION_2);
		this.addValley(name, Continentalness.MID_INLAND, temperature, humidity, Erosion.EROSION_3);
		this.addValley(name, Continentalness.MID_INLAND, temperature, humidity, Erosion.EROSION_4);
		this.addValley(name, Continentalness.MID_INLAND, temperature, humidity, Erosion.EROSION_5);
		if (tem0) {
			this.addValley(name, Continentalness.MID_INLAND, temperature, humidity, Erosion.EROSION_6);
		}
		// 深陆 E=[2..5]  |  E=6, T=0
		this.addValley(name, Continentalness.FAR_INLAND, temperature, humidity, Erosion.EROSION_2);
		this.addValley(name, Continentalness.FAR_INLAND, temperature, humidity, Erosion.EROSION_3);
		this.addValley(name, Continentalness.FAR_INLAND, temperature, humidity, Erosion.EROSION_4);
		this.addValley(name, Continentalness.FAR_INLAND, temperature, humidity, Erosion.EROSION_5);
		if (tem0) {
			this.addValley(name, Continentalness.FAR_INLAND, temperature, humidity, Erosion.EROSION_6);
		}
		return this;
	}
	
	/**
	 * 添加石岸类生物群系
	 *
	 * @param name
	 * @param temperature
	 * @param humidity
	 * @param isVariant
	 * @return
	 */
	public OverworldRegion addStonyShore(ResourceLocation name, Temperature temperature, Humidity humidity, boolean isVariant) {
		// Only Coast 沿岸
		// 低地带 E=[0, 1]
		if (isVariant) {
			this.addCoast(name, temperature, humidity, Erosion.EROSION_0, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		} else {
			this.addCoast(name, temperature, humidity, Erosion.EROSION_0, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
		}
		// 低山带 E=[0, 1, 2]
		if (isVariant) {
			this.addCoast(name, temperature, humidity, Erosion.EROSION_0, Weirdness.MID_SLICE_VARIANT_ASCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_0, Weirdness.MID_SLICE_VARIANT_DESCENDING);
			
			this.addCoast(name, temperature, humidity, Erosion.EROSION_1, Weirdness.MID_SLICE_VARIANT_ASCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_1, Weirdness.MID_SLICE_VARIANT_DESCENDING);
			
			this.addCoast(name, temperature, humidity, Erosion.EROSION_2, Weirdness.MID_SLICE_VARIANT_ASCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_2, Weirdness.MID_SLICE_VARIANT_DESCENDING);
		} else {
			this.addCoast(name, temperature, humidity, Erosion.EROSION_0, Weirdness.MID_SLICE_NORMAL_ASCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_0, Weirdness.MID_SLICE_NORMAL_DESCENDING);
			
			this.addCoast(name, temperature, humidity, Erosion.EROSION_1, Weirdness.MID_SLICE_NORMAL_ASCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_1, Weirdness.MID_SLICE_NORMAL_DESCENDING);
			
			this.addCoast(name, temperature, humidity, Erosion.EROSION_2, Weirdness.MID_SLICE_NORMAL_ASCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_2, Weirdness.MID_SLICE_NORMAL_DESCENDING);
		}
		return this;
	}
	
	/**
	 * 添加滩涂类生物群系
	 *
	 * @param name
	 * @param temperature
	 * @param humidity
	 * @return
	 */
	public OverworldRegion addBeachBiome(ResourceLocation name, Temperature temperature, Humidity humidity, boolean isVariant) {
		// Only Coast 沿岸
		// 谷地-null
		// 低地 E=[3, 4]  |  E=5, W<0  |  E=6
		if (isVariant) {
			this.addCoast(name, temperature, humidity, Erosion.EROSION_3, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_4, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_6, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		} else {
			this.addCoast(name, temperature, humidity, Erosion.EROSION_3, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_4, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_5, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_6, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
		}
		// 低山 E=4: W<0  |  E=5: W<0  |  E=6: W<0
		if (!isVariant) {
			this.addCoast(name, temperature, humidity, Erosion.EROSION_4, Weirdness.MID_SLICE_NORMAL_ASCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_4, Weirdness.MID_SLICE_NORMAL_DESCENDING);
			
			this.addCoast(name, temperature, humidity, Erosion.EROSION_5, Weirdness.MID_SLICE_NORMAL_ASCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_5, Weirdness.MID_SLICE_NORMAL_DESCENDING);
			
			this.addCoast(name, temperature, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_NORMAL_ASCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_NORMAL_DESCENDING);
		}
		// 高山-null
		// 山峰-null
		return this;
	}
	
	/**
	 * 恶地类生物群系
	 *
	 * @param name
	 * @param humidity
	 * @return
	 */
	private OverworldRegion addBadlandBiome(ResourceLocation name, Humidity humidity, boolean isVariant) {
		// 沿岸-谷地-null
		// 沿岸-低地-null
		// 沿岸-低山-null
		// 沿岸-高山-null
		// 沿岸-山峰 E=0, T=4  |  E=1, T=4
		if (isVariant) {
			this.addCoast(name, Temperature.HOT, humidity, Erosion.EROSION_0, Weirdness.PEAK_VARIANT);
			this.addCoast(name, Temperature.HOT, humidity, Erosion.EROSION_1, Weirdness.PEAK_VARIANT);
		} else {
			this.addCoast(name, Temperature.HOT, humidity, Erosion.EROSION_0, Weirdness.PEAK_NORMAL);
			this.addCoast(name, Temperature.HOT, humidity, Erosion.EROSION_1, Weirdness.PEAK_NORMAL);
		}
		// 准陆-谷地-null
		// 准陆-低地 E=[0, 1], T=4
		if (isVariant) {
			this.addNearInland(name, Temperature.HOT, humidity, Erosion.EROSION_0, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
			this.addNearInland(name, Temperature.HOT, humidity, Erosion.EROSION_1, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		} else {
			this.addNearInland(name, Temperature.HOT, humidity, Erosion.EROSION_0, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
			this.addNearInland(name, Temperature.HOT, humidity, Erosion.EROSION_1, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
		}
		// 准陆-低山 E=1, T=4
		if (isVariant) {
			this.addNearInland(name, Temperature.HOT, humidity, Erosion.EROSION_1, Weirdness.MID_SLICE_VARIANT_ASCENDING);
			this.addNearInland(name, Temperature.HOT, humidity, Erosion.EROSION_1, Weirdness.MID_SLICE_VARIANT_DESCENDING);
		} else {
			this.addNearInland(name, Temperature.HOT, humidity, Erosion.EROSION_1, Weirdness.MID_SLICE_NORMAL_ASCENDING);
			this.addNearInland(name, Temperature.HOT, humidity, Erosion.EROSION_1, Weirdness.MID_SLICE_NORMAL_DESCENDING);
		}
		// 准陆-高山 E=1, T=4
		if (isVariant) {
			this.addNearInland(name, Temperature.HOT, humidity, Erosion.EROSION_1, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
			this.addNearInland(name, Temperature.HOT, humidity, Erosion.EROSION_1, Weirdness.HIGH_SLICE_VARIANT_DESCENDING);
		} else {
			this.addNearInland(name, Temperature.HOT, humidity, Erosion.EROSION_1, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
			this.addNearInland(name, Temperature.HOT, humidity, Erosion.EROSION_1, Weirdness.HIGH_SLICE_NORMAL_DESCENDING);
		}
		// 准陆-山峰 E=[0, 1], T=4
		if (isVariant) {
			this.addNearInland(name, Temperature.HOT, humidity, Erosion.EROSION_0, Weirdness.PEAK_VARIANT);
			this.addNearInland(name, Temperature.HOT, humidity, Erosion.EROSION_1, Weirdness.PEAK_VARIANT);
		} else {
			this.addNearInland(name, Temperature.HOT, humidity, Erosion.EROSION_0, Weirdness.PEAK_NORMAL);
			this.addNearInland(name, Temperature.HOT, humidity, Erosion.EROSION_1, Weirdness.PEAK_NORMAL);
		}
		// 内陆-谷地 E=[0, 1], T=4
		this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_0, Weirdness.VALLEY);
		this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_1, Weirdness.VALLEY);
		// 内陆-低地E=[0, 1], T=4  |  E=[2, 3], T=4
		if (isVariant) {
			this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_0, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
			this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_1, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
			this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_2, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
			this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_3, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		} else {
			this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_0, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
			this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_1, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
			this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_2, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
			this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_3, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
		}
		// 内陆-低山 E=1, T=4  |  E=3, T=4
		if (isVariant) {
			this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_1, Weirdness.MID_SLICE_VARIANT_ASCENDING);
			this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_1, Weirdness.MID_SLICE_VARIANT_DESCENDING);
			this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_3, Weirdness.MID_SLICE_VARIANT_ASCENDING);
			this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_3, Weirdness.MID_SLICE_VARIANT_DESCENDING);
		} else {
			this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_1, Weirdness.MID_SLICE_NORMAL_ASCENDING);
			this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_1, Weirdness.MID_SLICE_NORMAL_DESCENDING);
			this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_3, Weirdness.MID_SLICE_NORMAL_ASCENDING);
			this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_3, Weirdness.MID_SLICE_NORMAL_DESCENDING);
		}
		// 内陆-高山 E=0, T=4  |  E=3, T=4
		if (isVariant) {
			this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_0, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
			this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_0, Weirdness.HIGH_SLICE_VARIANT_DESCENDING);
			this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_3, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
			this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_3, Weirdness.HIGH_SLICE_VARIANT_DESCENDING);
		} else {
			this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_0, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
			this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_0, Weirdness.HIGH_SLICE_NORMAL_DESCENDING);
			this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_3, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
			this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_3, Weirdness.HIGH_SLICE_NORMAL_DESCENDING);
		}
		// 内陆-山峰 E=[0, 1], T=4  |  E=3, T=4
		if (isVariant) {
			this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_0, Weirdness.PEAK_VARIANT);
			this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_0, Weirdness.PEAK_VARIANT);
			this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_3, Weirdness.PEAK_VARIANT);
		} else {
			this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_0, Weirdness.PEAK_NORMAL);
			this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_0, Weirdness.PEAK_NORMAL);
			this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_3, Weirdness.PEAK_NORMAL);
		}
		// 深陆-谷地 E=[0, 1], T=4
		this.addFarInland(name, Temperature.HOT, humidity, Erosion.EROSION_0, Weirdness.VALLEY);
		this.addFarInland(name, Temperature.HOT, humidity, Erosion.EROSION_1, Weirdness.VALLEY);
		// 深陆-低地 E=[0, 1, 2, 3], T=4
		if (isVariant) {
			this.addFarInland(name, Temperature.HOT, humidity, Erosion.EROSION_0, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
			this.addFarInland(name, Temperature.HOT, humidity, Erosion.EROSION_1, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
			this.addFarInland(name, Temperature.HOT, humidity, Erosion.EROSION_2, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
			this.addFarInland(name, Temperature.HOT, humidity, Erosion.EROSION_3, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		} else {
			this.addFarInland(name, Temperature.HOT, humidity, Erosion.EROSION_0, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
			this.addFarInland(name, Temperature.HOT, humidity, Erosion.EROSION_1, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
			this.addFarInland(name, Temperature.HOT, humidity, Erosion.EROSION_2, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
			this.addFarInland(name, Temperature.HOT, humidity, Erosion.EROSION_3, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
		}
		// 深陆-低山 E=3, T=4
		if (isVariant) {
			this.addFarInland(name, Temperature.HOT, humidity, Erosion.EROSION_3, Weirdness.MID_SLICE_VARIANT_ASCENDING);
			this.addFarInland(name, Temperature.HOT, humidity, Erosion.EROSION_3, Weirdness.MID_SLICE_VARIANT_ASCENDING);
		} else {
			this.addFarInland(name, Temperature.HOT, humidity, Erosion.EROSION_3, Weirdness.MID_SLICE_NORMAL_ASCENDING);
			this.addFarInland(name, Temperature.HOT, humidity, Erosion.EROSION_3, Weirdness.MID_SLICE_NORMAL_DESCENDING);
		}
		// 深陆-高山 E=0, T=4
		if (isVariant) {
			this.addFarInland(name, Temperature.HOT, humidity, Erosion.EROSION_0, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
			this.addFarInland(name, Temperature.HOT, humidity, Erosion.EROSION_0, Weirdness.HIGH_SLICE_VARIANT_DESCENDING);
		} else {
			this.addFarInland(name, Temperature.HOT, humidity, Erosion.EROSION_0, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
			this.addFarInland(name, Temperature.HOT, humidity, Erosion.EROSION_0, Weirdness.HIGH_SLICE_NORMAL_DESCENDING);
		}
		// 深陆-山峰 E=[0, 1], T=4
		if (isVariant) {
			this.addFarInland(name, Temperature.HOT, humidity, Erosion.EROSION_0, Weirdness.PEAK_VARIANT);
			this.addFarInland(name, Temperature.HOT, humidity, Erosion.EROSION_1, Weirdness.PEAK_VARIANT);
		} else {
			this.addFarInland(name, Temperature.HOT, humidity, Erosion.EROSION_0, Weirdness.PEAK_NORMAL);
			this.addFarInland(name, Temperature.HOT, humidity, Erosion.EROSION_1, Weirdness.PEAK_NORMAL);
		}
		return this;
	}
	
	/**
	 * 低山带类生物群系
	 *
	 * @param name
	 * @param temperature
	 * @param humidity
	 * @param isVariant
	 * @return
	 */
	private OverworldRegion addMiddleBiome(ResourceLocation name, Temperature temperature, Humidity humidity, boolean isVariant) {
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
		if ((isVariant && tem01)) {
			this.addCoast(name, temperature, humidity, Erosion.EROSION_5, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		} else if (hum4) {
			if (isVariant) {
				this.addCoast(name, temperature, humidity, Erosion.EROSION_5, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
			} else {
				this.addCoast(name, temperature, humidity, Erosion.EROSION_5, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
			}
		}
		// 沿岸-低山
		// E=4: W>0
		if (isVariant) {
			this.addCoast(name, temperature, humidity, Erosion.EROSION_4, Weirdness.MID_SLICE_VARIANT_DESCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_4, Weirdness.MID_SLICE_VARIANT_ASCENDING);
		}
		// E=5: W>0, T=[0, 1] || H=4
		if (isVariant && tem01) {
			this.addCoast(name, temperature, humidity, Erosion.EROSION_5, Weirdness.MID_SLICE_VARIANT_DESCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_5, Weirdness.MID_SLICE_VARIANT_ASCENDING);
		} else if (hum4) {
			if (isVariant) {
				this.addCoast(name, temperature, humidity, Erosion.EROSION_5, Weirdness.MID_SLICE_VARIANT_DESCENDING);
				this.addCoast(name, temperature, humidity, Erosion.EROSION_5, Weirdness.MID_SLICE_VARIANT_ASCENDING);
			} else {
				this.addCoast(name, temperature, humidity, Erosion.EROSION_5, Weirdness.MID_SLICE_NORMAL_ASCENDING);
				this.addCoast(name, temperature, humidity, Erosion.EROSION_5, Weirdness.MID_SLICE_NORMAL_DESCENDING);
			}
		}
		// E=6: W>0
		if (isVariant) {
			this.addCoast(name, temperature, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_VARIANT_DESCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_VARIANT_ASCENDING);
		}
		// 沿岸-高山
		// E=[0..4]
		if (isVariant) {
			this.addCoast(name, temperature, humidity, Erosion.EROSION_0, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_0, Weirdness.HIGH_SLICE_VARIANT_DESCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_1, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_1, Weirdness.HIGH_SLICE_VARIANT_DESCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_2, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_2, Weirdness.HIGH_SLICE_VARIANT_DESCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_3, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_3, Weirdness.HIGH_SLICE_VARIANT_DESCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_4, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_4, Weirdness.HIGH_SLICE_VARIANT_DESCENDING);
		} else {
			this.addCoast(name, temperature, humidity, Erosion.EROSION_0, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_0, Weirdness.HIGH_SLICE_NORMAL_DESCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_1, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_1, Weirdness.HIGH_SLICE_NORMAL_DESCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_2, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_2, Weirdness.HIGH_SLICE_NORMAL_DESCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_3, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_3, Weirdness.HIGH_SLICE_NORMAL_DESCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_4, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_4, Weirdness.HIGH_SLICE_NORMAL_DESCENDING);
		}
		
		// E=5: W<0, T=[0, 1] || H=4
		if (!isVariant && tem01) {
			this.addCoast(name, temperature, humidity, Erosion.EROSION_5, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_5, Weirdness.HIGH_SLICE_NORMAL_DESCENDING);
		} else if (hum4) {
			if (isVariant) {
				this.addCoast(name, temperature, humidity, Erosion.EROSION_5, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
				this.addCoast(name, temperature, humidity, Erosion.EROSION_5, Weirdness.HIGH_SLICE_VARIANT_DESCENDING);
			} else {
				this.addCoast(name, temperature, humidity, Erosion.EROSION_5, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
				this.addCoast(name, temperature, humidity, Erosion.EROSION_5, Weirdness.HIGH_SLICE_NORMAL_DESCENDING);
			}
		}
		// E=6
		if (isVariant) {
			this.addCoast(name, temperature, humidity, Erosion.EROSION_6, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_6, Weirdness.HIGH_SLICE_VARIANT_DESCENDING);
		} else {
			this.addCoast(name, temperature, humidity, Erosion.EROSION_6, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_6, Weirdness.HIGH_SLICE_NORMAL_DESCENDING);
		}
		// 沿岸-山峰
		// E=1: 0<T<4
		if (tem0_4) {
			if (isVariant) {
				this.addCoast(name, temperature, humidity, Erosion.EROSION_1, Weirdness.PEAK_VARIANT);
			} else {
				this.addCoast(name, temperature, humidity, Erosion.EROSION_1, Weirdness.PEAK_NORMAL);
			}
		}
		// E=[2..4]
		if (isVariant) {
			this.addCoast(name, temperature, humidity, Erosion.EROSION_2, Weirdness.PEAK_VARIANT);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_3, Weirdness.PEAK_VARIANT);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_4, Weirdness.PEAK_VARIANT);
		} else {
			this.addCoast(name, temperature, humidity, Erosion.EROSION_2, Weirdness.PEAK_NORMAL);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_3, Weirdness.PEAK_NORMAL);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_4, Weirdness.PEAK_NORMAL);
		}
		// E=6
		if (isVariant) {
			this.addCoast(name, temperature, humidity, Erosion.EROSION_6, Weirdness.PEAK_VARIANT);
		} else {
			this.addCoast(name, temperature, humidity, Erosion.EROSION_6, Weirdness.PEAK_NORMAL);
		}
		// 准陆-谷地-null
		// 准陆-低地
		// E=[0, 1]: T<4
		if (tem_4) {
			if (isVariant) {
				this.addNearInland(name, temperature, humidity, Erosion.EROSION_0, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
				this.addNearInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
			} else {
				this.addNearInland(name, temperature, humidity, Erosion.EROSION_0, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
				this.addNearInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
			}
		}
		// E=[2..4]
		if (isVariant) {
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		} else {
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
		}
		// E=5: W<0 || T=[0, 1] || H=4
		if (!isVariant) {
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
		} else if (tem01 || hum4) {
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		}
		// E=6: T=0
		if (tem0) {
			if (isVariant) {
				this.addNearInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
			} else {
				this.addNearInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
			}
		}
		// 准陆-低山
		// E=1: 0<T<4
		if (tem0_4) {
			if (isVariant) {
				this.addNearInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.MID_SLICE_VARIANT_DESCENDING);
				this.addNearInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.MID_SLICE_VARIANT_ASCENDING);
			} else {
				this.addNearInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.MID_SLICE_NORMAL_ASCENDING);
				this.addNearInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.MID_SLICE_NORMAL_DESCENDING);
			}
		}
		// E=[2..4]
		if (isVariant) {
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.MID_SLICE_VARIANT_ASCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.MID_SLICE_VARIANT_DESCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.MID_SLICE_VARIANT_ASCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.MID_SLICE_VARIANT_DESCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.MID_SLICE_VARIANT_ASCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.MID_SLICE_VARIANT_DESCENDING);
		} else {
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.MID_SLICE_NORMAL_ASCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.MID_SLICE_NORMAL_DESCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.MID_SLICE_NORMAL_ASCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.MID_SLICE_NORMAL_DESCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.MID_SLICE_NORMAL_ASCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.MID_SLICE_NORMAL_DESCENDING);
		}
		// E=5: W<0 || T=[0, 1] || H=4
		if (!isVariant) {
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.MID_SLICE_NORMAL_ASCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.MID_SLICE_NORMAL_DESCENDING);
		} else if (tem01 || hum4) {
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.MID_SLICE_VARIANT_ASCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.MID_SLICE_VARIANT_DESCENDING);
		}
		// E=6: T=0
		if (tem0) {
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_NORMAL_ASCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_NORMAL_DESCENDING);
			if (isVariant) {
				this.addNearInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_VARIANT_ASCENDING);
				this.addNearInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_VARIANT_DESCENDING);
			}
		}
		// 准陆-高山
		// E=1: 0<T<4
		if (tem0_4) {
			if (isVariant) {
				this.addNearInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.HIGH_SLICE_VARIANT_DESCENDING);
				this.addNearInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
			} else {
				this.addNearInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
				this.addNearInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.HIGH_SLICE_NORMAL_DESCENDING);
			}
		}
		// E=[2..4]
		if (isVariant) {
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.HIGH_SLICE_VARIANT_DESCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.HIGH_SLICE_VARIANT_DESCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.HIGH_SLICE_VARIANT_DESCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
		} else {
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.HIGH_SLICE_NORMAL_DESCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.HIGH_SLICE_NORMAL_DESCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.HIGH_SLICE_NORMAL_DESCENDING);
		}
		// E=5: W<0 || T=[0, 1] || H=4
		if (!isVariant) {
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.HIGH_SLICE_NORMAL_DESCENDING);
		} else if (tem01 || hum4) {
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.HIGH_SLICE_VARIANT_DESCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
		}
		// E=6
		if (isVariant) {
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.HIGH_SLICE_VARIANT_DESCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
		} else {
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.HIGH_SLICE_NORMAL_DESCENDING);
		}
		// 准陆-山峰
		// E=1: 0<T<4
		if (tem0_4) {
			if (isVariant) {
				this.addNearInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.PEAK_VARIANT);
			} else {
				this.addNearInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.PEAK_NORMAL);
			}
		}
		// E=[2..4]
		if (isVariant) {
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.PEAK_VARIANT);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.PEAK_VARIANT);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.PEAK_VARIANT);
		} else {
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.PEAK_NORMAL);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.PEAK_NORMAL);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.PEAK_NORMAL);
		}
		// E=6
		if (isVariant) {
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.PEAK_VARIANT);
		} else {
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.PEAK_NORMAL);
			
		}
		// 内陆-谷地
		// E=[0, 1]: T<4
		if (tem_4) {
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_0, Weirdness.VALLEY);
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.VALLEY);
		}
		// 内陆-低地
		// E=[0, 1]: 0<T<4
		if (tem0_4) {
			
			if (isVariant) {
				this.addMidInland(name, temperature, humidity, Erosion.EROSION_0, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
				this.addMidInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
			} else {
				this.addMidInland(name, temperature, humidity, Erosion.EROSION_0, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
				this.addMidInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
			}
		}
		// E=[2, 3]: T<4
		if (tem_4) {
			
			if (isVariant) {
				this.addMidInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
				this.addMidInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
			} else {
				this.addMidInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
				this.addMidInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
			}
		}
		// E=[4, 5]
		if (isVariant) {
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		} else {
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
		}
		// E=6: T=0
		if (tem0) {
			if (isVariant) {
				this.addMidInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
			} else {
				this.addMidInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
				
			}
		}
		// 内陆-低山
		// E=1: 0<T<4
		if (tem0_4) {
			
			if (isVariant) {
				this.addMidInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.MID_SLICE_VARIANT_ASCENDING);
				this.addMidInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.MID_SLICE_VARIANT_DESCENDING
				);
			} else {
				this.addMidInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.MID_SLICE_NORMAL_ASCENDING);
				this.addMidInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.MID_SLICE_NORMAL_DESCENDING
				);
			}
		}
		// E=[2, 3]: T<4
		if (tem_4) {
			
			if (isVariant) {
				this.addMidInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.MID_SLICE_VARIANT_ASCENDING);
				this.addMidInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.MID_SLICE_VARIANT_DESCENDING
				);
				this.addMidInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.MID_SLICE_VARIANT_ASCENDING);
				this.addMidInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.MID_SLICE_VARIANT_DESCENDING
				);
			} else {
				this.addMidInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.MID_SLICE_NORMAL_ASCENDING);
				this.addMidInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.MID_SLICE_NORMAL_DESCENDING
				);
				this.addMidInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.MID_SLICE_NORMAL_ASCENDING);
				this.addMidInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.MID_SLICE_NORMAL_DESCENDING
				);
			}
		}
		// E=4
		
		if (isVariant) {
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.MID_SLICE_VARIANT_ASCENDING);
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.MID_SLICE_VARIANT_DESCENDING
			);
		} else {
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.MID_SLICE_NORMAL_ASCENDING);
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.MID_SLICE_NORMAL_DESCENDING
			);
		}
		// E=6: T=0
		if (tem0) {
			if (isVariant) {
				this.addMidInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_VARIANT_ASCENDING);
				this.addMidInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_VARIANT_DESCENDING
				);
			} else {
				this.addMidInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_NORMAL_ASCENDING);
				this.addMidInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_NORMAL_DESCENDING
				);
			}
		}
		// 内陆-高山
		// E=3: T<4
		if (tem_4) {
			if (isVariant) {
				this.addMidInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
				this.addMidInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.HIGH_SLICE_VARIANT_DESCENDING
				);
			} else {
				this.addMidInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
				this.addMidInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.HIGH_SLICE_NORMAL_DESCENDING
				);
			}
		}
		// E=4
		if (isVariant) {
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.HIGH_SLICE_VARIANT_DESCENDING
			);
		} else {
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.HIGH_SLICE_NORMAL_DESCENDING
			);
		}
		// E=6
		if (isVariant) {
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.HIGH_SLICE_VARIANT_DESCENDING
			);
		} else {
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.HIGH_SLICE_NORMAL_DESCENDING
			);
		}
		// 内陆-山峰
		// E=3: T<4
		if (tem_4) {
			if (isVariant) {
				this.addMidInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.PEAK_VARIANT);
			} else {
				this.addMidInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.PEAK_NORMAL);
			}
		}
		// E=4
		if (isVariant) {
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.PEAK_VARIANT);
		} else {
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.PEAK_NORMAL);
		}
		// E=6
		if (isVariant) {
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.PEAK_VARIANT);
		} else {
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.PEAK_NORMAL);
		}
		// 深陆-谷地
		// E=[0, 1]: T<4
		
		if (tem_4) {
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_0, Weirdness.VALLEY);
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.VALLEY);
		}
		// 深陆-低地
		// E=[0, 1]: 0<T<4
		if (tem0_4) {
			if (isVariant) {
				this.addFarInland(name, temperature, humidity, Erosion.EROSION_0, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
				this.addFarInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
			} else {
				this.addFarInland(name, temperature, humidity, Erosion.EROSION_0, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
				this.addFarInland(name, temperature, humidity, Erosion.EROSION_1, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
			}
		}
		// E=[2, 3]: T<4
		if (tem_4) {
			if (isVariant) {
				this.addFarInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
				this.addFarInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
			} else {
				this.addFarInland(name, temperature, humidity, Erosion.EROSION_2, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
				this.addFarInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
			}
		}
		// E=[4, 5]
		if (isVariant) {
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		} else {
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
		}
		// E=6: T=0
		if (tem0) {
			if (isVariant) {
				this.addFarInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
			} else {
				this.addFarInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
			}
		}
		// 深陆-低山
		// E=3: T<4
		if (tem_4) {
			if (isVariant) {
				this.addFarInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.MID_SLICE_VARIANT_ASCENDING);
				this.addFarInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.MID_SLICE_VARIANT_DESCENDING);
			} else {
				this.addFarInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.MID_SLICE_NORMAL_ASCENDING);
				this.addFarInland(name, temperature, humidity, Erosion.EROSION_3, Weirdness.MID_SLICE_NORMAL_DESCENDING);
			}
		}
		// E=4
		if (isVariant) {
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.MID_SLICE_VARIANT_ASCENDING);
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.MID_SLICE_VARIANT_DESCENDING);
		} else {
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.MID_SLICE_NORMAL_ASCENDING);
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.MID_SLICE_NORMAL_DESCENDING);
		}
		// E=6: T=0
		if (tem0) {
			if (isVariant) {
				this.addFarInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_VARIANT_ASCENDING);
				this.addFarInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_VARIANT_DESCENDING);
			} else {
				this.addFarInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_NORMAL_ASCENDING);
				this.addFarInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_NORMAL_DESCENDING);
			}
		}
		// 深陆-高山
		// E=4
		if (isVariant) {
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.HIGH_SLICE_VARIANT_DESCENDING);
		} else {
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.HIGH_SLICE_NORMAL_DESCENDING);
		}
		// E=6
		if (isVariant) {
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.HIGH_SLICE_VARIANT_DESCENDING);
		} else {
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.HIGH_SLICE_NORMAL_DESCENDING);
		}
		// 深陆-山峰
		// E=4
		if (isVariant) {
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.PEAK_VARIANT);
		} else {
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_4, Weirdness.PEAK_NORMAL);
		}
		// E=6
		if (isVariant) {
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.PEAK_VARIANT);
		} else {
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_6, Weirdness.PEAK_NORMAL);
		}
		return this;
	}
	
	public OverworldRegion addPlateauBiome(ResourceLocation name, Temperature temperature, Humidity humidity, boolean isVariant) {
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
		if (tem34) {
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_0, Weirdness.MID_SLICE_NORMAL_ASCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_0, Weirdness.MID_SLICE_NORMAL_DESCENDING);
			if (isVariant) {
				this.addNearInland(name, temperature, humidity, Erosion.EROSION_0, Weirdness.MID_SLICE_VARIANT_ASCENDING);
				this.addNearInland(name, temperature, humidity, Erosion.EROSION_0, Weirdness.MID_SLICE_VARIANT_ASCENDING);
			}
		}
		// 准陆-高山
		// E=0: T=[3, 4]
		if (tem34) {
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_0, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_0, Weirdness.HIGH_SLICE_NORMAL_DESCENDING);
			if (isVariant) {
				this.addNearInland(name, temperature, humidity, Erosion.EROSION_0, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
				this.addNearInland(name, temperature, humidity, Erosion.EROSION_0, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
			}
		}
		// 准陆-山峰-null
		// 内陆-谷地-null
		// 内陆-低地-null
		// 内陆-低山
		// E=0: T=[3, 4]
		if (tem34) {
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_0, Weirdness.MID_SLICE_NORMAL_ASCENDING);
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_0, Weirdness.MID_SLICE_NORMAL_DESCENDING);
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_0, Weirdness.MID_SLICE_VARIANT_ASCENDING);
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_0, Weirdness.MID_SLICE_VARIANT_DESCENDING);
		}
		// 内陆-高山
		// E=1: T=[3, 4]
		if (tem34) {
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
		if (tem34) {
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_0, Weirdness.MID_SLICE_NORMAL_ASCENDING);
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_0, Weirdness.MID_SLICE_NORMAL_DESCENDING);
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_0, Weirdness.MID_SLICE_VARIANT_ASCENDING);
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_0, Weirdness.MID_SLICE_VARIANT_DESCENDING);
		}
		// E=1: T>0
		if (tem0_) {
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
		if (tem34) {
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
		
		return this;
	}
	
	public OverworldRegion addWindsweptSavannaBiome(ResourceLocation name, Temperature temperature, Humidity humidity, boolean isVariant) {
		// 条件
		// T>1
		boolean tem1_ = (temperature == Temperature.NEUTRAL || temperature == Temperature.WARM || temperature == Temperature.HOT);
		// H<4
		boolean hum_4 = (humidity == Humidity.ARID || humidity == Humidity.DRY || humidity == Humidity.NEUTRAL || humidity == Humidity.WET);
		// T=0
		boolean tem0 = (temperature == Temperature.ICY);
		// H=4
		boolean hum4 = (humidity == Humidity.HUMID);
		// 沿岸-谷地-null
		// 沿岸-低地
		// E=5: W>0, T>1, H<4
		if (isVariant && tem1_ && hum_4) {
			this.addCoast(name, temperature, humidity, Erosion.EROSION_5, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		}
		// 沿岸-低山
		// E=5: W>0, T>1, H<4
		if (isVariant && tem1_ && hum_4) {
			this.addCoast(name, temperature, humidity, Erosion.EROSION_5, Weirdness.MID_SLICE_VARIANT_DESCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_5, Weirdness.MID_SLICE_VARIANT_ASCENDING);
		}
		// 沿岸-高山
		// E=5: W>0, T>1, H<4
		if (isVariant && tem1_ && hum_4) {
			this.addCoast(name, temperature, humidity, Erosion.EROSION_5, Weirdness.MID_SLICE_VARIANT_DESCENDING);
			this.addCoast(name, temperature, humidity, Erosion.EROSION_5, Weirdness.MID_SLICE_VARIANT_ASCENDING);
		}
		// 沿岸-山峰
		// E=5: W>0, T>1, H<4
		if (isVariant && tem1_ && hum_4) {
			this.addCoast(name, temperature, humidity, Erosion.EROSION_5, Weirdness.PEAK_VARIANT);
		}
		// 准陆-谷地-null
		// 准陆-低地
		// E=5: W>0, T>1, H<4
		if (isVariant && tem1_ && hum_4) {
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		}
		// 准陆-低山
		// E=5: W>0, T>1, H<4
		if (isVariant && tem1_ && hum_4) {
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.MID_SLICE_VARIANT_ASCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.MID_SLICE_VARIANT_DESCENDING);
		}
		// 准陆-高山
		// E=5: W>0, T>1, H<4
		if (isVariant && tem1_ && hum_4) {
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.HIGH_SLICE_VARIANT_DESCENDING);
		}
		// 准陆-山峰
		// E=5: W>0, T>1, H<4
		if (isVariant && tem1_ && hum_4) {
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.PEAK_VARIANT);
		}
		// 内陆-谷地-null
		// 内陆-低地-null
		// 内陆-低山-null
		// 内陆-高山-null
		// 内陆-山峰-null
		// 深陆-谷地-null
		// 深陆-低地-null
		// 深陆-低山-null
		// 深陆-高山-null
		// 深陆-山峰-null
		return this;
	}
	
	/**
	 * 添加风袭类生物群系
	 *
	 * @param name
	 * @param temperature
	 * @param humidity
	 * @param isVariant
	 * @return
	 */
	public OverworldRegion addShatteredBiome(ResourceLocation name, Temperature temperature, Humidity humidity, boolean isVariant) {
		// 条件
		// T=[0, 1]
		boolean tem01 = (temperature == Temperature.ICY || temperature == Temperature.COOL);
		// H=4
		boolean hum4 = (humidity == Humidity.HUMID);
		// 沿岸-谷地-null
		// 沿岸-低地-null
		// 沿岸-低山-null
		// 沿岸-高山-null
		// 沿岸-山峰 E=5: W<0  |  E=5, T=[0, 1]  |  E=5, H=4
		if (isVariant) {
			if (tem01 || hum4) {
				this.addCoast(name, temperature, humidity, Erosion.EROSION_5, Weirdness.PEAK_VARIANT);
			}
		} else {
			this.addCoast(name, temperature, humidity, Erosion.EROSION_5, Weirdness.PEAK_NORMAL);
		}
		// 准陆-谷地-null
		// 准陆-低地-null
		// 准陆-低山-null
		// 准陆-高山-null
		// 准陆-山峰 E=5: W<0  |  E=5, T=[0, 1]  |  E=5, H=4
		if (isVariant) {
			if (tem01 || hum4) {
				this.addNearInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.PEAK_VARIANT);
			}
		} else {
			this.addNearInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.PEAK_NORMAL);
		}
		// 内陆-谷地-null
		// 内陆-低地-null
		// 内陆-低山 E=5
		if (isVariant) {
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.MID_SLICE_VARIANT_ASCENDING);
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.MID_SLICE_VARIANT_DESCENDING);
		} else {
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.MID_SLICE_NORMAL_ASCENDING);
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.MID_SLICE_NORMAL_DESCENDING);
		}
		// 内陆-高山 E=5
		if (isVariant) {
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.HIGH_SLICE_VARIANT_DESCENDING);
		} else {
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.HIGH_SLICE_NORMAL_DESCENDING);
		}
		// 内陆-山峰 E=5
		if (isVariant) {
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.PEAK_NORMAL);
		} else {
			this.addMidInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.PEAK_VARIANT);
		}
		// 深陆-谷地-null
		// 深陆-低地-null
		// 深陆-低山 E=5
		if (isVariant) {
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.MID_SLICE_VARIANT_ASCENDING);
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.MID_SLICE_VARIANT_DESCENDING);
		} else {
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.MID_SLICE_NORMAL_ASCENDING);
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.MID_SLICE_NORMAL_DESCENDING);
		}
		// 深陆-高山 E=5
		if (isVariant) {
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.HIGH_SLICE_VARIANT_DESCENDING);
		} else {
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.HIGH_SLICE_NORMAL_DESCENDING);
		}
		// 深陆-山峰 E=5
		if (isVariant) {
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.PEAK_NORMAL);
		} else {
			this.addFarInland(name, temperature, humidity, Erosion.EROSION_5, Weirdness.PEAK_VARIANT);
		}
		return this;
	}
	
	
	private void addDripstoneCaves(ResourceLocation name, Temperature temperature, Erosion erosion, Weirdness weirdness) {
		ParameterPointListBuilder builder = new ParameterPointListBuilder();
		builder.continentalness(C_DRIPSTONE_CAVES)
			   .temperature(temperature)
			   .humidity(H_CAVES)
			   .erosion(erosion)
			   .weirdness(weirdness)
			   .depth(Depth.UNDERGROUND);
		this.list.add(new Pair<>(builder.build(), this.createBiomeKey(name)));
	}
	
	private void addLushCaves(ResourceLocation name, Temperature temperature, Erosion erosion, Weirdness weirdness){
		ParameterPointListBuilder builder = new ParameterPointListBuilder();
		builder.continentalness(Continentalness.FULL_RANGE)
			   .temperature(temperature)
			   .humidity(H_LUSH_CAVES)
			   .erosion(erosion)
			   .weirdness(weirdness)
			   .depth(Depth.UNDERGROUND);
		this.list.add(new Pair<>(builder.build(), this.createBiomeKey(name)));
	}
	
	private void addDeepDark(ResourceLocation name, Temperature temperature, Weirdness weirdness){
		ParameterPointListBuilder builder = new ParameterPointListBuilder();
		builder.continentalness(Continentalness.FULL_RANGE)
			   .temperature(temperature)
			   .humidity(Humidity.FULL_RANGE)
			   .erosion(Erosion.EROSION_0, Erosion.EROSION_1)
			   .weirdness(weirdness)
			   .depth(D_DEEP_DARK);
		this.list.add(new Pair<>(builder.build(), this.createBiomeKey(name)));
	}
	
	public OverworldRegion addDripstoneCaveBiome(ResourceLocation name){
		this.addDripstoneCaves(name, Temperature.FULL_RANGE, Erosion.FULL_RANGE, Weirdness.FULL_RANGE);
		return this;
	}
	
	public OverworldRegion addLushCaveBiome(ResourceLocation name){
		this.addLushCaves(name, Temperature.FULL_RANGE, Erosion.FULL_RANGE, Weirdness.FULL_RANGE);
		return this;
	}
	
	public OverworldRegion addDeepDarkBiome(ResourceLocation name){
		this.addDeepDark(name, Temperature.FULL_RANGE, Weirdness.FULL_RANGE);
		return this;
	}
	
	
	
	/**
	 * 添加沼泽类生物群系
	 *
	 * @param name
	 * @param humidity
	 * @return
	 */
	private void addSwampBiome(ResourceLocation name, Humidity humidity) {
		// 沿岸-谷地-null
		// 沿岸-低地-null
		// 沿岸-低山-null
		// 沿岸-高山-null
		// 沿岸-山峰-null
		// 准陆-谷地 E=6: T=[1, 2]
		this.addNearInland(name, Temperature.COOL, humidity, Erosion.EROSION_6, Weirdness.VALLEY);
		this.addNearInland(name, Temperature.NEUTRAL, humidity, Erosion.EROSION_6, Weirdness.VALLEY);
		// 准陆-低地 E=6: T=[1, 2]
		
		this.addNearInland(name, Temperature.COOL, humidity, Erosion.EROSION_6, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		this.addNearInland(name, Temperature.NEUTRAL, humidity, Erosion.EROSION_6, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		
		this.addNearInland(name, Temperature.COOL, humidity, Erosion.EROSION_6, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
		this.addNearInland(name, Temperature.NEUTRAL, humidity, Erosion.EROSION_6, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
		
		// 准陆-低山 E=6: T=[1, 2]
		
		this.addNearInland(name, Temperature.COOL, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_VARIANT_ASCENDING);
		this.addNearInland(name, Temperature.COOL, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_VARIANT_DESCENDING);
		
		this.addNearInland(name, Temperature.NEUTRAL, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_VARIANT_ASCENDING);
		this.addNearInland(name, Temperature.NEUTRAL, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_VARIANT_DESCENDING);
		
		this.addNearInland(name, Temperature.COOL, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_NORMAL_ASCENDING);
		this.addNearInland(name, Temperature.COOL, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_NORMAL_DESCENDING);
		
		this.addNearInland(name, Temperature.NEUTRAL, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_NORMAL_ASCENDING);
		this.addNearInland(name, Temperature.NEUTRAL, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_NORMAL_DESCENDING);
		
		// 准陆-高山-null
		// 准陆-山峰-null
		// 内陆-谷地 E=6, T=[1, 2]
		this.addMidInland(name, Temperature.COOL, humidity, Erosion.EROSION_6, Weirdness.VALLEY);
		this.addMidInland(name, Temperature.NEUTRAL, humidity, Erosion.EROSION_6, Weirdness.VALLEY);
		// 内陆-低地 E=6, T=[1, 2]
		
		this.addMidInland(name, Temperature.COOL, humidity, Erosion.EROSION_6, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		this.addMidInland(name, Temperature.NEUTRAL, humidity, Erosion.EROSION_6, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		
		this.addMidInland(name, Temperature.COOL, humidity, Erosion.EROSION_6, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
		this.addMidInland(name, Temperature.NEUTRAL, humidity, Erosion.EROSION_6, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
		
		// 内陆-低山 E=6, T=[1, 2]
		
		this.addMidInland(name, Temperature.COOL, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_VARIANT_ASCENDING);
		this.addMidInland(name, Temperature.COOL, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_VARIANT_DESCENDING);
		
		this.addMidInland(name, Temperature.NEUTRAL, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_VARIANT_ASCENDING);
		this.addMidInland(name, Temperature.NEUTRAL, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_VARIANT_DESCENDING);
		
		this.addMidInland(name, Temperature.COOL, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_NORMAL_ASCENDING);
		this.addMidInland(name, Temperature.COOL, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_NORMAL_DESCENDING);
		
		this.addMidInland(name, Temperature.NEUTRAL, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_NORMAL_ASCENDING);
		this.addMidInland(name, Temperature.NEUTRAL, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_NORMAL_DESCENDING);
		
		// 内陆-高山-null
		// 内陆-山峰-null
		// 深陆-谷地 E=6, T=[1, 2]
		this.addFarInland(name, Temperature.COOL, humidity, Erosion.EROSION_6, Weirdness.VALLEY);
		this.addFarInland(name, Temperature.NEUTRAL, humidity, Erosion.EROSION_6, Weirdness.VALLEY);
		// 深陆-低地 E=6, T=[1, 2]
		
		this.addFarInland(name, Temperature.COOL, humidity, Erosion.EROSION_6, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		this.addFarInland(name, Temperature.NEUTRAL, humidity, Erosion.EROSION_6, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		
		this.addFarInland(name, Temperature.COOL, humidity, Erosion.EROSION_6, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
		this.addFarInland(name, Temperature.NEUTRAL, humidity, Erosion.EROSION_6, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
		
		// 深陆-低山 E=6, T=[1, 2]
		
		this.addFarInland(name, Temperature.COOL, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_VARIANT_ASCENDING);
		this.addFarInland(name, Temperature.COOL, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_VARIANT_DESCENDING);
		
		this.addFarInland(name, Temperature.NEUTRAL, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_VARIANT_ASCENDING);
		this.addFarInland(name, Temperature.NEUTRAL, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_VARIANT_DESCENDING);
		
		this.addFarInland(name, Temperature.COOL, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_NORMAL_ASCENDING);
		this.addFarInland(name, Temperature.COOL, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_NORMAL_DESCENDING);
		
		this.addFarInland(name, Temperature.NEUTRAL, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_NORMAL_ASCENDING);
		this.addFarInland(name, Temperature.NEUTRAL, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_NORMAL_DESCENDING);
		
		
	}
	
	/**
	 * 红树林沼泽
	 *
	 * @param name
	 * @param humidity
	 * @return
	 */
	private void addMangroveSwamp(ResourceLocation name, Humidity humidity) {
		// 沿岸-谷地-null
		// 沿岸-低地-null
		// 沿岸-低山-null
		// 沿岸-高山-null
		// 沿岸-山峰-null
		// 准陆-谷地 E=6: T=[3, 4]
		this.addNearInland(name, Temperature.WARM, humidity, Erosion.EROSION_6, Weirdness.VALLEY);
		this.addNearInland(name, Temperature.HOT, humidity, Erosion.EROSION_6, Weirdness.VALLEY);
		// 准陆-低地 E=6: T=[3, 4]
		
		this.addNearInland(name, Temperature.WARM, humidity, Erosion.EROSION_6, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		this.addNearInland(name, Temperature.HOT, humidity, Erosion.EROSION_6, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		
		this.addNearInland(name, Temperature.WARM, humidity, Erosion.EROSION_6, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
		this.addNearInland(name, Temperature.HOT, humidity, Erosion.EROSION_6, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
		
		// 准陆-低山 E=6: T=[3, 4]
		
		this.addNearInland(name, Temperature.WARM, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_VARIANT_ASCENDING);
		this.addNearInland(name, Temperature.WARM, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_VARIANT_ASCENDING);
		this.addNearInland(name, Temperature.HOT, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_VARIANT_ASCENDING);
		this.addNearInland(name, Temperature.HOT, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_VARIANT_DESCENDING);
		
		this.addNearInland(name, Temperature.WARM, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_NORMAL_ASCENDING);
		this.addNearInland(name, Temperature.WARM, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_NORMAL_ASCENDING);
		this.addNearInland(name, Temperature.HOT, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_NORMAL_ASCENDING);
		this.addNearInland(name, Temperature.HOT, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_NORMAL_DESCENDING);
		
		// 准陆-高山-null
		// 准陆-山峰-null
		// 内陆-谷地 E=6, T=[3, 4]
		this.addMidInland(name, Temperature.WARM, humidity, Erosion.EROSION_6, Weirdness.VALLEY);
		this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_6, Weirdness.VALLEY);
		// 内陆-低地 E=6, T=[3, 4]
		
		this.addMidInland(name, Temperature.WARM, humidity, Erosion.EROSION_6, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_6, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		
		this.addMidInland(name, Temperature.NEUTRAL, humidity, Erosion.EROSION_6, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
		this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_6, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
		
		// 内陆-低山 E=6, T=[3, 4]
		
		this.addMidInland(name, Temperature.WARM, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_VARIANT_ASCENDING);
		this.addMidInland(name, Temperature.WARM, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_VARIANT_ASCENDING);
		this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_VARIANT_ASCENDING);
		this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_VARIANT_DESCENDING);
		
		this.addMidInland(name, Temperature.WARM, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_NORMAL_ASCENDING);
		this.addMidInland(name, Temperature.WARM, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_NORMAL_ASCENDING);
		this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_NORMAL_ASCENDING);
		this.addMidInland(name, Temperature.HOT, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_NORMAL_DESCENDING);
		
		// 内陆-高山-null
		// 内陆-山峰-null
		// 深陆-谷地 E=6, T=[3, 4]
		this.addFarInland(name, Temperature.WARM, humidity, Erosion.EROSION_6, Weirdness.VALLEY);
		this.addFarInland(name, Temperature.HOT, humidity, Erosion.EROSION_6, Weirdness.VALLEY);
		// 深陆-低地 E=6, T=[3, 4]
		
		this.addFarInland(name, Temperature.WARM, humidity, Erosion.EROSION_6, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		this.addFarInland(name, Temperature.HOT, humidity, Erosion.EROSION_6, Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		
		this.addFarInland(name, Temperature.WARM, humidity, Erosion.EROSION_6, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
		this.addFarInland(name, Temperature.HOT, humidity, Erosion.EROSION_6, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
		
		// 深陆-低山 E=6, T=[3, 4]
		
		this.addFarInland(name, Temperature.WARM, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_VARIANT_ASCENDING);
		this.addFarInland(name, Temperature.HOT, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_VARIANT_DESCENDING);
		
		this.addFarInland(name, Temperature.WARM, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_VARIANT_ASCENDING);
		this.addFarInland(name, Temperature.HOT, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_VARIANT_DESCENDING);
		
		this.addFarInland(name, Temperature.WARM, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_NORMAL_ASCENDING);
		this.addFarInland(name, Temperature.HOT, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_NORMAL_DESCENDING);
		
		this.addFarInland(name, Temperature.WARM, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_NORMAL_ASCENDING);
		this.addFarInland(name, Temperature.HOT, humidity, Erosion.EROSION_6, Weirdness.MID_SLICE_NORMAL_DESCENDING);
	}
	
	/**
	 * 沼泽类生物群系
	 * @param name
	 * @param humidity
	 * @param isVariant 是否为变种? 沼泽: 红树林沼泽
	 * @return
	 */
	public OverworldRegion addSwampBiome(ResourceLocation name, Humidity humidity, boolean isVariant){
		if(isVariant){
			this.addSwampBiome(name, humidity);
		}else {
			this.addMangroveSwamp(name, humidity);
		}
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