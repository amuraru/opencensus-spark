package com.github.amuraru.opencensus.metrics5;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import io.dropwizard.metrics5.Metric;
import io.dropwizard.metrics5.MetricName;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

public class Metrics5Adapter extends io.dropwizard.metrics5.MetricRegistry {

  private final MetricRegistry codaHaleRegistry;

  public Metrics5Adapter(MetricRegistry codaHaleRegistry) {
    this.codaHaleRegistry = codaHaleRegistry;
  }

  @Override
  public Map<MetricName, Metric> getMetrics() {
    throw new IllegalStateException("Not implemented");
  }

  private <T extends com.codahale.metrics.Metric> SortedMap<String, T> getMetrics(
      Class<T> klass, MetricFilter filter) {
    final TreeMap<String, T> metrics = new TreeMap<>();
    for (Map.Entry<String, com.codahale.metrics.Metric> entry :
        codaHaleRegistry.getMetrics().entrySet()) {
      com.codahale.metrics.Metric value = entry.getValue();
      if (klass.isInstance(entry.getValue()) && filter.matches(entry.getKey(), entry.getValue())) {
        metrics.put(entry.getKey(), (T) entry.getValue());
      }
    }
    return Collections.unmodifiableSortedMap(metrics);
  }

  @Override
  public SortedMap<MetricName, io.dropwizard.metrics5.Gauge> getGauges() {
    return this.getGauges(io.dropwizard.metrics5.MetricFilter.ALL);
  }

  @Override
  public SortedMap<MetricName, io.dropwizard.metrics5.Gauge> getGauges(
      io.dropwizard.metrics5.MetricFilter filter) {

    MetricFilter f = (name, metric) -> filter.matches(MetricName.build(name), null);
    SortedMap<String, Gauge> gauges = this.getMetrics(Gauge.class, f);
    SortedMap<MetricName, io.dropwizard.metrics5.Gauge> results = new TreeMap<>();
    MetricName name;
    io.dropwizard.metrics5.Gauge gauge;
    for (Entry<String, Gauge> entry : gauges.entrySet()) {
      name = MetricName.build(entry.getKey());
      final Object value = entry.getValue().getValue();
      if (value instanceof Number) {
        gauge = (io.dropwizard.metrics5.Gauge<Number>) () -> (Number) value;
      } else if (value instanceof Boolean) {
        gauge = (io.dropwizard.metrics5.Gauge<Boolean>) () -> (Boolean) value;
      } else {
        gauge = null;
      }

      if (gauge != null) {
        results.put(name, gauge);
      }
    }
    return results;
  }

  @Override
  public SortedMap<MetricName, io.dropwizard.metrics5.Counter> getCounters() {
    return this.getCounters(io.dropwizard.metrics5.MetricFilter.ALL);
  }

  @Override
  public SortedMap<MetricName, io.dropwizard.metrics5.Counter> getCounters(
      io.dropwizard.metrics5.MetricFilter filter) {
    MetricFilter f = (name, metric) -> filter.matches(MetricName.build(name), null);
    SortedMap<String, Counter> counters = this.getMetrics(Counter.class, f);
    SortedMap<MetricName, io.dropwizard.metrics5.Counter> results = new TreeMap<>();
    MetricName name;
    io.dropwizard.metrics5.Counter counter;
    for (Entry<String, Counter> entry : counters.entrySet()) {
      name = MetricName.build(entry.getKey());
      final long value = entry.getValue().getCount();
      counter = new io.dropwizard.metrics5.Counter();
      counter.inc(value);
      results.put(name, counter);
    }
    return results;
  }

  @Override
  public SortedMap<MetricName, io.dropwizard.metrics5.Meter> getMeters() {
    return this.getMeters(io.dropwizard.metrics5.MetricFilter.ALL);
  }

  @Override
  public SortedMap<MetricName, io.dropwizard.metrics5.Meter> getMeters(
      io.dropwizard.metrics5.MetricFilter filter) {
    MetricFilter f = (name, metric) -> filter.matches(MetricName.build(name), null);
    SortedMap<String, Meter> meters = this.getMetrics(Meter.class, f);
    SortedMap<MetricName, io.dropwizard.metrics5.Meter> results = new TreeMap<>();
    MetricName name;
    io.dropwizard.metrics5.Meter meter;
    for (Entry<String, Meter> entry : meters.entrySet()) {
      name = MetricName.build(entry.getKey());
      final long value = entry.getValue().getCount();
      meter = new io.dropwizard.metrics5.Meter();
      meter.mark(value);
      results.put(name, meter);
    }
    return results;
  }

  @Override
  public SortedMap<MetricName, io.dropwizard.metrics5.Histogram> getHistograms() {
    return this.getHistograms(io.dropwizard.metrics5.MetricFilter.ALL);
  }

  @Override
  public SortedMap<MetricName, io.dropwizard.metrics5.Histogram> getHistograms(
      io.dropwizard.metrics5.MetricFilter filter) {
    MetricFilter f = (name, metric) -> filter.matches(MetricName.build(name), null);
    SortedMap<String, Histogram> histograms = this.getMetrics(Histogram.class, f);
    SortedMap<MetricName, io.dropwizard.metrics5.Histogram> results = new TreeMap<>();
    MetricName name;
    io.dropwizard.metrics5.Histogram histogram;
    for (Entry<String, Histogram> entry : histograms.entrySet()) {
      name = MetricName.build(entry.getKey());
      final Histogram value = entry.getValue();
      histogram = new HistogramAdapter(value);
      results.put(name, histogram);
    }
    return results;
  }

  @Override
  public SortedMap<MetricName, io.dropwizard.metrics5.Timer> getTimers() {
    return this.getTimers(io.dropwizard.metrics5.MetricFilter.ALL);
  }

  @Override
  public SortedMap<MetricName, io.dropwizard.metrics5.Timer> getTimers(
      io.dropwizard.metrics5.MetricFilter filter) {
    MetricFilter f = (name, metric) -> filter.matches(MetricName.build(name), null);
    SortedMap<String, Timer> timers = this.getMetrics(Timer.class, f);
    SortedMap<MetricName, io.dropwizard.metrics5.Timer> results = new TreeMap<>();
    MetricName name;
    io.dropwizard.metrics5.Timer timer;
    for (Entry<String, Timer> entry : timers.entrySet()) {
      name = MetricName.build(entry.getKey());
      final Timer value = entry.getValue();
      timer = new TimerAdapter(value);
      results.put(name, timer);
    }
    return results;
  }
}
