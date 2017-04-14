package de.randomerror.util;

/**
 * Created by Henri on 14.04.17.
 */
public class Instance<T> {
    private boolean initialized;
    private boolean circularDetection;
    private T data;

    public Instance() {};
    public Instance(T data) {this.data = data; initialized = true; }

    public T getData() { return data; }
    public void setData(T d) { data = d; initialized = true; }
    public boolean isInitialized() { return initialized; }

    public boolean getCircularDetection() { return circularDetection; }
    public void setCircularDetection(boolean cD) { circularDetection = cD; }
}
