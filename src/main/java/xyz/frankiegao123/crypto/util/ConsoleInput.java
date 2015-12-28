package xyz.frankiegao123.crypto.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public abstract class ConsoleInput {

    private final static ConsoleInput instance = loadInstance();
    private final static String[] INPUT_TRUE = { "y", "yes", "true", "t" }; 

    private ConsoleInput() {
    }

    public static ConsoleInput getInstance() {
        return instance;
    }
    
    protected boolean convertBoolean(String input, boolean defaultValue) {
        if(Tools.isEmpty(input)) {
            return defaultValue;
        }
        for(int i = 0; i < INPUT_TRUE.length; i++) {
            if(INPUT_TRUE[i].equalsIgnoreCase(input)) {
                return true;
            }
        }
        return false;
    }
    
    public void bellOutput(String prompt) {
        System.out.println("\7" + prompt);
    }
    
    public boolean inputBoolean(String prompt) {
        return convertBoolean( inputString(prompt), false );
    }
    
    public boolean inputBoolean(String prompt, boolean defaultValue) {
        return convertBoolean( inputString(prompt), defaultValue );
    }
    
    public String inputString(String prompt, boolean acceptEmpty) {
        String input = null;
        do {
            input = inputString(prompt);
        } while ( !acceptEmpty && Tools.isEmpty(input) );
        return input;
    }
    
    public abstract int inputInt(String prompt, String errorMessage);    
    public abstract String inputString(String prompt);

    private static class ScannerConsoleInput extends ConsoleInput {
        
        private final Scanner scanner;
        
        private ScannerConsoleInput() {
            this.scanner = new Scanner(System.in);
        }

        @Override
        public int inputInt(String prompt, String errorMessage) {
            while(true) {
                System.out.print(prompt);
                try {
                    return scanner.nextInt();
                } catch (Exception e) {
                    scanner.next();
                    System.out.println(errorMessage);
                }
            }
        }
    
        @Override
        public String inputString(String prompt) {
            System.out.print(prompt);
            return scanner.nextLine();
        }
    }
    
    private static class BufferedReaderConsoleInput extends ConsoleInput {
        
        private final BufferedReader br;
        
        private BufferedReaderConsoleInput() {
            this.br = new BufferedReader(new InputStreamReader(System.in));
        }

        @Override
        public int inputInt(String prompt, String errorMessage) {
            while(true) {
                System.out.print(prompt);
                try {
                    return Integer.parseInt(br.readLine());
                } catch (Exception e) {                    
                    System.out.println(errorMessage);
                }
            }
        }
    
        @Override
        public String inputString(String prompt) {
            System.out.print(prompt);
            try {
                return br.readLine();
            } catch (IOException e) {
                return null;
            }
        }
    }
    
    private static int javaVersion() {
        String javaVer = System.getProperty("java.specification.version");
        if(javaVer == null) {
            javaVer = System.getProperty("java.version");
        }
        int start = javaVer.indexOf('.') + 1;
        int end = javaVer.indexOf('.', start);
        String ver = null;
        if(end > start) {
            ver = javaVer.substring(start, end);
        } else {
            ver = javaVer.substring(start);
        }
        return Integer.parseInt(ver);
    }
    
    private static ConsoleInput loadInstance() {
        if(javaVersion() > 4) {
            return new ScannerConsoleInput();
        }
        return new BufferedReaderConsoleInput();
    }
}
