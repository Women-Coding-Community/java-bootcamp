package com.wcc.bootcamp.java.mentorship.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Configuration to enable asynchronous method execution.
 * Used by EmailService to send emails without blocking the main request.
 */
@Configuration
@EnableAsync
public class AsyncConfig {
}
