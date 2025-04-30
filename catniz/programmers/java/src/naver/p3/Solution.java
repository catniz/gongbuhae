package naver.p3;


import java.util.PriorityQueue;

class Solution {
    public int solution(String[] grid) {
        int yLen = grid.length;
        int xLen = grid[0].length();
        return bfs(new Point(0, 0), new Point(xLen - 1, yLen - 1), grid);
    }

    private int bfs(Point start, Point end, String[] grid) {
        PriorityQueue<State> pq = new PriorityQueue<>();
        pq.add(new State(start, Direction.RIGHT, 0, 0));

        while (!pq.isEmpty()) {
            State current = pq.poll();
            // 도착!
            if (current.point.equals(end)) {
                return current.time;
            }

            // 가능한 모든 경우를 큐에 넣음. 1. goStraight, 2. rotate(최대 두번까지만)
            // 1. goStraight - 앞에 #이 아닐 경우만. rotateCnt 0으로 초기화
            Point next = current.direction.goStraight(current.point);
            if (canGo(next, grid)) {
                pq.add(new State(next, current.direction, 0, current.time + 1));
            }

            // 2. rotate - rotateCnt < 2인 경우만
            if (current.canRotate()) {
                Direction nextDir = current.direction.rotate();
                pq.add(new State(current.point, nextDir, current.rotateCnt + 1, current.time + 1));
            }
        }

        return -1;
    }

    private boolean canGo(Point p, String[] grid) {
        if (p.y < 0 || p.y >= grid.length || p.x < 0 || p.x >= grid[0].length()) {  // 범위를 벗어난 경우
            return false;
        }
        return grid[p.y].charAt(p.x) != '#';
    }

    private static class Point {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
            if (super.equals(obj)) {
                return true;
            }
            if (!(obj instanceof Point p)) {
                return false;
            }
            return x == p.x && y == p.y;
        }
    }

    private enum Direction {
        LEFT(-1, 0),
        RIGHT(1, 0),
        UP(0, -1),
        DOWN(0, 1);

        final int dx;
        final int dy;

        Direction(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }

        public Direction rotate() {
            return switch (this) {
                case LEFT -> DOWN;
                case RIGHT -> UP;
                case UP -> LEFT;
                case DOWN -> RIGHT;
            };
        }

        public Point goStraight(Point current) {
            return new Point(current.x + dx, current.y + dy);
        }
    }

    private static class State implements Comparable<State> {
        static final int ROTATE_LIMIT = 2;

        Point point;
        Direction direction;
        int rotateCnt;
        int time;

        State(Point point, Direction direction, int rotateCnt, int time) {
            this.point = point;
            this.direction = direction;
            this.rotateCnt = rotateCnt;
            this.time = time;
        }

        @Override
        public int compareTo(State o) {
            return Integer.compare(this.time, o.time); // 더 짧은 시간 우선
        }

        boolean canRotate() {
            return rotateCnt < ROTATE_LIMIT;
        }
    }
}
