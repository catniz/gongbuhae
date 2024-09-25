package diskcontroller

import "testing"

func TestPriorityQueue(t *testing.T) {
	type args struct {
		order PriorityQueueOrder
		items []Job
	}
	tests := []struct {
		name string
		args args
		want []Job
	}{
		{"case - Desc",
			args{OrderDesc,
				[]Job{
					{0, 1},
					{0, 3},
					{0, 5},
					{1, 3},
				},
			}, []Job{
				{0, 5},
				{1, 3},
				{0, 3},
				{0, 1},
			},
		},
		{"case - Asc",
			args{OrderAsc,
				[]Job{
					{0, 1},
					{0, 3},
					{0, 5},
					{1, 3},
				},
			}, []Job{
				{0, 1},
				{0, 3},
				{1, 3},
				{0, 5},
			},
		},
	}

	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			pq := NewPriorityQueue(tt.args.order)
			for _, j := range tt.args.items {
				pq.Push(j)
			}
			for _, j := range tt.want {
				pop := pq.Pop()
				if j != pop {
					t.Errorf("JobPriorityQueue test failed expected %v, got %v", j, pop)
				}
			}
			if !pq.Empty() {
				t.Errorf("Expected empty queue")
			}
		})
	}
}

func Test_solution(t *testing.T) {
	type args struct {
		jobs [][]int
	}
	tests := []struct {
		name string
		args args
		want int
	}{
		{"case#1", args{[][]int{{0, 3}, {1, 9}, {2, 6}}}, 9},
		//[[0, 1], [0, 2], [1, 6]] => 4
		{"case#2", args{[][]int{{0, 1}, {0, 2}, {1, 6}}}, 4},
		// [[0, 3], [4, 2], [2, 5]] => 15 => 5
		{"case#3", args{[][]int{{0, 3}, {4, 2}, {2, 5}}}, 5},
		// [[0, 3], [5, 3]] => 3
		{"case#4", args{[][]int{{0, 3}, {5, 3}}}, 3},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			if got := solution(tt.args.jobs); got != tt.want {
				t.Errorf("solution() = %v, want %v", got, tt.want)
			}
		})
	}
}
