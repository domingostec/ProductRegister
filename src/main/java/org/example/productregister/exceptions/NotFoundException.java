package org.example.productregister.exceptions;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String mesage){
        super(mesage);
    }
}
