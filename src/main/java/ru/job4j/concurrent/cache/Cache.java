package ru.job4j.concurrent.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public Base get(int id) {
        return memory.get(id);
    }

    public boolean update(Base model) {
        return memory.computeIfPresent(model.getId(), (key, value) -> {
            if (model.getVersion() != value.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
            Base base = new Base(key, model.getVersion() + 1);
            base.setName(model.getName());
            return base;
        }) != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId());
    }
}