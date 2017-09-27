package com.farming.app.dao;


import com.farming.app.model.Farm;
import com.farming.app.model.Field;

import java.util.List;

public interface FarmDao {

     int add(Farm farm);

     int deleteById(int id);

     List<Farm> findAll();

     Farm findById(int id);

     List<Field> findField(int id);

}