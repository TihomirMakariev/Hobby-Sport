package bg.tihomir.hobby.handler;

public class FailToDeleteException extends RuntimeException{

    public FailToDeleteException(String message) {
        super(message);
    }
}
