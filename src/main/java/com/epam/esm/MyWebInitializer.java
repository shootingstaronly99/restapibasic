//package com.epam.esm;
//
//import com.epam.esm.config.DatabaseBeanConfiguration;
//import org.springframework.web.WebApplicationInitializer;
//import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
//import org.springframework.web.servlet.DispatcherServlet;
//
//import javax.servlet.ServletContext;
//import javax.servlet.ServletException;
//
//public class MyWebInitializer implements WebApplicationInitializer {
//
//    @Override
//    public void onStartup(ServletContext servletContext) throws ServletException {
//
//        var ctx = new AnnotationConfigWebApplicationContext();
//        ctx.register(EsmApplication.class, DatabaseBeanConfiguration.class);
//        ctx.setServletContext(servletContext);
//
//        var servlet = servletContext.addServlet("dispatcher", new DispatcherServlet(ctx));
//        servlet.setLoadOnStartup(1);
//        servlet.addMapping("/");
//    }
//}
