package com.ddadjee.iam.adapter.hashing;

import com.ddadjee.iam.application.port.out.DomainEventPublisherPort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpringApplicationEventPublisherAdapter implements DomainEventPublisherPort {
    private final ApplicationEventPublisher publisher;

    @Override
    public void publishAll(Iterable<?> events) {
        events.forEach(publisher::publishEvent);
    }
}
