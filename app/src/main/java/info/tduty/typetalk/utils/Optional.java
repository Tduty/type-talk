package info.tduty.typetalk.utils;

import androidx.annotation.Nullable;

import java.util.NoSuchElementException;
import java.util.Objects;

import timber.log.Timber;

/**
 * Created by Evgeniy Mezentsev on 07.04.2020.
 */
public class Optional<T> {

    private static final Optional<?> EMPTY = new Optional<>();

    private final T value;

    private Optional() {
        this.value = null;
    }

    public static <T> Optional<T> empty() {
        @SuppressWarnings("unchecked")
        Optional<T> t = (Optional<T>) EMPTY;
        return t;
    }

    private Optional(T value) {
        this.value = Objects.requireNonNull(value);
    }

    public static <T> Optional<T> of(@Nullable T value) {
        try {
            return value == null ? (Optional<T>) empty() : new Optional<>(value);
        } catch (Exception ex) {
            Timber.e(ex);
            return Optional.of(null);
        }
    }

    @Nullable
    public T getOrNull() {
        return value;
    }

    public T getOrDefault(T defValue) {
        return value == null ? defValue : value;
    }

    public T get() {
        if (value == null) {
            throw new NoSuchElementException("No value present");
        }
        return value;
    }

    public boolean isPresent() {
        return value != null;
    }

    public boolean isNotPresent() {
        return !isPresent();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof Optional)) {
            return false;
        }
        Optional<?> other = (Optional<?>) obj;
        return value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value != null
                ? String.format("Maybe[%s]", value)
                : "Maybe.empty";
    }
}
