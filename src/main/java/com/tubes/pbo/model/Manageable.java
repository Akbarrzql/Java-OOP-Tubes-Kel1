package com.tubes.pbo.model;

import java.util.List;

public interface Manageable<T> {
    void add(T data);
    List<T> view();
    void edit(int id, T data);
    void delete(int id);
}