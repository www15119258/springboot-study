package com.cangzhitao.springboot.study.configuration;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.cangzhitao.springboot.study.filter.ForbiddenFilter;

@Configuration
public class WebConfiguration extends WebMvcConfigurationSupport {
	
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		super.configureMessageConverters(converters);
		StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
		stringConverter.setDefaultCharset(StandardCharsets.UTF_8);
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
        		SerializerFeature.BrowserSecure, 
				SerializerFeature.WriteEnumUsingToString, 
				SerializerFeature.WriteDateUseDateFormat, 
				SerializerFeature.DisableCircularReferenceDetect);
        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.ALL);
        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
        fastConverter.setSupportedMediaTypes(supportedMediaTypes);
        fastConverter.setFastJsonConfig(fastJsonConfig);
        stringConverter.setSupportedMediaTypes(supportedMediaTypes);
        converters.add(stringConverter);
        converters.add(fastConverter);
    }

	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**")
        	.addResourceLocations("classpath:/resources/")
        	.addResourceLocations("classpath:/static/")
        	.addResourceLocations("classpath:/public/");
		super.addResourceHandlers(registry);
	}
	
	@Bean
	public FilterRegistrationBean<ForbiddenFilter> forbiddenFilter() {
		FilterRegistrationBean<ForbiddenFilter> frBean = new FilterRegistrationBean<>();
		ForbiddenFilter ff = new ForbiddenFilter();
		frBean.setFilter(ff);
		frBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		frBean.addUrlPatterns("/login.do");
		return frBean;
	}

	@Bean
	public FilterRegistrationBean<CoreFilter> CoreFilter() {
		FilterRegistrationBean<CoreFilter> frBean = new FilterRegistrationBean<>();
		CoreFilter ff = new CoreFilter();
		frBean.setFilter(ff);
		frBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		frBean.addUrlPatterns("/*");
		return frBean;
	}
	
}
