package org.qualihub.resume.config;

import java.time.Clock;
import java.time.ZoneId;

public class DateConfig {
    public static Clock PARIS = Clock.system(ZoneId.of("Europe/Paris"));
    public static Clock CLOCK = Clock.system(ZoneId.of("Europe/Paris"));
}
