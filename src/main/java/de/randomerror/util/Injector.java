package de.randomerror.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by Henri on 11.04.17.
 */
public class Injector {

    private static Map<Class, Instance> classPool = new HashMap<>();

    public static void main(String[] args) throws IOException {
        init();
    }

    public static void init() throws IOException {
        List<Class> classes = new Scanner().scanPackage("de.randomerror");

        classes.stream().filter(c -> c.getAnnotation(Provided.class) != null).map(Class::getCanonicalName).forEach(System.out::println);

        addToClassPool(classes);
    }

    public static <T> T getProvided(Class<T> c) {
        return (T) classPool.get(c).getData();
    }

    private static void addToClassPool(List<Class> classes) {
        classes.stream()
                .filter(c -> c.getAnnotation(Provided.class) != null)
                .forEach(c -> classPool.put(c, new Instance()));

        classPool.keySet().forEach(Injector::resolveInstance);
    }

    private static <T> void resolveInstance(Class<T> c) {
        Instance<T> i = classPool.get(c);

        if(i.isInitialized())
            return;

        if(i.isCircularDetection())
            throw new CircularDependencyException("Circular Dependency detected! Aborting...");

        i.setCircularDetection(true);

        T instance = null;
        try {
            instance = c.newInstance();
            final T finalInstance = instance;

            Arrays.stream(c.getDeclaredFields())
                .filter(f -> classPool.containsKey(f.getType()))
                .forEach(f -> injectField(f, finalInstance));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        i.setData(instance);
        i.setInitialized(true);
        i.setCircularDetection(false);
    }

    private static void injectField(Field f, Object instance) {
        try {
            if(!classPool.get(f.getType()).isInitialized()) {
                resolveInstance(f.getType());
            }
            System.out.println("injecting field: " + f.getName() + " on Instance: " + instance.getClass().getCanonicalName());
            f.setAccessible(true);
            f.set(instance, classPool.get(f.getType()).getData());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
