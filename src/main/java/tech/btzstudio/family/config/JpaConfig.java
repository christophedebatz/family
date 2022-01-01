package tech.btzstudio.family.config;

import org.hibernate.reactive.mutiny.Mutiny;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.Persistence;

@Configuration
public class JpaConfig {

    @Bean
    public Mutiny.SessionFactory sessionFactory() {
        return Persistence
                .createEntityManagerFactory("familyPU")
                .unwrap(Mutiny.SessionFactory.class);
    }
}
