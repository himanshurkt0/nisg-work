package com.sidbi.config;


import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.sidbi"})
public class WebMvcConfig implements WebMvcConfigurer {

   @Bean
   public InternalResourceViewResolver resolver() {
      InternalResourceViewResolver resolver = new InternalResourceViewResolver();
      resolver.setViewClass(JstlView.class);
      resolver.setPrefix("/WEB-INF/views/");
      resolver.setSuffix(".jsp");
      return resolver;
   }

   @Bean
   public MessageSource messageSource1() {
      ResourceBundleMessageSource source = new ResourceBundleMessageSource();
      source.setBasename("messages");
      return source;
   }
   
   @Bean(name = "multipartResolver")
   public CommonsMultipartResolver multipartResolver() {
       CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
     //  multipartResolver.setMaxUploadSizePerFile(2097152);
       multipartResolver.setMaxUploadSize(2097152);
   //    multipartResolver.setMaxInMemorySize(0);
       return multipartResolver;
   }

   @Override
   public Validator getValidator() {
      LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
      validator.setValidationMessageSource(messageSource());
      return validator;
   }
   @Override
   public void addResourceHandlers(ResourceHandlerRegistry registry) {
       registry.addResourceHandler("/assets/**").addResourceLocations("/assets/");
       registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
       registry.addResourceHandler("/jquery_api/**").addResourceLocations("/jquery_api/");
   }
   @Bean("messageSource")
   public MessageSource messageSource() {
      ReloadableResourceBundleMessageSource messageSource=new ReloadableResourceBundleMessageSource();
      messageSource.setBasename("/WEB-INF/message/messages");
      messageSource.setDefaultEncoding("UTF-8");
      messageSource.setUseCodeAsDefaultMessage(true);
      return messageSource;
   }

   @Bean
   public LocaleResolver localeResolver() {
      CookieLocaleResolver localeResolver = new CookieLocaleResolver();
      return localeResolver;
   }
   
   @Override
   public void configureMessageConverters(List<HttpMessageConverter<?>> converters){
	   converters.add(mappingJackson2HttpMessageConverter());
   }

   private MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
	return new MappingJackson2HttpMessageConverter();
}

@Override
   public void addInterceptors(InterceptorRegistry registry) {     

      LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
      localeChangeInterceptor.setParamName("lang");
      registry.addInterceptor(localeChangeInterceptor);
   }
}
