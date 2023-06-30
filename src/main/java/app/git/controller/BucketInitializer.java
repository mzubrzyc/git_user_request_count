package app.git.controller;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BucketInitializer {

    public Bucket initGitBucket() {
        return Bucket.builder()
                     .addLimit(Bandwidth.classic(600, Refill.intervally(300, Duration.of(500, ChronoUnit.MILLIS))))
                     .build();
    }
}
