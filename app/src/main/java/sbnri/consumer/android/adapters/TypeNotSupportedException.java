package sbnri.consumer.android.adapters;

/**
 * Created by meier on 29/12/2016.
 */

public class TypeNotSupportedException extends RuntimeException {
    private TypeNotSupportedException(String message) {
        super(message);
    }

    public static TypeNotSupportedException create(String message) {
        return new TypeNotSupportedException(message);
    }
}
