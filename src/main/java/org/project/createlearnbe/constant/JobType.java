package org.project.createlearnbe.constant;

public enum JobType {
  FULL_TIME("Full-time"),
  CONTRACT("Contract"),
  PART_TIME("Part-time");

  private final String displayValue;

  JobType(String displayValue) {
    this.displayValue = displayValue;
  }

  public String getDisplayValue() {
    return displayValue;
  }
}
