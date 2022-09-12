package com.softgames.petscare.domain.contracts

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.softgames.petscare.R

class GoogleAuthContract : ActivityResultContract<Int, Task<GoogleSignInAccount>?>() {
    override fun createIntent(context: Context, input: Int): Intent {

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.google_client_id))
            .requestEmail()
            .build()

        return GoogleSignIn.getClient(context, gso).signInIntent.apply {
            putExtra("input", input)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Task<GoogleSignInAccount>? {
        return when (resultCode) {
            Activity.RESULT_OK -> GoogleSignIn.getSignedInAccountFromIntent(intent)
            else -> null
        }
    }
}