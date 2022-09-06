package newstream;

import helpers.Distinct;
import helpers.Terminal;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class NewStreamImpl<T> implements NewStream<T> {

    private final List<T> list;
    private final List<Object> pipeline = new ArrayList<>();

    public NewStreamImpl(List<T> list) {
        this.list = list;
    }

    public static <T> NewStream<T> of(List<T> list) {
        return new NewStreamImpl<>(list);
    }

    @Override
    public NewStream<T> filter(Predicate<? super T> predicate) {
        pipeline.add(predicate);
        return this;
    }

    @Override
    public <R> NewStream<R> map(Function<? super T, ? extends R> mapper) {
        pipeline.add(mapper);
        return (NewStream<R>) this;
    }

    @Override
    public NewStream<T> limit(long maxSize) {
        if (maxSize < 0)
            throw new IllegalArgumentException(Long.toString(maxSize));
        pipeline.add(maxSize);
        return this;
    }

    @Override
    public NewStream<T> distinct() {
        pipeline.add(new Distinct());
        return this;
    }


    @Override
    public List<T> toList() {
        return new Terminal<>(list, pipeline).terminalList();
    }

    @Override
    public <K, V> Map<K, V> toMap(Function<? super T, ? extends K> keyMapper,
                                  Function<? super T, ? extends V> valueMapper) {

        return new Terminal<>(list, pipeline).terminalMap(keyMapper, valueMapper);
    }

    @Override
    public Optional<T> findFirst() {

        return new Terminal<>(list, pipeline).terminalFindFirst();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        new Terminal<>(list, pipeline).terminalForEach(action);
    }

    @Override
    public long count() {
        return new Terminal<>(list, pipeline).terminalCount();
    }
}