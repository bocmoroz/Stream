package helpers;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class Terminal<T> {

    private final List<T> list;
    private final List<Object> pipeline;

    private final List<Long> listForLimits;                //лист лимитов
    private final List<Long> listForElements;              //текущее количество элементов для проверки каждого лимита
    private final List<List<T>> uniqueList;                //для каждого вызова distinct отдельный List

    public Terminal(List<T> list, List<Object> pipeline) {
        this.list = list;
        this.pipeline = pipeline;
        listForLimits = fillListLimits(pipeline);
        listForElements = initOfElements(listForLimits.size());
        uniqueList = initOfUnique(pipeline);
    }

    public List<T> terminalList() {
        List<T> newList = new ArrayList<>();

        for (T obj : list) {
            obj = PipelineHandler.startHandler(obj, pipeline, listForLimits, listForElements, uniqueList);
            if (obj != null) {
                newList.add(obj);
            }
        }
        return newList;
    }

    public <K, V> Map<K, V> terminalMap(Function<? super T, ? extends K> keyMapper,
                                        Function<? super T, ? extends V> valueMapper) {
        Map<K, V> map = new HashMap<>();

        for (T obj : list) {
            obj = PipelineHandler.startHandler(obj, pipeline, listForLimits, listForElements, uniqueList);
            if (obj != null) {
                map.put(keyMapper.apply(obj), valueMapper.apply(obj));
            }
        }

        return map;
    }

    public Optional<T> terminalFindFirst() {

        for (T obj : list) {
            obj = PipelineHandler.startHandler(obj, pipeline, listForLimits, listForElements, uniqueList);
            if (obj != null) {
                return Optional.of(obj);
            }
        }

        return Optional.empty();
    }

    public void terminalForEach(Consumer<? super T> action) {

        for (T obj : list) {
            obj = PipelineHandler.startHandler(obj, pipeline, listForLimits, listForElements, uniqueList);
            if (obj != null) {
                action.accept(obj);
            }
        }
    }

    public long terminalCount() {
        long count = 0;

        for (T obj : list) {
            obj = PipelineHandler.startHandler(obj, pipeline, listForLimits, listForElements, uniqueList);
            if (obj != null) {
                count++;
            }
        }

        return count;
    }


    private static List<Long> fillListLimits(List<Object> pipeline) {
        List<Long> list = new ArrayList<>();

        for (Object lambda : pipeline) {
            if (lambda instanceof Long) {
                list.add((Long) lambda);
            }
        }

        return list;
    }

    private static List<Long> initOfElements(int size) {
        List<Long> list = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            list.add(0L);
        }

        return list;
    }

    private static <T> List<List<T>> initOfUnique(List<Object> pipeline) {
        List<List<T>> list = new ArrayList<>();

        for (Object lambda : pipeline) {
            if (lambda instanceof Distinct) {
                list.add(new ArrayList<>());
            }
        }

        return list;
    }

}
