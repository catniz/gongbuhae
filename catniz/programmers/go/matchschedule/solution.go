package matchschedule

func solution(_ int, a int, b int) int {
	answer := 0

	for a != b {
		answer++
		a = (a + 1) / 2
		b = (b + 1) / 2
	}

	return answer
}
