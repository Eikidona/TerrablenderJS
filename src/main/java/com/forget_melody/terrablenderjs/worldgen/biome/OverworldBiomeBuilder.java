package com.forget_melody.terrablenderjs.worldgen.biome;

import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.Climate.*;
import terrablender.api.ParameterUtils.*;

import java.util.function.Consumer;

import static terrablender.api.Region.DEFERRED_PLACEHOLDER;

public class OverworldBiomeBuilder {
	private final Temperature[] TEMPERATURES = new Temperature[]{Temperature.ICY, Temperature.COOL, Temperature.NEUTRAL, Temperature.WARM, Temperature.HOT};
	private final Humidity[] HUMIDITIES = new Humidity[]{Humidity.ARID, Humidity.DRY, Humidity.NEUTRAL, Humidity.WET, Humidity.HUMID};
	private final Continentalness[] INLAND_CONTINENTALNESSS = new Continentalness[]{Continentalness.COAST, Continentalness.NEAR_INLAND, Continentalness.MID_INLAND, Continentalness.FAR_INLAND};
	private final Erosion[] EROSIONS = new Erosion[]{Erosion.EROSION_0, Erosion.EROSION_1, Erosion.EROSION_2, Erosion.EROSION_3, Erosion.EROSION_4, Erosion.EROSION_5, Erosion.EROSION_6};
	
	private final ResourceKey<Biome>[][] MUSHROOM_FIELDS = new ResourceKey[5][5];
	private final ResourceKey<Biome>[][] OCEANS = new ResourceKey[5][5];
	private final ResourceKey<Biome>[][] DEEP_OCEANS = new ResourceKey[5][5];
	private final ResourceKey<Biome>[][] RIVERS = new ResourceKey[5][5];
	private final ResourceKey<Biome>[][] STONE_SHORES = new ResourceKey[5][5];
	private final ResourceKey<Biome>[][] STONE_SHORES_VARIANT = new ResourceKey[5][5];
	private final ResourceKey<Biome>[][] BEACHS = new ResourceKey[5][5];
	private final ResourceKey<Biome>[][] BEACHS_VARIANT = new ResourceKey[5][5];
	private final ResourceKey<Biome>[][] MIDDLES = new ResourceKey[5][5];
	private final ResourceKey<Biome>[][] MIDDLES_VARIANT = new ResourceKey[5][5];
	private final ResourceKey<Biome>[][] PLATEAUS = new ResourceKey[5][5];
	private final ResourceKey<Biome>[][] PLATEAUS_VARIANT = new ResourceKey[5][5];
	private final ResourceKey<Biome>[][] BADLANDS = new ResourceKey[5][5];
	private final ResourceKey<Biome>[][] BADLANDS_VARIANT = new ResourceKey[5][5];
	private final ResourceKey<Biome>[][] WINDSWEPTS = new ResourceKey[5][5];
	private final ResourceKey<Biome>[][] WINDSWEPTS_VARIANT = new ResourceKey[5][5];
	private final ResourceKey<Biome>[][] SWAMPS = new ResourceKey[5][5];
	private final ResourceKey<Biome>[][] SWAMPS_VARIANT = new ResourceKey[5][5];
	private final ResourceKey<Biome>[][] COAST_WINDSWEPTS = new ResourceKey[5][5];
	private final ResourceKey<Biome>[][] COAST_WINDSWEPTS_VARIANT = new ResourceKey[5][5];
	private final ResourceKey<Biome>[][] INLAND_WINDSWEPTS = new ResourceKey[5][5];
	private final ResourceKey<Biome>[][] INLAND_WINDSWEPTS_VARIANT = new ResourceKey[5][5];
	private final ResourceKey<Biome>[][] SLOPES = new ResourceKey[5][5];
	private final ResourceKey<Biome>[][] SLOPES_VARIANT = new ResourceKey[5][5];
	private final ResourceKey<Biome>[][] PEAKS = new ResourceKey[5][5];
	private final ResourceKey<Biome>[][] PEAKS_VARIANT = new ResourceKey[5][5];
	
	private ResourceKey<Biome> windsweptSavanna = DEFERRED_PLACEHOLDER;
	
	public OverworldBiomeBuilder() {
	}
	
	public void addBiomes(Consumer<Pair<ParameterPoint, ResourceKey<Biome>>> mapper) {
		addOffCoastBiomes(mapper);
		addInlandBiomes(mapper);
	}
	
	protected void addOffCoastBiomes(Consumer<Pair<ParameterPoint, ResourceKey<Biome>>> mapper) {
		addMushroomFieldsBiomes(mapper);
		addDeepOceanBiomes(mapper);
		addOceanBiomes(mapper);
	}
	
	protected void addInlandBiomes(Consumer<Pair<ParameterPoint, ResourceKey<Biome>>> mapper) {
		addValley(mapper, Weirdness.VALLEY.parameter());
		addLow(mapper, Weirdness.LOW_SLICE_NORMAL_DESCENDING.parameter());
		addLow(mapper, Weirdness.LOW_SLICE_VARIANT_ASCENDING.parameter());
		addMid(mapper, Weirdness.MID_SLICE_NORMAL_ASCENDING.parameter());
		addMid(mapper, Weirdness.MID_SLICE_NORMAL_DESCENDING.parameter());
		addMid(mapper, Weirdness.MID_SLICE_VARIANT_ASCENDING.parameter());
		addMid(mapper, Weirdness.MID_SLICE_VARIANT_DESCENDING.parameter());
		addHigh(mapper, Weirdness.HIGH_SLICE_NORMAL_ASCENDING.parameter());
		addHigh(mapper, Weirdness.HIGH_SLICE_NORMAL_DESCENDING.parameter());
		addHigh(mapper, Weirdness.HIGH_SLICE_VARIANT_ASCENDING.parameter());
		addHigh(mapper, Weirdness.HIGH_SLICE_VARIANT_ASCENDING.parameter());
	}
	
	protected ResourceKey<Biome> pickMushroomFieldsBiome(int temperatureIndex, int humidityIndex) {
		ResourceKey<Biome> biome = MUSHROOM_FIELDS[temperatureIndex][humidityIndex];
		return biome != null ? biome : DEFERRED_PLACEHOLDER;
	}
	
