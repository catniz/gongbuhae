package naver.p1;

import java.util.Arrays;
import java.util.List;

class Test {
    public static void main(String[] args) {
        List<TestCase> testCases = Arrays.asList(
                new TestCase(5, new int[][]{{4, 3}, {3, 1}, {2, 2}, {1, 4}}, 3),
                new TestCase(5, new int[][]{{3, 3}, {2, 2}, {1, 1}}, 1)
        );

        for (int i = 0; i < testCases.size(); i++) {
            TestCase tc = testCases.get(i);
            Solution solution = new Solution();
            int result = solution.solution(tc.n, tc.trees);

            System.out.printf("case %d - ", i);
            if (result == tc.expected) {
                System.out.println("Passed");
            } else {
                System.out.println("Failed");

                System.out.printf("expected: %d \n", tc.expected);
                System.out.printf("actual: %d \n", result);
                throw new AssertionError();
            }
        }
        System.out.println("success");
    }

    private static class TestCase {
        private final int n;
        private final int[][] trees;

        private final int expected;

        private TestCase(int n, int[][] trees, int expected) {

            this.n = n;
            this.trees = trees;
            this.expected = expected;
        }
    }
}