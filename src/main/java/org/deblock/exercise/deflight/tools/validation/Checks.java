package org.deblock.exercise.deflight.tools.validation;

import org.deblock.exercise.deflight.exception.BadRequestException;

import java.time.LocalDate;

public class Checks {
    private static final String LENGTH_INVALID = "%s length must be equal to %s";
    private static final String VALUE_NOT_IN_RANGE = "%s must be in range [%s,%s]";
    private static final String FROM_AFTER_TO = "%s cannot be after %s";

    public static String exactLength(String value, int exactLength, String fieldName) {
        if (value == null || value.length() != exactLength) {
            throw new BadRequestException(String.format(LENGTH_INVALID, fieldName, exactLength));
        }
        return value;
    }

    public static int valueInRange(int value, int minValue, int maxValue, String fieldName) {
        if (value <= minValue || value > maxValue) {
            throw new BadRequestException(String.format(VALUE_NOT_IN_RANGE, fieldName, minValue, maxValue));
        }
        return value;
    }

    public static void fromNotAfterTo(LocalDate from, LocalDate to, String fromFieldName, String toFieldName) {
        if (from == null || to == null || from.isAfter(to)) {
            throw new BadRequestException(String.format(FROM_AFTER_TO, fromFieldName, toFieldName));
        }
    }
}
