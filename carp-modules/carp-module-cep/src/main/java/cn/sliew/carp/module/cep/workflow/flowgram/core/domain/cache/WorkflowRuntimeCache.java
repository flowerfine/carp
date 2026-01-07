package cn.sliew.carp.module.cep.workflow.flowgram.core.domain.cache;

import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.cache.ICache;

import java.util.HashMap;
import java.util.Map;

public class WorkflowRuntimeCache implements ICache {

    private Map<String, Object> map;

    @Override
    public void init() {
        this.map = new HashMap<>();
    }

    @Override
    public void dispose() {
        this.map.clear();
    }

    @Override
    public Object get(String key) {
        return map.get(key);
    }

    @Override
    public ICache set(String key, Object value) {
        map.put(key, value);
        return this;
    }

    @Override
    public boolean delete(String key) {
        map.remove(key);
        return true;
    }

    @Override
    public boolean has(String key) {
        return map.containsKey(key);
    }
}
