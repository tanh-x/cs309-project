package cs309.backend.services;

import cs309.backend.DTOs.DegreeAudit;
import cs309.backend.DTOs.DegreeCourseEntry;
import cs309.backend.DTOs.SeasonYear;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ReaderService {
    private static final Pattern HEADER_SEPARATION_REGEX = Pattern.compile("^=+$");
    private static final Pattern CREDITS_GPA_REGEX = Pattern.compile("^Total credits\\s+([0-9.]+)[* ]+CUM GPA\\s+([0-9.]+).*$");
    private static final Pattern COURSE_ENTRY_REGEX = Pattern.compile("^(F|S|SU|W)\\s?(\\d{2})\\s([A-Z ]{2,10})\\s(\\d{2,3}).?\\s([\\d.]+)\\s([A-Z]+)([A-Za-z0-9:<>\\s]*)$");
    private static final Pattern IN_PROGRESS_CREDITS_REGEX = Pattern.compile("^IN-P[.\\s]+([0-9.]+)credits$");
    private static final Pattern TERMS_REGEX = Pattern.compile("^Entered.*(Spring|Summer|Fall|Winter)\\s+(\\d{4})\\s+Graduate.*(Spring|Summer|Fall|Winter)\\s+(\\d{4})$");
    private static final Pattern HEADER_ANNOTATIONS_REGEX = Pattern.compile("^(S\\s.+)$");

    public DegreeAudit extractTextFromPdf(InputStream pdfFile) throws IOException {
        PDDocument document = PDDocument.load(pdfFile);
        PDFTextStripper pdfTextStripper = new PDFTextStripper();
        String text = pdfTextStripper.getText(document);
        document.close();
        return readAuditPdf(text);
    }

    private static DegreeAudit readAuditPdf(String pdfString) {
        int inProgressCredits = -1;
        int appliedCredits = -1;
        double gpa = -1d;
        SeasonYear entryTerm = null;
        SeasonYear graduationTerm = null;
        List<String> annotations = new ArrayList<>();
        HashSet<DegreeCourseEntry> courseSet = new HashSet<>();

        boolean atHeader = true;

        for (String line : pdfString.split("\n")) {
            if (atHeader) {
                if (HEADER_SEPARATION_REGEX.matcher(line).find()) {
                    atHeader = false;
                    System.out.println("Header done");
                    continue;
                }

                Matcher inpCreditsMatcher = IN_PROGRESS_CREDITS_REGEX.matcher(line);
                if (inpCreditsMatcher.matches()) {
                    inProgressCredits = Double.valueOf(inpCreditsMatcher.group(1)).intValue();
                    continue;
                }

                Matcher termsMatcher = TERMS_REGEX.matcher(line);
                if (termsMatcher.matches()) {
                    entryTerm = SeasonYear.fromAuditEntry(termsMatcher.group(1), termsMatcher.group(2), false);
                    graduationTerm = SeasonYear.fromAuditEntry(termsMatcher.group(3), termsMatcher.group(4), false);
                }

                Matcher creditsGpaMatcher = CREDITS_GPA_REGEX.matcher(line);
                if (creditsGpaMatcher.matches()) {
                    appliedCredits = Double.valueOf(creditsGpaMatcher.group(1)).intValue();
                    gpa = Double.parseDouble(creditsGpaMatcher.group(2));
                    continue;
                }

                if (HEADER_ANNOTATIONS_REGEX.matcher(line).find()) {
                    annotations.add(line);
                }
            } else {
                Matcher courseMatcher = COURSE_ENTRY_REGEX.matcher(line);
                if (courseMatcher.matches()) {
                    DegreeCourseEntry courseEntry = new DegreeCourseEntry(
                        courseMatcher.group(3).trim(),
                        courseMatcher.group(4).trim(),
                        SeasonYear.fromAuditEntry(courseMatcher.group(1), courseMatcher.group(2), true),
                        courseMatcher.group(6).trim(),
                        Double.valueOf(courseMatcher.group(5)).intValue(),
                        courseMatcher.group(7).trim()
                    );
                    courseSet.add(courseEntry);
                }
            }
        }

        for (DegreeCourseEntry course : courseSet) System.out.println(course);

        return new DegreeAudit(
            inProgressCredits,
            appliedCredits,
            gpa,
            entryTerm,
            graduationTerm,
            annotations,
            courseSet
        );
    }

    private LinkedHashSet<String> textFilter(String args) {
        LinkedHashSet<String> arr = new LinkedHashSet<>();
        Scanner scr = new Scanner(args);
        while (scr.hasNextLine()) {
            String line = scr.nextLine();
            if (line.startsWith("F") || line.startsWith("S")) {  // TODO: REGEX???????
                if (line.length() != 6) {
                    if (!line.contains(".")) {
                        line = line.substring(24);
                        arr.add(line);
                    } else {
                        int index = line.indexOf(".");
                        line = line.substring(0, index - 2);
                        arr.add(line);
                    }
                }
            }
        }
        scr.close();
        return arr;
    }

}
