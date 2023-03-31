package org.deblock.exercise.deflight

import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.PropertySource
import spock.lang.Specification

@org.springframework.boot.test.context.TestConfiguration
@PropertySource("classpath:application-test.properties")
class TestConfiguration extends Specification{

    @Bean
    TestRestTemplate testRestTemplate() {
        new TestRestTemplate()
    }
}
