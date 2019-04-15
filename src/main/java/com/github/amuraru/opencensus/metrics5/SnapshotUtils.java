package com.github.amuraru.opencensus.metrics5;

import com.codahale.metrics.Snapshot;
import java.io.OutputStream;

public class SnapshotUtils {

  static io.dropwizard.metrics5.Snapshot of(Snapshot snapshotDelegate) {
    return new io.dropwizard.metrics5.Snapshot() {

      @Override
      public double getValue(double quantile) {
        double d = snapshotDelegate.getValue(quantile);
        return d;
      }

      @Override
      public long[] getValues() {
        return snapshotDelegate.getValues();
      }

      @Override
      public int size() {
        return snapshotDelegate.size();
      }

      @Override
      public long getMax() {
        return snapshotDelegate.getMax();
      }

      @Override
      public double getMean() {
        return snapshotDelegate.getMean();
      }

      @Override
      public long getMin() {
        return snapshotDelegate.getMin();
      }

      @Override
      public double getStdDev() {
        return snapshotDelegate.getStdDev();
      }

      @Override
      public void dump(OutputStream output) {
        snapshotDelegate.dump(output);
      }
    };
  }
}
