package com.forget_melody.terrablenderjs.kubejs;

import com.forget_melody.terrablenderjs.TerraBlenderJS;
import com.forget_melody.terrablenderjs.kubejs.eventgroups.TerraBlenderEvents;
import dev.latvian.mods.kubejs.KubeJSPlugin;

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
}

