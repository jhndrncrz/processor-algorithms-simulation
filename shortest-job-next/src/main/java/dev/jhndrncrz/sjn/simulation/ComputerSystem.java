package dev.jhndrncrz.sjn.simulation;

import java.util.List;
import java.util.ArrayList;

import dev.jhndrncrz.util.*;

public class ComputerSystem {
  private final List<Process> processes;
  private final List<Process> timelineProcesses;
  private final ProcessScheduler processScheduler;
  private final Processor processor;

  public ComputerSystem() {
    this.processes = new ArrayList<Process>();
    this.timelineProcesses = new ArrayList<Process>();
    this.processScheduler = new ProcessScheduler();
    this.processor = new Processor();
  }

  public void inputProcesses() {
    int processesCount = 0;

    while (true) {
      try {
        Terminal.clearLine();
        String processesCountInput = IO.displayPrompt("Enter the", "number of processes");
        processesCount = Integer.parseInt(processesCountInput);

        if (processesCount <= 0) {
          throw new Exception();
        }

        break;
      } catch (Exception e) {
        IO.displayError("Please enter a valid value for the number of processes.");
        IO.displayInfo("Number of processes must be a positive integer.");
        Terminal.cursorPreviousLine(3);

        IO.isLastInputValid = false;
      }
    }

    if (!IO.isLastInputValid) {
      Terminal.clearLine();
      Terminal.cursorNextLine(1);
      Terminal.clearLine();
      Terminal.cursorPreviousLine(1);

      IO.isLastInputValid = true;
    }

    IO.isLastInputValid = true;
    
    IO.displaySpacer(1);

    for (int i = 0; i < processesCount; ++i) {
      int arrivalTime;
      int burstTime;

      IO.displaySubtitle(String.format("Process %d", i + 1));
      while (true) {
        try {
          Terminal.clearLine();
          String arrivalTimeInput = IO.displayPrompt("\tEnter the", IO.applyStyle(String.format("arrival time of P%d", i + 1), "fg-magenta"));
          arrivalTime = Integer.parseInt(arrivalTimeInput);

          if (arrivalTime < 0) {
            throw new Exception();
          }

          break;
        } catch (Exception e) {
          IO.displayError(String.format("Please enter a valid value for the %s.", IO.applyStyle(String.format("arrival time of P%d", i + 1), "fg-magenta")));
          IO.displayInfo(String.format("%s must be a non-negative integer.", IO.applyStyle("Arrival time", "fg-magenta italic")));
          Terminal.cursorPreviousLine(3);

          IO.isLastInputValid = false;
        }
      }

      if (!IO.isLastInputValid) {
        Terminal.clearLine();
        Terminal.cursorNextLine(1);
        Terminal.clearLine();
        Terminal.cursorPreviousLine(1);

        IO.isLastInputValid = true;
      }

      while (true) {
        try {
          Terminal.clearLine();
          String burstTimeInput = IO.displayPrompt("\tEnter the", IO.applyStyle(String.format("burst time of P%d", i + 1), "fg-cyan"));
          burstTime = Integer.parseInt(burstTimeInput);

          if (burstTime <= 0) {
            throw new Exception();
          }

          break;
        } catch (Exception e) {
          IO.displayError(String.format("Please enter a valid value for the %s.", IO.applyStyle(String.format("burst time of P%d", i + 1), "fg-cyan")));
          IO.displayInfo(String.format("%s must be a non-negative integer.", IO.applyStyle("Burst time", "fg-cyan italic")));
          Terminal.cursorPreviousLine(3);
          
          IO.isLastInputValid = false;
        }
      }

      if (!IO.isLastInputValid) {
        Terminal.clearLine();
        Terminal.cursorNextLine(1);
        Terminal.clearLine();
        Terminal.cursorPreviousLine(1);

        IO.isLastInputValid = true;
      }

      this.processes.add(new Process(String.format("P%d", i + 1), arrivalTime, burstTime));
    }
  }

  public void showProcessesList() {
    IO.displayTitle("Process List");
    IO.display(IO.applyStyle("+==============+=======+=======+", "bold"));
    IO.display(IO.applyStyle("| Process Name |    AT |    BT |", "bold"));
    IO.display(IO.applyStyle("+==============+=======+=======+", "bold"));
    for (Process process : processes) {
      IO.display(
          String.format("| %12s | %5s | %5s |", process.getName(), process.getArrivalTime(), process.getBurstTime()));
    }
    IO.display("+==============+=======+=======+");
  }

