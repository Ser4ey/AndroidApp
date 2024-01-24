package model_kotlin

class user_city(val id: Int, val name: String) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as user_city
        if (id != other.id) return false
        return true
    }
}