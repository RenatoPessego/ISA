package si.um.feri.aiv.jms;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

// Optional bean to confirm application startup
@Startup
@Singleton
public class JmsQueueConfig {

    @PostConstruct
    public void init() {
        System.out.println("[JMS] Application started. Make sure 'DoctorQueue' exists in WildFly.");
    }
}
