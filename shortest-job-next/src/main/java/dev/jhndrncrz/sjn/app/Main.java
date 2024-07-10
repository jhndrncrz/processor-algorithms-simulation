package dev.jhndrncrz.sjn.app;

import dev.jhndrncrz.util.*;
import dev.jhndrncrz.sjn.simulation.*;

public class Main {
  public static void main(String[] args) {
    ComputerSystem computerSystem = new ComputerSystem();
    
    IO.displayHeader();
    computerSystem.inputProcesses();
    IO.displayFooter();

    IO.displayHeader();
    computerSystem.showProcessesList();
    IO.displayFooter();

    computerSystem.simulateProcessScheduling();

    IO.displayHeader();
    computerSystem.summarizeProcessSchedule();
    IO.displayFooter();
  }
}