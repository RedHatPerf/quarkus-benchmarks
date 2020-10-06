package io.quarkus.benchmark.kafka;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import io.vertx.core.json.JsonObject;

public class Stats {
   private long since = Long.MIN_VALUE;
   private AtomicLong count = new AtomicLong();

   public void addOne() {
      // Using AtomicLong shouldn't be a bottleneck since this method is not called concurrently
      count.incrementAndGet();
   }

   public JsonObject fetchAndReset() {
      long now = System.nanoTime();
      long count;
      long since;
      synchronized (this) {
         since = this.since;
         this.since = now;
         count = this.count.getAndSet(0);
      }

      JsonObject result = new JsonObject();
      result.put("count", count);
      if (since != Long.MIN_VALUE) {
         result.put("duration", now - since);
         result.put("throughput", ((double) (TimeUnit.SECONDS.toNanos(1) * count)) / (now - since));
      }
      return result;
   }
}
