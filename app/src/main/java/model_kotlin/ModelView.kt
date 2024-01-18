package model_kotlin

class ModelView {
    fun getAllUsers(): List<User> {
//        актуальный список всех пользователей
        val user1 = User(1, "Oka1", user_sex(1, "М"), user_group(1, "White"), user_city(1, "Москва"))
        val user2 = User(2, "Pols2", user_sex(1, "Ж"), user_group(1, "Red"), user_city(1, "Питер"))
        val user3 = User(3, "Pols21", user_sex(1, "Ж"), user_group(1, "Red"), user_city(1, "Питер"))

        return listOf(user1, user2, user3)
    }

    fun getAllSortedUsers(reverse: Boolean = false): List<User> {
//        актуальный отсортированный список всех пользователей
        val user1 = User(1, "Oka1", user_sex(1, "М"), user_group(1, "White"), user_city(1, "Москва"))
        val user2 = User(2, "Pols2", user_sex(1, "Ж"), user_group(1, "Red"), user_city(1, "Питер"))

        if (reverse)
            return listOf(user1, user2)
        else
            return listOf(user2, user1)
    }

    fun filterUser(byCity: List<user_city>, byGroup: List<user_group>, bySex: List<user_sex>): List<User>  {
        //        актуальный список всех пользователей, котрые удовлетворяют всем 3 фильтрам
        val user1 = User(1, "Oka1", user_sex(1, "М"), user_group(1, "White"), user_city(1, "Москва"))
        val user2 = User(2, "Pols2", user_sex(1, "Ж"), user_group(1, "Red"), user_city(1, "Питер"))

        return listOf(user1, user2)
    }

    fun getUserBuId(userId: Int): User {
        val user1 = User(1, "Oka1", user_sex(1, "М"), user_group(1, "White"), user_city(1, "Москва"))
        return user1
    }

    fun delUser(userId: Int): Boolean {
//        удаляет пользователя из бд по id
        return true
    }

    fun addUser(user: User) {}

    fun updateUser(newUserData: User): Boolean {
        return true
    }

    fun getAllGroups(): List<user_group> {
        return listOf(user_group(1, "white"),  user_group(2, "red"))
    }

    fun getGroupById(id: Int): user_group {
        return user_group(1, "white")
    }

    fun getAllCities(): List<user_city> {
        return listOf(user_city(1, "white"),  user_city(2, "red"))
    }

    fun getCityById(id: Int): user_city {
        return user_city(1, "Москва")
    }

    fun getAllSex(): List<user_sex> {
        return listOf(user_sex(1, "М"),  user_sex(2, "Ж"))
    }

    fun getSexById(id: Int): user_sex {
        return user_sex(1, "М")
    }
}