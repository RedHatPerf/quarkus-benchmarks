package io.quarkus.benchmark.kafka;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import io.vertx.core.json.JsonObject;

@ApplicationScoped
@Path("/consumer")
public class Consumer {
   private final Stats notThrottledStats = new Stats();
   private final Stats imperativeStats = new Stats();

   @Incoming("not-throttled")
   public void notThrottled(Bottle bottle) {
      notThrottledStats.addOne();
   }

   @Incoming("imperative")
   public void imperative(Bottle bottle) {
      imperativeStats.addOne();
   }

   @GET
   @Path("not-throttled")
   @Produces("application/json")
   public JsonObject notThrottledStats() {
      return notThrottledStats.fetchAndReset();
   }

   @GET
   @Path("imperative")
   @Produces("application/json")
   public JsonObject imperativeStats() {
      return imperativeStats.fetchAndReset();
   }
}
