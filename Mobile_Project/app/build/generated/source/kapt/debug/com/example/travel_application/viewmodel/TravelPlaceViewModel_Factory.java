package com.example.travel_application.viewmodel;

import com.example.travel_application.accessibility.TravelRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class TravelPlaceViewModel_Factory implements Factory<TravelPlaceViewModel> {
  private final Provider<TravelRepository> travelRepositoryProvider;

  public TravelPlaceViewModel_Factory(Provider<TravelRepository> travelRepositoryProvider) {
    this.travelRepositoryProvider = travelRepositoryProvider;
  }

  @Override
  public TravelPlaceViewModel get() {
    return newInstance(travelRepositoryProvider.get());
  }

  public static TravelPlaceViewModel_Factory create(
      Provider<TravelRepository> travelRepositoryProvider) {
    return new TravelPlaceViewModel_Factory(travelRepositoryProvider);
  }

  public static TravelPlaceViewModel newInstance(TravelRepository travelRepository) {
    return new TravelPlaceViewModel(travelRepository);
  }
}
