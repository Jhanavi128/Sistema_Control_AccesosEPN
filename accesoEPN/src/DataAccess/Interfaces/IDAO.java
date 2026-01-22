package DataAccess.Interfaces;

import java.util.List;

import Infrastructure.AppException;

public interface IDAO<T> {

    List<T> readAll() throws AppException;

}
