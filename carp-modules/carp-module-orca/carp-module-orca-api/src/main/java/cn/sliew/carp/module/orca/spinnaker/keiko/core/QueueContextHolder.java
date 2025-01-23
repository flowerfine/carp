package cn.sliew.carp.module.orca.spinnaker.keiko.core;

public class QueueContextHolder {

    private static final ThreadLocal<Message> holder = new ThreadLocal();

    public static void set(Message message) {
        holder.set(message);
    }

    public static Message getMessage() {
        return holder.get();
    }

    public static void clear() {
        holder.remove();
    }
}
