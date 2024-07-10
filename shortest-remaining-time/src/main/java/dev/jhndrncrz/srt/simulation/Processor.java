package dev.jhndrncrz.srt.simulation;

import java.util.List;

import dev.jhndrncrz.util.IO;

public class Processor {
  private int currentTime;

  public Processor() {
    currentTime = 0;
  }

  public int getCurrentTime() {
    return this.currentTime;
  }

  public void setCurrentTime(int currentTime) {
    this.currentTime = currentTime;
  }

  public void incrementCurrentTime(int deltaTime) {
    this.currentTime += deltaTime;
  }

  public void executeProcess(Process process, List<Process> incomingProcesses, List<Process> waitingProcesses,
      List<Process> timelineProcesses) {
    if (this.getCurrentTime() < process.getArrivalTime()) {
      Process idleMockProcess = new Process("IDLE", 0, 0);
      idleMockProcess.setCompletionTime(process.getArrivalTime());
      timelineProcesses.add(idleMockProcess);

      this.setCurrentTime(process.getArrivalTime());
    }

    Process interruptingProcess = null;

    for (Process incomingProcess : incomingProcesses) {
        if (incomingProcess.getArrivalTime() < this.getCurrentTime() + process.getRemainingTime()
          && incomingProcess.getRemainingTime() < process.getRemainingTime() - (incomingProcess.getArrivalTime() - this.getCurrentTime())) {
          interruptingProcess = incomingProcess;
          break;
        }
    }

    if (!process.isResponseTimeSet()) {
      process.setResponseTime(this.getCurrentTime());
    }

    if (interruptingProcess != null) {
      incomingProcesses.remove(interruptingProcess);
      process.decrementRemainingTime(interruptingProcess.getArrivalTime() - this.getCurrentTime());
      process.incrementInterruptCount();
      this.setCurrentTime(interruptingProcess.getArrivalTime());

      waitingProcesses.add(process);
      waitingProcesses.add(0, interruptingProcess);

      Process mockProcess = new Process(process.getName(), 0, 0);
      mockProcess.setCompletionTime(this.getCurrentTime());
      timelineProcesses.add(mockProcess);
    } else {
      this.incrementCurrentTime(process.getRemainingTime());
      process.decrementRemainingTime(process.getRemainingTime());
      process.setCompletionTime(this.getCurrentTime());
      process.setTurnaroundTime(this.getCurrentTime() - process.getArrivalTime());
      process.setWaitingTime(this.getCurrentTime() - process.getArrivalTime() - process.getBurstTime());

      Process mockProcess = new Process(process.getName(), 0, 0);
      mockProcess.setCompletionTime(this.getCurrentTime());
      timelineProcesses.add(mockProcess);
    }
  }
}