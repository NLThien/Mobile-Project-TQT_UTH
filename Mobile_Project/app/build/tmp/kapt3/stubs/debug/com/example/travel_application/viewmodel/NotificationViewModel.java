package com.example.travel_application.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u00020\u000bJ\u000e\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aJ\u000e\u0010\u001b\u001a\u00020\u00182\u0006\u0010\u001c\u001a\u00020\u001aJ\u0012\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010\u001aH\u0002R\u0016\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001d\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\t0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u0012\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000f\u00a8\u0006 "}, d2 = {"Lcom/example/travel_application/viewmodel/NotificationViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/example/travel_application/accessibility/TravelRepository;", "(Lcom/example/travel_application/accessibility/TravelRepository;)V", "_notificationDetail", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/example/travel_application/db/Notification;", "_notifications", "", "_transactionDocument", "Lcom/google/firebase/firestore/DocumentSnapshot;", "notificationDetail", "Lkotlinx/coroutines/flow/StateFlow;", "getNotificationDetail", "()Lkotlinx/coroutines/flow/StateFlow;", "notifications", "getNotifications", "transactionDocument", "getTransactionDocument", "createBookingFromDocument", "Lcom/example/travel_application/db/Booking;", "doc", "loadNotificationDetail", "", "transactionId", "", "loadNotifications", "userId", "parseFirestoreDate", "Ljava/util/Date;", "dateString", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class NotificationViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.example.travel_application.accessibility.TravelRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.example.travel_application.db.Notification>> _notifications = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.travel_application.db.Notification>> notifications = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.example.travel_application.db.Notification> _notificationDetail = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.example.travel_application.db.Notification> notificationDetail = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.google.firebase.firestore.DocumentSnapshot> _transactionDocument = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.google.firebase.firestore.DocumentSnapshot> transactionDocument = null;
    
    @javax.inject.Inject()
    public NotificationViewModel(@org.jetbrains.annotations.NotNull()
    com.example.travel_application.accessibility.TravelRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.travel_application.db.Notification>> getNotifications() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.example.travel_application.db.Notification> getNotificationDetail() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.google.firebase.firestore.DocumentSnapshot> getTransactionDocument() {
        return null;
    }
    
    public final void loadNotifications(@org.jetbrains.annotations.NotNull()
    java.lang.String userId) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.example.travel_application.db.Booking createBookingFromDocument(@org.jetbrains.annotations.NotNull()
    com.google.firebase.firestore.DocumentSnapshot doc) {
        return null;
    }
    
    public final void loadNotificationDetail(@org.jetbrains.annotations.NotNull()
    java.lang.String transactionId) {
    }
    
    private final java.util.Date parseFirestoreDate(java.lang.String dateString) {
        return null;
    }
}