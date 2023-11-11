package net.fabricmc.cryptic.utils.datatypes;

import java.util.AbstractMap;

public class Pair<K, V> extends AbstractMap.SimpleEntry<K, V> {
    public Pair(K one, V two) {
        super(one, two);
    }

    public static <K, V> Pair<K, V> of(K one, V two) {
        return new Pair<>(one, two);
    }

    public K getOne() {
        return super.getKey();
    }

    public V getTwo() {
        return super.getValue();
    }

    public K getLeft() {
        return super.getKey();
    }

    public V getRight() {
        return super.getValue();
    }

    public K getKey() {
        return super.getKey();
    }

    public V getValue() {
        return super.getValue();
    }
}

