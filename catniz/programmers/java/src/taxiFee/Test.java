package taxiFee;

import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args) {

        List<TestCase> testCases = Arrays.asList(
                new TestCase(6, 4, 6, 2, new int[][]{{4, 1, 10}, {3, 5, 24}, {5, 6, 2}, {3, 1, 41}, {5, 1, 24}, {4, 6, 50}, {2, 4, 66}, {2, 3, 22}, {1, 6, 25}}, 82),
                new TestCase(7, 3, 4, 1, new int[][]{{5, 7, 9}, {4, 6, 4}, {3, 6, 1}, {3, 2, 3}, {2, 1, 6}}, 14),
                new TestCase(6, 4, 5, 6, new int[][]{{2, 6, 6}, {6, 3, 7}, {4, 6, 7}, {6, 5, 11}, {2, 5, 12}, {5, 3, 20}, {2, 4, 8}, {4, 3, 9}}, 18)
        );

        for (int i = 0; i < testCases.size(); i++) {
            TestCase tc = testCases.get(i);
            int dResult = new Dijkstra().solution(tc.n, tc.s, tc.a, tc.b, tc.fares);
            int fResult = new FloydWarshall().solution(tc.n, tc.s, tc.a, tc.b, tc.fares);
            System.out.printf("case %d - ", i);
            if (dResult == tc.expected && fResult == tc.expected) {
                System.out.println("Passed");
            } else {
                System.out.println("Failed");

                System.out.printf("expected: %d \n", tc.expected);
                System.out.printf("actual: dijkstra: %d, floydWarshall: %d \n", dResult, fResult);
                throw new AssertionError();
            }
        }
    }

    private static class TestCase {
        private final int n;
        private final int s;
        private final int a;
        private final int b;
        private final int[][] fares;

        private final int expected;

        private TestCase(int n, int s, int a, int b, int[][] fares, int expected) {

            this.n = n;
            this.s = s;
            this.a = a;
            this.b = b;
            this.fares = fares;
            this.expected = expected;
        }
    }
}
