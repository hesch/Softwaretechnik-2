package de.randomerror.util;

import lombok.Data;
import lombok.NoArgsConstructor;
/**
*
*/
@Data
@NoArgsConstructor
public class Instance<T> {
    private boolean initialized;
    private boolean circularDetection;
    private T data;

    public Instance(T data) {this.data = data; initialized = true; }
}
