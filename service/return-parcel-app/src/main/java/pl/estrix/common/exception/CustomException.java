package pl.estrix.common.exception;

import com.google.common.base.Preconditions;
import org.apache.commons.lang.StringUtils;

import javax.faces.application.ViewExpiredException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

public class CustomException extends ViewExpiredException {

    private static String NEW_LINE = "</br>";
    private static String BOLD_START = "<h1>";
    private static String BOLD_STOP = "</h1>";

    private StringBuilder stringBuilder = new StringBuilder();

    public CustomException(String message) {
        this(message, null, null);
    }

    public CustomException(String message, StackTraceElement[] stackTraceElements, String viewId) {
        Preconditions.checkArgument(StringUtils.isNotBlank(message), "Message can't be blank");
        throw new ViewExpiredException(extractMsg(message, stackTraceElements),null,viewId);
    }

    private String extractMsg(String message, StackTraceElement[] stackTraceElements){
//        StringWriter sw = new StringWriter();
//        PrintWriter pw = new PrintWriter(sw);
//        super.printStackTrace(pw);
        if (stringBuilder.length()>1) stringBuilder.delete(0,stringBuilder.length()-1);
        Arrays.stream(stackTraceElements)
                .filter( trace -> trace.getClassName().startsWith("pl"))
                .forEach(trace -> stringBuilder.append("\r\n\t").append(trace.toString()));
        return BOLD_START+message+BOLD_STOP+NEW_LINE+stringBuilder.toString();
    }

}
