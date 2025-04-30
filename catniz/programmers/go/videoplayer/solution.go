package videoplayer

import "fmt"

func solution(video_len string, pos string, op_start string, op_end string, commands []string) string {
	video_len_sec := toSecond(video_len)
	pos_sec := toSecond(pos)
	op_start_sec := toSecond(op_start)
	op_end_sec := toSecond(op_end)

	pos_sec = skipOpening(pos_sec, op_start_sec, op_end_sec)
	for _, command := range commands {
		switch command {
		case "prev":
			pos_sec = prev(pos_sec)
		case "next":
			pos_sec = next(video_len_sec, pos_sec)
		default:
			return "Invalid command"
		}
		pos_sec = skipOpening(pos_sec, op_start_sec, op_end_sec)
	}
	return toTime(pos_sec)
}

const interval = 10

func toSecond(time string) int {
	// "mm:ss"
	minute := int(time[0]-'0')*10 + int(time[1]-'0')
	second := int(time[3]-'0')*10 + int(time[4]-'0')
	return minute*60 + second
}

func toTime(sec int) string {
	minute := sec / 60
	second := sec % 60
	return fmt.Sprintf("%02d:%02d", minute, second)
}

func skipOpening(pos, op_start, op_end int) int {
	if op_start <= pos && pos <= op_end {
		return op_end
	}
	return pos
}

func prev(pos int) int {
	if pos-interval < 0 {
		return 0
	}
	return pos - interval
}

func next(video_len, pos int) int {
	if pos+interval > video_len {
		return video_len
	}
	return pos + interval
}
