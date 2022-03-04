package ru.job4j.concurrent.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        return memory.computeIfPresent(model.getId(), (key, value) -> {
            if (model.getVersion() != memory.get(model.getId()).getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
            return model;
        }) != null;
    }

    public void delete(Base model) {
        memory.remove(model);
    }
}