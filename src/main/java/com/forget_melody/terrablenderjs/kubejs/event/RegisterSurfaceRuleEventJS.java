package com.forget_melody.terrablenderjs.kubejs.event;

import dev.latvian.mods.kubejs.event.EventJS;
import net.minecraft.world.level.levelgen.SurfaceRules.*;
import terrablender.api.SurfaceRuleManager.*;

import java.util.ArrayList;
import java.util.List;

public class RegisterSurfaceRuleEventJS extends EventJS {
	private static final List<RuleSourceRecorder> LIST = new ArrayList<>();
	
	public RegisterSurfaceRuleEventJS() {
	}
	
	/**
	 * 注册SurfaceRule
	 * @param regionName
	 * @param category
	 * @param ruleSource
	 */
	public void register(String regionName, RuleCategory category, RuleSource ruleSource){
		RuleSourceRecorder recorder = new RuleSourceRecorder(regionName, category, ruleSource);
		LIST.add(recorder);
	}
	
	public List<RuleSourceRecorder> getRuleSourceBuilders() {
		return LIST;
	}
	
	public record RuleSourceRecorder(String name, RuleCategory category, RuleSource ruleSource) { }
}
