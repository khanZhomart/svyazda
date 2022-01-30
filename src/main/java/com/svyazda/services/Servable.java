package com.svyazda.services;

import java.util.List;

interface Servable<T> {
    
    List<T> findAll();
    T findById(Integer id);
    T save(T payload);
    T update(T payload);
    void remove(Integer id);
}