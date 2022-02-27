package hu.dianaszanto.trackinvoices.model.exception;

public class NoSuchInvoiceException extends RuntimeException {
    private static final String MESSAGE = "Invoice with ID : %s doesn't exists!";

    public NoSuchInvoiceException(Long id) {
        super(String.format(MESSAGE, id));
    }
}
