package cs309.backend.config;


import jakarta.websocket.server.ServerEndpointConfig;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class CustomConfigurator extends ServerEndpointConfig.Configurator implements ApplicationContextAware {
    private static volatile ApplicationContext context;

    @Override
    public <T> T getEndpointInstance(Class<T> endpointClass) {
        return context.getBean(endpointClass);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        CustomConfigurator.context = applicationContext;
    }
}
