package naver.p2;

import java.util.Arrays;
import java.util.List;

class Test {

    public static void main(String[] args) {
        List<TestCase> testCases = Arrays.asList(
                new TestCase(13, 5, new int[]{1, 9},
                        new int[][]{
                                {1, 2, 3}, {2, 4, 4}, {3, 2, 1}, {1, 6, 6}, {1, 5, 6}, {1, 7, 6},
                                {6, 7, 2}, {5, 7, 5}, {7, 8, 2}, {9, 7, 3}, {9, 10, 6}, {9, 11, 3},
                                {11, 12, 2}, {11, 13, 4}
                        },
                        new int[]{2, 3, 6, 7, 8, 11, 12}),
                new TestCase(7, 10, new int[]{2},
                        new int[][]{
                                {1, 2, 11}, {1, 5, 1}, {2, 4, 5}, {5, 4, 4}, {4, 3, 7}, {4, 6, 8},
                                {4, 7, 3}, {6, 7, 3}
                        },
                        new int[]{1, 4, 5, 7})
        );

        for (int i = 0; i < testCases.size(); i++) {
            TestCase tc = testCases.get(i);
            Solution solution = new Solution();
            int[] result = solution.solution(tc.n, tc.k, tc.capitals, tc.edges);

            System.out.printf("case %d - ", i);

            if (Arrays.equals(result, tc.expected)) {
                System.out.println("Passed");
            } else {
                System.out.println("Failed");

                System.out.printf("expected: %s \n", Arrays.toString(tc.expected));
                System.out.printf("actual: %s \n", Arrays.toString(result));
                throw new AssertionError();
            }
        }
        System.out.println("success");
    }

    private static class TestCase {
        private final int n;
        private final int k;
        private final int[] capitals;
        private final int[][] edges;
        private final int[] expected;


        private TestCase(int n, int k, int[] capitals, int[][] edges, int[] expected) {
            this.n = n;
            this.k = k;
            this.capitals = capitals;
            this.edges = edges;
            this.expected = expected;
        }
    }
}