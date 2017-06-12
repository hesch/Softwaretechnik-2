package de.randomerror.GUI.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by henri on 12.06.17.
 */
@Data
@AllArgsConstructor
public class ObservableEvent {
    private Object argument;
    private EventState state;
}
