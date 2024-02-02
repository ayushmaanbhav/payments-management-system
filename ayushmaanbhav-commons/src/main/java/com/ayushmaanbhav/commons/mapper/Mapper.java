package com.ayushmaanbhav.commons.mapper;

import com.ayushmaanbhav.commons.exception.ApiException;
import lombok.NonNull;

import java.util.Collection;

public interface Mapper<I, O> {
    @NonNull O forward(@NonNull I input) throws ApiException;

    @NonNull I reverse(@NonNull O input) throws ApiException;

    default @NonNull <OC extends Collection<O>> OC forward(@NonNull Collection<I> input, OC outputCollection) throws ApiException {
        for (I item : input) {
            outputCollection.add(forward(item));
        }
        return outputCollection;
    }

    default @NonNull <IC extends Collection<I>> IC reverse(@NonNull Collection<O> input, IC outputCollection) throws ApiException {
        for (O item : input) {
            outputCollection.add(reverse(item));
        }
        return outputCollection;
    }
}
