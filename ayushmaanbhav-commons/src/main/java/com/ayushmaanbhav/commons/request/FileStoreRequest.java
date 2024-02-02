package com.ayushmaanbhav.commons.request;

import com.ayushmaanbhav.commons.contstants.FileType;
import lombok.Data;

@Data
public class FileStoreRequest {

    private FileType fileType;
    private String identifier;

}
