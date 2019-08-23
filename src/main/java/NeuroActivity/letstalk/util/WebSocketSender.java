package NeuroActivity.letstalk.util;

import NeuroActivity.letstalk.dto.EventType;
import NeuroActivity.letstalk.dto.ObjectType;
import NeuroActivity.letstalk.dto.WSEventDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

@Component
public class WebSocketSender {

    private final SimpMessagingTemplate template;
    private final ObjectMapper mapper;

    public WebSocketSender(SimpMessagingTemplate template, ObjectMapper mapper) {
        this.template = template;
        this.mapper = mapper;
    }

    public <T> BiConsumer<EventType, T> getSender(ObjectType objectType, Class view){
        ObjectWriter objectWriter = mapper
                .setConfig(mapper.getSerializationConfig())
                .writerWithView(view);


        return (EventType eventType, T payload) ->{
            String value = null;
            try {
                value = objectWriter.writeValueAsString(payload);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("WS objectWriter error");
            }

            template.convertAndSend(
                    "/topic/activityThink",
                    new WSEventDTO(objectType, eventType, value)
            );
        };
    }
}
