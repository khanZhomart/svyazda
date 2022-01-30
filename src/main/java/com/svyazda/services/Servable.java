package com.svyazda.services;

import java.util.List;

interface Servable<T> {
    
    List<T> findAll();
    T findById(String id);
    T save(T payload);
    T update(T payload);
    void remove(String id);
}