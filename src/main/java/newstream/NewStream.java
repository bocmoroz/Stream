package newstream;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public interface NewStream<T> {

    NewStream<T> filter(Predicate<? super T> predicate);

    <R> NewStream<R> map(Function<? super T, ? extends R> mapper);

    NewStream<T> limit(long maxSize);

    NewStream<T> distinct();

    List<T> toList();

    <K, V> Map<K, V> toMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends V> valueMapper);

    Optional<T> findFirst();

    void forEach(Consumer<? super T> action);

    long count();
}
