package model_kotlin

class user_sex(val id: Int, val value: String) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as user_sex
        if (id != other.id) return false
        return true
    }
}