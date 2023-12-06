package cs309.backend.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

/**
 * Coupled with core.models.in.DegreeCourseEntryDeserializable
 *
 * @param annotations NTR(2,4)=No Transfer, remedial course
 *                    IPT(2,4,T)=Transfer course, prospective student
 *                    -TR = Transfer work prior to 1980's with no grade assigned
 *                    >N =Non-Designated Rpt Course    I = Academic Renew/Ignore course
 *                    >I =Incomplete Grade             **=Not Including IN-P... Courses
 *                    >T =P to graded or NP to not     >H = Honors course
 *                    >* =Previous grade:I(incomplete) >G=Non & Designated Rpt Course
 *                    >@ =Repeated course              >- =Short credits due to limit
 *                    >P =P-NP course                  TCUR =Test out crs, holding file
 *                    >S =Split Course                 REG =currently registered course
 *                    R  =Required Course              CUR =currently enrolled course
 *                    -  =Sub-Req NOT Complete         TT =Career/Tech transfer credit
 *                    +  =Sub-Req Complete             T2 =2yr college transfer credit
 *                    IX =cur/reg PNP course           T4 =4yr college transfer credit
 *                    >R =Course repeated in future    AUD =Audited course-no credit
 *                    IP =Requirement In Progress      IN-P... =Sub-Req In Progress
 *                    NO =Requirement NOT Complete     >RP = Repeatable course
 *                    OK =Requirement Complete         >D = Designated Repeat Course
 */
public record DegreeCourseEntry(
    @NotNull @NotBlank String program,
    @NotNull @NotBlank String num,
    SeasonYear term,
    String grade,
    int credits,
    String annotations
) implements Comparable<DegreeCourseEntry> {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DegreeCourseEntry that = (DegreeCourseEntry) o;
        return Objects.equals(program, that.program) && Objects.equals(num, that.num);
    }

    @Override
    public int hashCode() {
        return Objects.hash(program, num);
    }

    @Override
    public int compareTo(DegreeCourseEntry other) {
        int yearComparison = Integer.compare(this.term.year(), other.term.year());
        if (yearComparison != 0) return yearComparison;

        int seasonComparison = Integer.compare(this.term.season(), other.term.season());
        if (seasonComparison != 0) return seasonComparison;

        int programComparison = this.program.compareTo(other.program);
        if (programComparison != 0) return programComparison;

        return this.num.compareTo(other.num);
    }
}
