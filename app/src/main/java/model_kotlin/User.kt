package model_kotlin

class User(val id: Int, var name: String, var sex: user_sex, var group: user_group, var city: user_city) {
    override fun toString(): String {
        return "User(id=$id, name=$name, sex=$sex, group=$group, city=$city)"
    }
}

