import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class ArgConfig {
    public static class TooFewArgsError extends Throwable {
        private int Given;
        private int Expected;

        TooFewArgsError(int Given, int Expected) {
            this.Given = Given;
            this.Expected = Expected;
        }

        @Override
        public String getMessage() {
            return "Передано слишком мало агрументов командной строки. Ожидалось: "
                    + String.valueOf(Expected)
                    + ", передано "
                    + String.valueOf(Given)
                    + ".";
        }
    }

    public enum Output {FILE, CONSOLE}

    public Scanner InputStream;
    public Output OutputStream;
    public String outputFile;

    public static ArgConfig ParseArgs(String[] args) throws TooFewArgsError, IOException {
        if (args.length < 1) {
            throw new TooFewArgsError(args.length, 2);
        }
        int index = 0;
        String arg = args[index];
        final ArgConfig result = new ArgConfig();
        if (arg.equals("-")) {
            result.InputStream = new Scanner(System.in);
        } else {
            result.InputStream = new Scanner(new BufferedInputStream(new FileInputStream(arg)));
        }
        ++index;
        arg = args[1];
        if (arg.equals("-")) {
            result.OutputStream = Output.CONSOLE;
        } else {
            result.OutputStream = Output.FILE;
            result.outputFile = arg;
        }
        return result;
    }
}
