package com.farming.app.service;


import com.farming.app.exception.BadRequestException;
import com.farming.app.exception.FieldNotFoundException;
import com.farming.app.model.Field;

import java.util.List;

public interface FieldService {

     int add(Field field) throws BadRequestException;

     int deleteById(int id) throws FieldNotFoundException;

     List<Field> findAll();

     Field findById(int id) throws FieldNotFoundException;
}