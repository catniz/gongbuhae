package videoplayer

import "testing"

func Test_solution(t *testing.T) {
	type args struct {
		video_len string
		pos       string
		op_start  string
		op_end    string
		commands  []string
	}
	tests := []struct {
		name string
		args args
		want string
	}{
		{"case#1", args{"34:33", "13:00", "00:55", "02:55", []string{"next", "prev"}}, "13:00"},
		{"case#2", args{"10:55", "00:05", "00:15", "06:55", []string{"prev", "next", "next"}}, "06:55"},
		{"case#3", args{"07:22", "04:05", "00:15", "04:07", []string{"next"}}, "04:17"},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			if got := solution(tt.args.video_len, tt.args.pos, tt.args.op_start, tt.args.op_end, tt.args.commands); got != tt.want {
				t.Errorf("solution() = %v, want %v", got, tt.want)
			}
		})
	}
}
