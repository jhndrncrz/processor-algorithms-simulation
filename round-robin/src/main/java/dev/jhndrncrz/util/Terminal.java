package dev.jhndrncrz.util;

public class Terminal {
  private Terminal() {
  }

  public static void clear() {
    Terminal.clearScreen();
    // try {
    //   Runtime.getRuntime().exec("clear");
    // } catch (Exception e) {
    // 
    // }
  }

  public static void pause() {
    IO.displayPause();
    // try {
    //   Runtime.getRuntime().exec(String.format("bash -c read -n 1 -s -r -p \"%s\"",
    // IO.applyStyle("Press any key to continue. . .", "italic")));
    // } catch (Exception e) {

    // }
  }

  public static void clearScreen() {
    System.out.print("\033[2J\033[1;1H");
    System.out.flush();
  }

  public static void clearScreen(int n) {
    System.out.format("\033[%dJ\033[1;1H", n);
    System.out.flush();
  }

  public static void clearLine() {
    System.out.print("\033[2K\033[1G");
    System.out.flush();
  }

  public static void clearLine(int n) {
    System.out.format("\033[%dK\033[1G", n);
    System.out.flush();
  }

  public static void cursorUp(int n) {
    System.out.format("\033[%dA", n);
    System.out.flush();
  }

  public static void cursorDown(int n) {
    System.out.format("\033[%dB", n);
    System.out.flush();
  }

  public static void cursorRight(int n) {
    System.out.format("\033[%dC", n);
    System.out.flush();
  }

  public static void cursorLeft(int n) {
    System.out.format("\033[%dD", n);
    System.out.flush();
  }

  public static void cursorNextLine(int n) {
    System.out.format("\033[%dE", n);
    System.out.flush();
  }

  public static void cursorPreviousLine(int n) {
    System.out.format("\033[%dF", n);
    System.out.flush();
  }
}