package com.github.amuraru.opencensus.metrics5;

import com.codahale.metrics.Histogram;
import io.dropwizard.metrics5.Reservoir;
import javax.validation.constraints.Null;

class HistogramAdapter extends io.dropwizard.metrics5.Histogram {

  private final Histogram delegate;

  private HistogramAdapter(@Null Reservoir reservoir, Histogram delegate) {
    super(null);
    this.delegate = delegate;
  }

  HistogramAdapter(Histogram delegate) {
    this(null, delegate);
  }

  @Override
  public long getCount() {
    return delegate.getCount();
  }

  @Override
  public long getSum() {
    return -1;
  }

  @Override
  public io.dropwizard.metrics5.Snapshot getSnapshot() {
    return SnapshotUtils.of(delegate.getSnapshot());
  }
}
