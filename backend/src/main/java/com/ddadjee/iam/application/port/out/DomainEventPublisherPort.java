package com.ddadjee.iam.application.port.out;

/**
 * Domain Event Publisher Port
 */
public interface DomainEventPublisherPort {
    void publishAll(Iterable<?> events);
}
