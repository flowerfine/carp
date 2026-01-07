package cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.cache;

public interface ICache<V> {

    void init();

    void dispose();

    V get(String key);

    ICache set(String key, V value);

    boolean delete(String key);

    boolean has(String key);
}
