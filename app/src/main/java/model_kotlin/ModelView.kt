package model_kotlin

val user1 = User(1, "Райан Гослинг", user_sex(1, "М"), user_group(2, "Белые"), user_city(1, "Москва"))
val user2 = User(2, "Аня", user_sex(2, "Ж"), user_group(1, "Красные"), user_city(2, "Питер"))
val user3 = User(3, "Ива", user_sex(2, "Ж"), user_group(1, "Красные"), user_city(1, "Москва"))
val users = mutableListOf<User>(user1, user2, user3)
var current_id = 4

class ModelView {

    fun getAllUsers(): List<User> {
        return users
    }

    fun getAllSortedUsers(reverse: Boolean = false): List<User> {
//        актуальный отсортированный список всех пользователей
        if (reverse)
            return users.sortedBy { U -> U.name }
        else
            return users.sortedBy { U -> U.name }.reversed()
    }

    fun filterUser(byCity: List<user_city>, byGroup: List<user_group>, bySex: List<user_sex>): List<User>  {
        //        актуальный список всех пользователей, котрые удовлетворяют всем 3 фильтрам
        val filteredUsers = mutableListOf<User>()

        println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
        println(byCity)
        println(byGroup)
        println(bySex)

        for (user in users){
            if (user.city in byCity && user.group in byGroup && user.sex in bySex)
                filteredUsers.add(user)
        }

        return filteredUsers
    }

    fun getUserBuId(userId: Int): User {
        for (user in users) {
            if (user.id == userId) return user
        }
        return user1
    }

    fun delUser(userId: Int): Boolean {
//        удаляет пользователя из бд по id
        for (user in users) {
            if (user.id == userId) {
                return users.remove(user)
            }
        }
        return false
    }

    fun addUser(user: User) {
//        user.id - по умолчанию 0, должен задаваться автоматически в бд
        val new_user = User(current_id, user.name, user.sex,  user.group, user.city)
        current_id += 1
        users.add(new_user)
    }

    fun updateUser(newUserData: User): Boolean {
        for (i in users.indices) {
            if (users[i].id == newUserData.id) {
                users[i].name = newUserData.name
                users[i].sex = newUserData.sex
                users[i].group = newUserData.group
                users[i].city = newUserData.city
                return true
            }
        }
        return false
    }

    fun getAllGroups(): List<user_group> {
        return listOf(user_group(1, "Красные"),  user_group(2, "Белые"))
    }

    fun getGroupById(id: Int): user_group {
        if (id == 1) return user_group(1, "Красные")
        return user_group(2, "Белые")
    }

    fun getGroupByName(name: String): user_group {
        if (name == "Красные") return user_group(1, "Красные")
        return user_group(2, "Белые")
    }

    fun getAllCities(): List<user_city> {
        return listOf(user_city(1, "Москва"),  user_city(2, "Питер"))
    }

    fun getCityById(id: Int): user_city {
        if (id == 1) return user_city(1, "Москва")
        return user_city(2, "Питер")
    }

    fun getCityByName(name: String): user_city {
        if (name == "Москва") return user_city(1, "Москва")
        return user_city(2, "Питер")
    }

    fun getAllSex(): List<user_sex> {
        return listOf(user_sex(1, "М"),  user_sex(2, "Ж"))
    }

    fun getSexById(id: Int): user_sex {
        if (id == 1) return user_sex(1, "М")
        return user_sex(2, "Ж")
    }

    fun getSexByName(name: String): user_sex {
        if (name == "М") return user_sex(1, "М")
        return user_sex(2, "Ж")
    }
}