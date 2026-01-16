package ipss.web2.examen.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// Configuración para habilitar JPA Auditing
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {
    // Habilita auditoría automática para @CreatedDate y @LastModifiedDate
}
