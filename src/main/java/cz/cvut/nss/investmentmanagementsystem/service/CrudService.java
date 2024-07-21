package cz.cvut.nss.investmentmanagementsystem.service;

public interface CrudService<T, ID> {
    void create(T entity);
    T get(ID id);
    void update(T entity);
    void delete(ID id);
}
