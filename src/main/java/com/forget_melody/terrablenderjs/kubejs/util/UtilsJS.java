package com.forget_melody.terrablenderjs.kubejs.util;

import dev.latvian.mods.kubejs.util.ConsoleJS;
import dev.latvian.mods.kubejs.util.MapJS;
import dev.latvian.mods.rhino.Context;
import net.minecraft.world.level.biome.Climate.*;
import org.jetbrains.annotations.Nullable;
import terrablender.api.ParameterUtils;
import terrablender.api.ParameterUtils.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

public class UtilsJS {
	
	@Nullable
	public static ParameterPoint toParameterPoint(Context context, @Nullable Object object){
		try{
			Map<?,?> map = MapJS.of(object);
			if(map != null){
				Set<String> keys = Set.of("temperature", "humidity", "continentalness", "erosion", "depth", "weirdness", "offset");
				for (String key: keys){
					if(!map.containsKey(key)){
						throw new RuntimeException("Key %s is missing".formatted(keys));
					}
				}
				Parameter temperature = toParameter(context, map.get("temperature"));
				Parameter humidity = toParameter(context, map.get("humidity"));
				Parameter continentalness = toParameter(context, map.get("continentalness"));
				Parameter erosion = toParameter(context, map.get("erosion"));
				Parameter depth = toParameter(context, map.get("depth"));Parameter weirdness = toParameter(context, map.get("weirdness"));
				long offset = (long) ((Double) map.get("offset")).doubleValue();
				
				return new ParameterPoint(temperature, humidity, continentalness, erosion, depth, weirdness, offset);
			}
		} catch (Exception e) {
			ConsoleJS.getCurrent(context).error("Failed to create ParameterPoint from %s".formatted(object) + e.getMessage());
		}
		return null;
	}
	
	@Nullable
	public static Parameter toParameter(Context context, @Nullable Object object){
		try{
			// 对象字面量
			Map<?,?> map = MapJS.of(object);
			if(map != null && map.containsKey("min") && map.containsKey("max")){
				Double min = (Double) map.get("min");
				Double max = (Double) map.get("max");
				return Parameter.span((long)min.doubleValue(), (long)max.doubleValue());
			}
//			// 枚举常量字符串
//			else {
//				Set<Function<Object, ?>> parameterFuncSet = Set.of(UtilsJS::toTemperature, UtilsJS::toContinentalness, UtilsJS::toHumidity, UtilsJS::toErosion, UtilsJS::toWeirdness, UtilsJS::toDepth);
//				for (Function<Object,?> parameterFunc: parameterFuncSet){
//					var parameter = parameterFunc.apply(object);
//					if(parameter != null){
//						if(parameter instanceof Continentalness) return ((Continentalness) parameter).parameter();
//						if(parameter instanceof Temperature) return ((Temperature) parameter).parameter();
//						if(parameter instanceof Humidity) return ((Humidity) parameter).parameter();
//						if(parameter instanceof Erosion) return ((Erosion) parameter).parameter();
//						if(parameter instanceof Weirdness) return ((Weirdness) parameter).parameter();
//						if(parameter instanceof Depth) return ((Depth) parameter).parameter();
//					}
//				}
//			}
			
			throw new RuntimeException();
			
		} catch (Exception e) {
			ConsoleJS.getCurrent(context).error("Failed to create Parameter from %s".formatted(object));
		}
		return null;
	}
	
	@Nullable
	public static Continentalness toContinentalness(@Nullable Object object){
		if(object == null) return null;
		return Enum.valueOf(Continentalness.class, object.toString());
	}
	
	@Nullable
	public static Temperature toTemperature(@Nullable Object object){
		if(object == null) return null;
		return Enum.valueOf(Temperature.class, object.toString());
	}
	
	@Nullable
	public static Humidity toHumidity( @Nullable Object object){
		if(object == null) return null;
		return Enum.valueOf(Humidity.class, object.toString());
	}
	
	@Nullable
	public static Erosion toErosion(@Nullable Object object){
		if(object == null) return null;
		return Enum.valueOf(Erosion.class, object.toString());
	}
	
	@Nullable
	public static Weirdness toWeirdness(@Nullable Object object){
		if(object == null) return null;
		return Enum.valueOf(Weirdness.class, object.toString());
	}
	
	@Nullable
	public static Depth toDepth(@Nullable Object object){
		if(object == null) return null;
		return Enum.valueOf(Depth.class, object.toString());
	}
}
