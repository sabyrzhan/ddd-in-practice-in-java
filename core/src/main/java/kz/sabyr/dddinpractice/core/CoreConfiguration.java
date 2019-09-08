package kz.sabyr.dddinpractice.core;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = {
        "kz.sabyr.dddinpractice.core.common.domain.events",

        "kz.sabyr.dddinpractice.core.snackmachine.domain.repository",
        "kz.sabyr.dddinpractice.core.snackmachine.jpa.repository",

        "kz.sabyr.dddinpractice.core.atms.domain.repository",
        "kz.sabyr.dddinpractice.core.atms.jpa.repository",
        "kz.sabyr.dddinpractice.core.atms.integration",

        "kz.sabyr.dddinpractice.core.management.domain.repository",
        "kz.sabyr.dddinpractice.core.management.jpa.repository",
        "kz.sabyr.dddinpractice.core.management.domain.events.handler"
})
@EnableJpaRepositories
@EntityScan(basePackages = {
        "kz.sabyr.dddinpractice.core.snackmachine.jpa",
        "kz.sabyr.dddinpractice.core.atms.jpa",
        "kz.sabyr.dddinpractice.core.management.jpa"
})
public class CoreConfiguration {
}