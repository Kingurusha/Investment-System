package cz.cvut.nss.investmentmanagementsystem.helper.validator;

import org.springframework.data.jpa.repository.JpaRepository;

public abstract class AbstractValidator<T, ID>{
    private final JpaRepository<T, ID> repository;

    protected AbstractValidator(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    public void validateExistById(ID id) {
        if (!repository.existsById(id)){
            throw new IllegalArgumentException("Object with ID " + id + " does not exist.");
        }
    }
}
