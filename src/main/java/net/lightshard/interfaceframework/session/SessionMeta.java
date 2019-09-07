package net.lightshard.interfaceframework.session;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SessionMeta
{
    private final Map<String, SessionMetaObject> metaObjects;

    public SessionMeta()
    {
        metaObjects = new HashMap<String, SessionMetaObject>();
    }

    public SessionMetaObject get(String key)
    {
        return metaObjects.get(key.toUpperCase());
    }

    public void set(String key, SessionMetaObject object)
    {
        metaObjects.put(key.toUpperCase(), object);
    }

    public Set<String> keySet()
    {
        return metaObjects.keySet();
    }

    public Set<Map.Entry<String, SessionMetaObject>> entrySet()
    {
        return metaObjects.entrySet();
    }

}
