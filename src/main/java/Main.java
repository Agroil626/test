import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println("Введите арифметическое выражение, для вывода списка команд введите \"help\".");
        try {
            ArgConfig config = ArgConfig.ParseArgs(args);
            action(config);
        } catch (ArgConfig.TooFewArgsError | IOException tooFewArgsError) {
            System.out.println(tooFewArgsError.getMessage());
        }
    }

    private static void action(ArgConfig config) throws IOException {
        while (config.InputStream.hasNextLine()) {
            String a = config.InputStream.nextLine();
            if (a.equals("exit")) {
                return;
            }
            if (a.equals("help")) {
                help();
            } else {
                if (config.OutputStream == ArgConfig.Output.CONSOLE) {
                    System.out.println(ParseString(a));
                } else {
                    try (FileWriter out = new FileWriter(config.outputFile)) {
                        out.write(ParseString(a));
                    }
                }
            }
        }
        config.InputStream.close();
    }

    private static String ParseString(String s) {
        ArrayList<String> list = new ArrayList<>(Arrays.asList(s.split(" ")));
        return count(list);
    }

    private static String count(ArrayList<String> list) {
        Operations operate = new Operations();
        if (list.size() < 3) {
            return "Недостаточно аргументов в выражении";
        }
        operate.clear();
        switch (list.get(0)) {
            case "add":
                for (int i = 1; i < list.size(); i++) {
                    if (checkingElemString(list.get(i))) {
                        operate.add(Integer.parseInt(list.get(i)));
                    }
                }
                return "Ответ: " + operate.getSum();
            case "mul":
                for (int i = 1; i < list.size(); i++) {
                    if (checkingElemString(list.get(i))) {
                        operate.multiply(Integer.parseInt(list.get(i)));
                    }
                }
                return "Ответ: " + operate.getResult();
            case "mulPlus":
                for (int i = 1; i < list.size(); i++) {
                    if (i < 3 && checkingElemString(list.get(i))) {
                        operate.multiply(Integer.parseInt(list.get(i)));
                    } else if (i < 4 && checkingElemString(list.get(i))) {
                        operate.add(Integer.parseInt(list.get(i)));
                        operate.multiplyAndSum();
                        return "Ответ: " + operate.getResult();
                    }
                }
                break;
        }
        return "Операция над числами не введена";
    }

    private static boolean checkingElemString(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException ex) {
            return false; 
        }

    }

    public static void help() {
        System.out.println("Арифметические операции: \n"
                + "add - сумма 2х и более чисел;\n"
                + "mul - произведение 2х и более чисел\n"
                + "mulPlus - операция для умножения первых 2х чисел, к которое суммируется с 3м числом.");
    }

}
