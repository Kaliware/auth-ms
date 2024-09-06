package br.com.kaliware.ms.auth_ms.enumeration;

public enum ProviderEnum {

  GOOGLE(1);

  private final int id;

  ProviderEnum(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

}
