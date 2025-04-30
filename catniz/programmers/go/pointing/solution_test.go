package pointing

import "testing"

func Test_solution(t *testing.T) {
	type args struct {
		k int
		d int
	}
	tests := []struct {
		name string
		args args
		want int64
	}{
		{"case1", args{2, 4}, 6},
		{"case2", args{1, 5}, 26},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			if got := solution(tt.args.k, tt.args.d); got != tt.want {
				t.Errorf("solution() = %v, want %v", got, tt.want)
			}
		})
	}
}
