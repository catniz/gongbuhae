package pointing

import "math"

func solution(k int, d int) int64 {
	ret := int64(0)

	for x := 0; x <= d; x += k {
		ret += getAvailablePointCount(x, k, d) + 1
	}

	return ret
}

func getAvailablePointCount(x, k, d int) int64 {
	y := int64(math.Sqrt(float64(d*d - x*x)))
	return y / int64(k)
}
