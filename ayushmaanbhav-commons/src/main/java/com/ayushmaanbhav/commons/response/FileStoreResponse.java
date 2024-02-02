package com.ayushmaanbhav.commons.response;

import com.ayushmaanbhav.commons.request.FileStoreRequest;
import lombok.Data;

@Data
public class FileStoreResponse extends FileStoreRequest {

    private int id;
    private String url;

}
