package cn.sliew.carp.module.orca.spinnaker.orca.queue;

import java.util.List;
import java.util.ListIterator;
import java.util.function.Consumer;

/**
 * 迭代器工具类，提供带有元数据的迭代功能
 */
public class Iterators {

    /**
     * 使用带有元数据的方式遍历列表
     * @param iterator 列表迭代器
     * @param consumer 处理每个元素的消费者函数
     * @param <T> 元素类型
     */
    public static <T> void forEachWithMetadata(ListIterator<T> iterator, Consumer<IteratorElement<T>> consumer) {
        while (iterator.hasNext()) {
            boolean first = !iterator.hasPrevious();
            int index = iterator.nextIndex();
            T value = iterator.next();
            boolean last = !iterator.hasNext();
            consumer.accept(new IteratorElement<>(value, index, first, last));
        }
    }

    /**
     * 使用带有元数据的方式遍历列表
     * @param list 要遍历的列表
     * @param consumer 处理每个元素的消费者函数
     * @param <T> 元素类型
     */
    public static <T> void forEachWithMetadata(List<T> list, Consumer<IteratorElement<T>> consumer) {
        forEachWithMetadata(list.listIterator(), consumer);
    }

    /**
     * 表示迭代过程中的元素及其元数据
     * @param <T> 元素类型
     */
    public static class IteratorElement<T> {
        private final T value;
        private final int index;
        private final boolean isFirst;
        private final boolean isLast;

        public IteratorElement(T value, int index, boolean isFirst, boolean isLast) {
            this.value = value;
            this.index = index;
            this.isFirst = isFirst;
            this.isLast = isLast;
        }

        public T getValue() {
            return value;
        }

        public int getIndex() {
            return index;
        }

        public boolean isFirst() {
            return isFirst;
        }

        public boolean isLast() {
            return isLast;
        }

        @Override
        public String toString() {
            return "IteratorElement(" +
                    "value=" + value +
                    ", index=" + index +
                    ", isFirst=" + isFirst +
                    ", isLast=" + isLast +
                    ')';
        }
    }

    /**
     * 使用范围获取子列表，类似 Groovy 的语法
     * 例如: assert getSubList(Arrays.asList(1, 2, 3), 1, 2).equals(Arrays.asList(2, 3))
     * 
     * @param list 源列表
     * @param start 起始索引（包含）
     * @param endInclusive 结束索引（包含）
     * @param <E> 元素类型
     * @return 子列表
     */
    public static <E> List<E> getSubList(List<E> list, int start, int endInclusive) {
        return list.subList(start, endInclusive + 1);
    }
}