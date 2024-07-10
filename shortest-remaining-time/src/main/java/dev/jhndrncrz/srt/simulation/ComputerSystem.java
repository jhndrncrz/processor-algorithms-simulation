package dev.jhndrncrz.srt.simulation;

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
    IO.display("+==============+============+==========+==============+");
    IO.display("| Process Name | Arrival T. | Burst T. | Remaining T. |");
    IO.display("+==============+============+==========+==============+");
    
    for (Process process : processes) {
      IO.display(
          String.format("| %12s | %10s | %8s | %12s |", process.getName(), process.getArrivalTime(), process.getBurstTime(), process.getRemainingTimesString()));
    }
    
    IO.display("+==============+============+==========+==============+");
  }

  public void simulateProcessScheduling() {
    List<Process> incomingProcesses;
    
    incomingProcesses = new ArrayList<Process>(this.processes);
    incomingProcesses.sort(new ProcessArrivalTimeComparator());
  
    while (incomingProcesses.size() > 0) {
      this.processScheduler.scheduleProcesses(this.processor, incomingProcesses, this.timelineProcesses);
    }

    this.timelineProcesses.sort(new ProcessCompletionTimeComparator());
    
    for (int i = 0; i < this.timelineProcesses.size(); ++i) {
      IO.displayHeader();

      IO.displayTitle("Process Schedule Table");
      
      IO.display("+==============+============+==========+=============+==============+===============+===============+============+");
      IO.display("| Process Name | Arrival T. | Burst T. | Response T. | Remaining T. | Completion T. | Turnaround T. | Waiting T. |");
      IO.display("+==============+============+==========+=============+==============+===============+===============+============+");
      
      for (int j = 0; j < processes.size(); ++j) {
        Process process = processes.get(j);

        if (process.getName().equals(this.timelineProcesses.get(i).getName())) {
          process.incrementInterruptRetrievalCount();
          if (process.getInterruptCount() == process.getInterruptRetrievalCount()) {
            IO.display("|" + IO.applyStyle(String.format(" %12s ", process.getName()), "fg-green") + String.format("| %10s | %8s | %11s | %12s | %13s | %13s | %10s ", process.getArrivalTime(),
            process.getBurstTime(),  process.getResponseTime(), process.getRemainingTimesString(process.getInterruptRetrievalCount()), "--", "--", "--") + "|");
          } else {
            IO.display("|" + IO.applyStyle(String.format(" %12s ", process.getName()), "fg-green") + String.format("| %10s | %8s | %11s | %12s | %13s | %13s | %10s ", process.getArrivalTime(),
            process.getBurstTime(), process.getResponseTime(), process.getRemainingTimesString(process.getInterruptRetrievalCount()), process.getCompletionTime(), process.getTurnaroundTime(), process.getWaitingTime()) + "|");
          }
        } else if (this.timelineProcesses.subList(0, i + 1).stream().map(Process::getName).toList().contains(process.getName())) {
            if (process.getInterruptCount() == process.getInterruptRetrievalCount()) {
                IO.display(String.format("| %12s | %10s | %8s | %11s | %12s | %13s | %13s | %10s |", process.getName(), process.getArrivalTime(),
              process.getBurstTime(), process.getResponseTime(), process.getRemainingTimesString(process.getInterruptRetrievalCount()),  "--", "--", "--"));
            } else {
              IO.display(String.format("| %12s | %10s | %8s | %11s | %12s | %13s | %13s | %10s |", process.getName(), process.getArrivalTime(),
              process.getBurstTime(), process.getResponseTime(), process.getRemainingTimesString(process.getInterruptRetrievalCount()), process.getCompletionTime(), process.getTurnaroundTime(), process.getWaitingTime()));
            }
            
        } 
        else {
            IO.display(String.format("| %12s | %10s | %8s | %11s | %12s | %13s | %13s | %10s |", process.getName(), process.getArrivalTime(),
              process.getBurstTime(), "--", "--", "--", "--", "--"));
        }
      }
      
      IO.display("+==============+============+==========+=============+==============+===============+===============+============+");

      IO.displaySpacer(1);
      
      IO.displayTitle("Timeline");

      StringBuilder timeline = new StringBuilder();
      StringBuilder timelineLabel = new StringBuilder();

      timeline.append("           Time | ");
      timelineLabel.append("   Process Name | ");

      timeline.append(String.format("%-8d", 0));
      for (int j = 0; j < this.timelineProcesses.size(); ++j) {
        if (j <= i) {
          timeline.append(String.format("%-8d", this.timelineProcesses.get(j).getCompletionTime()));
        } else {
          timeline.append(String.format("%-8s", "--"));
        }
      }

      for (int j = 0; j < this.timelineProcesses.size(); ++j) {
        if (j < i) {
          timelineLabel.append(String.format("| %5s ", this.timelineProcesses.get(j).getName()));
        } else if (j == i) {
          timelineLabel.append("|");
          timelineLabel.append(IO.applyStyle(String.format(" %5s ", this.timelineProcesses.get(j).getName()), "fg-green"));
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
    
    IO.display("+==============+============+==========+=============+==============+===============+===============+============+");
    IO.display("| Process Name | Arrival T. | Burst T. | Response T. | Remaining T. | Completion T. | Turnaround T. | Waiting T. |");
    IO.display("+==============+============+==========+=============+==============+===============+===============+============+");
    
    for (Process process : outgoingProcesses) {
      averageTurnaroundTime += process.getTurnaroundTime();
      averageWaitingTime += process.getWaitingTime();
      
      IO.display(String.format("| %12s | %10s | %8s | %11s | %12s | %13s | %13s | %10s |", process.getName(), process.getArrivalTime(),
          process.getBurstTime(), process.getResponseTime(), process.getRemainingTimesString(), process.getCompletionTime(), process.getTurnaroundTime(), process.getWaitingTime()));
    }
    
    IO.display("+==============+============+==========+=============+==============+===============+===============+============+");

    IO.displaySpacer(1);

    IO.displayTitle("Timeline");

    StringBuilder timeline = new StringBuilder();
    StringBuilder timelineLabel = new StringBuilder();

    timeline.append("           Time | ");
    timelineLabel.append("   Process Name | ");

    timeline.append(String.format("%-8d", 0));
    for (Process timelineProcess : this.timelineProcesses) {
      timeline.append(String.format("%-8d", timelineProcess.getCompletionTime()));
    }

    for (Process timelineProcess : this.timelineProcesses) {
      timelineLabel.append(String.format("| %5s ", timelineProcess.getName()));
    }
    timelineLabel.append("|");

    IO.display(timeline.toString());
    IO.display(timelineLabel.toString());
    
    IO.displaySpacer(1);

    averageTurnaroundTime /= processes.size();
    averageWaitingTime /= processes.size();

    IO.displayLabelled("Average Turnaround Time",  String.format("%.2f", averageTurnaroundTime));
    IO.displayLabelled("Average Waiting Time", String.format("%.2f", averageWaitingTime));
  }
}