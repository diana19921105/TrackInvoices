package hu.dianaszanto.trackinvoices.service;

import hu.dianaszanto.trackinvoices.model.Invoice;
import hu.dianaszanto.trackinvoices.model.InvoiceResponseDTO;

import java.util.List;

public interface InvoiceService {
    List<InvoiceResponseDTO> findAll();

    InvoiceResponseDTO findById(Long id);

    void save(Invoice invoice);

}
