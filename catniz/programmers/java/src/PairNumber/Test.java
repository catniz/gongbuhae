package PairNumber;

import java.util.Arrays;
import java.util.List;

class Test {
    public static void main(String[] args) {
        List<TestCase> testCases = Arrays.asList(
                new TestCase("100", "2345", "-1"),
                new TestCase("100", "203045", "0"),
                new TestCase("100", "123450", "10"),
                new TestCase("12321", "42531", "321"),
                new TestCase("5525", "1255", "552"),
                new TestCase("3403", "13203", "330")
        );

        for (int i = 0; i < testCases.size(); i++) {
            TestCase tc = testCases.get(i);
            Solution solution = new Solution();
            String result = solution.solution(tc.arg1, tc.arg2);
            if (!result.equals(tc.expectedResult)) {
                System.out.printf("case %d \n", i);
                System.out.printf("expected: %s \n", tc.expectedResult);
                System.out.printf("actual: %s \n", result);
                throw new AssertionError(result);
            }
        }
        System.out.println("success");
    }

    private static class TestCase {
        private final String arg1;
        private final String arg2;

        private final String expectedResult;

        private TestCase(String arg1, String arg2, String expectedResult) {
            this.arg1 = arg1;
            this.arg2 = arg2;
            this.expectedResult = expectedResult;
        }
    }
}