package org.example.han.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class JpaConfig implements AuditorAware {

    @Override
    public Optional getCurrentAuditor() {
        return Optional.empty();
    }
}
