package personal.project.dao;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import personal.net.RequestEntity;
import personal.net.ResponseEntity;
import personal.project.vo.Board;

public class BoardNetWorkDao implements BoardDao {

  String dataName;
  DataInputStream in;
  DataOutputStream out;


  public BoardNetWorkDao(String dataName, DataInputStream in, DataOutputStream out) {
    this.dataName = dataName;
    this.in = in;
    this.out = out;
  }

  @Override
  public void insert(Board board) {
    try {
      out.writeUTF(new RequestEntity().command(dataName + "/insert").data(board).toJson());

      ResponseEntity response = ResponseEntity.fromJson(in.readUTF());
      if (response.getStatus().equals(ResponseEntity.ERROR)) {
        throw new RuntimeException(response.getResult());
      }

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<Board> list() {
    try {
      out.writeUTF(new RequestEntity().command(dataName + "/list").toJson());

      ResponseEntity response = ResponseEntity.fromJson(in.readUTF());
      if (response.getStatus().equals(ResponseEntity.ERROR)) {
        throw new RuntimeException(response.getResult());
      }

      return response.getList(Board.class);

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Board findBy(int no) {
    try {
      out.writeUTF(new RequestEntity().command(dataName + "/findBy").data(no).toJson());

      ResponseEntity response = ResponseEntity.fromJson(in.readUTF());

      if (response.getStatus().equals(ResponseEntity.ERROR)) {
        throw new RuntimeException(response.getResult());
      } else if (response.getStatus().equals(ResponseEntity.FAILURE)) {
        return null;
      }

      return response.getObject(Board.class);

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public int update(Board board) {
    try {
      out.writeUTF(new RequestEntity().command(dataName + "/update").data(board).toJson());

      ResponseEntity response = ResponseEntity.fromJson(in.readUTF());

      if (response.getStatus().equals(ResponseEntity.ERROR)) {
        throw new RuntimeException(response.getResult());
      } else if (response.getStatus().equals(ResponseEntity.FAILURE)) {
        return 0;
      }

      return response.getObject(Integer.class);

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }


  @Override
  public int delete(int no) {
    try {
      out.writeUTF(new RequestEntity().command(dataName + "/delete").data(no).toJson());

      ResponseEntity response = ResponseEntity.fromJson(in.readUTF());

      if (response.getStatus().equals(ResponseEntity.ERROR)) {
        throw new RuntimeException(response.getResult());
      } else if (response.getStatus().equals(ResponseEntity.FAILURE)) {
        return 0;
      }

      return response.getObject(Integer.class);

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
