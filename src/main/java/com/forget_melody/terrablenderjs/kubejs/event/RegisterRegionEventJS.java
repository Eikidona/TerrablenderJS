package com.forget_melody.terrablenderjs.kubejs.event;

import com.forget_melody.terrablenderjs.terrablender.region.CustomRegion;
import dev.latvian.mods.kubejs.event.EventJS;
import com.forget_melody.terrablenderjs.terrablender.region.CustomRegion.CustomRegionBuilder;
import net.minecraft.resources.ResourceLocation;
import terrablender.api.RegionType;

import java.util.ArrayList;
import java.util.List;

public class RegisterRegionEventJS extends EventJS {
	private List<CustomRegionBuilder> customRegionBuilderList = new ArrayList<>();
	
	public RegisterRegionEventJS() {
	
	}
	
	/**
	 * 创建并返回一个CustomRegionBuilder实例，并将其加入等待构造的队列。
	 * @param name Region的注册名
	 * @param regionType Region的类型 仅有Overworld与Nerther
	 * @return CustomRegionBuilder
	 */
	public CustomRegionBuilder create(ResourceLocation name, RegionType regionType){
		CustomRegionBuilder builder = new CustomRegionBuilder(name, regionType);
		customRegionBuilderList.add(builder);
		return builder;
	}
	
	public List<CustomRegion> getRegisteredRegions(){
		return this.customRegionBuilderList.stream().map(CustomRegionBuilder::build).toList();
	}
}
