package naver.p3;

import java.util.Arrays;
import java.util.List;

class Test {
    public static void main(String[] args) {
        List<TestCase> testCases = Arrays.asList(
                new TestCase(new String[]{"...", "#.#", "..#", "#.."}, 11),
                new TestCase(new String[]{"..#..", ".#...", ".#...", "...#."}, 26)
        );

        for (int i = 0; i < testCases.size(); i++) {
            TestCase tc = testCases.get(i);
            Solution solution = new Solution();
            int result = solution.solution(tc.grid);

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
        private final String[] grid;

        private final int expected;

        private TestCase(String[] grid, int expected) {
            this.grid = grid;
            this.expected = expected;
        }
    }
}