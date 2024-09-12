package puzzlegame

import "testing"

func TestSolution(t *testing.T) {
	type args struct {
		diffs []int
		times []int
		limit int64
	}
	tests := []struct {
		name string
		args args
		want int
	}{
		{"case#1", args{[]int{1, 5, 3}, []int{2, 4, 7}, 30}, 3},
		{"case#2", args{[]int{1, 4, 4, 2}, []int{6, 3, 8, 2}, 59}, 2},
		{"case#3", args{[]int{1, 328, 467, 209, 54}, []int{2, 7, 1, 4, 3}, 1723}, 294},
		{"case#4", args{[]int{1, 99999, 100000, 99995}, []int{9999, 9001, 9999, 9001}, 3456789012}, 39354},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			if got := solution(tt.args.diffs, tt.args.times, tt.args.limit); got != tt.want {
				t.Errorf("solution() = %v, want %v", got, tt.want)
			}
		})
	}
}
