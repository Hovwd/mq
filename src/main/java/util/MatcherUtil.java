package util;

import com.mq.task.dto.MatchCombination;
import com.mq.task.dto.MatchCombinationsResult;
import com.mq.task.dto.Pair;
import com.mq.task.entity.Employee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MatcherUtil {


    public static MatchCombinationsResult constructCombinationsResult(final List<Employee> employeeList) {
        List<Integer> indexList = IntStream.rangeClosed(0, employeeList.size() - 1)
                .boxed().collect(Collectors.toList());
        ArrayList<List<List<Integer>>> possibleCombinationList = new ArrayList<>();
        computePossibleCombinations(indexList, new ArrayList<>(), possibleCombinationList);

        MatchCombinationsResult matchCombinationsResult = new MatchCombinationsResult();
        List<MatchCombination> matchCombinationList = new ArrayList<>();
        matchCombinationsResult.setCombinationList(matchCombinationList);
        possibleCombinationList.forEach(combination -> {
            AtomicInteger allCombinationPercentage = new AtomicInteger();
            List<Pair> pairList = combination.stream().map(pairIndexes -> {
                Pair pair = new Pair();
                Employee employee1 = employeeList.get(pairIndexes.get(0));
                Employee employee2 = employeeList.get(pairIndexes.get(1));
                pair.setEmployee1(employee1.getName());
                pair.setEmployee2(employee2.getName());
                int pairMatchPercantage = calculateMatchPercentage(employee1, employee2);
                allCombinationPercentage.addAndGet(pairMatchPercantage);
                pair.setMatchPercentage(calculateMatchPercentage(employee1, employee2));
                return pair;
            }).collect(Collectors.toList());
            MatchCombination matchCombination = new MatchCombination();
            matchCombination.setPairList(pairList);

            matchCombination.setAverageMatchPercentage(allCombinationPercentage.get() / pairList.size());
            matchCombinationList.add(matchCombination);
            if (matchCombinationsResult.getResult() == null ||
                    matchCombinationsResult.getResult().getAverageMatchPercentage() < matchCombination.getAverageMatchPercentage()) {
                matchCombinationsResult.setResult(matchCombination);
            }

        });


        return matchCombinationsResult;
    }

    private static int calculateMatchPercentage(Employee employee1, Employee employee2) {
        int percentage = 0;
        if (employee1.getUtcOffset() == employee2.getUtcOffset()) {
            percentage += 40;
        }
        if (employee1.getDivision().equals(employee2.getDivision())) {
            percentage += 30;
        }
        if (Math.abs(employee1.getAge() - employee2.getAge()) < 5) {
            percentage += 30;
        }
        return percentage;
    }


    private static void computePossibleCombinations(List<Integer> indexList,
                                                    List<List<Integer>> currentResults,
                                                    List<List<List<Integer>>> results) {
        if (indexList.size() % 2 == 1) {
            for (int i = indexList.size() - 1; i >= 0; i--) {
                Integer temp = indexList.remove(i);
                computePossibleCombinations(indexList, new ArrayList<>(), results);
                indexList.add(i, temp);
            }
        } else {
            if (indexList.size() < 2) {
                results.add(new ArrayList<>(currentResults));
                return;
            }
            List<Integer> list = new ArrayList<>(indexList);
            Integer first = list.remove(0);
            for (int i = 0; i < list.size(); i++) {
                Integer second = list.get(i);
                List<Integer> nextSet = new ArrayList<>(list);
                nextSet.remove(second);
                List<Integer> pair = Arrays.asList(first, second);
                currentResults.add(pair);
                computePossibleCombinations(nextSet, currentResults, results);
                currentResults.remove(pair);
            }
        }
    }
}


