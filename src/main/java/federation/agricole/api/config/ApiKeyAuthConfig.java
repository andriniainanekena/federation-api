package federation.agricole.api.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ApiKeyAuthConfig {

    @Bean
    public ApiKeyAuthFilter apiKeyAuthFilter() {
        String expected = System.getenv("AGRI_API_KEY");
        if (expected == null || expected.isBlank()) {
            expected = "agri-secure-key";
        }
        return new ApiKeyAuthFilter(expected);
    }

    @Bean
    public FilterRegistrationBean<ApiKeyAuthFilter> apiKeyAuthFilterRegistration(ApiKeyAuthFilter filter) {
        FilterRegistrationBean<ApiKeyAuthFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(filter);
        registration.addUrlPatterns("/*");
        registration.setOrder(Integer.MIN_VALUE);
        return registration;
    }
}


