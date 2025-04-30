package deliveryrobot

func solution(points [][]int, routes [][]int) int {
	// init robots
	robots := make([]*robot, len(routes))
	for i, route := range routes {
		robots[i] = newRobot(points, route)
	}

	// simulate
	var completed bool
	totalCrash := countCrash(robots)

	for !completed {
		completed = true
		for _, r := range robots {
			completed = !r.move() && completed
		}

		totalCrash += countCrash(robots)
	}

	return totalCrash
}

// robot count :: routes.len

// 1tick - simulateNextPoints(cur, routes) - countCrash(points) int
// (r, c)
type robot struct {
	pos           coord
	destinations  []coord
	targetDestIdx int
}

// 도착지점에서도 카운트를 해야함
func countCrash(robots []*robot) int {
	dup := make(map[coord]int)

	for _, r := range robots {
		if r.isExit() {
			continue
		}
		dup[r.pos]++
	}

	total := 0
	for _, count := range dup {
		if count > 1 {
			total++
		}
	}

	return total
}

func newRobot(points [][]int, route []int) *robot {
	startIdx := route[0] - 1
	start := coord{r: points[startIdx][0], c: points[startIdx][1]}
	destinations := make([]coord, len(route)-1)
	for i, destIdx := range route[1:] {
		destinations[i] = coord{r: points[destIdx-1][0], c: points[destIdx-1][1]}
	}

	return &robot{
		pos:           start,
		destinations:  destinations,
		targetDestIdx: 0,
	}
}

func (r *robot) isExit() bool {
	return r.pos.c == -1 && r.pos.r == -1
}

func (r *robot) exit() {
	r.pos = coord{r: -1, c: -1}
}

func (r *robot) reachDestination() bool {
	return r.targetDestIdx >= len(r.destinations)
}

func (r *robot) move() bool {
	for !r.reachDestination() && r.pos == r.destinations[r.targetDestIdx] {
		r.targetDestIdx++
	}

	if r.reachDestination() {
		r.exit()
		return false
	}

	dest := r.destinations[r.targetDestIdx]
	if r.pos.r != dest.r {
		if r.pos.r < dest.r {
			r.pos.r++
		} else {
			r.pos.r--
		}
	} else if r.pos.c != dest.c {
		if r.pos.c < dest.c {
			r.pos.c++
		} else {
			r.pos.c--
		}
	} else {
		return false
	}

	return true
}

type coord struct {
	r, c int
}

// 200(최대 이동거리) * 100 (목적지 수) * (100 (로봇수) + 100 (충돌계산 수)) = 2 * 10^6
