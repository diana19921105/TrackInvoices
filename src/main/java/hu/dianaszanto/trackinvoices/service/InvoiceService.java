package hu.dianaszanto.trackinvoices.service;

import hu.dianaszanto.trackinvoices.model.Invoice;
import org.springframework.data.domain.Page;

public interface InvoiceService {
    Page<Invoice> findAll(Integer page, Integer size);

    Invoice findById(Long id);
}
