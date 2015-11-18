package io.nothing.http.volley.response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import io.nothing.http.Result;

/**
 * Created by sanvi on 9/28/15.
 */
public class FileResponse implements Result {
    public File file;
    public InputStream inputStream;

    public FileResponse(File file) {
        this.file = file;
        try {
            this.inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public FileResponse(){}

    @Override
    public <T extends Result> List<T> transfer(JSONArray jsonArray) {
        return null;
    }

    @Override
    public <T extends Result> T transfer(JSONObject jsonObject) {
        return null;
    }

    @Override
    public <T extends Result> T transfer(String str) {
        File file = new File(str);
        this.file = file;
        try {
            this.inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return (T)this;
    }
}
