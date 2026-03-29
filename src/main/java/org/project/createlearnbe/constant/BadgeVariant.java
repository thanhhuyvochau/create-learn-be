package org.project.createlearnbe.constant;

public enum BadgeVariant {
  PRIMARY("primary"),
  SECONDARY("secondary"),
  TERTIARY("tertiary");

  private final String displayValue;

  BadgeVariant(String displayValue) {
    this.displayValue = displayValue;
  }

  public String getDisplayValue() {
    return displayValue;
  }
}
