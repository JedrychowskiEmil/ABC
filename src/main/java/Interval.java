public final class Interval {

  private final int start;
  private final int end;

  private final int length;

  public Interval(int start, int end) {
    if (end < start) {
      String message = "interval <  0";
      throw new IllegalArgumentException(String.format(message, start, end));
    }

    this.start = start;
    this.end = end;

    length = end - start;
  }

  public int getEnd() {
    return end;
  }

  public int getLength() {
    return length;
  }

  @Override
  public String toString() {
    return "<" + start + "; " + end + ">";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Interval interval = (Interval) o;

    return end == interval.end && start == interval.start;
  }

  @Override
  public int hashCode() {
    int result = start;
    result = 31 * result + end;
    return result;
  }

}
