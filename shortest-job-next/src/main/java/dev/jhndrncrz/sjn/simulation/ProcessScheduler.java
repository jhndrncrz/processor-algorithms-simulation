package dev.jhndrncrz.sjn.simulation;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class ProcessScheduler {
  private final List<Process> waitingProcesses;

  public ProcessScheduler() {
    this.waitingProcesses = new ArrayList<Process>();
  }

  public void scheduleProcesses(Processor processor, List<Process> incomingProcesses, List<Process> timelineProcesses) {
    Process process;

    if (waitingProcesses.size() == 0) {
      process = incomingProcesses.remove(0);
      waitingProcesses.add(process);
    }

    while (waitingProcesses.size() > 0) {
      process = Collections.min(waitingProcesses, new ProcessBurstTimeComparator());
      waitingProcesses.remove(process);
      processor.executeProcess(process, timelineProcesses);

      while (incomingProcesses.size() > 0) {
        if (incomingProcesses.get(0).getArrivalTime() <= processor.getCurrentTime()) {
          waitingProcesses.add(incomingProcesses.remove(0));
        } else {
          break;
        }
      }
    }
  }
}