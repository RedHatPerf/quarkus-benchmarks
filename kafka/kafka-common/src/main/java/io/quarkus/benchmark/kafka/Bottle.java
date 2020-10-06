package io.quarkus.benchmark.kafka;

import java.util.concurrent.ThreadLocalRandom;

import io.quarkus.kafka.client.serialization.JsonbDeserializer;

public class Bottle {
   private static final String LOREM_IPSUM = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?";
   private static final String[] LIQUORS = new String[] { "slivovice", "rum", "gin", "vodka", "whisky" };

   public String content;
   public double fill;
   public int years;
   public String description;

   public Bottle() {}

   public Bottle(String content, double fill, int years, String description) {
      this.content = content;
      this.fill = fill;
      this.years = years;
      this.description = description;
   }

   public static Bottle random(ThreadLocalRandom random) {
      return new Bottle(LIQUORS[random.nextInt(LIQUORS.length)], random.nextDouble(), random.nextInt(1, 25), LOREM_IPSUM.substring(0, random.nextInt(LOREM_IPSUM.length())));
   }

   public static class Deserializer extends JsonbDeserializer<Bottle> {
      public Deserializer() {
         super(Bottle.class);
      }
   }
}
