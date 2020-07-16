package com.devper.template.core.smartlogin.users

import com.facebook.AccessToken

class SmartFacebookUser : SmartUser() {
    var accessToken: AccessToken? = null
}
