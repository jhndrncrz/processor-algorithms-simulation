package dev.jhndrncrz.rr.simulation;

import java.util.List;
import java.util.ArrayList;

public class ProcessScheduler {
  private final List<Process> waitingProcesses;

  public ProcessScheduler() {
    this.waitingProcesses = new ArrayList<Process>();
  }

  public void scheduleProcesses(Processor processor, List<Process> incomingProcesses, List<Process> timelineProcesses) {
    if (waitingProcesses.size() == 0) {
      waitingProcesses.add(incomingProcesses.remove(0));
    }

    while (waitingProcesses.size() > 0) {
      Process process = waitingProcesses.remove(0);

      List<Process> interruptingProcesses = new ArrayList<Process>();
      
      processor.executeProcess(process, timelineProcesses);

      while (incomingProcesses.size() > 0) {
        if (incomingProcesses.get(0).getArrivalTime() <= processor.getCurrentTime()) {
          interruptingProcesses.add(incomingProcesses.remove(0));
        } else {
          break;
        }
      }

      waitingProcesses.addAll(interruptingProcesses);
      interruptingProcesses.clear();

      if (process.getRemainingTime() != 0) {
        waitingProcesses.add(process);
      }
    }
  }
}