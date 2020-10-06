package io.quarkus.benchmark.kafka;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.OnOverflow;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import io.reactivex.Flowable;

@ApplicationScoped
public class Producer {
   @Inject
   @Channel("imperative")
   @OnOverflow(value = OnOverflow.Strategy.DROP)
   Emitter<Bottle> imperativeEmitter;

   @PostConstruct
   public void init() {
      new Thread(() -> {
         ThreadLocalRandom random = ThreadLocalRandom.current();
         while (true) {
            // Wait until we have a subscriber
            while (imperativeEmitter.isCancelled()) {
               try {
                  Thread.sleep(100);
               } catch (InterruptedException e) {
                  System.out.println("Interrupted");
                  return;
               }
            }
            try {
               while (true) {
                  imperativeEmitter.send(Bottle.random(random));
               }
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      }, "imperative-emitter").start();
   }

   @Outgoing("not-throttled")
   public Flowable<Bottle> notThrottled() {
      ThreadLocalRandom random = ThreadLocalRandom.current();
      return Flowable.generate(emitter -> emitter.onNext(Bottle.random(random)));
   }
}
