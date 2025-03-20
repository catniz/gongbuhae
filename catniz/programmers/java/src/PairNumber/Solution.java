package PairNumber;

class Solution {
    public String solution(String X, String Y) {
        int[] pairNumbers = new int[10];
        int[] XNumbers = new int[10];

        for (int i = 0; i < X.length(); i++) {
            XNumbers[X.charAt(i) - '0']++;
        }

        for (int i=0; i< Y.length(); i++) {
            int y = Y.charAt(i) -'0';

            if(XNumbers[y] > 0) {
                pairNumbers[y]++;
                XNumbers[y]--;
            }
        }

        StringBuilder resultSb = new StringBuilder();
        for (int i = 9; i>= 0; i--) {
            while (pairNumbers[i] > 0) {
                resultSb.append(i);
                pairNumbers[i]--;
            }
        }

        String answer = resultSb.toString();
        if (answer.isEmpty()) {
            return "-1";
        } else if(answer.charAt(0) == '0') {
            return "0";
        }

        return answer;
    }
}