package bg.tihomir.hobby.handler;

public class NoHobbiesFoundException extends RuntimeException{
    public NoHobbiesFoundException(String message) {
        super(message);
    }
}
