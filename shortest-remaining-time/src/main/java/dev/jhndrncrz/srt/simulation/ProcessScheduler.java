package dev.jhndrncrz.srt.simulation;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

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
      Process process = Collections.min(waitingProcesses, new ProcessRemainingTimeComparator());
      waitingProcesses.remove(process);
      processor.executeProcess(process, incomingProcesses, waitingProcesses, timelineProcesses);

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