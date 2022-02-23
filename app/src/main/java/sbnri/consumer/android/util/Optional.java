package sbnri.consumer.android.util;

import io.reactivex.functions.Predicate;

public class Optional<T> {

    private final T value;
    private T defaultObj;
    private Predicate<T> additionalChecks;

    private Optional() {
        this.value = null;
    }

    private Optional(T value) {
        this.value = value;
    }

    private Optional(T value, T defaultObj) {
        this.value = value;
        this.defaultObj = defaultObj;
    }

    private Optional(T value, T defaultObj, Predicate<T> additionalChecks) {
        this.value = value;
        this.defaultObj = defaultObj;
        this.additionalChecks = additionalChecks;
    }

    public static <T> Optional<T> empty() {
        return new Optional<>();
    }

    public static <T> Optional<T> of(T value) {
        return new Optional<>(value);
    }

    public static <T> Optional<T> orElse(T value, T defaultObj) {
        return new Optional<>(value, defaultObj);
    }

    public interface Action<T> {
        void apply(T value);
    }

    public void ifPresent(Action<T> action) {
        if (value != null) {
            action.apply(value);
        }
    }

    public static <T> Optional<T> orElse(T value, T defaultObj, Predicate<T> additionalChecks) {
        return new Optional<>(value, defaultObj, additionalChecks);
    }

    public T get() {
        try {
            return value == null || (additionalChecks != null && additionalChecks.test(value)) ? defaultObj : value;
        } catch (Exception e) {
            return defaultObj;
        }
    }
}