	protected ResourceKey<Biome> pickDeepOceanBiome(int temperatureIndex, int humidityIndex) {
		ResourceKey<Biome> biome = DEEP_OCEANS[temperatureIndex][humidityIndex];
		return biome != null ? biome : DEFERRED_PLACEHOLDER;
	}
	
	protected ResourceKey<Biome> pickOceanBiome(int temperatureIndex, int humidityIndex) {
		ResourceKey<Biome> biome = OCEANS[temperatureIndex][humidityIndex];
		return biome != null ? biome : DEFERRED_PLACEHOLDER;
	}
	
	protected ResourceKey<Biome> pickRiverBiome(int temperatureIndex, int humidityIndex) {
		ResourceKey<Biome> biome = RIVERS[temperatureIndex][humidityIndex];
		return biome != null ? biome : DEFERRED_PLACEHOLDER;
	}
	
	protected ResourceKey<Biome> pickStoneShoreBiome(int temperatureIndex, int humidityIndex, Parameter weirdness) {
		if (weirdness.max() < 0) {
			ResourceKey<Biome> biome = STONE_SHORES[temperatureIndex][humidityIndex];
			return biome != null ? biome : DEFERRED_PLACEHOLDER;
		} else {
			ResourceKey<Biome> biomeVariant = STONE_SHORES_VARIANT[temperatureIndex][humidityIndex];
			ResourceKey<Biome> biome = STONE_SHORES[temperatureIndex][humidityIndex];
			if (biomeVariant != null) return biomeVariant;
			if (biome != null) return biome;
			return DEFERRED_PLACEHOLDER;
		}
	}
	
	protected ResourceKey<Biome> pickBeachBiome(int temperatureIndex, int humidityIndex, Parameter weirdness) {
		if (weirdness.max() < 0) {
			ResourceKey<Biome> biome = BEACHS[temperatureIndex][humidityIndex];
			return biome != null ? biome : DEFERRED_PLACEHOLDER;
		} else {
			ResourceKey<Biome> biomeVariant = BEACHS_VARIANT[temperatureIndex][humidityIndex];
			ResourceKey<Biome> biome = BEACHS[temperatureIndex][humidityIndex];
			if (biomeVariant != null) return biomeVariant;
			if (biome != null) return biome;
			return DEFERRED_PLACEHOLDER;
		}
	}
	
	protected ResourceKey<Biome> pickSwampBiome(int temperatureIndex, int humidityIndex, Parameter weirdness) {
		if (weirdness.max() < 0) {
			ResourceKey<Biome> biome = SWAMPS[temperatureIndex][humidityIndex];
			return biome != null ? biome : DEFERRED_PLACEHOLDER;
		} else {
			ResourceKey<Biome> biomeVariant = SWAMPS_VARIANT[temperatureIndex][humidityIndex];
			ResourceKey<Biome> biome = SWAMPS[temperatureIndex][humidityIndex];
			if (biomeVariant != null) return biomeVariant;
			if (biome != null) return biome;
			return DEFERRED_PLACEHOLDER;
		}
	}
	
	protected ResourceKey<Biome> pickBadlandBiome(int temperatureIndex, int humidityIndex, Parameter weirdness) {
		if (weirdness.max() < 0) {
			ResourceKey<Biome> biome = BADLANDS[temperatureIndex][humidityIndex];
			return biome != null ? biome : DEFERRED_PLACEHOLDER;
		} else {
			ResourceKey<Biome> biomeVariant = BADLANDS_VARIANT[temperatureIndex][humidityIndex];
			ResourceKey<Biome> biome = BADLANDS[temperatureIndex][humidityIndex];
			if (biomeVariant != null) return biomeVariant;
			if (biome != null) return biome;
			return DEFERRED_PLACEHOLDER;
		}
	}
	
	protected ResourceKey<Biome> pickMiddleBiome(int temperatureIndex, int humidityIndex, Parameter weirdness) {
		if (weirdness.max() < 0) {
			ResourceKey<Biome> biome = MIDDLES[temperatureIndex][humidityIndex];
			return biome != null ? biome : DEFERRED_PLACEHOLDER;
		} else {
			ResourceKey<Biome> biomeVariant = MIDDLES_VARIANT[temperatureIndex][humidityIndex];
			ResourceKey<Biome> biome = MIDDLES[temperatureIndex][humidityIndex];
			if (biomeVariant != null) return biomeVariant;
			if (biome != null) return biome;
			return DEFERRED_PLACEHOLDER;
		}
	}
	
	protected ResourceKey<Biome> pickWindSweptSavanna(int temperatureIndex, int humidityIndex, Parameter weirdness) {
		return this.windsweptSavanna;
	}
	
	protected ResourceKey<Biome> pickWindSweptBiome(int temperatureIndex, int humidityIndex, Parameter weirdness) {
		if (weirdness.max() < 0) {
			ResourceKey<Biome> biome = INLAND_WINDSWEPTS[temperatureIndex][humidityIndex];
			return biome != null ? biome : DEFERRED_PLACEHOLDER;
		} else {
			ResourceKey<Biome> biomeVariant = INLAND_WINDSWEPTS_VARIANT[temperatureIndex][humidityIndex];
			ResourceKey<Biome> biome = INLAND_WINDSWEPTS[temperatureIndex][humidityIndex];
			if (biomeVariant != null) return biomeVariant;
			if (biome != null) return biome;
			return DEFERRED_PLACEHOLDER;
		}
	}
	
	protected ResourceKey<Biome> pickSlopeBiome(int temperatureIndex, int humidityIndex, Parameter weirdness) {
		if (weirdness.max() < 0) {
			ResourceKey<Biome> biome = SLOPES[temperatureIndex][humidityIndex];
			return biome != null ? biome : DEFERRED_PLACEHOLDER;
		} else {
			ResourceKey<Biome> biomeVariant = SLOPES_VARIANT[temperatureIndex][humidityIndex];
			ResourceKey<Biome> biome = SLOPES[temperatureIndex][humidityIndex];
			if (biomeVariant != null) return biomeVariant;
			if (biome != null) return biome;
			return DEFERRED_PLACEHOLDER;
		}
	}
	
