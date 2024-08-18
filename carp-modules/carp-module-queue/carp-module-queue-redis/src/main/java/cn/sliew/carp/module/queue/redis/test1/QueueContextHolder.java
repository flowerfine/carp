package cn.sliew.carp.module.queue.redis.test1;

public class QueueContextHolder {

    private static ThreadLocal<TestMessage> holder = new ThreadLocal<>();

    public static void set(TestMessage context) {
        holder.set(context);
    }

    public static TestMessage get() {
        return holder.get();
    }

    public static void clear() {
        holder.remove();
    }

}
