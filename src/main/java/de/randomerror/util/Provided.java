package de.randomerror.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Marker Annotation. All classes with the Annotation can be managed by the Injector
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Provided {
}
