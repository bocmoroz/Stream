import newstream.NewStreamImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }
        list.add(2);
        list.add(2);
        list.add(2);

        System.out.println("Original list: " + NewStreamImpl.of(list).toList());

        System.out.println("-------------------------");

        System.out.println("Distinct values list: " + NewStreamImpl.of(list).distinct().distinct().toList());

        System.out.println("-------------------------");

        Map<String, Integer> mapa = NewStreamImpl.of(list).filter(n -> (n > 0)).
                filter(n -> (n % 2 == 0)).limit(10).map(Object::toString).map(Integer::valueOf).limit(5).
                toMap(s -> s.toString() + "+1", s -> s + 1);

        System.out.println("toMap(...): " + mapa);

        System.out.println("-------------------------");

        System.out.println("toList(): " + NewStreamImpl.of(list).filter(n -> (n > 0)).
                filter(n -> (n % 2 == 0)).limit(10).map(Object::toString).map(Integer::valueOf).limit(5).
                toList());

        System.out.println("-------------------------");

        System.out.println("findFirst(): " + NewStreamImpl.of(list).filter(n -> (n > 0)).
                filter(n -> (n % 2 == 0)).limit(10).map(Object::toString).map(Integer::valueOf).limit(5).
                findFirst().orElse(-999));

        System.out.println("-------------------------");

        System.out.println("count(): " + NewStreamImpl.of(list).filter(n -> (n > 0)).
                filter(n -> (n % 2 == 0)).limit(10).map(Object::toString).map(Integer::valueOf).limit(5).
                count());

        System.out.println("-------------------------");

        System.out.print("forEach(...): ");
        NewStreamImpl.of(list).filter(n -> (n > 0)).
                filter(n -> (n % 2 == 0)).limit(10).map(Object::toString).map(Integer::valueOf).limit(5).
                forEach(System.out::print);

        System.out.println("\n-------------------------");

        System.out.println("Double distinct: " + NewStreamImpl.of(list).filter(n -> (n > 0)).
                filter(n -> (n % 2 == 0)).distinct().map(n -> 0).distinct().toList());
    }

}
