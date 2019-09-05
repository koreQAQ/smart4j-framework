package work.lishubin.smart4j.framework.commons;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lishubin
 */
public class MyThreadLocal<T> {

    private final Map<Thread, T> container = Collections.synchronizedMap(new HashMap<>());

    private static final Thread CURRENT_THREAD = Thread.currentThread();

    public void setValue(T t) {
        container.put(CURRENT_THREAD, t);
    }

    public T get() {
        T t = null;
        if (container.containsKey(CURRENT_THREAD)) {
            t = container.get(CURRENT_THREAD);
        }
        return t;
    }

    public void remove() {
        container.remove(CURRENT_THREAD);
    }

    /**
     * 返回容器的初始值
     * 留给子类重写
     *
     * @return 默认为空
     */
    protected T initialValue() {
        return null;
    }


}