	protected ResourceKey<Biome> pickPlateauBiome(int temperatureIndex, int humidityIndex, Parameter weirdness) {
		if (weirdness.max() < 0) {
			ResourceKey<Biome> biome = PLATEAUS[temperatureIndex][humidityIndex];
			return biome != null ? biome : DEFERRED_PLACEHOLDER;
		} else {
			ResourceKey<Biome> biomeVariant = PLATEAUS_VARIANT[temperatureIndex][humidityIndex];
			ResourceKey<Biome> biome = PLATEAUS[temperatureIndex][humidityIndex];
			if (biomeVariant != null) return biomeVariant;
			if (biome != null) return biome;
			return DEFERRED_PLACEHOLDER;
		}
	}
	
	protected ResourceKey<Biome> pickPeakBiome(int temperatureIndex, int humidityIndex, Parameter weirdness) {
		if (weirdness.max() < 0) {
			ResourceKey<Biome> biome = PEAKS[temperatureIndex][humidityIndex];
			return biome != null ? biome : DEFERRED_PLACEHOLDER;
		} else {
			ResourceKey<Biome> biomeVariant = PEAKS_VARIANT[temperatureIndex][humidityIndex];
			ResourceKey<Biome> biome = PEAKS[temperatureIndex][humidityIndex];
			if (biomeVariant != null) return biomeVariant;
			if (biome != null) return biome;
			return DEFERRED_PLACEHOLDER;
		}
	}
	
	protected void addMushroomFieldsBiomes(Consumer<Pair<ParameterPoint, ResourceKey<Biome>>> mapper) {
		for (int temperatureIndex = 0; temperatureIndex < 5; temperatureIndex++) {
			for (int humidityIndex = 0; humidityIndex < 5; humidityIndex++) {
				Parameter temperature = TEMPERATURES[temperatureIndex].parameter();
				;
				Parameter humidity = HUMIDITIES[humidityIndex].parameter();
				ResourceKey<Biome> biome = pickMushroomFieldsBiome(temperatureIndex, humidityIndex);
				addSurfaceBiomes(mapper, biome, temperature, humidity, Continentalness.MUSHROOM_FIELDS.parameter(), Erosion.FULL_RANGE.parameter(), Weirdness.FULL_RANGE.parameter());
			}
		}
	}
	
	protected void addDeepOceansBiomes(Consumer<Pair<ParameterPoint, ResourceKey<Biome>>> mapper) {
		for (int temperatureIndex = 0; temperatureIndex < 5; temperatureIndex++) {
			for (int humidityIndex = 0; humidityIndex < 5; humidityIndex++) {
				Parameter temperature = TEMPERATURES[temperatureIndex].parameter();
				;
				Parameter humidity = HUMIDITIES[humidityIndex].parameter();
				ResourceKey<Biome> biome = pickDeepOceanBiome(temperatureIndex, humidityIndex);
				addSurfaceBiomes(mapper, biome, temperature, humidity, Continentalness.DEEP_OCEAN.parameter(), Erosion.FULL_RANGE.parameter(), Weirdness.FULL_RANGE.parameter());
			}
		}
	}
	
	protected void addOceansBiomes(Consumer<Pair<ParameterPoint, ResourceKey<Biome>>> mapper) {
		for (int temperatureIndex = 0; temperatureIndex < 5; temperatureIndex++) {
			for (int humidityIndex = 0; humidityIndex < 5; humidityIndex++) {
				Parameter temperature = TEMPERATURES[temperatureIndex].parameter();
				;
				Parameter humidity = HUMIDITIES[humidityIndex].parameter();
				ResourceKey<Biome> biome = pickOceanBiome(temperatureIndex, humidityIndex);
				addSurfaceBiomes(mapper, biome, temperature, humidity, Continentalness.OCEAN.parameter(), Erosion.FULL_RANGE.parameter(), Weirdness.FULL_RANGE.parameter());
			}
		}
	}
	
	protected void addDeepOceanBiomes(Consumer<Pair<ParameterPoint, ResourceKey<Biome>>> mapper) {
		for (int temperatureIndex = 0; temperatureIndex < MUSHROOM_FIELDS.length; temperatureIndex++) {
			for (int humidityIndex = 0; humidityIndex < MUSHROOM_FIELDS[temperatureIndex].length; humidityIndex++) {
				Parameter temperature = TEMPERATURES[temperatureIndex].parameter();
				;
				Parameter humidity = HUMIDITIES[humidityIndex].parameter();
				ResourceKey<Biome> biome = pickDeepOceanBiome(temperatureIndex, humidityIndex);
				addSurfaceBiomes(mapper, biome, temperature, humidity, Continentalness.DEEP_OCEAN.parameter(), Erosion.FULL_RANGE.parameter(), Weirdness.FULL_RANGE.parameter());
			}
		}
	}
	
	protected void addOceanBiomes(Consumer<Pair<ParameterPoint, ResourceKey<Biome>>> mapper) {
		for (int temperatureIndex = 0; temperatureIndex < 5; temperatureIndex++) {
			for (int humidityIndex = 0; humidityIndex < 5; humidityIndex++) {
				Parameter temperature = TEMPERATURES[temperatureIndex].parameter();
				;
				Parameter humidity = HUMIDITIES[humidityIndex].parameter();
				ResourceKey<Biome> biome = pickOceanBiome(temperatureIndex, humidityIndex);
				addSurfaceBiomes(mapper, biome, temperature, humidity, Continentalness.OCEAN.parameter(), Erosion.FULL_RANGE.parameter(), Weirdness.FULL_RANGE.parameter());
			}
		}
	}
	
