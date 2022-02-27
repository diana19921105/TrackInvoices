package hu.dianaszanto.trackinvoices.controller;

import hu.dianaszanto.trackinvoices.model.Invoice;
import hu.dianaszanto.trackinvoices.model.exception.NoSuchInvoiceExistException;
import hu.dianaszanto.trackinvoices.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
public class InvoiceController {
    private final InvoiceService invoiceService;

    @GetMapping("/invoices")
    public String getAllInvoices(Model model,
                                 @RequestParam(required = false) Integer page,
                                 @RequestParam(required = false) Integer size) {
        page = page == null ? 0 : page;
        size = size == null ? 10 : size;
        Page<Invoice> invoices = invoiceService.findAll(page, size);

        model.addAttribute("prevPage", page - 1);
        model.addAttribute("nextPage", page + 1);
        model.addAttribute("size", size);
        model.addAttribute("page", invoices);

        return "invoices";
    }

    @GetMapping("/invoices/{id}")
    public String showInvoice(@PathVariable Long id, Model model) {
        try {
            Invoice invoice = invoiceService.findById(id);
            model.addAttribute("items", invoice.getItems());
            model.addAttribute("customerName", invoice.getCustomerName());
            model.addAttribute("issueDate", invoice.getIssueDate());
            model.addAttribute("dueDate", invoice.getDueDate());
            model.addAttribute("comment", invoice.getComment());
            model.addAttribute("totalPrice", invoice.getTotalPrice());
            return "invoiceview";
        } catch (NoSuchInvoiceExistException e) {
            model.addAttribute("errorMessage", e.getMessage());
            log.error(e.getMessage());
            return "error";
        }


    }
}
