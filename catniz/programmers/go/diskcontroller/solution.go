package diskcontroller

// shortest job first
func solution(jobs [][]int) int {
	queued := make([]bool, len(jobs))

	pq := NewPriorityQueue(OrderAsc)

	ret, curTime := 0, 0
	for !pq.Empty() || !allQueued(queued) {
		for i, job := range jobs {
			if !queued[i] && job[0] <= curTime {
				pq.Push(Job{job[0], job[1]})
				queued[i] = true
			}
		}

		// skip to next start time
		if pq.Empty() {
			// find minimum start time
			minStartTime := -1
			for i, job := range jobs {
				if !queued[i] && (minStartTime == -1 || job[0] < minStartTime) {
					minStartTime = job[0]
				}
			}
			curTime = minStartTime
			continue
		}

		job := pq.Pop()

		ret += job.totalTime(curTime)
		curTime += job.jobTime
	}

	return ret / len(jobs)
}

func allQueued(queued []bool) bool {
	for _, q := range queued {
		if !q {
			return false
		}
	}
	return true
}

type Job struct {
	startTime int
	jobTime   int
}

func (j Job) totalTime(requestTime int) int {
	return requestTime - j.startTime + j.jobTime
}

func (j Job) Compare(other Job) int {
	if j.jobTime < other.jobTime {
		return -1
	} else if j.jobTime > other.jobTime {
		return 1
	}

	if j.startTime < other.startTime {
		return -1
	} else if j.startTime > other.startTime {
		return 1
	}

	return 0
}

type PriorityQueueOrder int

const (
	OrderAsc  PriorityQueueOrder = -1
	OrderDesc PriorityQueueOrder = 1
)

// JobPriorityQueue - order by job time, request time
type JobPriorityQueue struct {
	items []Job
	order PriorityQueueOrder
}

func NewPriorityQueue(order PriorityQueueOrder) *JobPriorityQueue {
	return &JobPriorityQueue{
		items: make([]Job, 0),
		order: order,
	}
}

func (pq *JobPriorityQueue) Empty() bool {
	return len(pq.items) == 0
}

func (pq *JobPriorityQueue) Push(j Job) {
	pq.items = append(pq.items, j)
	pq.up(len(pq.items) - 1)
}

func (pq *JobPriorityQueue) Pop() Job {
	n, first := len(pq.items), pq.items[0]

	pq.swap(0, n-1)
	pq.items = pq.items[:n-1]
	pq.down(0)

	return first
}

func (pq *JobPriorityQueue) swap(i, j int) {
	pq.items[i], pq.items[j] = pq.items[j], pq.items[i]
}

func (pq *JobPriorityQueue) up(i int) {
	for i > 0 {
		j := (i - 1) / 2
		if pq.less(j, i) {
			break
		}

		pq.swap(i, j)
		i = j
	}
}

func (pq *JobPriorityQueue) less(i, j int) bool {
	return pq.items[i].Compare(pq.items[j])*int(pq.order) > 0
}

func (pq *JobPriorityQueue) down(i int) {
	for {
		jl, jr := 2*i+1, 2*i+2
		if jl >= len(pq.items) {
			break
		}

		j := jl
		if jr < len(pq.items) && pq.less(jr, jl) {
			j = jr
		}

		if pq.less(i, j) {
			break
		}

		pq.swap(i, j)
		i = j
	}
}
