package dev.jhndrncrz.srt.simulation;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public class Process {
  private final String name;
  private final int arrivalTime;
  private final int burstTime;
  private final List<Integer> remainingTimes;
  private int remainingTime;
  private boolean isResponseTimeSet;
  private int responseTime;
  private int completionTime;
  private int turnaroundTime;
  private int waitingTime;
  private int interruptCount;
  private int interruptRetrievalCount;

  public Process(String name, int arrivalTime, int burstTime) {
    this.name = name;
    this.arrivalTime = arrivalTime;
    this.burstTime = burstTime;
    this.remainingTime = burstTime;
    this.remainingTimes = new ArrayList<Integer>();
    this.remainingTimes.add(this.remainingTime);
    this.interruptCount = 0;
    this.interruptRetrievalCount = 0;
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

  public List<Integer> getRemainingTimes() {
    return this.remainingTimes;
  } 

  public String getRemainingTimesString() {
    return String.join("/", this.remainingTimes.stream().map(remainingTime -> remainingTime.toString()).toList());
  } 

  public String getRemainingTimesString(int index) {
    return String.join("/", this.remainingTimes.subList(0, this.interruptRetrievalCount + 1).stream().map(remainingTime -> remainingTime.toString()).toList());
  } 

  public int getRemainingTime() {
    return this.remainingTime;
  }

  public int getResponseTime() {
    return this.responseTime;
  }

  public int getCompletionTime() {
    return this.completionTime;
  } 
  
  public int getTurnaroundTime() {
    return this.turnaroundTime;
  }

  public int getWaitingTime() {
    return this.waitingTime;
  } 

  public int getInterruptCount() {
    return this.interruptCount;
  }

  public int getInterruptRetrievalCount() {    
    return this.interruptRetrievalCount;
  }

  public boolean isResponseTimeSet() {
    return this.isResponseTimeSet;
  }

  public void incrementInterruptRetrievalCount() {
    ++this.interruptRetrievalCount;
  }

  public void setRemainingTime(int remainingTime) {
    this.remainingTime = remainingTime;
    this.remainingTimes.add(this.remainingTime);
  }

  public void decrementRemainingTime(int deltaTime) {
    this.remainingTime -= deltaTime;
    this.remainingTimes.add(this.remainingTime);
  }

  public void setResponseTime(int responseTime) {
    this.isResponseTimeSet = true;
    this.responseTime = responseTime;
  }

  public void setCompletionTime(int completionTime) {
    this.completionTime = completionTime;
  }

  public void setTurnaroundTime(int turnaroundTime) {
    this.turnaroundTime = turnaroundTime;
  }
  
  public void setWaitingTime(int waitingTime) {
    this.waitingTime = waitingTime;
  }  

  public void setInterruptCount(int interruptCount) {
    this.interruptCount = interruptCount;
  }

  public void incrementInterruptCount() {
    ++this.interruptCount;
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

class ProcessRemainingTimeComparator implements Comparator<Process> {
  public int compare(Process process1, Process process2) {
      return Integer.compare(process1.getRemainingTime(), process2.getRemainingTime());
  }
}

class ProcessCompletionTimeComparator implements Comparator<Process> {
  public int compare(Process process1, Process process2) {
      return Integer.compare(process1.getCompletionTime(), process2.getCompletionTime());
  }
}