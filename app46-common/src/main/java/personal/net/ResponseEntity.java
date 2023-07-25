package personal.net;

import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ResponseEntity {

  public static final String SUCCESS = "success";
  public static final String FAILURE = "failure";
  public static final String ERROR = "error";

  String status;
  String result;

  public ResponseEntity status(String status) {
    this.status = status;
    return this;
  }

  public ResponseEntity result(Object obj) {
    if (obj == null) {
      return this;
    }

    if (obj.getClass() == String.class) {
      this.result = (String) obj;
    } else {
      this.result = new Gson().toJson(obj);
    }
    return this;
  }

  public static ResponseEntity fromJson(String json) {
    return new Gson().fromJson(json, ResponseEntity.class);
  }

  public String getStatus() {
    return status;
  }

  public String getResult() {
    return result;
  }

  public <T> List<T> getList(Class<T> clazz) {
    return new Gson().fromJson(result, TypeToken.getParameterized(List.class, clazz).getType());
  }

  @SuppressWarnings("unchecked")
  public <T> T getObject(Class<T> clazz) {
    if (clazz == String.class) {
      return (T) result;
    } else {
      return new Gson().fromJson(result, clazz);
    }
  }

  public String toJson() {
    return new Gson().toJson(this);
  }



}
