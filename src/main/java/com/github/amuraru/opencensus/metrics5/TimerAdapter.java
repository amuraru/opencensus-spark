package com.github.amuraru.opencensus.metrics5;

import com.codahale.metrics.Timer;

class TimerAdapter extends io.dropwizard.metrics5.Timer {

  private final Timer delegate;

  TimerAdapter(Timer delegate) {
    this.delegate = delegate;
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
    return SnapshotUtils.of(this.delegate.getSnapshot());
  }
}
