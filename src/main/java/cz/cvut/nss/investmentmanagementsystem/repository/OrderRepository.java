package cz.cvut.nss.investmentmanagementsystem.repository;

import cz.cvut.nss.investmentmanagementsystem.model.Order;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends BaseRepository<Order, Long>, JpaSpecificationExecutor<Order> {
}
