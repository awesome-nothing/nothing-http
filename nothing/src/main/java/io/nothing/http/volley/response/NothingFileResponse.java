package io.nothing.http.volley.response;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import io.nothing.http.Result;


public abstract class NothingFileResponse<T extends Result> extends NothingResponse<FileResponse>{
  public void onProgress(long transferredBytes, long totalSize){}

}
