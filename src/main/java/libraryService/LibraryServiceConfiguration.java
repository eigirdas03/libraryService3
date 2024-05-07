package libraryService;

import java.util.List;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.server.endpoint.interceptor.PayloadValidatingInterceptor;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.XsdSchemaCollection;
import org.springframework.xml.xsd.commons.CommonsXsdSchemaCollection;

import libraryService.endpoints.LibraryEndpoint;


@EnableWs
@Configuration
public class LibraryServiceConfiguration extends WsConfigurerAdapter
{
    @Bean
    ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext)
	{
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(applicationContext);
		servlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean<>(servlet, "/library_service/*");
	}
    
	@Bean(name = "library_service")
	DefaultWsdl11Definition libraryWsdl11Definition(XsdSchemaCollection librarySchema)
	{
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		
		wsdl11Definition.setPortTypeName("LibraryServicePort");
		wsdl11Definition.setLocationUri("/library_service");
		wsdl11Definition.setTargetNamespace(LibraryEndpoint.NAMESPACE);
		wsdl11Definition.setSchemaCollection(librarySchema);
		
		return wsdl11Definition;
	}
	
	@Bean
	XsdSchemaCollection librarySchema()
	{
	    CommonsXsdSchemaCollection commonsXsdSchemaCollection = new CommonsXsdSchemaCollection(
	            new ClassPathResource("plant.xsd"),
	            new ClassPathResource("book.xsd"),
	            new ClassPathResource("library.xsd"));
	    commonsXsdSchemaCollection.setInline(true);
	    return commonsXsdSchemaCollection;
	}
	
	@Override
    public void addInterceptors(List<EndpointInterceptor> interceptors) 
    {
        PayloadValidatingInterceptor validatingInterceptor = new PayloadValidatingInterceptor();
        validatingInterceptor.setValidateRequest(true);
        validatingInterceptor.setValidateResponse(true);
        validatingInterceptor.setXsdSchemaCollection(librarySchema());
        interceptors.add(validatingInterceptor);
    }
}
