package cs309.backend.DTOs;

public record SeasonYear(
    int season,
    int year
) {
    public static SeasonYear fromAuditEntry(String season, String year, boolean add2000) {
        return new SeasonYear(seasonStringToInt(season), Integer.parseInt(year) + (add2000 ? 2000 : 0));
    }

    public static Integer seasonStringToInt(String season) {
        switch (season.trim()) {
            case "S", "Spring" -> { return 0; }
            case "SU", "SS", "Summer" -> { return 1; }
            case "F", "Fall" -> { return 2; }
            case "W", "Winter" -> { return 3; }
            default -> { return -1; }
        }
    }
}
