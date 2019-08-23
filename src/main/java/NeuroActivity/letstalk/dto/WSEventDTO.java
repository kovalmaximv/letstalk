package NeuroActivity.letstalk.dto;

import NeuroActivity.letstalk.domain.Views;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonView(Views.id.class)
public class WSEventDTO {
    private ObjectType objectType;
    private EventType eventType;

    @JsonRawValue
    private String body;
}
