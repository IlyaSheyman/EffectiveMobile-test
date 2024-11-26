package effective.mobile.test.constants;

public enum TaskFilter {
    ALL,
    PENDING,
    IN_PROGRESS,
    COMPLETED,
    HIGH_PRIORITY,
    MEDIUM_PRIORITY,
    LOW_PRIORITY;

    public static TaskFilter fromString(String filter) {
        try {
            return TaskFilter.valueOf(filter.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ALL;
        }
    }
}
