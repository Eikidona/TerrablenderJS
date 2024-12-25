package com.forget_melody.terrablenderjs.kubejs.event;

import com.forget_melody.terrablenderjs.terrablender.region.OverworldRegion;
import dev.latvian.mods.kubejs.script.ScriptType;

import java.util.List;

/**
 * 该类作为所有EventJS实例的Factory 作为容纳发布各种事件的方法的容器类
 */
public class KubeJSEventFactory {
	public static void init() {
	
	}
	
	public static List<OverworldRegion> registerRegions() {
		RegisterRegionEventJS event = new RegisterRegionEventJS();
		TerraBlenderEvents.REGISTER_REGIONS.post(ScriptType.STARTUP, event);
		return event.getRegions();
	}
	
	public static List<RegisterSurfaceRuleEventJS.RuleSourceRecorder> registerSurfaceRules(){
		RegisterSurfaceRuleEventJS event = new RegisterSurfaceRuleEventJS();
		TerraBlenderEvents.REGISTER_SURFACE_RULES.post(ScriptType.STARTUP, event);
		return event.getRuleSourceBuilders();
	}
}
