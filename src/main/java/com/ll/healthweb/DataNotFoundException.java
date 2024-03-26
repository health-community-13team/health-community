package com.ll.healthweb;

public class DataNotFoundException extends RuntimeException {
    private static final long seriaVersionUID = 1L;
    public DataNotFoundException(String message) {
        super(message);
    }
}
