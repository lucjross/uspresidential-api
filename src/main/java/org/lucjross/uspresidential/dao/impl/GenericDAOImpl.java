//package org.lucjross.uspresidential.dao.impl;
//
//import org.lucjross.uspresidential.dao.GenericDAO;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import java.lang.reflect.ParameterizedType;
//
///**
// * Created by lucas on 11/21/2014.
// */
//public abstract class GenericDAOImpl<T> implements GenericDAO<T> {
//
//    @PersistenceContext
//    protected EntityManager entityManager;
//
//    private Class<T> type;
//
//    @SuppressWarnings("unchecked")
//    public GenericDAOImpl()
//    {
//        ParameterizedType pt = (ParameterizedType) getClass().getGenericSuperclass();
//        type = (Class<T>) pt.getActualTypeArguments()[0];
//    }
//
//    @Override
//    public T create(T t) {
//        entityManager.persist(t);
//        return t;
//    }
//
//    @Override
//    public T find(Object id) {
//        return entityManager.find(type, id);
//    }
//
//    @Override
//    public T update(T t) {
//        return entityManager.merge(t);
//    }
//
//    @Override
//    public void delete(Object id) {
//        entityManager.remove(entityManager.getReference(type, id));
//    }
//}
