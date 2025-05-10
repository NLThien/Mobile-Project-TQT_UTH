package com.example.travel_application.accessibility

import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import javax.inject.Singleton

object ServiceLocator {
    private val instances = mutableMapOf<Class<*>, Any>()

    init {
        // Đăng ký các dependencies
        register(FirebaseFirestore::class.java, FirebaseFirestore.getInstance())
        register(TravelRepository::class.java, TravelRepository(get(FirebaseFirestore::class.java)))
    }

    fun <T> register(clazz: Class<T>, instance: T) {
        instances[clazz] = instance as Any
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> get(clazz: Class<T>): T {
        return instances[clazz] as? T ?: throw IllegalStateException("${clazz.simpleName} not registered")
    }
}