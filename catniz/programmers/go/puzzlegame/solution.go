package puzzlegame

func solution(diffs []int, times []int, limit int64) int {
	minLevel, maxLevel := 1, 100000
	for minLevel < maxLevel {
		mid := (minLevel + maxLevel) / 2
		if canSolve(mid, diffs, times, limit) {
			maxLevel = mid
		} else {
			minLevel = mid + 1
		}
	}
	return minLevel
}

// diff <= level -> time_cur
// diff > level
// - count := diff - level
// - count * (time_cur + time_prev) + time_cur

// diff 3, time_cur 2, time_prev 4
// level=1 -> 2 * (2 + 4) + 2 = 14
// level=2 -> 1 * (2 + 4) + 2 = 8
// level=3 -> 0 * (2 + 4) + 2 = 2
// level=4 -> 0 * (2 + 4) + 2 = 2

// diff[0]=1, times[0]

// 1 <= diff <= 100000

func calcTime(level, diff, timeCur, timePrev int64) int64 {
	if diff-level <= 0 {
		return timeCur
	}
	return (diff-level)*(timeCur+timePrev) + timeCur
}

func canSolve(level int, diffs []int, times []int, limit int64) bool {
	total := int64(times[0])

	for i := 1; i < len(diffs); i++ {
		if total > limit {
			return false
		}
		total += calcTime(int64(level), int64(diffs[i]), int64(times[i]), int64(times[i-1]))
	}

	return total <= limit
}
