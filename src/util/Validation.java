package util;

import domain.Account;
import exeptions.ValidationException;

@FunctionalInterface
public interface Validation<T> {
    void validate(T value) throws ValidationException;

}
