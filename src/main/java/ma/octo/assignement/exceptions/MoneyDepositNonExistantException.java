package ma.octo.assignement.exceptions;

public class MoneyDepositNonExistantException extends Exception{
    private static final long serialVersionUID = 1L;
    public MoneyDepositNonExistantException() {
    }
    public MoneyDepositNonExistantException(String message) {
        super(message);
    }
}
