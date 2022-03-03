package hu.dianaszanto.trackinvoices.controller;

import hu.dianaszanto.trackinvoices.model.Invoice;
import hu.dianaszanto.trackinvoices.model.InvoiceItem;
import hu.dianaszanto.trackinvoices.model.InvoiceResponseDTO;
import hu.dianaszanto.trackinvoices.model.exception.NoSuchInvoiceExistException;
import hu.dianaszanto.trackinvoices.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class InvoiceController {
    private final InvoiceService invoiceService;

    @GetMapping("/invoices")
    public String getAllInvoices(Model model) {
        List<InvoiceResponseDTO> invoices = invoiceService.findAll();

        model.addAttribute("invoices", invoices);

        return "invoices";
    }

    @GetMapping("/invoices/{id}")
    public String showInvoice(@PathVariable Long id, Model model) {
        try {
            InvoiceResponseDTO invoice = invoiceService.findById(id);
            model.addAttribute("items", invoice.getItems());
            model.addAttribute("customerName", invoice.getCustomerName());
            model.addAttribute("issueDate", invoice.getIssueDate());
            model.addAttribute("dueDate", invoice.getDueDate());
            model.addAttribute("comment", invoice.getComment());
            model.addAttribute("totalPriceHUF", invoice.getTotalPriceInHUF());
            model.addAttribute("totalPriceEUR", invoice.getTotalPriceInEUR());
            return "invoiceview";
        } catch (NoSuchInvoiceExistException e) {
            model.addAttribute("errorMessage", e.getMessage());
            log.error(e.getMessage());
            return "error";
        }
    }

    @PostMapping("/invoices/create")
    public String createInvoice(@RequestParam String customerName,
                                @RequestParam LocalDate issueDate,
                                @RequestParam LocalDate dueDate,
                                @RequestParam String comment,
                                @RequestParam Integer totalPrice,
                                @ModelAttribute Invoice invoice,
                                Model model) {
        List<InvoiceItem> items = new ArrayList<>();
        model.addAttribute("items", items);
        model.addAttribute("customerName", customerName);
        model.addAttribute("issueDate", issueDate);
        model.addAttribute("dueDate", dueDate);
        model.addAttribute("comment", comment);
        model.addAttribute("totalPrice", totalPrice);
        invoiceService.save(invoice);
        return "redirect:/?items";
    }

    @GetMapping("/invoices/create")
    public String createInvoice() {
        return "create";
    }
}
