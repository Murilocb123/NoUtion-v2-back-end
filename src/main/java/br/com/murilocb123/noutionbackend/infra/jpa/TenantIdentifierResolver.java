package br.com.murilocb123.noutionbackend.infra.jpa;

import br.com.murilocb123.noutionbackend.infra.security.UserContextHolder;
import org.hibernate.cfg.MultiTenancySettings;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class TenantIdentifierResolver
        implements CurrentTenantIdentifierResolver<UUID>, HibernatePropertiesCustomizer {

    @Override
    public UUID resolveCurrentTenantIdentifier() {
        UUID id = UserContextHolder.getUserId();
        return (id != null) ? id : UUID.fromString("00000000-0000-0000-0000-000000000001");
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }

    @Override
    public void customize(Map<String, Object> props) {
        props.put(MultiTenancySettings.MULTI_TENANT_IDENTIFIER_RESOLVER, this);
    }
}