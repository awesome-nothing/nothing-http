package io.nothing.http.volley.params;

import java.io.File;
import java.util.List;

/**
 * @author: Sanvi
 * @date: 12/17/14
 * @email: sanvibyfish@gmail.com
 */
public class NothingFilesParam implements NothingParam {
  public String name;
  public List<File> value;

  public NothingFilesParam(String name, List<File> value){
    this.name = name;
    this.value = value;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public List<File> getValue() {
    return value;
  }
}
