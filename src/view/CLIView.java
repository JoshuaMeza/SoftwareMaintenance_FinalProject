package src.view;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CLIView implements ICLI {
    private CLIConfig config;
    private BufferedReader reader;

    public CLIView() {
        this.config = new CLIConfig();
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void update(String[] list) {
        printList(list);
    }

    private void printList(String[] list) {
        for (String element : list) {
            print(element);
        }
    }

    @Override
    public void print(String msg) {
        System.out.println(config.getMainColor() + msg + config.getResetColor());
    }

    @Override
    public String read(String desc) {
        try {
            successMsg(desc);
            return reader.readLine();
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    @Override
    public void successMsg(String msg) {
        print(config.getSuccessColor() + msg + config.getResetColor());
    }

    @Override
    public void warningMsg(String msg) {
        print(config.getWarningColor() + msg + config.getResetColor());
    }

    @Override
    public void dangerMsg(String msg) {
        print(config.getDangerColor() + msg + config.getResetColor());
    }
}