  public void simulateProcessScheduling() {
    List<Process> incomingProcesses;
    
    incomingProcesses = new ArrayList<Process>(this.processes);
    incomingProcesses.sort(new ProcessArrivalTimeComparator());
  
    while (incomingProcesses.size() > 0) {
      this.processScheduler.scheduleProcesses(this.processor, incomingProcesses, timelineProcesses);
    }

    timelineProcesses.sort(new ProcessCompletionTimeComparator());
    
    for (int i = 0; i < timelineProcesses.size(); ++i) {
      IO.displayHeader();

      IO.displayTitle("Process Schedule Table");
      IO.display(IO.applyStyle("+==============+=======+=======+=======+=======+=======+", "bold"));
      IO.display(IO.applyStyle("| Process Name |    AT |    BT |    CT |    WT |   TAT |", "bold"));
      IO.display(IO.applyStyle("+==============+=======+=======+=======+=======+=======+", "bold"));
      for (int j = 0; j < processes.size(); ++j) {
        Process process = processes.get(j);

        if (process.equals(timelineProcesses.get(i))) {
            IO.display("|" + IO.applyStyle(String.format(" %12s ", process.getName()), "fg-green") + String.format("| %5s | %5s | %5s | %5s | %5s ", process.getArrivalTime(),
              process.getBurstTime(), process.getCompletionTime(), process.getWaitingTime(), process.getTurnaroundTime()) + "|");
        } else if (timelineProcesses.subList(0, i + 1).contains(process)) {
            IO.display(String.format("| %12s | %5s | %5s | %5s | %5s | %5s |", process.getName(), process.getArrivalTime(),
              process.getBurstTime(), process.getCompletionTime(), process.getWaitingTime(), process.getTurnaroundTime()));
        } 
        else {
            IO.display(String.format("| %12s | %5s | %5s | %5s | %5s | %5s |", process.getName(), process.getArrivalTime(),
              process.getBurstTime(), "--", "--", "--"));
        }
      }
      IO.display("+==============+=======+=======+=======+=======+=======+");

      IO.displaySpacer(1);
      
      IO.displayTitle("Timeline");
      
      StringBuilder timeline = new StringBuilder();
      StringBuilder timelineLabel = new StringBuilder();

      timeline.append("Completion Time | ");
      timelineLabel.append("   Process Name | ");

      timeline.append(String.format("%-8d", 0));
      for (int j = 0; j < timelineProcesses.size(); ++j) {
        if (j <= i) {
          timeline.append(String.format("%-8d", timelineProcesses.get(j).getCompletionTime()));
        } else {
          timeline.append(String.format("%-8s", "--"));
        }
      }
      
      for (int j = 0; j < timelineProcesses.size(); ++j) {
        if (j < i) {
          timelineLabel.append(String.format("| %5s ", timelineProcesses.get(j).getName()));
        } else if (j == i) {
          timelineLabel.append("|");
          timelineLabel.append(IO.applyStyle(String.format(" %5s ", timelineProcesses.get(j).getName()), "fg-green"));
        } else {
          timelineLabel.append(String.format("| %5s ", "--"));
        }
      }
      timelineLabel.append("|");

      IO.display(timeline.toString());
      IO.display(timelineLabel.toString());

      IO.displayFooter();
    }
  }

  public void summarizeProcessSchedule() {
    List<Process> outgoingProcesses = new ArrayList<Process>(this.processes);
    outgoingProcesses.sort(new ProcessNameComparator());
    
    double averageTurnaroundTime = 0;
    double averageWaitingTime = 0;

    IO.displayTitle("Process Schedule Summary");
    IO.display(IO.applyStyle("+==============+=======+=======+=======+=======+=======+", "bold"));
    IO.display(IO.applyStyle("| Process Name |    AT |    BT |    CT |    WT |   TAT |", "bold"));
    IO.display(IO.applyStyle("+==============+=======+=======+=======+=======+=======+", "bold"));
    for (Process process : outgoingProcesses) {
      averageWaitingTime += process.getWaitingTime();
      averageTurnaroundTime += process.getTurnaroundTime();
      IO.display(String.format("| %12s | %5s | %5s | %5s | %5s | %5s |", process.getName(), process.getArrivalTime(),
          process.getBurstTime(), process.getCompletionTime(), process.getWaitingTime(), process.getTurnaroundTime()));
    }
    IO.display("+==============+=======+=======+=======+=======+=======+");

    IO.displaySpacer(1);

    IO.displayTitle("Timeline");

    StringBuilder timeline = new StringBuilder();
    StringBuilder timelineLabel = new StringBuilder();

    timeline.append("Completion Time | ");
    timelineLabel.append("   Process Name | ");

    timeline.append(String.format("%-8d", 0));
    for (int i = 0; i < timelineProcesses.size(); ++i) {
      timeline.append(String.format("%-8d", timelineProcesses.get(i).getCompletionTime()));
    }

    for (int i = 0; i < timelineProcesses.size(); ++i) {
      timelineLabel.append(String.format("| %5s ", timelineProcesses.get(i).getName()));
    }
    timelineLabel.append("|");

    IO.display(timeline.toString());
    IO.display(timelineLabel.toString());
    
    IO.displaySpacer(1);
    
    averageWaitingTime /= processes.size();
    averageTurnaroundTime /= processes.size();

    IO.displayLabelled("Average Waiting Time", String.format("%.2f", averageWaitingTime));
    IO.displayLabelled("Average Turnaround Time",  String.format("%.2f", averageTurnaroundTime));
  }
}