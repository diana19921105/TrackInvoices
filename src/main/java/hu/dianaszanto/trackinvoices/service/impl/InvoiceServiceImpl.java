package hu.dianaszanto.trackinvoices.service.impl;

import hu.dianaszanto.trackinvoices.client.GetExchangeRatesResponseBody;
import hu.dianaszanto.trackinvoices.model.Invoice;
import hu.dianaszanto.trackinvoices.model.InvoiceItem;
import hu.dianaszanto.trackinvoices.model.InvoiceItemResponseDTO;
import hu.dianaszanto.trackinvoices.model.InvoiceResponseDTO;
import hu.dianaszanto.trackinvoices.model.exception.NoSuchInvoiceExistException;
import hu.dianaszanto.trackinvoices.repository.InvoiceRepository;
import hu.dianaszanto.trackinvoices.service.InvoiceService;
import hu.dianaszanto.trackinvoices.service.MNBServiceClientImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.StringReader;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final MNBServiceClientImpl mnbServiceClient;

    @Override
    public List<InvoiceResponseDTO> findAll() {
        List<Invoice> invoices = invoiceRepository.findAll();
        invoices.forEach((invoice -> {
            int totalInHuf = calculateInvoiceTotalInHuf(invoice);
            invoice.setTotalPrice(totalInHuf);
        }));

        List<InvoiceItemResponseDTO> dtoCollection = invoices.stream().flatMap(i -> i.getItems().stream().map(it -> new InvoiceItemResponseDTO(it.getId(), it.getProductName(),
                it.getPrice(), it.getQuantity(), it.getTotalPrice()))).collect(Collectors.toList());
        return invoices.stream().map(i -> new InvoiceResponseDTO(i.getId(), i.getCustomerName(), i.getIssueDate(), i.getDueDate(), i.getComment(), dtoCollection,
                i.getTotalPrice(), 0.0)).collect(Collectors.toList());
    }

    private void calculateTotalPrices(Invoice in, List<InvoiceItem> items) {
        items.forEach(item -> item.setTotalPrice(calculateItemTotalPrice(item)));
        in.setTotalPrice(calculateInvoiceTotalInHuf(in));
    }

    @Override
    public InvoiceResponseDTO findById(Long id) {
        Invoice invoice = invoiceRepository.findById(id).orElseThrow(() -> new NoSuchInvoiceExistException(id));
        List<InvoiceItemResponseDTO> itemDTOCollection = invoice.getItems().stream().map(i -> new InvoiceItemResponseDTO(i.getId(), i.getProductName(), i.getPrice(), i.getQuantity(),
                i.getTotalPrice())).collect(Collectors.toList());
        calculateTotalPrices(invoice, invoice.getItems());

        return new InvoiceResponseDTO(invoice.getId(), invoice.getCustomerName(), invoice.getIssueDate(), invoice.getDueDate(), invoice.getComment(),
                itemDTOCollection, invoice.getTotalPrice(), 0.0);
    }


    private int calculateInvoiceTotalInHuf(Invoice invoice) {
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

    @Override
    public void save(Invoice invoice) {
        invoiceRepository.save(invoice);
    }

    private String getExchangeRate() {
        GetExchangeRatesResponseBody response = mnbServiceClient.getResponse();
        StringReader reader = new StringReader(response.getGetExchangeRatesResult().getValue());
        System.out.println(reader);
        return reader.toString();
    }

//    private double calculateInvoiceTotalPriceInEur(Invoice invoice) throws ParseException {
//        Integer totalPrice = invoice.getTotalPrice();
//        String exchangeRate = getExchangeRate();
//        String substring = exchangeRate.substring(67, 73);
//        NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
//        Number number = format.parse(substring);

//        return totalPrice / number.doubleValue();
}

//    private GetExchangeRatesResponseBody calculateItemTotalPriceInEur(InvoiceItem item) throws MNBArfolyamServiceSoapGetExchangeRatesStringFaultFaultMessage, ParseException, JAXBException {
//        Integer totalPrice = item.getTotalPrice();
//        return getExchangeRate();
////        return totalPrice / exchangeRate;
//    }
