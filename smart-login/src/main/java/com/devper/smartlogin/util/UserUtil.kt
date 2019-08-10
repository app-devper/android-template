package com.devper.smartlogin.util

import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.linecorp.linesdk.auth.LineLoginResult
import com.devper.smartlogin.users.SmartFacebookUser
import com.devper.smartlogin.users.SmartGoogleUser
import com.devper.smartlogin.users.SmartLineUser

object UserUtil {

    fun populateLineUser(result: LineLoginResult): SmartLineUser {
        return SmartLineUser().apply {
            userId = result.lineProfile!!.userId
            displayName = result.lineProfile!!.displayName
            if (result.lineProfile!!.pictureUrl != null) {
                profileLink = result.lineProfile!!.pictureUrl!!.toString()
            }
            accessToken = result.lineCredential!!.accessToken.accessToken
        }
    }

    fun populateFacebookUser(access: AccessToken): SmartFacebookUser {
        return SmartFacebookUser().apply {
            userId = access.userId
            accessToken = access
            gender = -1
        }
    }

    fun populateGoogleUser(account: GoogleSignInAccount): SmartGoogleUser {
        return SmartGoogleUser().apply {
            userId = account.id
            firstName = account.givenName
            lastName = account.familyName
            displayName = account.displayName
            email = account.email
            idToken = account.idToken
            if (account.photoUrl != null) {
                profileLink = account.photoUrl!!.toString()
            }
            if (account.account != null) {
                username = account.account!!.name
            }
        }
    }
}
