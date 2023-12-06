package cs309.backend.DTOs;

import java.util.List;

// Coupled with core.models.in DegreeAudit
public record DegreeAudit(
    String major,
    int inProgressCredits,
    int appliedCredits,
    double gpa,
    SeasonYear entryTerm,
    SeasonYear graduationTerm,
    List<String> annotations,
    List<DegreeCourseEntry> courses
) {
}
