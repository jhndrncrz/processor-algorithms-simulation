package dev.jhndrncrz.util;

import java.util.Scanner;

public class IO {
    public static boolean isLastInputValid = true;
    private static final Scanner scanner = new Scanner(System.in);
    private static final String NAME = "CRUZ, JOHN ADRIAN B.";
    private static final String TERM = "Finals";
    private static final byte MODULE_NUMBER = 5;
    private static final byte ACTIVITY_NUMBER = 3;
    private static final String ACTIVITY_NAME = "Processor Scheduling Algorithms: Round Robin";

    private IO() {
    }

    // Header and Footer
    public static void displayHeader() {
        Terminal.clear();

        displayTitle(String.format("%sL-M%d-ACT%d: %s", TERM.charAt(0), MODULE_NUMBER, ACTIVITY_NUMBER, ACTIVITY_NAME));
        displaySubtitle(NAME);
        IO.displayDivider();
    }

    public static void displayFooter() {
        IO.displayDivider();

        Terminal.pause();
    }

    // General
    public static void display(String string) {
        System.out.format("%s\n", string);
    }

    public static void displayTitle(String string) {
        System.out.format("%s\n", IO.applyStyle(string, "bold"));
    }

    public static void displaySubtitle(String string) {
        System.out.format("%s\n", IO.applyStyle(string, "italic"));
    }

    public static void displayLabelled(String label, String value) {
        System.out.format("%s: %s\n", IO.applyStyle(label, "italic"), value);
    }

    public static void displaySpacer(int n) {
        for (int i = 0; i < n; ++i) {
            System.out.print("\n");
        }
    }

    public static void displayDivider() {
        System.out.print("-------------------------------------------------\n");
    }

    // Status
    public static void displayInfo(String message) {
        System.out.format("%s %s\n", IO.applyStyle(" INFO ", "bold bg-yellow"), message);
    }

    public static void displayLog(String message) {
        System.out.format("%s %s\n", IO.applyStyle(" LOG ", "bold bg-white"), message);
    }

    public static void displaySuccess(String message) {
        System.out.format("%s %s\n", IO.applyStyle(" SUCCESS ", "bold bg-green"), message);
    }

    public static void displayError(String message) {
        System.out.format("%s %s\n", IO.applyStyle(" ERROR ", "bold bg-red"), message);
    }

    // Inputs
    public static String displayPrompt(String prompt, String description) {
        System.out.format("%s %s: ", prompt, IO.applyStyle(description, "fg-yellow italic"));

        return scanner.nextLine();
    }

    public static String displayPause() {
        System.out.print(IO.applyStyle("Press Enter key to continue. . .", "fg-yellow italic"));

        return scanner.nextLine();
    }

    // Styles
    public static String applyStyle(String string, String styles) {
        StringBuilder styleCodes = new StringBuilder();

        for (String style : styles.split(" ")) {
            String styleCode;

            switch (style) {
                case "bold":
                    styleCode = "\033[1m";
                    break;
                case "italic":
                    styleCode = "\033[3m";
                    break;
                case "underline":
                    styleCode = "\033[4m";
                    break;
                case "fg-black":
                    styleCode = "\033[30m";
                    break;
                case "fg-red":
                    styleCode = "\033[31m";
                    break;
                case "fg-green":
                    styleCode = "\033[32m";
                    break;
                case "fg-yellow":
                    styleCode = "\033[33m";
                    break;
                case "fg-blue":
                    styleCode = "\033[34m";
                    break;
                case "fg-magenta":
                    styleCode = "\033[35m";
                    break;
                case "fg-cyan":
                    styleCode = "\033[36m";
                    break;
                case "fg-white":
                    styleCode = "\033[37m";
                    break;
                case "bg-red":
                    styleCode = "\033[41m";
                    break;
                case "bg-green":
                    styleCode = "\033[42m";
                    break;
                case "bg-yellow":
                    styleCode = "\033[43m";
                    break;
                case "bg-blue":
                    styleCode = "\033[44m";
                    break;
                case "bg-magenta":
                    styleCode = "\033[45m";
                    break;
                case "bg-cyan":
                    styleCode = "\033[46m";
                    break;
                case "bg-white":
                    styleCode = "\033[47m";
                    break;
                default:
                    styleCode = "";
            }
            styleCodes.append(styleCode);
        }

        return String.format("%s%s\033[0m", styleCodes.toString(), string);
    }
}