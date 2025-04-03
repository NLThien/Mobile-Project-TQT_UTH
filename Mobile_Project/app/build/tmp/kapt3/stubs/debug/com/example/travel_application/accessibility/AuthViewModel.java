package com.example.travel_application.accessibility;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u0004\u0018\u00010\bJ\u001c\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\n0\u000eJ\u000e\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\u0011J\u0006\u0010\u0012\u001a\u00020\nR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lcom/example/travel_application/accessibility/AuthViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "googleSignInClient", "Lcom/google/android/gms/auth/api/signin/GoogleSignInClient;", "getCurrentUser", "Lcom/google/firebase/auth/FirebaseUser;", "handleSignInResult", "", "result", "Landroidx/activity/result/ActivityResult;", "onSuccess", "Lkotlin/Function0;", "initializeAuth", "context", "Landroid/content/Context;", "signOut", "app_debug"})
public final class AuthViewModel extends androidx.lifecycle.ViewModel {
    private com.google.firebase.auth.FirebaseAuth auth;
    private com.google.android.gms.auth.api.signin.GoogleSignInClient googleSignInClient;
    
    public AuthViewModel() {
        super();
    }
    
    public final void initializeAuth(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    public final void handleSignInResult(@org.jetbrains.annotations.NotNull()
    androidx.activity.result.ActivityResult result, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSuccess) {
    }
    
    public final void signOut() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.google.firebase.auth.FirebaseUser getCurrentUser() {
        return null;
    }
}