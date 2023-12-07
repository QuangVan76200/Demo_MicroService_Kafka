/**
 * 
 */
package com.service.emailservice.service;

import java.util.Map;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Service
public class ThymleafServiceImpl implements IThymleafService{
	
	private static final String MAIL_TEMPLATE_BASE_NAME = "mail/MailMessage";
	
	private static final String MAIL_TEMPLATE_PREFIX = "/templates/";
	
	private static final String MAIL_TEMPLATE_SUFFIX = ".html";
	
	private static final String UTF_8 = "UTF-8";
	
	private static TemplateEngine templateEngine;
	
	static {
		templateEngine = emailTemplateEngine();
	}

	

	private static TemplateEngine emailTemplateEngine() {
		final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(htmlTempldateResolver());
		templateEngine.setTemplateEngineMessageSource(emailMessageSource());
		
		return templateEngine;
	}

	private static ITemplateResolver htmlTempldateResolver() {
		final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setPrefix(MAIL_TEMPLATE_PREFIX);
		templateResolver.setSuffix(MAIL_TEMPLATE_SUFFIX);
		templateResolver.setTemplateMode(TemplateMode.HTML);
		templateResolver.setCharacterEncoding(UTF_8);
		templateResolver.setCacheable(false);
		
		return templateResolver;
	}

	private static ResourceBundleMessageSource emailMessageSource() {
		final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename(MAIL_TEMPLATE_BASE_NAME);
		
		return messageSource;
	}
	
	@Override
	public String createContext(String tempalte, Map<String, Object> variables) {
		final Context context = new Context();
		context.setVariables(variables);
		return templateEngine.process(tempalte, context);
	}

}
