package hu.dianaszanto.trackinvoices.model.exception;

public class NoSuchInvoiceExistException extends RuntimeException {
    private static final String MESSAGE = "Invoice with ID : %s doesn't exists!";

    public NoSuchInvoiceExistException(Long id) {
        super(String.format(MESSAGE, id));
    }
}
