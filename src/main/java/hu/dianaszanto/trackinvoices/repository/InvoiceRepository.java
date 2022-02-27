package hu.dianaszanto.trackinvoices.repository;

import hu.dianaszanto.trackinvoices.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
