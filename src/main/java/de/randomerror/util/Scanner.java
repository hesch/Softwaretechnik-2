package de.randomerror.util;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Scans the classpath for classes
 * much scan, such classes, wow
 */
@NoArgsConstructor
@Log4j2
public class Scanner {
    /**
     *
     * @param packageName
     * @return a List of all Classes found in the given package
     * @throws IOException
     * @throws URISyntaxException
     */
    public List<Class> scanPackage(String packageName) throws IOException, URISyntaxException {
        ClassLoader classLoader = Scanner.class.getClassLoader();//ClassLoader.getSystemClassLoader();
        String path = packageName.replace('.', '/');
        int stuff = Paths.get(path).getNameCount();
        Enumeration<URL> resources = classLoader.getResources(path);
        return Collections.list(resources)
                .stream()
                .map(this::URLToURI)
                .peek(res -> {
                    if(res.getScheme().equals("file")) return;
                    try {FileSystems.newFileSystem(res, new HashMap<>());}
                    catch (IOException e) {e.printStackTrace();}
                })
                .map(this::URIToPath)
                .flatMap(it -> pathToClasses(it, stuff))
                .collect(Collectors.toList());
    }

    /**
     * translates a given URL to the corresponding URI
     * @param url
     * @return
     */
    private URI URLToURI(URL url) {
        try {
            return url.toURI();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * translates a given URI to the corresponding Path
     * @param uri
     * @return
     */
    private Path URIToPath(URI uri) {
        try {
            return Paths.get(uri);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    /**
     * If the path points to a single file, the file is transformed to a class
     * @param path
     * @param baseSegments
     * @return
     */
    private Class singleFileToClass(Path path, int baseSegments) {
        if(path.toString().endsWith(".class")) {
            String name = path.subpath(baseSegments, path.getNameCount()).toString().replace('/', '.');
            try {
                log.trace("found class: " + name.substring(0, name.length() - 6));
                return Class.forName(name.substring(0, name.length() - 6));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        return null;
    }

    /**
     * transforms all Files in the given path to classes
     * @param path
     * @param stuff
     * @return
     */
    private Stream<Class> pathToClasses(Path path, int stuff) {
        try {
            log.trace(path);
            int baseSegments = path.getNameCount()-stuff;
            return Files.walk(path, 10).filter(Files::isRegularFile).map(file -> singleFileToClass(file, baseSegments)).filter(Objects::nonNull);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
