package com.farming.app.service;


import com.farming.app.exception.BadRequestException;
import com.farming.app.exception.FarmNotFoundException;
import com.farming.app.model.Farm;
import com.farming.app.model.Field;

import java.util.List;

public interface FarmService {

     int add(Farm farm) throws BadRequestException;

     int deleteById(int id) throws FarmNotFoundException;

     List<Farm> findAll();

     Farm findById(int id) throws FarmNotFoundException;

     List<Field> findField(int id);
}