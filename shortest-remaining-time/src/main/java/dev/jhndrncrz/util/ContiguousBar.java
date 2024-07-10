package dev.jhndrncrz.util;

import java.util.List;
import java.util.ArrayList;

class ContiguousBar {
  private final static class Slice {
    public final String label;
    public final int value;

    public Slice(String label, int value) {
      this.label = label;
      this.value = value;
    }
  }

  private final List<Slice> slices;
  private final int scaledWidth;
  private int trueWidth;

  public ContiguousBar(int scaledWidth) {
    this.slices = new ArrayList<Slice>();
    this.scaledWidth = scaledWidth;
    this.trueWidth = 0;
  }

  public void add(String label, int value) {
    this.slices.add(new Slice(label, value));
    this.trueWidth += value;
  }

  public String toString() {
    final StringBuilder stringBuilder = new StringBuilder();
    final List<Integer> distances = new ArrayList<Integer>();
    final double scalingFactor = scaledWidth / trueWidth;

    for (Slice slice : slices) {
      distances.add((int) (slice.value * scalingFactor));
    }

    for (int i = 0; i < distances.size(); ++i) {
      String color = "";

      switch (i % 5) {
        case 0:
          color = "fg-red";
          break;
        case 1:
          color = "fg-green";
          break;
        case 2:
          color = "fg-yellow";
          break;
        case 3:
          color = "fg-blue";
          break;
        case 4:
          color = "fg-magenta";
          break;
      }

      stringBuilder.append(IO.applyStyle("█".repeat(distances.get(i)), color));
    }
    stringBuilder.append("\n");

    for (int i = 0; i < distances.size(); ++i) {
      String color = "";

      switch (i % 5) {
        case 0:
          color = "fg-red";
          break;
        case 1:
          color = "fg-green";
          break;
        case 2:
          color = "fg-yellow";
          break;
        case 3:
          color = "fg-blue";
          break;
        case 4:
          color = "fg-magenta";
          break;
      }

      stringBuilder.append(" ".repeat(distances.subList(0, i).stream().mapToInt(Integer::intValue).sum()));
      stringBuilder.append(IO.applyStyle(String.format("%s: %d\n", slices.get(i).label, slices.get(i).value), color));
    }

    return stringBuilder.toString();
  }
}