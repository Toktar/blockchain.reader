package ru.blockboxchain.reader.models.events;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Created by toktar.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
public class ICarEvent {
}
