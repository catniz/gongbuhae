package naver.p2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class Solution {
    private final int INVALID_DISTANCE = -1;

    public int[] solution(int n, int k, int[] capitals, int[][] edges) {
        List<List<Node>> mapping = makeMapping(n, edges);
        boolean[] capitalRegion = new boolean[n + 1];

        for (int capital : capitals) { // O(n * (e * log e))
            int[] minDistances = dijkstra(capital, mapping);
            for (int i = 0; i < minDistances.length; i++) {
                if (minDistances[i] != INVALID_DISTANCE && minDistances[i] <= k) { // 수도권 체크
                    capitalRegion[i] = true;
                }
            }
        }

        // get answer
        // 수도는 제외
        for (int capital : capitals) {
            capitalRegion[capital] = false;
        }

        List<Integer> answer = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (capitalRegion[i]) {
                answer.add(i);
            }
        }
        return answer.stream().mapToInt(i -> i).toArray();
    }

    private int[] dijkstra(int root, List<List<Node>> mapping) {
        int[] distances = new int[mapping.size()];
        Arrays.fill(distances, INVALID_DISTANCE);

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(root, 0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();

            for (int i = 0; i < mapping.get(current.index).size(); i++) {
                Node next = mapping.get(current.index).get(i);
                int newDistance = current.weight + next.weight;
                if (distances[next.index] == INVALID_DISTANCE || distances[next.index] > newDistance) {
                    distances[next.index] = newDistance;
                    pq.add(new Node(next.index, newDistance));
                }
            }
        }

        return distances;
    }

    private List<List<Node>> makeMapping(int n, int[][] edges) {
        List<List<Node>> mapping = new ArrayList<>(n + 1);    // 도시는 1~, index는 0~
        for (int i = 0; i <= n; i++) {
            mapping.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            int s = edge[0];
            int e = edge[1];
            int w = edge[2];

            mapping.get(s).add(new Node(e, w));
            mapping.get(e).add(new Node(s, w));
        }

        return mapping;
    }

    private static class Node implements Comparable<Node> {
        int index, weight;

        Node(int index, int weight) {
            this.index = index;
            this.weight = weight;
        }

        @Override
        public int compareTo(Node o) {
            if (this.weight == o.weight) {
                return Integer.compare(this.index, o.index);
            }
            return Integer.compare(this.weight, o.weight); // 최솟값으로 정렬
        }
    }
}
