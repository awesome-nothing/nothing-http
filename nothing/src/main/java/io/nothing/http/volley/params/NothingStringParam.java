package io.nothing.http.volley.params;

public class NothingStringParam implements NothingParam {
  public String name;
  public String value;

  public NothingStringParam(String name, String value) {
    this.name = name;
    this.value = value;
  }

  public NothingStringParam(String name, int value) {
    this.name = name;
    this.value = "" + value;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
