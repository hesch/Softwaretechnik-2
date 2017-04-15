package de.randomerror.util;

import de.randomerror.persistence.Lager;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        classPool.values().stream()
                .map(i -> i.getData())
                .filter(o -> o instanceof Lager)
                .map(o -> (Lager)o)
                .forEach(lager -> lager.testMethod());
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
            throw new RuntimeException("Circular Dependency detected! Aborting...");

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
            if(classPool.get(f.getType()).isInitialized())
                f.set(instance, classPool.get(f.getType()).getData());
            else
                resolveInstance(f.getType());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
