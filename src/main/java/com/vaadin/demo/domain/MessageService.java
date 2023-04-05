package com.vaadin.demo.domain;

import com.vaadin.flow.spring.annotation.SpringComponent;

import java.time.Instant;
import java.util.stream.Stream;

@SpringComponent
public class MessageService {
    public Stream<Message> findAllByTopicSince(String topic, Instant since) {
        return null;
    }

    public void save(Message message) {
    }
}
