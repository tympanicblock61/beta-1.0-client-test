package net.fabricmc.cryptic.utils;

import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

@SuppressWarnings("unchecked")
public class ClassUtils {

    public static Set<Class<?>> getSubtypes(String packageName, @NotNull Object clazz) {
        Reflections reflections = new Reflections(new ConfigurationBuilder().forPackage(packageName).setUrls(ClasspathHelper.forJavaClassPath()).setScanners(Scanners.SubTypes));
        return (clazz instanceof Class<?>) ? (Set<Class<?>>) reflections.getSubTypesOf((Class<?>) clazz) : (Set<Class<?>>) reflections.getSubTypesOf(clazz.getClass());
    }

    public static Set<Method> getAnnotatedMethods(String packageName, Object clazz) {
        Reflections reflections = new Reflections(new ConfigurationBuilder().forPackage(packageName).setUrls(ClasspathHelper.forJavaClassPath()).setScanners(Scanners.MethodsAnnotated));
        return (clazz instanceof Class<?>) ? reflections.getMethodsAnnotatedWith((Class<? extends Annotation>) clazz) : reflections.getMethodsAnnotatedWith((Class<? extends Annotation>) clazz.getClass());
    }
}

