package hu.dianaszanto.trackinvoices.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class InvoiceRequestDTO {
    private Long id;
    private String customerName;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private String comment;
    private List<InvoiceItemResponseDTO> items;
    private Integer totalPrice;

}
