package formulafinder

import (
	"reflect"
	"testing"
)

/*
["14 + 3 = 17", "13 - 6 = X", "51 - 5 = 44"]	["13 - 6 = 5"]
["1 + 1 = 2", "1 + 3 = 4", "1 + 5 = X", "1 + 2 = X"]	["1 + 5 = ?", "1 + 2 = 3"]
["10 - 2 = X", "30 + 31 = 101", "3 + 3 = X", "33 + 33 = X"]	["10 - 2 = 4", "3 + 3 = 10", "33 + 33 = 110"]
["2 - 1 = 1", "2 + 2 = X", "7 + 4 = X", "5 - 5 = X"]	["2 + 2 = 4", "7 + 4 = ?", "5 - 5 = 0"]
["2 - 1 = 1", "2 + 2 = X", "7 + 4 = X", "8 + 4 = X"]	["2 + 2 = 4", "7 + 4 = 12", "8 + 4 = 13"]
*/

func Test_solution(t *testing.T) {
	type args struct {
		expressions []string
	}
	tests := []struct {
		name string
		args args
		want []string
	}{
		{"case#1", args{[]string{"14 + 3 = 17", "13 - 6 = X", "51 - 5 = 44"}}, []string{"13 - 6 = 5"}},
		{"case#2", args{[]string{"1 + 1 = 2", "1 + 3 = 4", "1 + 5 = X", "1 + 2 = X"}}, []string{"1 + 5 = ?", "1 + 2 = 3"}},
		{"case#3", args{[]string{"10 - 2 = X", "30 + 31 = 101", "3 + 3 = X", "33 + 33 = X"}}, []string{"10 - 2 = 4", "3 + 3 = 10", "33 + 33 = 110"}},
		{"case#4", args{[]string{"2 - 1 = 1", "2 + 2 = X", "7 + 4 = X", "5 - 5 = X"}}, []string{"2 + 2 = 4", "7 + 4 = ?", "5 - 5 = 0"}},
		{"case#5", args{[]string{"2 - 1 = 1", "2 + 2 = X", "7 + 4 = X", "8 + 4 = X"}}, []string{"2 + 2 = 4", "7 + 4 = 12", "8 + 4 = 13"}},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			if got := solution(tt.args.expressions); !reflect.DeepEqual(got, tt.want) {
				t.Errorf("solution() = %v, want %v", got, tt.want)
			}
		})
	}
}

func Test_baseToDec(t *testing.T) {
	type args struct {
		base int
		num  string
	}
	tests := []struct {
		name    string
		args    args
		want    int
		wantErr bool
	}{
		{"case#1", args{2, "101"}, 5, false},
		{"case#2", args{2, "210"}, 0, true},
		{"case#3", args{8, "123"}, 83, false},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			got, err := baseToDec(tt.args.base, tt.args.num)
			if (err != nil) != tt.wantErr {
				t.Errorf("baseToDec() error = %v, wantErr %v", err, tt.wantErr)
				return
			}
			if got != tt.want {
				t.Errorf("baseToDec() got = %v, want %v", got, tt.want)
			}
		})
	}
}

func Test_decToBase(t *testing.T) {
	type args struct {
		base int
		num  int
	}
	tests := []struct {
		name string
		args args
		want string
	}{
		{"case#1", args{2, 5}, "101"},
		{"case#2", args{8, 83}, "123"},
		{"case#3", args{2, 0}, "0"},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			if got := decToBase(tt.args.base, tt.args.num); got != tt.want {
				t.Errorf("decToBase() = %v, want %v", got, tt.want)
			}
		})
	}
}

func Test_unknownFormula_resultString(t *testing.T) {
	type fields struct {
		a  string
		b  string
		op operator
	}
	type args struct {
		availableBases []int
	}
	tests := []struct {
		name   string
		fields fields
		args   args
		want   string
	}{
		{"case#1", fields{"1", "1", plus}, args{[]int{3, 4, 5}}, "1 + 1 = 2"},
		{"case#2", fields{"1", "2", plus}, args{[]int{3, 4, 5}}, "1 + 2 = ?"},
		{"case#3", fields{"33", "33", plus}, args{[]int{6}}, "33 + 33 = 110"},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			uf := formula{
				a:  tt.fields.a,
				b:  tt.fields.b,
				op: tt.fields.op,
			}
			if got := uf.resultStr(tt.args.availableBases); got != tt.want {
				t.Errorf("resultStr() = %v, want %v", got, tt.want)
			}
		})
	}
}

func Test_formula_calcAvailableBases(t *testing.T) {
	type fields struct {
		a  string
		b  string
		c  string
		op operator
	}
	tests := []struct {
		name   string
		fields fields
		want   map[int]bool
	}{
		{"case#1", fields{"14", "3", "17", plus}, map[int]bool{8: true, 9: true}},
		{"case#2", fields{"51", "5", "44", minus}, map[int]bool{8: true}},
		{"case#3", fields{"2", "1", "1", minus}, map[int]bool{3: true, 4: true, 5: true, 6: true, 7: true, 8: true, 9: true}},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			f := formula{
				a:  tt.fields.a,
				b:  tt.fields.b,
				c:  tt.fields.c,
				op: tt.fields.op,
			}
			if got := f.calcAvailableBases(); !reflect.DeepEqual(got, tt.want) {
				t.Errorf("calcAvailableBases() = %v, want %v", got, tt.want)
			}
		})
	}
}
