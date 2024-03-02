package ir.inabama.common;

import lombok.Getter;

public class MyException extends Exception {

    public MyException(String message) {
        this(message, null);
    }

    public MyException(String message, Exception cause) {
        super(message, cause);
    }
}
