import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.reducing;


public class StreamAPI {


    public static void main(String[] args) {
        Employee e1 = new Employee("tom", 10, 1000.1);
        Employee e2 = new Employee("dick", 30, 2000.1);
        Employee e3 = new Employee("tommy", 20, 2000.1);
        Employee e4 = new Employee("harry", 20, 1000.1);
        Employee e5 = new Employee("mina", 10, 5000.3);

        List.of(e1, e2, e3)
                .stream()
                .map(Employee::getAge)
                .collect(Collectors.toList())
                .forEach(System.out::println);

        Set<Integer> i1 = List.of(e1, e2, e3)
                              .stream()
                              .map(Employee::getAge)
                              .collect(Collectors.toCollection(TreeSet::new));
        System.out.println(i1);

        String s = List.of(e1, e2, e3)
                       .stream()
                       .map(Employee::getName)
                       .collect(Collectors.joining(","));
        String s1 = List.of(e1, e2, e3)
                        .stream()
                        .map(Employee::getName)
                        .collect(Collectors.joining(",", "Employees are ", " ."));

        System.out.println(s1);

        int sum = List.of(e1, e2, e3)
                      .stream()
                      .collect(Collectors.summingInt(Employee::getAge));
        /*int sum=List.of(e1,e2,e3)
                      .stream()
                      .mapToInt(Employee::getAge).sum();*/
        System.out.println(sum);

        Map<Integer, List<Employee>> groupByAge = List.of(e1, e2, e3, e4)
                                                      .stream()
                                                      .collect(Collectors.groupingBy(Employee::getAge));
        System.out.println(groupByAge);

        Map<String, Integer> groupByNameAndSummingAge = List.of(e1, e2, e3, e4, e5)
                                                            .stream()
                                                            .collect(Collectors.groupingBy(Employee::getName,
                                                                                           Collectors.summingInt(Employee::getAge)));
        System.out.println(groupByNameAndSummingAge);

        Map<Boolean, List<Employee>> partitionByAge = List.of(e1, e2, e3, e4, e5)
                                                          .stream()
                                                          .collect(Collectors.partitioningBy(e -> e.getAge() >= 20));
        System.out.println(partitionByAge);

        Map<Integer, Set<String>> nameByAge = List.of(e1, e2, e3, e4, e5)
                                                  .stream()
                                                  .collect(Collectors.groupingBy(Employee::getAge,
                                                                                 Collectors.mapping(Employee::getName,
                                                                                 Collectors.toSet())));
        System.out.println(nameByAge);

        List<Employee> empList = List.of(e1, e2, e3, e4, e5)
                                     .stream()
                                     .collect(Collectors.collectingAndThen(Collectors.toList(),
                                                                           Collections::unmodifiableList));
        System.out.println(empList);

        Map<Integer, Long> countingByAge = List.of(e1, e2, e3, e4, e5)
                                               .stream()
                                               .collect(Collectors.groupingBy(Employee::getAge,
                                                                              Collectors.counting()));
        System.out.println(countingByAge);

        /*Optional<Employee> minByAge=List.of(e1,e2,e3,e4,e5)
                                          .stream()
                                          .collect(Collectors.minBy((emp1,emp2)-> emp1.getAge()-emp2.getAge()));*/
        Optional<Employee> minByAge = List.of(e1, e2, e3, e4, e5)
                                          .stream()
                                          .min((emp1, emp2) -> emp1.getAge() - emp2.getAge());
        System.out.println(minByAge);

        /*Optional<Employee> maxByAge=List.of(e1,e2,e3,e4,e5)
                                          .stream()
                                          .collect(Collectors.maxBy((emp1,emp2)-> emp1.getAge()-emp2.getAge()));*/
        Optional<Employee> maxByAge = List.of(e1, e2, e3, e4, e5)
                                           .stream()
                                           .max((emp1, emp2) -> emp1.getAge() - emp2.getAge());
        System.out.println(maxByAge);

        Double averageSalary = List.of(e1, e2, e3, e4, e5)
                                   .stream()
                                   .collect(Collectors.averagingDouble(Employee::getSalary));
        System.out.println(averageSalary);


        Double sumSalaryByAgeTwenty = List.of(e1, e2, e3, e4, e5)
                                          .parallelStream()
                                          .filter(e -> e.getAge() == 20)
                                          .peek(System.out::println)
                                          .mapToDouble(e -> e.getSalary())
                                          .peek(System.out::println)
                                          .sum();
        System.out.println(sumSalaryByAgeTwenty);

        int[] intArray={1,2,3,4,5,6,7,8,9,10};
        IntStream.of(intArray)
                 .filter(i->i%2==0)
                 .skip(2)
                 .forEach(System.out::println);
        IntStream.of(intArray)
                 .filter(i->i%2==0)
                 .limit(2)
                 .forEach(System.out::println);

        List.of(e1, e2, e3, e4, e5)
            .stream()
            .collect(Collectors.toMap(Employee::getName,Employee::getSalary))
            .forEach((k,v)->System.out.println("Key:"+k+"--"+"value:"+v));

        List.of(e1, e2, e3, e4, e5)
             .stream()
             .collect(Collectors.toMap(Employee::getSalary, Function.identity(),(exist,replace)->exist))
             .forEach((k,v)->System.out.println("Key:"+k+"--"+"value:"+v));

        List.of(e1, e2, e3, e4, e5)
                .stream()
                .collect(Collectors.toMap(Employee::getName, Function.identity(),(p1,p2)->p1,TreeMap::new))
                .forEach((k,v)->System.out.println("Key:"+k+"--"+"value:"+v));

        int[] a1=IntStream.rangeClosed(0,10).boxed().mapToInt(i->i).toArray();

        Map<Employee,Integer> mapEmp=new HashMap<>();
        mapEmp.put(e1,1);
        mapEmp.put(e2,2);
        mapEmp.put(e3,3);
        mapEmp.put(e4,4);
        mapEmp.put(e5,5);

        mapEmp.entrySet().stream().map(e->e.getKey()).forEachOrdered(System.out::println);

        long count = List.of(e1, e2, e3, e4, e5)
                         .stream()
                         .filter(c -> c.getName().startsWith("t"))
                         .count();
        System.out.println(count);

        int max = IntStream.of(2000, 980, 112, 711, 35)
                           .max()
                           .getAsInt();
        System.out.println(max);

        int sumOfData = IntStream.of(2000, 980, 112, 711, 35)
                                 .sum();
        System.out.println(sumOfData);

        Stream<Integer> stream1 = Stream.of(1, 30, 15);
        Stream<Integer> stream2 = Stream.of(9, 4, 6);
        Stream<Integer> finalStream=Stream.concat(stream1,stream2);
        finalStream.collect(Collectors.toList()).forEach(System.out::println);

        Stream<Integer> integers = Stream.iterate(0, i -> i + 1).limit(10);
                                          integers.forEach(System.out::println);

        List<String> list = Arrays.asList("Apple", "Bat", "Cat", "Dog");

        Optional<String> result = list.stream().findFirst();
        result.stream().forEach(System.out::println);


    }
}