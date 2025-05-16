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
public final class NotificationViewModel_Factory implements Factory<NotificationViewModel> {
  private final Provider<TravelRepository> repositoryProvider;

  public NotificationViewModel_Factory(Provider<TravelRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public NotificationViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static NotificationViewModel_Factory create(
      Provider<TravelRepository> repositoryProvider) {
    return new NotificationViewModel_Factory(repositoryProvider);
  }

  public static NotificationViewModel newInstance(TravelRepository repository) {
    return new NotificationViewModel(repository);
  }
}
