package com.example.jing.kapep.HttpClient.BaseHttp;

import java.io.File;

/**
 * Created by jing on 2017/6/22.
 */

public class HttpFile {
    // 上传文件类
    private String name;
    private String fileName;
    private String typeString;
    private File file;
    public HttpFile(String name,String fileName,String typeString,File file){
        this.name = name;
        this.fileName = fileName;
        this.typeString = typeString;
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public String getFileName() {
        return fileName;
    }

    public String getTypeString() {
        return typeString;
    }

    public File getFile() {
        return file;
    }
}
