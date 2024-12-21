package com.forget_melody.terrablenderjs.kubejs.eventgroups;

import com.forget_melody.terrablenderjs.kubejs.event.RegisterRegionEventJS;
import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

public final class TerraBlenderEvents {
	public static EventGroup GROUP = EventGroup.of("TerraBlenderEvents");
	
	public static EventHandler REGISTER_REGIONS = GROUP.startup("registerRegions", () -> RegisterRegionEventJS.class);
}
