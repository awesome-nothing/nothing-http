package io.nothing.http.volley.params;

import java.io.File;

/**
 * @author: Sanvi
 * @date: 12/17/14
 * @email: sanvibyfish@gmail.com
 */
public class NothingFileParam implements NothingParam {
  public String name;
  public File value;

  public NothingFileParam(String name, File value){
    this.name = name;
    this.value = value;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public File getValue() {
    return value;
  }
}
