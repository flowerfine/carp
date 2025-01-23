package cn.sliew.carp.module.orca.spinnaker.kork.expressions.allowlist;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

public class InstantiationTypeRestrictor {
    private Set<Class<?>> allowedTypes =
            Collections.unmodifiableSet(
                    new HashSet<>(
                            Arrays.asList(
                                    String.class,
                                    Date.class,
                                    Integer.class,
                                    Long.class,
                                    Double.class,
                                    Byte.class,
                                    SimpleDateFormat.class,
                                    Math.class,
                                    Random.class,
                                    UUID.class,
                                    Boolean.class,
                                    LocalDate.class,
                                    LocalDateTime.class,
                                    DayOfWeek.class,
                                    Instant.class,
                                    ChronoUnit.class,
                                    URLEncoder.class,
                                    TemporalAdjusters.class)));

    boolean supports(Class<?> type) {
        return allowedTypes.contains(type);
    }
}
