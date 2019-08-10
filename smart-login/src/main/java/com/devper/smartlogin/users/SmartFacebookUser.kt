package com.devper.smartlogin.users

import com.facebook.AccessToken

class SmartFacebookUser : SmartUser() {
    var accessToken: AccessToken? = null
}
