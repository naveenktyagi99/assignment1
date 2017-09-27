package com.farming.app.service;


import com.farming.app.dao.FarmDao;
import com.farming.app.exception.BadRequestException;
import com.farming.app.exception.FarmNotFoundException;
import com.farming.app.model.Farm;
import com.farming.app.model.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("farmService")
public class FarmServiceImpl implements FarmService{

    @Autowired
    FarmDao farmDao;

    @Autowired
    ClientService clientService;

    public int add(Farm add) throws BadRequestException{
        try {
            return farmDao.add(add);
        }
        catch (DuplicateKeyException ex){
            throw new BadRequestException(ex.getCause());
        }
        catch (DataIntegrityViolationException ex){
            throw new BadRequestException(ex.getCause());
        }
    }

    public int deleteById(int id) throws FarmNotFoundException {

        int count = farmDao.deleteById(id);
        if(count<1){
            throw new FarmNotFoundException("Farm Not Found");
        }
        return count;
    }

    public Farm findById(int id) throws FarmNotFoundException {

        Farm found = null;
        try {
             found = farmDao.findById(id);
        }
        catch (EmptyResultDataAccessException ex){
            throw new FarmNotFoundException("Farm Not Found");
        }
        return found;
    }

    public List<Farm> findAll() {
        return farmDao.findAll();
    }

    public List<Field> findField(int id){
        return farmDao.findField(id);
    }
}