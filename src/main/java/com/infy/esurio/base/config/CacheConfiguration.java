package com.infy.esurio.base.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.infy.esurio.base.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.infy.esurio.base.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.infy.esurio.base.domain.User.class.getName());
            createCache(cm, com.infy.esurio.base.domain.Authority.class.getName());
            createCache(cm, com.infy.esurio.base.domain.User.class.getName() + ".authorities");
            createCache(cm, com.infy.esurio.base.domain.Centers.class.getName());
            createCache(cm, com.infy.esurio.base.domain.Foodcourts.class.getName());
            createCache(cm, com.infy.esurio.base.domain.Esuriits.class.getName());
            createCache(cm, com.infy.esurio.base.domain.Outlets.class.getName());
            createCache(cm, com.infy.esurio.base.domain.Menus.class.getName());
            createCache(cm, com.infy.esurio.base.domain.Vendors.class.getName());
            createCache(cm, com.infy.esurio.base.domain.Attendants.class.getName());
            createCache(cm, com.infy.esurio.base.domain.Items.class.getName());
            createCache(cm, com.infy.esurio.base.domain.Categories.class.getName());
            createCache(cm, com.infy.esurio.base.domain.Tags.class.getName());
            createCache(cm, com.infy.esurio.base.domain.Dishes.class.getName());
            createCache(cm, com.infy.esurio.base.domain.Orders.class.getName());
            createCache(cm, com.infy.esurio.base.domain.Servings.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }
}
