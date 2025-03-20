package taxiFee;

public class FloydWarshall {
    public int solution(int n, int s, int a, int b, int[][] fares) {
        int[][] distances = floydWarshall(n, fares);
        int answer = -1;
        for (int i = 0; i < n; i++) {
            if (distances[i][s - 1] == -1 || distances[i][a - 1] == -1 || distances[i][b - 1] == -1) {
                continue;
            }

            int total = distances[i][s - 1] + distances[i][a - 1] + distances[i][b - 1];
            if (answer == -1 || answer > total) {
                answer = total;
            }
        }

        return answer;
    }

    private int[][] floydWarshall(int n, int[][] fares) {
        int[][] distances = Dijkstra.makeMapping(n, fares);

        for (int k = 0; k < n; k++) {               // 중간 -- dp라 순서가 중요!
            for (int i = 0; i < n; i++) {           // 시작
                for (int j = 0; j < n; j++) {       // 끝

                    if (distances[i][k] == -1 || distances[k][j] == -1) {
                        continue;
                    }

                    int newD = distances[i][k] + distances[k][j];
                    if (distances[i][j] == -1 || distances[i][j] > newD) {
                        distances[i][j] = newD;
                    }
                }
            }
        }

        return distances;
    }

}
