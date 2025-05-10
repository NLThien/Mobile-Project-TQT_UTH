package com.example.travel_application.accessibility

import android.content.Context
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.example.travel_application.R
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore


class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var googleSignInClient: GoogleSignInClient

    // Thêm trạng thái loading và error
    var isLoading by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set

    // Thêm hàm này để lấy Intent cho Google Sign-In
    fun getGoogleSignInIntent() = googleSignInClient.signInIntent

    fun initializeAuth(context: Context) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(context, gso)
    }

    fun handleSignInResult(result: ActivityResult, onSuccess: () -> Unit) {
        val data = result.data
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account: GoogleSignInAccount? = task.getResult(ApiException::class.java)
            if (account != null) {
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                auth.signInWithCredential(credential).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onSuccess()
                    } else {
                        Log.e("FirebaseAuth", "Login failed: ${task.exception}")
                    }
                }
            }
        } catch (e: ApiException) {
            Log.e("GoogleSignIn", "Sign-in failed", e)
        }
    }
    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun signOut() {
        auth.signOut()
        googleSignInClient.signOut().addOnCompleteListener {
            Log.d("AuthViewModel", "Google sign out completed")
        }
    }

    fun clearError() {
        errorMessage = null
    }
    // Đăng ký bằng email/mật khẩu
    fun registerWithEmail(
        email: String,
        password: String,
        userData: Map<String, Any>,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        isLoading = true
        errorMessage = null

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                isLoading = false
                if (task.isSuccessful) {
                    // Lưu thông tin bổ sung vào Firestore
                    saveUserDataToFirestore(userData, onSuccess, onError)
                } else {
                    errorMessage = task.exception?.message ?: "Đăng ký thất bại"
                    onError(errorMessage!!)
                }
            }
    }

    // Đăng nhập bằng email/mật khẩu
    fun loginWithEmail(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        isLoading = true
        errorMessage = null

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                isLoading = false
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    errorMessage = task.exception?.message ?: "Đăng nhập thất bại"
                    onError(errorMessage!!)
                }
            }
    }

    // Lưu thông tin người dùng vào Firestore
    private fun saveUserDataToFirestore(
        userData: Map<String, Any>,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val userId = auth.currentUser?.uid ?: run {
            onError("Không thể lấy ID người dùng")
            return
        }

        FirebaseFirestore.getInstance().collection("user")
            .document(userId)
            .set(userData)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { e ->
                errorMessage = "Lỗi khi lưu thông tin: ${e.message}"
                onError(errorMessage!!)
            }
    }

    // Kiểm tra xem email đã tồn tại chưa
    fun checkEmailExists(
        email: String,
        onResult: (Boolean) -> Unit
    ) {
        FirebaseFirestore.getInstance().collection("user")
            .whereEqualTo("Email", email)
            .get()
            .addOnSuccessListener { documents ->
                onResult(!documents.isEmpty)
            }
            .addOnFailureListener {
                onResult(false)
            }
    }
}