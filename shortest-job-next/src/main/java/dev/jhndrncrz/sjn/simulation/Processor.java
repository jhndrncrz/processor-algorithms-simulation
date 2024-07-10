package dev.jhndrncrz.sjn.simulation;

import java.util.List;

public class Processor {
  private int currentTime;

  public Processor() {
    currentTime = 0;
  }

  public int getCurrentTime() {
    return this.currentTime;
  }

  public void setCurrentTime(int currentTime) {
    if (currentTime < this.currentTime) {
      // throw new Exception();
    }
    
    this.currentTime = currentTime;
  }

  public void incrementCurrentTime(int deltaTime) {
    if (deltaTime < 0) {
      // throw new Exception();
    }

    this.currentTime += deltaTime;
  }

  public void executeProcess(Process process, List<Process> timelineProcesses) {
    if (this.getCurrentTime() < process.getArrivalTime()) {
      Process idleMockProcess = new Process("IDLE", 0, 0);
      idleMockProcess.setCompletionTime(process.getArrivalTime());
      timelineProcesses.add(idleMockProcess);
      this.setCurrentTime(process.getArrivalTime());
    }
    
    this.incrementCurrentTime(process.getBurstTime());
    
    process.setCompletionTime(currentTime);
    process.setWaitingTime(currentTime - process.getBurstTime() - process.getArrivalTime());
    process.setTurnaroundTime(currentTime - process.getArrivalTime());
    timelineProcesses.add(process);
  }
}