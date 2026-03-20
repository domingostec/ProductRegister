package org.example.productregister.exceptions;

public class InvalidArgumentsException extends RuntimeException{

    public InvalidArgumentsException(String mesage){
        super(mesage);
    }
}
