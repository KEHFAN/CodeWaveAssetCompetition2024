package com.netease.lowcode.extension;

import com.netease.lowcode.extension.spring.component.DevToolsAspect;
import com.netease.lowcode.extension.spring.controller.LogController;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;

@Configurable
@ComponentScan(basePackageClasses = {LogController.class, DevToolsAspect.class})
public class DevToolsAutoScan {
}
