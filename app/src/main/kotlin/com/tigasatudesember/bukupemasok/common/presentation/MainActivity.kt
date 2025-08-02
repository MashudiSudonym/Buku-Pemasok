package com.tigasatudesember.bukupemasok.common.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.tigasatudesember.bukupemasok.common.di.SupabaseModule
import com.tigasatudesember.bukupemasok.common.presentation.ui.theme.BukuPemasokTheme
import dagger.hilt.android.AndroidEntryPoint
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.builtin.IDToken
import io.github.jan.supabase.exceptions.RestException
import kotlinx.coroutines.launch
import timber.log.Timber
import java.security.MessageDigest
import java.util.UUID

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BukuPemasokTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Greeting(
                            name = "Android",
                            modifier = Modifier.padding(innerPadding)
                        )
                        GoogleSignInButton()
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun GoogleSignInButton() {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val onClick: () -> Unit = {
        val credentialManager = CredentialManager.create(context)

        // Generate a nonce and hash it with sha-256
        // Providing a nonce is optional but recommended
        val rawNonce = UUID.randomUUID()
            .toString() // Generate a random String. UUID should be sufficient, but can also be any other random string.
        val bytes = rawNonce.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        val hashedNonce =
            digest.fold("") { str, it -> str + "%02x".format(it) } // Hashed nonce to be passed to Google sign-in


        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId("885246818673-3l9ht3a43dd5pandm0qfa7qu60cn9sve.apps.googleusercontent.com")
            .setNonce(hashedNonce) // Provide the nonce if you have one
            .build()

        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        coroutineScope.launch {
            try {
                val result = credentialManager.getCredential(
                    request = request,
                    context = context,
                )

                val googleIdTokenCredential = GoogleIdTokenCredential
                    .createFrom(result.credential.data)

                val googleIdToken = googleIdTokenCredential.idToken

                SupabaseModule.provideSupabaseAuth(SupabaseModule.provideSupabaseClient())
                    .signInWith(IDToken) {
                        idToken = googleIdToken
                        provider = Google
                        nonce = rawNonce
                    }

                // Handle successful sign-in
                Toast.makeText(context, "Signin success", Toast.LENGTH_SHORT).show()
                Timber.d("Signin success")
            } catch (e: GetCredentialException) {
                // Handle GetCredentialException thrown by `credentialManager.getCredential()`
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                Timber.e("getCredentialException: ${e.message}")
            } catch (e: GoogleIdTokenParsingException) {
                // Handle GoogleIdTokenParsingException thrown by `GoogleIdTokenCredential.createFrom()`
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                Timber.e("googleIdTokenParsingException: ${e.message}")
            } catch (e: RestException) {
                // Handle RestException thrown by Supabase
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                Timber.e("RestException: ${e.message}")
            } catch (e: Exception) {
                // Handle unknown exceptions
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                Timber.e("exception: ${e.message}")
            }
        }
    }

    Button(
        onClick = onClick,
    ) {
        Text("Sign in with Google")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BukuPemasokTheme {
        Greeting("Android")
    }
}