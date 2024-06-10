package bg.tihomir.hobby.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;


@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(GlobalExceptionHandler.class);

    // The code is appropriate when we want to return JSON file;
    /*@ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        LOGGER.warn("File size exceeds the maximum allowed size", exc);

        return ResponseEntity
                .status(HttpStatus.PAYLOAD_TOO_LARGE)
                .body("File size exceeds the maximum allowed size of 5MB.");
    }*/

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ModelAndView handleMaxSizeException(MaxUploadSizeExceededException exc) {
        LOGGER.warn("File size exceeds the maximum allowed size", exc);
        ModelAndView modelAndView = new ModelAndView("error/payload-exceeded");
        modelAndView.addObject("message", "File size exceeds the maximum allowed size of 1MB.");
        return modelAndView;
    }

    @ExceptionHandler(NoHobbiesFoundException.class)
    public ModelAndView handleNoHobbiesFoundException(NoHobbiesFoundException ex) {
        ModelAndView modelAndView = new ModelAndView("error/no-hobbies");
        modelAndView.addObject("message", ex.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public String handleUnauthorizedAccessException(UnauthorizedAccessException ex, Model model) {
        model.addAttribute("errorMessage", "You are not authorized to delete this hobby offer.");
        return "error/unauthorized"; // Връщаме името на шаблона за грешка
    }

}
