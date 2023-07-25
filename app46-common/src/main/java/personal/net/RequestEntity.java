package personal.net;

import com.google.gson.Gson;

public class RequestEntity {
  String command;
  String data;

  public RequestEntity command(String command) {
    this.command = command;
    return this;
  }

  public String toJson() {
    return new Gson().toJson(this);
  }

  public static RequestEntity fromJson(String json) {
    return new Gson().fromJson(json, RequestEntity.class);
  }

  public String getCommand() {
    return this.command;
  }

  @SuppressWarnings("unchecked")
  public <T> T getObject(Class<T> clazz) {
    if (clazz == String.class) {
      return (T) data;
    } else {
      return new Gson().fromJson(data, clazz);
    }
  }

  public RequestEntity data(Object obj) {
    if (obj == null) {
      return this;
    }

    if (obj.getClass() == String.class) {
      this.data = (String) obj;
    } else {
      this.data = new Gson().toJson(obj);
    }

    return this;
  }



}
