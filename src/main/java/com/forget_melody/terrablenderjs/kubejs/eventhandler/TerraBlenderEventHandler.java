package com.forget_melody.terrablenderjs.kubejs.eventhandler;

import com.forget_melody.terrablenderjs.kubejs.event.RegisterRegionEventJS;
import com.forget_melody.terrablenderjs.kubejs.eventgroups.TerraBlenderEvents;
import com.forget_melody.terrablenderjs.terrablender.region.CustomRegion;
import dev.latvian.mods.kubejs.script.ScriptType;

import java.util.List;

public class TerraBlenderEventHandler {
	public static void init(){
	
	}
	public static List<CustomRegion> registerRegions(){
		RegisterRegionEventJS event = new RegisterRegionEventJS();
		TerraBlenderEvents.REGISTER_REGIONS.post(ScriptType.STARTUP, event);
		return event.getRegisteredRegions();
	}
}