	protected void addValley(Consumer<Pair<ParameterPoint, ResourceKey<Biome>>> mapper, Parameter weirdness) {
		for (int temperatureIndex = 0; temperatureIndex < 5; temperatureIndex++) {
			for (int humidityIndex = 0; humidityIndex < 5; humidityIndex++) {
				Parameter temperature = TEMPERATURES[temperatureIndex].parameter();
				;
				Parameter humidity = HUMIDITIES[humidityIndex].parameter();
				// Coast
				addValleyBiomes(mapper, pickRiverBiome(temperatureIndex, humidityIndex), temperature, humidity, Continentalness.COAST.parameter(), Erosion.FULL_RANGE.parameter());
				// NearInland
				addValleyBiomes(mapper, pickRiverBiome(temperatureIndex, humidityIndex), temperature, humidity, Continentalness.NEAR_INLAND.parameter(), Erosion.span(Erosion.EROSION_0, Erosion.EROSION_5));
				addValleyBiomes(mapper, pickSwampBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.NEAR_INLAND.parameter(), Erosion.EROSION_6.parameter());
				// MidInland
				addValleyBiomes(mapper, pickBadlandBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.MID_INLAND.parameter(), Erosion.span(Erosion.EROSION_0, Erosion.EROSION_1));
				addValleyBiomes(mapper, pickRiverBiome(temperatureIndex, humidityIndex), temperature, humidity, Continentalness.MID_INLAND.parameter(), Erosion.span(Erosion.EROSION_2, Erosion.EROSION_5));
				addValleyBiomes(mapper, pickSwampBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.MID_INLAND.parameter(), Erosion.EROSION_6.parameter());
				// FarInland
				addValleyBiomes(mapper, temperatureIndex < 4 ? pickMiddleBiome(temperatureIndex, humidityIndex, weirdness) : pickBadlandBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.FAR_INLAND.parameter(), Erosion.span(Erosion.EROSION_0, Erosion.EROSION_1));
				addValleyBiomes(mapper, pickRiverBiome(temperatureIndex, humidityIndex), temperature, humidity, Continentalness.FAR_INLAND.parameter(), Erosion.span(Erosion.EROSION_2, Erosion.EROSION_5));
				addValleyBiomes(mapper, pickSwampBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.FAR_INLAND.parameter(), Erosion.EROSION_6.parameter());
			}
		}
	}
	
