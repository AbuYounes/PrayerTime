package com.example.farid.prayertime.rxbus;

import androidx.annotation.NonNull;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class RxBusAlarmAction {
    private static BehaviorSubject<Object> sSubject = BehaviorSubject.create();

    private RxBusAlarmAction() {
        // hidden constructor
    }

    public static Disposable subscribe(@NonNull Consumer<Object> action) {
        return sSubject.subscribe(action);
    }

    public static void publish(@NonNull Object message) {
        sSubject.onNext(message);
    }
}
