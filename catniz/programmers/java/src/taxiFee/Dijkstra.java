package taxiFee;


import java.util.Arrays;
import java.util.PriorityQueue;

public class Dijkstra {
    public int solution(int n, int s, int a, int b, int[][] fares) {
        int answer = -1;

        int[] sRet = dijkstra(n, s - 1, fares);
        int[] aRet = dijkstra(n, a - 1, fares);
        int[] bRet = dijkstra(n, b - 1, fares);

        for (int i = 0; i < n; i++) {
            if (sRet[i] == -1 || aRet[i] == -1 || bRet[i] == -1) {
                continue;
            }

            int total = sRet[i] + aRet[i] + bRet[i];
            if (answer == -1 || answer > total) {
                answer = total;
            }
        }

        return answer;
    }

    private int[] dijkstra(int n, int root, int[][] fares) {
        int[][] mapping = makeMapping(n, fares);
        int[] distance = new int[n];
        Arrays.fill(distance, -1);
        distance[root] = 0;

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(0, root));

        while (!pq.isEmpty()) {
            Node poll = pq.poll();
            int d = poll.minDistance;
            int current = poll.current;

            for (int i = 0; i < n; i++) {
                if (current == i || mapping[current][i] == -1) {
                    continue;
                }

                int newD = d + mapping[current][i];
                if (distance[i] == -1 || distance[i] > newD) {
                    distance[i] = newD;
                    pq.add(new Node(newD, i));
                }
            }
        }

        return distance;
    }

    protected static int[][] makeMapping(int n, int[][] fares) {
        int[][] mapping = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(mapping[i], -1);
            mapping[i][i] = 0;
        }

        for (int[] fare : fares) {
            int x = fare[0] - 1;
            int y = fare[1] - 1;
            mapping[x][y] = fare[2];
            mapping[y][x] = fare[2];
        }

        return mapping;
    }

    private static class Node implements Comparable<Node> {
        private final int minDistance;
        private final int current;

        private Node(int minDistance, int current) {
            this.minDistance = minDistance;
            this.current = current;
        }

        @Override
        public int compareTo(Node o) {
            if (this.minDistance == o.minDistance) {
                return Integer.compare(this.current, o.current);
            }
            return Integer.compare(o.minDistance, this.minDistance); // 최소값이 우선하도록

        }
    }
}
