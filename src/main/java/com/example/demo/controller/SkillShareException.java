package com.example.demo.controller;

public class SkillShareException extends RuntimeException {

    String message;       // Missatge per mostrar a la vista
    String errorName;     // Identificador de l’error

    public SkillShareException(String message, String errorName)
    {
        this.message=message;
        this.errorName=errorName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorName() {
        return errorName;
    }

    public void setErrorName(String errorName) {
        this.errorName = errorName;
    }
}