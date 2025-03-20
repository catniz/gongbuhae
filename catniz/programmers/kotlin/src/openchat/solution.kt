package openchat

class Solution {
    // uid ordered by access time, only Enter and Leave
    private val accessLogs = mutableListOf<AccessLog>()
    // uid - nickname
    private val userMap = mutableMapOf<String, String>()

    fun solution(record: Array<String>): Array<String> {
        val answer = mutableListOf<String>()

        // record to accessLogs and userMap
        for (r in record) {
            val split = r.split(" ")
            val command = Command.valueOf(split[0])
            val uid = split[1]

            when (command) {
                Command.Enter -> {
                    accessLogs.add(AccessLog(uid, command))
                    userMap[uid] = split[2]
                }
                Command.Leave -> {
                    accessLogs.add(AccessLog(uid, command))
                }
                Command.Change -> {
                    userMap[uid] = split[2]
                }
            }

        }

        // accessLogs to answer
        for (al in accessLogs) {
            answer.add(al.print(userMap[al.uid]!!))
        }

        return answer.toTypedArray()
    }


}

class AccessLog(val uid: String, private val command: Command) {
    fun print(nickname: String): String {
        return "$nickname${command.getDesc()}"
    }
}

enum class Command {
    Enter {
        override fun getDesc(): String {
            return "님이 들어왔습니다."
        }
    },
    Leave {
        override fun getDesc(): String {
            return "님이 나갔습니다."
        }
    },
    Change;

    open fun getDesc(): String {
        throw IllegalStateException("${this.name} is not allowed to get description.")
    }
}