package federation.agricole.api.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class ApiKeyAuthFilter extends OncePerRequestFilter {

    private static final String HEADER_NAME = "x-api-key";


    private static final String FAILURE_MESSAGE = "Bad credentials";

    private final String expectedApiKey;

    public ApiKeyAuthFilter(String expectedApiKey) {
        this.expectedApiKey = expectedApiKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String providedKey = request.getHeader(HEADER_NAME);

        if (providedKey == null || !providedKey.equals(expectedApiKey)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().write(FAILURE_MESSAGE);
            response.getWriter().flush();
            return;
        }

        filterChain.doFilter(request, response);
    }


}


