package com.example.travel_application.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u0012\u001a\u00020\u0013H\u0002J\b\u0010\u0014\u001a\u00020\u0013H\u0002J\u000e\u0010\u0015\u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\u000bR\u001a\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001d\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001d\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lcom/example/travel_application/viewmodel/TravelPlaceViewModel;", "Landroidx/lifecycle/ViewModel;", "travelRepository", "Lcom/example/travel_application/accessibility/TravelRepository;", "(Lcom/example/travel_application/accessibility/TravelRepository;)V", "_displayedTravelPlaces", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "Lcom/example/travel_application/db/TravelPlace;", "_travelPlaces", "currentSearchQuery", "", "displayedTravelPlaces", "Lkotlinx/coroutines/flow/StateFlow;", "getDisplayedTravelPlaces", "()Lkotlinx/coroutines/flow/StateFlow;", "travelPlaces", "getTravelPlaces", "filterDisplayedPlaces", "", "loadTravelPlaces", "updateSearchQuery", "query", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class TravelPlaceViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.example.travel_application.accessibility.TravelRepository travelRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.example.travel_application.db.TravelPlace>> _travelPlaces = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.travel_application.db.TravelPlace>> travelPlaces = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.example.travel_application.db.TravelPlace>> _displayedTravelPlaces = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.travel_application.db.TravelPlace>> displayedTravelPlaces = null;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String currentSearchQuery = "";
    
    @javax.inject.Inject()
    public TravelPlaceViewModel(@org.jetbrains.annotations.NotNull()
    com.example.travel_application.accessibility.TravelRepository travelRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.travel_application.db.TravelPlace>> getTravelPlaces() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.travel_application.db.TravelPlace>> getDisplayedTravelPlaces() {
        return null;
    }
    
    private final void loadTravelPlaces() {
    }
    
    public final void updateSearchQuery(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
    }
    
    private final void filterDisplayedPlaces() {
    }
}