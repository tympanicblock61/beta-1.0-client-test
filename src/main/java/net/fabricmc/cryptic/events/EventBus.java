package net.fabricmc.cryptic.events;

import net.fabricmc.cryptic.Cryptic;
import net.fabricmc.cryptic.utils.ClassUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class EventBus {
    private List<Object> subscribers = new ArrayList<>();

    public EventBus() {
        Set<Method> meths = ClassUtils.getAnnotatedMethods(Cryptic.class.getPackage().getName(), EventListener.class);
        subscribers = meths.stream().collect(Collectors.toMap(Method::getDeclaringClass, method -> method, (existing, replacement) -> existing)).values().stream().map(Method::getDeclaringClass).collect(Collectors.toList());
        for (Object subscriber : subscribers) {
            if (subscriber instanceof Class<?>) {
                System.out.println("subscribed " + ((Class<?>) subscriber).getSimpleName());
            } else System.out.println("subscribed " + subscriber.getClass().getSimpleName());
        }
    }

    public void register(Object subscriber) {
        subscribers.add(subscriber);
        if (subscriber instanceof Class<?>) {
            System.out.println("subscribed " + ((Class<?>) subscriber).getSimpleName());
        } else System.out.println("subscribed " + subscriber.getClass().getSimpleName());
    }

    public HashMap<Method, Class<?>> getHandlers() {
        HashMap<Method, Class<?>> handlers = new HashMap<>();
        Iterator<Object> iterator = subscribers.iterator();
        while (iterator.hasNext()) {
            Object subscriber = iterator.next();
            Method[] methods;
            if (subscriber instanceof Class<?>) {
                methods = ((Class<?>) subscriber).getDeclaredMethods();
            } else methods = subscriber.getClass().getDeclaredMethods();
            boolean found = false;
            for (Method method : methods) {
                if (method.isAnnotationPresent(EventListener.class)) {
                    if (method.getParameterCount() >= 1) {
                        Class<?> eventType = method.getParameterTypes()[0];
                        if (Event.class.isAssignableFrom(eventType)) {
                            found = true;
                            handlers.put(method, eventType);
                        }
                    }
                }
            }
            if (!found) iterator.remove();
        }
        return handlers;
    }

    public void post(Event event) {
        HashMap<Method, Class<?>> handlers = getHandlers();
        handlers.forEach((Method method, Class<?> clazz) -> {
            if (event.getClass().equals(clazz)) {
                try {
                    Class<?> subscriber = method.getDeclaringClass();
                    if (!method.getDeclaringClass().isAssignableFrom(subscriber)) return;
                    method.invoke(subscriber, event);
                } catch (InvocationTargetException | IllegalAccessException | IllegalArgumentException e) {
                    e.printStackTrace();
                    subscribers.remove(method.getDeclaringClass());
                }
            }
        });
    }
}