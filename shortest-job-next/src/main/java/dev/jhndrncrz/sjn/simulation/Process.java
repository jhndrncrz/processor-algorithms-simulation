package dev.jhndrncrz.sjn.simulation;

import java.util.Comparator;

public class Process {
  private final String name;
  private final int arrivalTime;
  private final int burstTime;
  private int completionTime;
  private int waitingTime;
  private int turnaroundTime;

  public Process(String name, int arrivalTime, int burstTime) {
    this.name = name;
    this.arrivalTime = arrivalTime;
    this.burstTime = burstTime;
  }

  public String getName() {
    return this.name;
  }

  public int getArrivalTime() {
    return this.arrivalTime;
  } 

  public int getBurstTime() {
    return this.burstTime;
  } 

  public int getCompletionTime() {
    return this.completionTime;
  } 

  public int getWaitingTime() {
    return this.waitingTime;
  } 
  
  public int getTurnaroundTime() {
    return this.turnaroundTime;
  } 

  public void setCompletionTime(int completionTime) {
    this.completionTime = completionTime;
  }
  
  public void setWaitingTime(int waitingTime) {
    this.waitingTime = waitingTime;
  }  

  public void setTurnaroundTime(int turnaroundTime) {
    this.turnaroundTime = turnaroundTime;
  }  
}

class ProcessNameComparator implements Comparator<Process> {
  public int compare(Process process1, Process process2) {
      return process1.getName().compareTo(process2.getName());
  }
}

class ProcessArrivalTimeComparator implements Comparator<Process> {
  public int compare(Process process1, Process process2) {
      return Integer.compare(process1.getArrivalTime(), process2.getArrivalTime());
  }
}

class ProcessBurstTimeComparator implements Comparator<Process> {
  public int compare(Process process1, Process process2) {
      return Integer.compare(process1.getBurstTime(), process2.getBurstTime());
  }
}

class ProcessCompletionTimeComparator implements Comparator<Process> {
  public int compare(Process process1, Process process2) {
      return Integer.compare(process1.getCompletionTime(), process2.getCompletionTime());
  }
}