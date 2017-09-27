package com.farming.app.util;

import com.farming.app.exception.ExceptionControllerAdvice;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

public class AdviceHandlerExceptionResolver {

    public static HandlerExceptionResolver initGlobalExceptionHandlerResolvers() {

        StaticApplicationContext applicationContext = new StaticApplicationContext();
        applicationContext.registerSingleton("exceptionHandler", ExceptionControllerAdvice.class);

        WebMvcConfigurationSupport webMvcConfigurationSupport = new WebMvcConfigurationSupport();
        webMvcConfigurationSupport.setApplicationContext(applicationContext);

        return webMvcConfigurationSupport.handlerExceptionResolver();
    }
}
