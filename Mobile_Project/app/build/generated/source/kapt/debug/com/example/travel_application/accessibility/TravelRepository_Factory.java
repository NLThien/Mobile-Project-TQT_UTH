package com.example.travel_application.accessibility;

import com.google.firebase.firestore.FirebaseFirestore;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class TravelRepository_Factory implements Factory<TravelRepository> {
  private final Provider<FirebaseFirestore> firestoreProvider;

  public TravelRepository_Factory(Provider<FirebaseFirestore> firestoreProvider) {
    this.firestoreProvider = firestoreProvider;
  }

  @Override
  public TravelRepository get() {
    return newInstance(firestoreProvider.get());
  }

  public static TravelRepository_Factory create(Provider<FirebaseFirestore> firestoreProvider) {
    return new TravelRepository_Factory(firestoreProvider);
  }

  public static TravelRepository newInstance(FirebaseFirestore firestore) {
    return new TravelRepository(firestore);
  }
}
