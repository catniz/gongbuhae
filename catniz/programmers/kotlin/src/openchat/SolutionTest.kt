package openchat


/*
record	result
["Enter uid1234 Muzi", "Enter uid4567 Prodo","Leave uid1234","Enter uid1234 Prodo","Change uid4567 Ryan"]	["Prodo님이 들어왔습니다.", "Ryan님이 들어왔습니다.", "Prodo님이 나갔습니다.", "Prodo님이 들어왔습니다."]
 */

fun main() {
    val record = arrayOf("Enter uid1234 Muzi", "Enter uid4567 Prodo","Leave uid1234","Enter uid1234 Prodo","Change uid4567 Ryan")
    val expected = arrayOf("Prodo님이 들어왔습니다.", "Ryan님이 들어왔습니다.", "Prodo님이 나갔습니다.", "Prodo님이 들어왔습니다.")

    val solution = Solution()
    val result = solution.solution(record)

    if (result.contentEquals(expected)) {
        println("success")
    } else {
        println("expected: ${expected.joinToString(", ")}")
        println("real: ${result.joinToString(", ")}")
        throw AssertionError()
    }
}