package uz.pdp.newssite.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import uz.pdp.newssite.entity.User;

@Configuration
@EnableJpaAuditing
public class ReturnWhoWriteAuditing {
    @Bean
    AuditorAware<User> auditorAware() {
        return new KnowingWhoCreateOrUpdAuditing();
    }
}
