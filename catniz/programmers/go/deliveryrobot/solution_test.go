package deliveryrobot

import "testing"

func Test_solution(t *testing.T) {
	type args struct {
		points [][]int
		routes [][]int
	}
	tests := []struct {
		name string
		args args
		want int
	}{
		{"case#1", args{[][]int{{3, 2}, {6, 4}, {4, 7}, {1, 4}}, [][]int{{4, 2}, {1, 3}, {2, 4}}}, 1},
		{"case#2", args{[][]int{{3, 2}, {6, 4}, {4, 7}, {1, 4}}, [][]int{{4, 2}, {1, 3}, {4, 2}, {4, 3}}}, 9},
		{"case#3", args{[][]int{{2, 2}, {2, 3}, {2, 7}, {6, 6}, {5, 2}}, [][]int{{2, 3, 4, 5}, {1, 3, 4, 5}}}, 0},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			if got := solution(tt.args.points, tt.args.routes); got != tt.want {
				t.Errorf("solution() = %v, want %v", got, tt.want)
			}
		})
	}
}
