package com.hop.data.dynamicFilter;

public class RowLevelFilterException extends Exception {

    public RowLevelFilterException(String message) {
        super(message);
    }

    public RowLevelFilterException(String message, Throwable throwable) {
        super(message, throwable);
    }
}