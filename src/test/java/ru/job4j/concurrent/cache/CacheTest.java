package ru.job4j.concurrent.cache;

import org.junit.Test;

import static org.junit.Assert.*;

public class CacheTest {

    @Test
    public void add() {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        cache.add(base);
        assertEquals(base, cache.get(1));
    }

    @Test
    public void update() {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        Base newBase = new Base(1, 1);
        cache.add(base);
        cache.update(newBase);
        assertEquals(newBase.getName(), cache.get(1).getName());
        assertEquals(2, cache.get(1).getVersion());
    }

    @Test
    public void delete() {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        cache.add(base);
        cache.delete(base);
        assertNull(cache.get(1));
    }
}