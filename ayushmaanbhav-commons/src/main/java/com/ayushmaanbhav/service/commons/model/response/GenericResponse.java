package com.ayushmaanbhav.service.commons.model.response;


import lombok.Getter;
import lombok.NonNull;

@Getter
public final class GenericResponse<T> {

    private final T data;

    public GenericResponse() {
        this.data = null;
    }

    public GenericResponse(T data) {
        this.data = data;
    }

    public static <T> @NonNull GenericResponse<T> fromData(T data) {
        return new GenericResponse<>(data);
    }

}
