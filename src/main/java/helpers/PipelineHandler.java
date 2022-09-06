package helpers;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class PipelineHandler {

    public static <T> T startHandler(T obj, List<Object> pipeline, List<Long> listForLimits, List<Long> listForElements, List<List<T>> uniqueList) {

        int countOfLong = 0;
        int countOfDistinct = 0;

        for (Object lambda : pipeline) {

            if (lambda instanceof Predicate) {

                if (!((Predicate) lambda).test(obj)) {
                    obj = null;
                    break;
                }
                continue;
            }

            if (lambda instanceof Function) {
                obj = (T) ((Function) lambda).apply(obj);
                continue;
            }

            if (lambda instanceof Long) {

                long currentLimit = listForLimits.get(countOfLong);
                long currentCount = listForElements.get(countOfLong);

                if (currentCount < currentLimit) {
                    listForElements.set(countOfLong, currentCount + 1);
                    countOfLong++;
                } else {
                    obj = null;
                    break;
                }
                continue;
            }

            if (lambda instanceof Distinct) {

                List<T> distinctList = uniqueList.get(countOfDistinct);

                if (!distinctList.contains(obj)) {
                    distinctList.add(obj);
                    countOfDistinct++;
                } else {
                    obj = null;
                    break;
                }
            }

        }

        return obj;

    }
}
