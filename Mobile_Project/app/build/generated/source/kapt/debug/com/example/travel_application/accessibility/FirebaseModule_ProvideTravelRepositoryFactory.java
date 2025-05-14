package com.example.travel_application.accessibility;

import com.google.firebase.firestore.FirebaseFirestore;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class FirebaseModule_ProvideTravelRepositoryFactory implements Factory<TravelRepository> {
  private final Provider<FirebaseFirestore> firestoreProvider;

  public FirebaseModule_ProvideTravelRepositoryFactory(
      Provider<FirebaseFirestore> firestoreProvider) {
    this.firestoreProvider = firestoreProvider;
  }

  @Override
  public TravelRepository get() {
    return provideTravelRepository(firestoreProvider.get());
  }

  public static FirebaseModule_ProvideTravelRepositoryFactory create(
      Provider<FirebaseFirestore> firestoreProvider) {
    return new FirebaseModule_ProvideTravelRepositoryFactory(firestoreProvider);
  }

  public static TravelRepository provideTravelRepository(FirebaseFirestore firestore) {
    return Preconditions.checkNotNullFromProvides(FirebaseModule.INSTANCE.provideTravelRepository(firestore));
  }
}
