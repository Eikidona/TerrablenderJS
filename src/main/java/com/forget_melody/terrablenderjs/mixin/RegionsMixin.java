package com.forget_melody.terrablenderjs.mixin;

import org.spongepowered.asm.mixin.Mixin;
import terrablender.api.Regions;

@Mixin(Regions.class)
public abstract class RegionsMixin {
	// @Shadow 访问混入类同名成员变量与方法 用于通过编译器。。 事实上此处的成员已被遮蔽
	// @Unique 将新的成员属性混入目标类 如果已存在同名属性则不起作用
	// @Inject 向方法内注入代码
	
}
