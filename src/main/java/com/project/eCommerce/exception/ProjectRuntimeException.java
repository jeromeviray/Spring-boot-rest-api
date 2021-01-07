package com.project.eCommerce.exception;

public class ProjectRuntimeException extends RuntimeException{
    private static final long serialVersionUID = -7596572113030192713L;

    public ProjectRuntimeException( String message ) {
        super( message );
    }

    public ProjectRuntimeException( String message, Throwable cause ) {
        super( message, cause );
    }
}
