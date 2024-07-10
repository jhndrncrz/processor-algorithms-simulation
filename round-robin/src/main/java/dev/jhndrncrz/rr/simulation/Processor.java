package dev.jhndrncrz.rr.simulation;

import java.util.List;
import java.util.ArrayList;

public class Processor {
  private int timeSlice;
  private int currentTime;

  public Processor() {
    this.currentTime = 0;
  }

  public int getTimeSlice() {
    return this.timeSlice;
  }

  public int getCurrentTime() {
    return this.currentTime;
  }

  public void setTimeSlice(int timeSlice) {
    this.timeSlice = timeSlice;
  }

  public void setCurrentTime(int currentTime) {
    this.currentTime = currentTime;
  }

  public void incrementCurrentTime(int deltaTime) {
    this.currentTime += deltaTime;
  }

  public void executeProcess(Process process, List<Process> timelineProcesses) {
    if (this.getCurrentTime() < process.getArrivalTime()) {
      Process idleMockProcess = new Process("IDLE", 0, 0);
      idleMockProcess.setCompletionTime(process.getArrivalTime());
      timelineProcesses.add(idleMockProcess);

      this.setCurrentTime(process.getArrivalTime());
    }

    if (!process.isResponseTimeSet()) {
      process.setResponseTime(this.getCurrentTime());
    }

    this.incrementCurrentTime(Math.min(process.getRemainingTime(), this.timeSlice));
    process.decrementRemainingTime(Math.min(process.getRemainingTime(), this.timeSlice));

    Process mockProcess = new Process(process.getName(), 0, 0);
    mockProcess.setCompletionTime(this.getCurrentTime());
    timelineProcesses.add(mockProcess);

    if (process.getRemainingTime() == 0) {
      process.setCompletionTime(this.getCurrentTime());
      process.setTurnaroundTime(this.getCurrentTime() - process.getArrivalTime());
      process.setWaitingTime(this.getCurrentTime() - process.getArrivalTime() - process.getBurstTime());
    }
  }
}