package hu.dianaszanto.trackinvoices.model;

import java.time.LocalDate;
import java.util.List;

public class InvoiceResponseDTO {
    private Long id;
    private String customerName;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private String comment;
    private List<InvoiceItemResponseDTO> items;
    private Integer totalPriceInHUF;
    private double totalPriceInEUR;

    public InvoiceResponseDTO(Long id, String customerName, LocalDate issueDate, LocalDate dueDate, String comment, List<InvoiceItemResponseDTO> items, Integer totalPriceInHUF, double totalPriceInEUR) {
        this.id = id;
        this.customerName = customerName;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.comment = comment;
        this.items = items;
        this.totalPriceInHUF = totalPriceInHUF;
        this.totalPriceInEUR = totalPriceInEUR;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public String getComment() {
        return comment;
    }

    public List<InvoiceItemResponseDTO> getItems() {
        return items;
    }

    public Integer getTotalPriceInHUF() {
        return totalPriceInHUF;
    }


    public double getTotalPriceInEUR() {
        return totalPriceInEUR;
    }
}
