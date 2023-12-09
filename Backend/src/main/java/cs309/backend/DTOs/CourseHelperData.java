package cs309.backend.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CourseHelperData(
        @NotNull @NotBlank String CourseName,
        @NotNull @NotBlank int CourseNum,
        @NotNull @NotBlank int start_time,
        @NotNull @NotBlank int end_time
) {}
