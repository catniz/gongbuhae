package naver.p1;

import java.util.Arrays;

public class Solution {
    public int solution(int N, int[][] trees) {
        int[] sortedByY = new int[N];
        Arrays.fill(sortedByY, -1);
        for (int i = 0; i < trees.length; i++) {
            sortedByY[trees[i][1]] = trees[i][0];
        }

        int answer = 0;
        int minX = -1;
        for (int i = 1; i < N; i++) {
            if (sortedByY[i] == -1) {   // 나무가 없는 칸
                continue;
            }
            if (minX == -1 || minX >= sortedByY[i]) { // 같은 경우도 경계선
                minX = sortedByY[i];
                answer++;
            }
        }

        return answer;
    }
}
