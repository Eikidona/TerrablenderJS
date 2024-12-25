package com.forget_melody.terrablenderjs.kubejs.event;

import com.forget_melody.terrablenderjs.terrablender.region.OverworldRegion;
import dev.latvian.mods.kubejs.event.EventJS;
import net.minecraft.resources.ResourceLocation;
import java.util.ArrayList;
import java.util.List;

public class RegisterRegionEventJS extends EventJS {
	private static final List<OverworldRegion.OverworldRegionJS> LIST = new ArrayList<>();
	
	public RegisterRegionEventJS() {
	
	}
	
	/**
	 * 创建Builder
	 * @param name Region的注册名
	 * @return CustomRegionBuilder
	 */
	public OverworldRegion.OverworldRegionJS create(ResourceLocation name, int weight){
		OverworldRegion.OverworldRegionJS builder = new OverworldRegion.OverworldRegionJS(name, weight);
		LIST.add(builder);
		return builder;
	}
	
	public OverworldRegion.OverworldRegionJS create(ResourceLocation name){
		return this.create(name, 10);
	}
	
	public List<OverworldRegion> getRegions() {
		return LIST.stream().map(builder -> builder.build()).toList();
	}
}
