package com.farming.app.dao;


import com.farming.app.model.Field;

import java.util.List;

public interface FieldDao {

     int add(Field field);

     int deleteById(int id);

     Field findById(int id);

     List<Field> findAll();
}