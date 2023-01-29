package com.lango.book.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing // JPA Auditing을 config 파일에서 수행하도록 분리함
public class JpaConfig {
}
