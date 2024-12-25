package com.forget_melody.terrablenderjs.kubejs.wrapper;

import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.Climate.*;
import terrablender.api.ParameterUtils;

import java.util.List;

public class ParameterPointListBuilderJS {
	public static class VanillaParameterPointListBuilder {
		private ParameterUtils.ParameterPointListBuilder builder = new ParameterUtils.ParameterPointListBuilder();
		
		public VanillaParameterPointListBuilder() {
		
		}
		
		public VanillaParameterPointListBuilder temperature(ParameterUtils.Temperature... values){
			this.builder.temperature(values);
			return this;
		}
		
		public VanillaParameterPointListBuilder humidity(ParameterUtils.Humidity... values){
			this.builder.humidity(values);
			return this;
		}
		
		public VanillaParameterPointListBuilder continentalness(ParameterUtils.Continentalness... values){
			this.builder.continentalness(values);
			return this;
		}
		
		public VanillaParameterPointListBuilder erosion(ParameterUtils.Erosion... values){
			this.builder.erosion(values);
			return this;
		}
		
		public VanillaParameterPointListBuilder weirdness(ParameterUtils.Weirdness... values){
			this.builder.weirdness(values);
			return this;
		}
		
		public VanillaParameterPointListBuilder depth(ParameterUtils.Depth... values){
			this.builder.depth(values);
			return this;
		}
		
		public List<ParameterPoint> build (){
			return this.builder.build();
		}
	}
}
