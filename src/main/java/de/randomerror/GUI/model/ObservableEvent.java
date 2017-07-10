package de.randomerror.GUI.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 */
@Data
@AllArgsConstructor
public class ObservableEvent {
    private Object argument;
    private EventState state;
}
