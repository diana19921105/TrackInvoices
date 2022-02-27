package hu.dianaszanto.trackinvoices.service.impl;

import hu.dianaszanto.trackinvoices.model.Invoice;
import hu.dianaszanto.trackinvoices.model.InvoiceItem;
import hu.dianaszanto.trackinvoices.model.exception.NoSuchInvoiceException;
import hu.dianaszanto.trackinvoices.repository.InvoiceRepository;
import hu.dianaszanto.trackinvoices.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;

    @Override
    public Page<Invoice> findAll(Integer page, Integer size) {
        Page<Invoice> invoices = invoiceRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC,
                "customerName")));

        invoices.stream().forEach((Invoice in) -> {
            calculateTotalPrices(in, in.getItems());
        });

        return invoices;
    }

    private void calculateTotalPrices(Invoice in, List<InvoiceItem> items) {
        items.forEach(item -> item.setTotalPrice(calculateItemTotalPrice(item)));
        in.setTotalPrice(calculateInvoiceTotal(in));
    }

    @Override
    public Invoice findById(Long id) {
        Invoice invoice = invoiceRepository.findById(id).orElseThrow(() -> new NoSuchInvoiceException(id));

        calculateTotalPrices(invoice, invoice.getItems());

        return invoice;
    }


    private int calculateInvoiceTotal(Invoice invoice) {
        List<Integer> prices = invoice.getItems().stream().map(InvoiceItem::getTotalPrice).collect(Collectors.toList());

        int sum = 0;
        for (Integer price : prices) {
            sum += Objects.requireNonNullElse(price, 0);
        }
        return sum;
    }

    private int calculateItemTotalPrice(InvoiceItem item) {
        if (item.getTotalPrice() == null) {
            item.setTotalPrice(0);
        }
        return item.getPrice() * item.getQuantity();
    }
}
