package com.farming.app.service;


import com.farming.app.dao.FieldDao;
import com.farming.app.exception.BadRequestException;
import com.farming.app.exception.FieldNotFoundException;
import com.farming.app.model.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("fieldService")
public class FieldServiceImpl implements FieldService{

    @Autowired
    FieldDao fieldDao;

    @Autowired
    FarmService farmService;

    public int add(Field field) throws BadRequestException{

        try {
            return fieldDao.add(field);
        }
        catch (DataAccessException ex){
            throw new BadRequestException(ex.getCause());
        }
    }

    public int deleteById(int id)  throws FieldNotFoundException{

        int count = fieldDao.deleteById(id);
        if(count<1){
            throw new FieldNotFoundException("Field Not Found");
        }
        return count;
    }

    public List<Field> findAll() {
        return fieldDao.findAll();
    }

    public Field findById(int id) throws FieldNotFoundException {

        Field found = null;
        try {
             found = fieldDao.findById(id);
        }
        catch (EmptyResultDataAccessException exception){
            throw new FieldNotFoundException(exception);
        }
        return found;
    }
}