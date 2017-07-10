package de.randomerror.GUI.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Event Containing and Object and a corresponding EventState
 */
@Data
@AllArgsConstructor
public class ObservableEvent {
    private Object argument;
    private EventState state;
}
