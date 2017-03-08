package se.arbetsformedlingen.venice.log;

public class CheckLogs implements java.util.function.Supplier<LogResponse> {

    @Override
    public LogResponse get() {
        System.out.println("Check logs");
        return null;
    }
}
