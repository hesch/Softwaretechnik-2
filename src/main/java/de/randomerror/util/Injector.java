package de.randomerror.util;

import java.io.File;
import java.io.IOException;
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

    public static void main(String[] args) throws IOException {
        init();
    }

    public static void init() throws IOException {
        List<Class> classes = scanPackage("de.randomerror");

        classes.stream().filter(c -> c.getAnnotation(Provided.class) != null).map(Class::getCanonicalName).forEach(System.out::println);
    }
    
    private static Path URLToPath(URL url) {
        try {
            return Paths.get(url.toURI());
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    private static List<Class> scanPackage(String packageName) throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        classLoader = ClassLoader.getSystemClassLoader();

        String path = packageName.replace('.', File.separatorChar);
        int stuff = Paths.get(path).getNameCount();
        Enumeration<URL> resources = classLoader.getResources(path);

        return Collections.list(resources).stream().map(Injector::URLToPath).flatMap(it -> Injector.pathToClasses(it, stuff)).collect(Collectors.toList());
    }

    private static Class singleFileToClass(Path path, int baseSegments) {
        if(path.toString().endsWith(".class")) {
            String name = path.subpath(baseSegments, path.getNameCount()).toString().replace('/', '.');
            try {
                return Class.forName(name.substring(0, name.length() - 6));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        return null;
    }

    private static Stream<Class> pathToClasses(Path path, int stuff) {
        try {
            System.out.println(path);
            int baseSegments = path.getNameCount()-stuff;
            return Files.walk(path, 10).filter(Files::isRegularFile).map(file -> singleFileToClass(file, baseSegments)).filter(Objects::nonNull);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
