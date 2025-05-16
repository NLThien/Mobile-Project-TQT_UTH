package com.example.travel_application.accessibility;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004Jh\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00140\u0016H\u0086@\u00a2\u0006\u0002\u0010\u0017J\u0018\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u0010\t\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\u001aJ\u0018\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\u0006\u0010\t\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\u001aJ\u0018\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\u001f\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\u001aJ\u0014\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00190!H\u0086@\u00a2\u0006\u0002\u0010\"J\u001c\u0010#\u001a\b\u0012\u0004\u0012\u00020$0!2\u0006\u0010\n\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\u001aJ\u0012\u0010%\u001a\u00020\f2\b\u0010&\u001a\u0004\u0018\u00010\bH\u0002J\f\u0010'\u001a\u00020(*\u00020)H\u0002J\f\u0010*\u001a\u00020\u0019*\u00020\u001eH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006+"}, d2 = {"Lcom/example/travel_application/accessibility/TravelRepository;", "", "firestore", "Lcom/google/firebase/firestore/FirebaseFirestore;", "(Lcom/google/firebase/firestore/FirebaseFirestore;)V", "bookTour", "", "tourId", "", "placeId", "userId", "bookingDate", "Ljava/util/Date;", "travelDate", "numberOfPeople", "", "cast", "", "onSuccess", "Lkotlin/Function0;", "", "onError", "Lkotlin/Function1;", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Date;Ljava/util/Date;IJLkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getPlaceById", "Lcom/example/travel_application/db/TravelPlace;", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getPlaceDetails", "Lcom/example/travel_application/db/TravelDetail;", "getTransactionDocument", "Lcom/google/firebase/firestore/DocumentSnapshot;", "transactionId", "getTravelPlaces", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getUserNotifications", "Lcom/example/travel_application/db/Notification;", "parseFirestoreDate", "dateString", "toLatLng", "Lcom/google/android/gms/maps/model/LatLng;", "Lcom/google/firebase/firestore/GeoPoint;", "toTravelPlace", "app_debug"})
public final class TravelRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.firestore.FirebaseFirestore firestore = null;
    
    @javax.inject.Inject()
    public TravelRepository(@org.jetbrains.annotations.NotNull()
    com.google.firebase.firestore.FirebaseFirestore firestore) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getTravelPlaces(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.travel_application.db.TravelPlace>> $completion) {
        return null;
    }
    
    private final com.example.travel_application.db.TravelPlace toTravelPlace(com.google.firebase.firestore.DocumentSnapshot $this$toTravelPlace) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getPlaceById(@org.jetbrains.annotations.NotNull()
    java.lang.String placeId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.travel_application.db.TravelPlace> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getPlaceDetails(@org.jetbrains.annotations.NotNull()
    java.lang.String placeId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.travel_application.db.TravelDetail> $completion) {
        return null;
    }
    
    private final com.google.android.gms.maps.model.LatLng toLatLng(com.google.firebase.firestore.GeoPoint $this$toLatLng) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object bookTour(@org.jetbrains.annotations.NotNull()
    java.lang.String tourId, @org.jetbrains.annotations.NotNull()
    java.lang.String placeId, @org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    java.util.Date bookingDate, @org.jetbrains.annotations.NotNull()
    java.util.Date travelDate, int numberOfPeople, long cast, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSuccess, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onError, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getUserNotifications(@org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.travel_application.db.Notification>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getTransactionDocument(@org.jetbrains.annotations.NotNull()
    java.lang.String transactionId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.google.firebase.firestore.DocumentSnapshot> $completion) {
        return null;
    }
    
    private final java.util.Date parseFirestoreDate(java.lang.String dateString) {
        return null;
    }
}