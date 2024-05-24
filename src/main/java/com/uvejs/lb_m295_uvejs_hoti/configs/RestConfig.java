package com.uvejs.lb_m295_uvejs_hoti.configs;
import com.uvejs.lb_m295_uvejs_hoti.securities.AuthenticationFilter;
import com.uvejs.lb_m295_uvejs_hoti.services.BookController;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@ApplicationPath("/controller")
public class RestConfig extends Application {
    public Set<Class<?>> getClasses() {
        return new HashSet<Class<?>>(
                List.of(BookController.class, AuthenticationFilter.class));
    }
}