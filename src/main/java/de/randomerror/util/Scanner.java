package de.randomerror.util;

import lombok.NoArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Henri on 15.04.17.
 */
@NoArgsConstructor
public class Scanner {

    public List<Class> scanPackage(String packageName) throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        classLoader = ClassLoader.getSystemClassLoader();

        String path = packageName.replace('.', File.separatorChar);
        int stuff = Paths.get(path).getNameCount();
        Enumeration<URL> resources = classLoader.getResources(path);

        return Collections.list(resources).stream().map(this::URLToPath).flatMap(it -> pathToClasses(it, stuff)).collect(Collectors.toList());
    }

    private Path URLToPath(URL url) {
        try {
            return Paths.get(url.toURI());
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    private Class singleFileToClass(Path path, int baseSegments) {
        if(path.toString().endsWith(".class")) {
            String name = path.subpath(baseSegments, path.getNameCount()).toString().replace(File.separatorChar, '.');
            try {
                return Class.forName(name.substring(0, name.length() - 6));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        return null;
    }

    private Stream<Class> pathToClasses(Path path, int stuff) {
        try {
            System.out.println(path);
            int baseSegments = path.getNameCount()-stuff;
            return Files.walk(path, 10).filter(Files::isRegularFile).map(file -> singleFileToClass(file, baseSegments)).filter(Objects::nonNull);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
