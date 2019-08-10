package com.devper.smartlogin.users

open class SmartUser {

    var userId: String? = null
    var username: String? = null
    var firstName: String? = null
    var middleName: String? = null
    var lastName: String? = null
    var email: String? = null
    var birthday: String? = null
    var gender: Int = 0
    var profileLink: String? = null

    override fun toString(): String {
        return "SmartUser{userId='$userId', username='$username', firstName='$firstName', middleName='$middleName', lastName='$lastName', email='$email', birthday='$birthday', gender=$gender, profileLink='$profileLink'}"
    }
}
