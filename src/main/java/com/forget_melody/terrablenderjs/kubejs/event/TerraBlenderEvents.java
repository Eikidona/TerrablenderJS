package com.forget_melody.terrablenderjs.kubejs.event;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

public interface TerraBlenderEvents {
	
	public static EventGroup GROUP = EventGroup.of("TerraBlenderEvents");
	
	public static EventHandler REGISTER_REGIONS = GROUP.startup("registerRegion", () -> RegisterRegionEventJS.class);
	public static EventHandler REGISTER_SURFACE_RULES = GROUP.startup("registerSurfaceRule", () -> RegisterSurfaceRuleEventJS.class);
	
}
