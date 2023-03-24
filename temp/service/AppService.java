package com.red.os_api.service;

import java.util.List;

public interface AppService<P,I> {

    List<P> getAll();

    P getById(I id);

    P insert(P p);

    void update(I id, P p);

    void delete(I id);


}
