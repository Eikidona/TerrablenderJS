package com.forget_melody.terrablenderjs.kubejs;

import com.forget_melody.terrablenderjs.TerraBlenderJS;
import com.forget_melody.terrablenderjs.kubejs.event.TerraBlenderEvents;
import com.forget_melody.terrablenderjs.kubejs.util.UtilsJS;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingsEvent;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.rhino.util.wrap.TypeWrappers;
import net.minecraft.world.level.biome.Climate.*;
import net.minecraft.world.level.biome.Climate.ParameterPoint;
import net.minecraft.world.level.levelgen.SurfaceRules;
import terrablender.api.ParameterUtils;

public class TerraBlenderKubeJSPlugin extends KubeJSPlugin {
    /* Basic example of a KubeJS Plugin.
       To register your own plugins, add this class and package name to "kubejs.plugins.txt" in your Resources directory.
    */

    @Override
    public void init() {
        TerraBlenderJS.LOGGER.info("This is my KubeJS Plugin!");
        /** If you don't know how to add content, use Kube's built-in Plugin for reference.
        @see dev.latvian.mods.kubejs.BuiltinKubeJSPlugin
         */
    }
    @Override
    public void registerEvents() {
        super.registerEvents();
        TerraBlenderEvents.GROUP.register();
    }
    
    @Override
    public void registerTypeWrappers(ScriptType type, TypeWrappers typeWrappers) {
        super.registerTypeWrappers(type, typeWrappers);
//        typeWrappers.register(ParameterUtils.Continentalness.class, UtilsJS::toContinentalness);
//        typeWrappers.register(ParameterUtils.Temperature.class, UtilsJS::toTemperature);
//        typeWrappers.register(ParameterUtils.Humidity.class, UtilsJS::toHumidity);
//        typeWrappers.register(ParameterUtils.Weirdness.class, UtilsJS::toWeirdness);
//        typeWrappers.register(ParameterUtils.Erosion.class, UtilsJS::toErosion);
//        typeWrappers.register(ParameterUtils.Depth.class, UtilsJS::toDepth);
        typeWrappers.register(ParameterPoint.class, UtilsJS::toParameterPoint);
        typeWrappers.register(Parameter.class, UtilsJS::toParameter);
    }
    
    @Override
    public void registerBindings(BindingsEvent event) {
        super.registerBindings(event);
        event.add("ParameterPoint", ParameterPoint.class);
        event.add("Parameter", Parameter.class);
        event.add("ParameterUtils", ParameterUtils.class);
        event.add("SurfaceRules", SurfaceRules.class);
        event.add("Temperature", ParameterUtils.Temperature.class);
        event.add("Temperature", ParameterUtils.Temperature.class);
        event.add("Humidity", ParameterUtils.Humidity.class);
        event.add("Continentalness", ParameterUtils.Continentalness.class);
        event.add("Erosion", ParameterUtils.Erosion.class);
        event.add("Weirdness", ParameterUtils.Weirdness.class);
        event.add("Depth", ParameterUtils.Depth.class);
    }
}

