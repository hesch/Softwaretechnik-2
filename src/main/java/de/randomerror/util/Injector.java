package de.randomerror.util;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.*;


/**
 * Manages Dependenies of Classes marked by the @Provided Annotation.
 */
@Log4j2
public class Injector {

    private static Injector instance = new Injector();

    public static Injector getInstance() {
        return instance;
    }

    private Injector() {}

    private Map<Class, Instance> classPool = new HashMap<>();

    /**
     *
     * @throws IOException
     * @throws URISyntaxException
     */
    public void init() throws IOException, URISyntaxException {
        List<Class> classes = new Scanner().scanPackage("de.randomerror");

        classes.stream().filter(c -> c.getAnnotation(Provided.class) != null).map(Class::getCanonicalName).forEach(log::debug);

        addToClassPool(classes);

        classPool.forEach((c, i) -> {
            try{
                Method m = c.getMethod("onInit");
                m.invoke(i.getData());
            } catch(Exception e) {
                //do nothing
            }
        });
    }

    /**
     *
     * @param c
     * @param <T>
     * @return
     */
    public <T> T getProvided(Class<T> c) {
        return (T) getInstanceFromClassPool(c).get().getData();
    }

    /**
     *
     * @param classes
     */
    private void addToClassPool(List<Class> classes) {
        classes.stream()
                .filter(c -> c.getAnnotation(Provided.class) != null)
                .forEach(c -> classPool.put(c, new Instance()));

        classPool.keySet().forEach(this::resolveInstance);
    }

    /**
     *
     * @param c
     * @param <T>
     */
    private <T> void resolveInstance(Class<T> c) {
        Instance<T> i = getInstanceFromClassPool(c).get();

        if(i.isInitialized())
            return;

        if(i.isCircularDetection())
            throw new CircularDependencyException("Circular Dependency detected! Aborting...");

        i.setCircularDetection(true);

        T instance = null;
        try {
            if(c.isInterface()) {
                Optional<Class> optional = classPool.keySet().stream().filter(c::isAssignableFrom).findFirst();
                if(optional.isPresent()) {
                    log.debug("found Interface: " + c.getCanonicalName() + " using implementation: " + optional.get().getCanonicalName());
                    c = optional.get();
                    instance = (T) optional.get().newInstance();
                } else return;
            } else {
                instance = c.newInstance();
            }
            final T finalInstance = instance;

            Arrays.stream(c.getDeclaredFields())
                .filter(f -> containsType(f.getType()))
                .forEach(f -> injectField(f, finalInstance));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        i.setData(instance);
        i.setInitialized(true);
        i.setCircularDetection(false);
    }

    /**
     *
     * @param f
     * @param instance
     */
    private void injectField(Field f, Object instance) {
        try {
            if(!getInstanceFromClassPool(f.getType()).map(Instance::isInitialized)
                    .orElse(false)) {
                resolveInstance(f.getType());
            }
            log.trace("injecting field: " + f.getName() + " on Instance: " + instance.getClass().getCanonicalName());
            f.setAccessible(true);
            f.set(instance, getInstanceFromClassPool(f.getType()).get().getData());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param type
     * @return
     */
    private Optional<Instance> getInstanceFromClassPool(Class type) {
        return classPool.keySet().stream()
                .filter(clazz -> type.isAssignableFrom(clazz))
                .findFirst()
                .map(classPool::get);
    }

    /**
     *
     * @param type
     * @return
     */
    private boolean containsType(Class type) {
        return classPool.keySet().stream()
                .anyMatch(clazz -> type.isAssignableFrom(clazz));
    }
}
