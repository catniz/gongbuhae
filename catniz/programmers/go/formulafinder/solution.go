package formulafinder

import (
	"fmt"
	"strconv"
)

func solution(expressions []string) []string {
	// 1. 수식 struct 생성
	formulas := make([]formula, 0, len(expressions))
	for _, expression := range expressions {
		formulas = append(formulas, buildFormula(expression))
	}

	// 2. 결과가 있는 수식의 가능한 진법 추출
	availableBases := findAvailableBases(formulas)

	// 3. 결과가 없는 수식의 결과 계산
	ret := make([]string, 0)
	for _, f := range formulas {
		if f.isUnknownFormula() {
			ret = append(ret, f.resultStr(availableBases))
		}
	}

	return ret
}

func findAvailableBases(formulas []formula) []int {
	availableBaseMap := make(map[int]bool)
	for i := availableBaseMin; i <= availableBaseMax; i++ {
		availableBaseMap[i] = true
	}

	for _, f := range formulas {
		formulaBases := f.calcAvailableBases()
		for availableBase := range availableBaseMap {
			if !formulaBases[availableBase] {
				delete(availableBaseMap, availableBase)
			}
		}
	}

	availableBases := make([]int, 0, len(availableBaseMap))
	for base := range availableBaseMap {
		availableBases = append(availableBases, base)
	}
	return availableBases
}

// X가 있는 수식만 결과로 보여주면 됨
// 확정 불가능한 수식은 ?로 표시, 확정 가능한 수식은 결과로 표시
// 수식: A +/- B = C
// 2~9 진법만 사용, 음수 결과는 나오지 않음

type operator string

const (
	plus  operator = "+"
	minus operator = "-"
)

func (op operator) calc(a, b int) int {
	switch op {
	case plus:
		return a + b
	case minus:
		return a - b
	}
	panic("unknown operator")
}

const (
	availableBaseMin = 2
	availableBaseMax = 9
)

type formula struct {
	a, b, c string
	op      operator
}

func buildFormula(expression string) formula {
	var a, b, c string
	var op operator
	sscanf, err := fmt.Sscanf(expression, "%s %s %s = %s", &a, &op, &b, &c)
	if err != nil {
		panic(err)
	}
	if sscanf != 4 {
		panic("invalid expression")
	}
	return formula{a, b, c, op}
}

func (f formula) calcAvailableBases() map[int]bool {
	ret := make(map[int]bool)
	for i := availableBaseMin; i <= availableBaseMax; i++ {
		if f.availableBase(i) {
			ret[i] = true
		}
	}
	return ret
}

func (f formula) calcUsingDec(base int) (int, error) {
	a, err := baseToDec(base, f.a)
	if err != nil {
		return 0, fmt.Errorf("a: %s, err: %v", f.a, err)
	}
	b, err := baseToDec(base, f.b)
	if err != nil {
		return 0, fmt.Errorf("b: %s, err: %v", f.b, err)
	}
	return f.op.calc(a, b), nil
}

func (f formula) availableBase(base int) bool {
	c, err := f.calcUsingDec(base)
	if err != nil {
		return false
	}
	return f.isUnknownFormula() || decToBase(base, c) == f.c
}

func (f formula) isUnknownFormula() bool {
	return f.c == "X"
}

func (f formula) calcResult(availableBases []int) string {
	ret := "!"
	for _, base := range availableBases {
		calcResult, err := f.calcUsingDec(base)
		if err != nil {
			continue
		}
		baseResult := decToBase(base, calcResult)

		if ret != "!" && ret != baseResult {
			return "?"
		}
		ret = baseResult
	}
	return ret
}

func (f formula) resultStr(availableBases []int) string {
	return fmt.Sprintf("%s %s %s = %s", f.a, f.op, f.b, f.calcResult(availableBases))
}

func baseToDec(base int, num string) (int, error) {
	ret := 0
	for i := 0; i < len(num); i++ {
		n := int(num[i] - '0')
		if n >= base {
			return 0, fmt.Errorf("invalid number: %d", n)
		}
		ret = ret*base + n
	}
	return ret, nil
}

func decToBase(base int, num int) string {
	ret := ""
	for num > 0 {
		r := num % base
		ret = strconv.Itoa(r) + ret
		num /= base
	}

	if ret == "" {
		ret = "0"
	}

	return ret
}
