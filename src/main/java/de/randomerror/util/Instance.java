package de.randomerror.util;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Henri on 14.04.17.
 */
@Data
@NoArgsConstructor
public class Instance<T> {
    private boolean initialized;
    private boolean circularDetection;
    private T data;

    public Instance(T data) {this.data = data; initialized = true; }
}