	protected void addLow(Consumer<Pair<ParameterPoint, ResourceKey<Biome>>> mapper, Parameter weirdness) {
		for (int temperatureIndex = 0; temperatureIndex < 5; temperatureIndex++) {
			for (int humidityIndex = 0; humidityIndex < 5; humidityIndex++) {
				Parameter temperature = TEMPERATURES[temperatureIndex].parameter();
				;
				Parameter humidity = HUMIDITIES[humidityIndex].parameter();
				// Coast
				addLowBiomes(mapper, pickStoneShoreBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.COAST.parameter(), Erosion.span(Erosion.EROSION_0, Erosion.EROSION_2), weirdness);
				addLowBiomes(mapper, pickBeachBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.COAST.parameter(), Erosion.span(Erosion.EROSION_3, Erosion.EROSION_4), weirdness);
				addLowBiomes(mapper, weirdness.max() < 0 ? pickBeachBiome(temperatureIndex, humidityIndex, weirdness) : ((temperatureIndex == 0 || temperatureIndex == 1) && humidityIndex == 4 ? pickMiddleBiome(temperatureIndex, humidityIndex, weirdness) : pickWindSweptSavanna(temperatureIndex, humidityIndex, weirdness)), temperature, humidity, Continentalness.COAST.parameter(), Erosion.EROSION_5.parameter(), weirdness);
				addLowBiomes(mapper, pickMiddleBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.COAST.parameter(), Erosion.EROSION_6.parameter(), weirdness);
				// NearInland
				addLowBiomes(mapper, temperatureIndex < 4 ? pickMiddleBiome(temperatureIndex, humidityIndex, weirdness) : pickBadlandBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.NEAR_INLAND.parameter(), Erosion.span(Erosion.EROSION_0, Erosion.EROSION_1), weirdness);
				addLowBiomes(mapper, pickMiddleBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.NEAR_INLAND.parameter(), Erosion.span(Erosion.EROSION_2, Erosion.EROSION_4), weirdness);
				addLowBiomes(mapper, (weirdness.max() < 0 || (temperatureIndex == 0 || temperatureIndex == 1) || humidityIndex == 4) ? pickMiddleBiome(temperatureIndex, humidityIndex, weirdness) : pickWindSweptSavanna(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.NEAR_INLAND.parameter(), Erosion.EROSION_5.parameter(), weirdness);
				addLowBiomes(mapper, pickSwampBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.NEAR_INLAND.parameter(), Erosion.EROSION_6.parameter(), weirdness);
				// MidInland
				addLowBiomes(mapper, temperatureIndex == 0 ? pickSlopeBiome(temperatureIndex, humidityIndex, weirdness) : (temperatureIndex == 4 ? pickBadlandBiome(temperatureIndex, humidityIndex, weirdness) : pickMiddleBiome(temperatureIndex, humidityIndex, weirdness)), temperature, humidity, Continentalness.MID_INLAND.parameter(), Erosion.span(Erosion.EROSION_0, Erosion.EROSION_1), weirdness);
				addLowBiomes(mapper, temperatureIndex < 4 ? pickMiddleBiome(temperatureIndex, humidityIndex, weirdness) : pickBadlandBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.MID_INLAND.parameter(), Erosion.span(Erosion.EROSION_2, Erosion.EROSION_3), weirdness);
				addLowBiomes(mapper, pickMiddleBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.MID_INLAND.parameter(), Erosion.span(Erosion.EROSION_4, Erosion.EROSION_5), weirdness);
				addLowBiomes(mapper, temperatureIndex == 0 ? pickMiddleBiome(temperatureIndex, humidityIndex, weirdness) : pickSwampBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.MID_INLAND.parameter(), Erosion.EROSION_6.parameter(), weirdness);
				// FarInland
				addLowBiomes(mapper, temperatureIndex == 0 ? pickSlopeBiome(temperatureIndex, humidityIndex, weirdness) : (temperatureIndex == 4 ? pickBadlandBiome(temperatureIndex, humidityIndex, weirdness) : pickMiddleBiome(temperatureIndex, humidityIndex, weirdness)), temperature, humidity, Continentalness.FAR_INLAND.parameter(), Erosion.span(Erosion.EROSION_0, Erosion.EROSION_1), weirdness);
				addLowBiomes(mapper, temperatureIndex < 4 ? pickMiddleBiome(temperatureIndex, humidityIndex, weirdness) : pickBadlandBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.FAR_INLAND.parameter(), Erosion.span(Erosion.EROSION_2, Erosion.EROSION_3), weirdness);
				addLowBiomes(mapper, pickMiddleBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.FAR_INLAND.parameter(), Erosion.span(Erosion.EROSION_4, Erosion.EROSION_5), weirdness);
				addLowBiomes(mapper, temperatureIndex == 0 ? pickMiddleBiome(temperatureIndex, humidityIndex, weirdness) : pickSwampBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.FAR_INLAND.parameter(), Erosion.EROSION_6.parameter(), weirdness);
			}
		}
	}
	
	protected void addMid(Consumer<Pair<ParameterPoint, ResourceKey<Biome>>> mapper, Parameter weirdness) {
		for (int temperatureIndex = 0; temperatureIndex < 5; temperatureIndex++) {
			for (int humidityIndex = 0; humidityIndex < 5; humidityIndex++) {
				Parameter temperature = TEMPERATURES[temperatureIndex].parameter();
				Parameter humidity = HUMIDITIES[humidityIndex].parameter();
				// Coast
				addMidBiomes(mapper, pickStoneShoreBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.COAST.parameter(), Erosion.span(Erosion.EROSION_0, Erosion.EROSION_2), weirdness);
				addMidBiomes(mapper, pickStoneShoreBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.COAST.parameter(), Erosion.span(Erosion.EROSION_0, Erosion.EROSION_2), weirdness);
				addMidBiomes(mapper, weirdness.max() < 0 ? pickBeachBiome(temperatureIndex, humidityIndex, weirdness) : pickMiddleBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.COAST.parameter(), Erosion.EROSION_4.parameter(), weirdness);
				addMidBiomes(mapper, weirdness.max() < 0 ? pickBeachBiome(temperatureIndex, humidityIndex, weirdness) : ((temperatureIndex == 0 || temperatureIndex == 1) || humidityIndex == 4 ? pickMiddleBiome(temperatureIndex, humidityIndex, weirdness) : pickWindSweptSavanna(temperatureIndex, humidityIndex, weirdness)), temperature, humidity, Continentalness.COAST.parameter(), Erosion.EROSION_5.parameter(), weirdness);
				addMidBiomes(mapper, weirdness.max() < 0 ? pickBeachBiome(temperatureIndex, humidityIndex, weirdness) : pickMiddleBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.COAST.parameter(), Erosion.EROSION_6.parameter(), weirdness);
				// NearInland
				addMidBiomes(mapper, temperatureIndex < 3 ? pickSlopeBiome(temperatureIndex, humidityIndex, weirdness) : pickPlateauBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.NEAR_INLAND.parameter(), Erosion.EROSION_0.parameter(), weirdness);
				addMidBiomes(mapper, temperatureIndex == 0 ? pickSlopeBiome(temperatureIndex, humidityIndex, weirdness) : (temperatureIndex < 4 ? pickMiddleBiome(temperatureIndex, humidityIndex, weirdness) : pickBadlandBiome(temperatureIndex, humidityIndex, weirdness)), temperature, humidity, Continentalness.NEAR_INLAND.parameter(), Erosion.EROSION_1.parameter(), weirdness);
				addMidBiomes(mapper, pickMiddleBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.NEAR_INLAND.parameter(), Erosion.span(Erosion.EROSION_2, Erosion.EROSION_4), weirdness);
				addMidBiomes(mapper, (weirdness.max() < 0 || temperatureIndex == 0 || temperatureIndex == 1 || humidityIndex == 4) ? pickMiddleBiome(temperatureIndex, humidityIndex, weirdness) : pickWindSweptSavanna(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.NEAR_INLAND.parameter(), Erosion.EROSION_5.parameter(), weirdness);
				addMidBiomes(mapper, temperatureIndex == 0 ? pickMiddleBiome(temperatureIndex, humidityIndex, weirdness) : pickSwampBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.NEAR_INLAND.parameter(), Erosion.EROSION_6.parameter(), weirdness);
				// MidInland
				addMidBiomes(mapper, temperatureIndex < 3 ? pickSlopeBiome(temperatureIndex, humidityIndex, weirdness) : pickPlateauBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.MID_INLAND.parameter(), Erosion.EROSION_0.parameter(), weirdness);
				addMidBiomes(mapper, temperatureIndex == 0 ? pickSlopeBiome(temperatureIndex, humidityIndex, weirdness) : (temperatureIndex < 4 ? pickMiddleBiome(temperatureIndex, humidityIndex, weirdness) : pickBadlandBiome(temperatureIndex, humidityIndex, weirdness)), temperature, humidity, Continentalness.MID_INLAND.parameter(), Erosion.EROSION_1.parameter(), weirdness);
				addMidBiomes(mapper, temperatureIndex < 4 ? pickMiddleBiome(temperatureIndex, humidityIndex, weirdness) : pickBadlandBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.MID_INLAND.parameter(), Erosion.span(Erosion.EROSION_2, Erosion.EROSION_3), weirdness);
				addMidBiomes(mapper, pickMiddleBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.MID_INLAND.parameter(), Erosion.EROSION_4.parameter(), weirdness);
				addMidBiomes(mapper, pickWindSweptBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.MID_INLAND.parameter(), Erosion.EROSION_5.parameter(), weirdness);
				addMidBiomes(mapper, temperatureIndex == 0 ? pickMiddleBiome(temperatureIndex, humidityIndex, weirdness) : pickSwampBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.MID_INLAND.parameter(), Erosion.EROSION_6.parameter(), weirdness);
				// FarInland
				addMidBiomes(mapper, temperatureIndex < 3 ? pickSlopeBiome(temperatureIndex, humidityIndex, weirdness) : pickPlateauBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.FAR_INLAND.parameter(), Erosion.EROSION_0.parameter(), weirdness);
				addMidBiomes(mapper, temperatureIndex == 0 ? pickSlopeBiome(temperatureIndex, humidityIndex, weirdness) : pickPlateauBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.FAR_INLAND.parameter(), Erosion.EROSION_1.parameter(), weirdness);
				addMidBiomes(mapper, pickPlateauBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.FAR_INLAND.parameter(), Erosion.EROSION_2.parameter(), weirdness);
				addMidBiomes(mapper, temperatureIndex < 4 ? pickMiddleBiome(temperatureIndex, humidityIndex, weirdness) : pickBadlandBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.FAR_INLAND.parameter(), Erosion.EROSION_3.parameter(), weirdness);
				addMidBiomes(mapper, pickMiddleBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.FAR_INLAND.parameter(), Erosion.EROSION_4.parameter(), weirdness);
				addMidBiomes(mapper, pickWindSweptBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.FAR_INLAND.parameter(), Erosion.EROSION_5.parameter(), weirdness);
				addMidBiomes(mapper, temperatureIndex == 0 ? pickMiddleBiome(temperatureIndex, humidityIndex, weirdness) : pickSwampBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.FAR_INLAND.parameter(), Erosion.EROSION_6.parameter(), weirdness);
			}
		}
	}
	
	protected void addHigh(Consumer<Pair<ParameterPoint, ResourceKey<Biome>>> mapper, Parameter weirdness) {
		for (int temperatureIndex = 0; temperatureIndex < 5; temperatureIndex++) {
			for (int humidityIndex = 0; humidityIndex < 5; humidityIndex++) {
				Parameter temperature = TEMPERATURES[temperatureIndex].parameter();
				Parameter humidity = HUMIDITIES[humidityIndex].parameter();
				// Coast
				addHighBiomes(mapper, pickMiddleBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.COAST.parameter(), Erosion.span(Erosion.EROSION_0, Erosion.EROSION_4), weirdness);
				addHighBiomes(mapper, (weirdness.max() < 0 || temperatureIndex == 0 || temperatureIndex == 1 || humidityIndex == 4) ? pickMiddleBiome(temperatureIndex, humidityIndex, weirdness) : pickWindSweptSavanna(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.COAST.parameter(), Erosion.EROSION_5.parameter(), weirdness);
				addHighBiomes(mapper, pickMiddleBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.COAST.parameter(), Erosion.EROSION_6.parameter(), weirdness);
				// NearInland
				addHighBiomes(mapper, temperatureIndex < 3 ? pickSlopeBiome(temperatureIndex, humidityIndex, weirdness) : pickPlateauBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.NEAR_INLAND.parameter(), Erosion.EROSION_0.parameter(), weirdness);
				addHighBiomes(mapper, temperatureIndex == 0 ? pickSlopeBiome(temperatureIndex, humidityIndex, weirdness) : (temperatureIndex < 4 ? pickMiddleBiome(temperatureIndex, humidityIndex, weirdness) : pickBadlandBiome(temperatureIndex, humidityIndex, weirdness)), temperature, humidity, Continentalness.NEAR_INLAND.parameter(), Erosion.EROSION_1.parameter(), weirdness);
				addHighBiomes(mapper, pickMiddleBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.NEAR_INLAND.parameter(), Erosion.span(Erosion.EROSION_2, Erosion.EROSION_4), weirdness);
				addHighBiomes(mapper, (weirdness.max() < 0 || temperatureIndex == 0 || temperatureIndex == 1 || humidityIndex == 4) ? pickMiddleBiome(temperatureIndex, humidityIndex, weirdness) : pickWindSweptSavanna(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.NEAR_INLAND.parameter(), Erosion.EROSION_5.parameter(), weirdness);
				addHighBiomes(mapper, pickMiddleBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.NEAR_INLAND.parameter(), Erosion.EROSION_6.parameter(), weirdness);
				// MidInland
				addHighBiomes(mapper, temperatureIndex < 4 ? pickSlopeBiome(temperatureIndex, humidityIndex, weirdness) : pickBadlandBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.MID_INLAND.parameter(), Erosion.EROSION_0.parameter(), weirdness);
				addHighBiomes(mapper, temperatureIndex < 3 ? pickSlopeBiome(temperatureIndex, humidityIndex, weirdness) : pickPlateauBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.MID_INLAND.parameter(), Erosion.EROSION_1.parameter(), weirdness);
				addHighBiomes(mapper, pickPlateauBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.MID_INLAND.parameter(), Erosion.EROSION_2.parameter(), weirdness);
				addHighBiomes(mapper, temperatureIndex < 4 ? pickMiddleBiome(temperatureIndex, humidityIndex, weirdness) : pickBadlandBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.MID_INLAND.parameter(), Erosion.EROSION_3.parameter(), weirdness);
				addHighBiomes(mapper, pickMiddleBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.MID_INLAND.parameter(), Erosion.EROSION_4.parameter(), weirdness);
				addHighBiomes(mapper, pickWindSweptBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.MID_INLAND.parameter(), Erosion.EROSION_5.parameter(), weirdness);
				addHighBiomes(mapper, pickMiddleBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.MID_INLAND.parameter(), Erosion.EROSION_6.parameter(), weirdness);
				// FarInland
				addHighBiomes(mapper, temperatureIndex < 4 ? pickPeakBiome(temperatureIndex, humidityIndex, weirdness) : pickBadlandBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.FAR_INLAND.parameter(), Erosion.EROSION_0.parameter(), weirdness);
				addHighBiomes(mapper, temperatureIndex < 3 ? pickSlopeBiome(temperatureIndex, humidityIndex, weirdness) : pickPlateauBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.FAR_INLAND.parameter(), Erosion.EROSION_1.parameter(), weirdness);
				addHighBiomes(mapper, pickPlateauBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.FAR_INLAND.parameter(), Erosion.span(Erosion.EROSION_2, Erosion.EROSION_3), weirdness);
				addHighBiomes(mapper, pickMiddleBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.FAR_INLAND.parameter(), Erosion.EROSION_4.parameter(), weirdness);
				addHighBiomes(mapper, pickWindSweptBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.FAR_INLAND.parameter(), Erosion.EROSION_5.parameter(), weirdness);
				addHighBiomes(mapper, pickMiddleBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.FAR_INLAND.parameter(), Erosion.EROSION_6.parameter(), weirdness);
			}
		}
		
	}
	
	protected void addPeak(Consumer<Pair<ParameterPoint, ResourceKey<Biome>>> mapper, Parameter weirdness) {
		for (int temperatureIndex = 0; temperatureIndex < 5; temperatureIndex++) {
			for (int humidityIndex = 0; humidityIndex < 5; humidityIndex++) {
				Parameter temperature = TEMPERATURES[temperatureIndex].parameter();
				Parameter humidity = HUMIDITIES[humidityIndex].parameter();
				// Coast
				addPeakBiomes(mapper, temperatureIndex < 4 ? pickPeakBiome(temperatureIndex, humidityIndex, weirdness) : pickBadlandBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.COAST.parameter(), Erosion.EROSION_0.parameter(), weirdness);
				addPeakBiomes(mapper, temperatureIndex == 0 ? pickSlopeBiome(temperatureIndex, humidityIndex, weirdness) : (temperatureIndex == 4 ? pickBadlandBiome(temperatureIndex, humidityIndex, weirdness) : pickMiddleBiome(temperatureIndex, humidityIndex, weirdness)), temperature, humidity, Continentalness.COAST.parameter(), Erosion.EROSION_1.parameter(), weirdness);
				addPeakBiomes(mapper, pickMiddleBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.COAST.parameter(), Erosion.span(Erosion.EROSION_2, Erosion.EROSION_4), weirdness);
				addPeakBiomes(mapper, (weirdness.max() < 0 || temperatureIndex == 0 || temperatureIndex == 1 || humidityIndex == 4) ? pickWindSweptBiome(temperatureIndex, humidityIndex, weirdness) : pickWindSweptSavanna(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.COAST.parameter(), Erosion.span(Erosion.EROSION_2, Erosion.EROSION_4), weirdness);
				addPeakBiomes(mapper, pickMiddleBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.COAST.parameter(), Erosion.EROSION_6.parameter(), weirdness);
				// NearInland
				addPeakBiomes(mapper, temperatureIndex < 4 ? pickPeakBiome(temperatureIndex, humidityIndex, weirdness) : pickBadlandBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.NEAR_INLAND.parameter(), Erosion.EROSION_0.parameter(), weirdness);
				addPeakBiomes(mapper, temperatureIndex == 0 ? pickSlopeBiome(temperatureIndex, humidityIndex, weirdness) : (temperatureIndex == 4 ? pickBadlandBiome(temperatureIndex, humidityIndex, weirdness) : pickMiddleBiome(temperatureIndex, humidityIndex, weirdness)), temperature, humidity, Continentalness.NEAR_INLAND.parameter(), Erosion.EROSION_1.parameter(), weirdness);
				addPeakBiomes(mapper, pickMiddleBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.NEAR_INLAND.parameter(), Erosion.span(Erosion.EROSION_2, Erosion.EROSION_4), weirdness);
				addPeakBiomes(mapper, (weirdness.max() < 0 || temperatureIndex == 0 || temperatureIndex == 1 || humidityIndex == 4) ? pickWindSweptBiome(temperatureIndex, humidityIndex, weirdness) : pickWindSweptSavanna(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.NEAR_INLAND.parameter(), Erosion.span(Erosion.EROSION_2, Erosion.EROSION_4), weirdness);
				addPeakBiomes(mapper, pickMiddleBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.NEAR_INLAND.parameter(), Erosion.EROSION_6.parameter(), weirdness);
				// MidInland
				addPeakBiomes(mapper, temperatureIndex < 4 ? pickPeakBiome(temperatureIndex, humidityIndex, weirdness) : pickBadlandBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.MID_INLAND.parameter(), Erosion.EROSION_0.parameter(), weirdness);
				addPeakBiomes(mapper, temperatureIndex == 0 ? pickSlopeBiome(temperatureIndex, humidityIndex, weirdness) : (temperatureIndex == 4 ? pickBadlandBiome(temperatureIndex, humidityIndex, weirdness) : pickMiddleBiome(temperatureIndex, humidityIndex, weirdness)), temperature, humidity, Continentalness.MID_INLAND.parameter(), Erosion.EROSION_1.parameter(), weirdness);
				addPeakBiomes(mapper, pickPlateauBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.MID_INLAND.parameter(), Erosion.EROSION_2.parameter(), weirdness);
				addPeakBiomes(mapper, temperatureIndex < 4 ? pickMiddleBiome(temperatureIndex, humidityIndex, weirdness) : pickBadlandBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.MID_INLAND.parameter(), Erosion.span(Erosion.EROSION_3, Erosion.EROSION_4), weirdness);
				addPeakBiomes(mapper, pickWindSweptBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.MID_INLAND.parameter(), Erosion.EROSION_5.parameter(), weirdness);
				addPeakBiomes(mapper, pickMiddleBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.MID_INLAND.parameter(), Erosion.EROSION_6.parameter(), weirdness);
				// FarInland
				addPeakBiomes(mapper, temperatureIndex < 4 ? pickPeakBiome(temperatureIndex, humidityIndex, weirdness) : pickBadlandBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.FAR_INLAND.parameter(), Erosion.EROSION_0.parameter(), weirdness);
				addPeakBiomes(mapper, temperatureIndex == 0 ? pickSlopeBiome(temperatureIndex, humidityIndex, weirdness) : (temperatureIndex == 4 ? pickBadlandBiome(temperatureIndex, humidityIndex, weirdness) : pickMiddleBiome(temperatureIndex, humidityIndex, weirdness)), temperature, humidity, Continentalness.FAR_INLAND.parameter(), Erosion.EROSION_1.parameter(), weirdness);
				addPeakBiomes(mapper, pickPlateauBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.FAR_INLAND.parameter(), Erosion.span(Erosion.EROSION_2, Erosion.EROSION_4), weirdness);
				addPeakBiomes(mapper, pickWindSweptBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.FAR_INLAND.parameter(), Erosion.EROSION_5.parameter(), weirdness);
				addPeakBiomes(mapper, pickMiddleBiome(temperatureIndex, humidityIndex, weirdness), temperature, humidity, Continentalness.FAR_INLAND.parameter(), Erosion.EROSION_6.parameter(), weirdness);
			}
		}
		
		
	}
	
	protected void addValleyBiomes(Consumer<Pair<ParameterPoint, ResourceKey<Biome>>> mapper, ResourceKey<Biome> biome, Parameter temperature, Parameter humidity, Parameter continentalness, Parameter erosion) {
		addSurfaceBiomes(mapper, biome, temperature, humidity, continentalness, erosion, Weirdness.VALLEY.parameter());
	}
	
	protected void addLowBiomes(Consumer<Pair<ParameterPoint, ResourceKey<Biome>>> mapper, ResourceKey<Biome> biome, Parameter temperature, Parameter humidity, Parameter continentalness, Parameter erosion, Parameter weirdness) {
		if (weirdness.max() < 0) {
			addSurfaceBiomes(mapper, biome, temperature, humidity, continentalness, erosion, Weirdness.LOW_SLICE_NORMAL_DESCENDING.parameter());
		} else {
			addSurfaceBiomes(mapper, biome, temperature, humidity, continentalness, erosion, Weirdness.LOW_SLICE_VARIANT_ASCENDING.parameter());
		}
	}
	
	protected void addMidBiomes(Consumer<Pair<ParameterPoint, ResourceKey<Biome>>> mapper, ResourceKey<Biome> biome, Parameter temperature, Parameter humidity, Parameter continentalness, Parameter erosion, Parameter weirdness) {
		if (weirdness.max() < 0) {
			addSurfaceBiomes(mapper, biome, temperature, humidity, continentalness, erosion, Weirdness.MID_SLICE_NORMAL_ASCENDING.parameter());
			addSurfaceBiomes(mapper, biome, temperature, humidity, continentalness, erosion, Weirdness.MID_SLICE_NORMAL_DESCENDING.parameter());
		} else {
			addSurfaceBiomes(mapper, biome, temperature, humidity, continentalness, erosion, Weirdness.MID_SLICE_VARIANT_ASCENDING.parameter());
			addSurfaceBiomes(mapper, biome, temperature, humidity, continentalness, erosion, Weirdness.MID_SLICE_VARIANT_DESCENDING.parameter());
		}
	}
	
	protected void addHighBiomes(Consumer<Pair<ParameterPoint, ResourceKey<Biome>>> mapper, ResourceKey<Biome> biome, Parameter temperature, Parameter humidity, Parameter continentalness, Parameter erosion, Parameter weirdness) {
		if (weirdness.max() < 0) {
			addSurfaceBiomes(mapper, biome, temperature, humidity, continentalness, erosion, Weirdness.HIGH_SLICE_NORMAL_ASCENDING.parameter());
			addSurfaceBiomes(mapper, biome, temperature, humidity, continentalness, erosion, Weirdness.HIGH_SLICE_NORMAL_DESCENDING.parameter());
		} else {
			addSurfaceBiomes(mapper, biome, temperature, humidity, continentalness, erosion, Weirdness.HIGH_SLICE_VARIANT_ASCENDING.parameter());
			addSurfaceBiomes(mapper, biome, temperature, humidity, continentalness, erosion, Weirdness.HIGH_SLICE_VARIANT_DESCENDING.parameter());
		}
	}
	
	protected void addPeakBiomes(Consumer<Pair<ParameterPoint, ResourceKey<Biome>>> mapper, ResourceKey<Biome> biome, Parameter temperature, Parameter humidity, Parameter continentalness, Parameter erosion, Parameter weirdness) {
		if (weirdness.max() < 0) {
			addSurfaceBiomes(mapper, biome, temperature, humidity, continentalness, erosion, Weirdness.PEAK_NORMAL.parameter());
		} else {
			addSurfaceBiomes(mapper, biome, temperature, humidity, continentalness, erosion, Weirdness.PEAK_VARIANT.parameter());
		}
	}
	
	protected void addSurfaceBiomes(Consumer<Pair<ParameterPoint, ResourceKey<Biome>>> mapper, ResourceKey<Biome> biome, Parameter temperature, Parameter humidity, Parameter continentalness, Parameter erosion, Parameter weirdness) {
		new ParameterPointListBuilder()
				.temperature(temperature)
				.humidity(humidity)
				.continentalness(continentalness)
				.erosion(erosion)
				.weirdness(weirdness)
				.depth(Depth.SURFACE, Depth.FLOOR)
				.build().forEach(point -> mapper.accept(new Pair<>(point, biome)));
	}
	
	protected void addUndergroundBiomes(Consumer<Pair<ParameterPoint, ResourceKey<Biome>>> mapper, ResourceKey<Biome> biome, Temperature temperature, Humidity humidity, Continentalness continentalness, Erosion erosion, Weirdness weirdness) {
		addUndergroundBiomes(mapper, biome, temperature.parameter(), humidity.parameter(), continentalness.parameter(), erosion.parameter(), weirdness.parameter());
	}
	
	protected void addUndergroundBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper, ResourceKey<Biome> biome, Parameter temperature, Parameter humidity, Parameter continentalness, Parameter erosion, Parameter weirdness) {
		new ParameterPointListBuilder()
				.temperature(temperature)
				.humidity(humidity)
				.continentalness(continentalness)
				.erosion(erosion)
				.weirdness(weirdness)
				.depth(Depth.UNDERGROUND)
				.build().forEach(point -> mapper.accept(new Pair<>(point, biome)));
	}
	
	protected void addBottomBiomes(Consumer<Pair<ParameterPoint, ResourceKey<Biome>>> mapper, ResourceKey<Biome> biome, Temperature temperature, Humidity humidity, Continentalness continentalness, Erosion erosion, Weirdness weirdness) {
		addBottomBiomes(mapper, biome, temperature.parameter(), humidity.parameter(), continentalness.parameter(), erosion.parameter(), weirdness.parameter());
	}
	
	protected void addBottomBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper, ResourceKey<Biome> biome, Parameter temperature, Parameter humidity, Parameter continentalness, Parameter erosion, Parameter weirdness) {
		new ParameterPointListBuilder()
				.temperature(temperature)
				.humidity(humidity)
				.continentalness(continentalness)
				.erosion(Erosion.EROSION_0, Erosion.EROSION_1)
				.weirdness(weirdness)
				.depth(Parameter.point(1.1F))
				.build().forEach(point -> mapper.accept(new Pair<>(point, biome)));
	}
}
