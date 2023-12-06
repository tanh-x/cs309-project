package cs309.backend.DTOs;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public record DegreeAudit(
    int inProgressCredits,
    int appliedCredits,
    double gpa,
    SeasonYear entryTerm,
    SeasonYear graduationTerm,
    List<String> annotations,
    Set<DegreeCourseEntry> courseSet
) {
}
