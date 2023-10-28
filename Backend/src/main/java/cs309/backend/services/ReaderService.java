package cs309.backend.services;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

@Service
public class ReaderService {
    public ArrayList<String> extractTextFromPdf(InputStream pdfFile) throws IOException {
        PDDocument document = PDDocument.load(pdfFile);
        PDFTextStripper pdfTextStripper = new PDFTextStripper();
        String text = pdfTextStripper.getText(document);
        document.close();
        ArrayList<String> arr = textFilter(text);
        return arr;
    }

    private ArrayList<String> textFilter(String args) {
        ArrayList<String> arr = new ArrayList<>();
        Scanner scnr = new Scanner(args);
        while (scnr.hasNextLine()) {
            String line = scnr.nextLine();
            if (line.startsWith("F") || line.startsWith("S")) {
                line = line.substring(5);
                if(line.length() != 1) {
                    if (!line.contains(".")) {
                        line = line.substring(19);
                        arr.add(line);
                    }
                    else {
                        int index = line.indexOf(".");
                        line = line.substring(0, index - 2);
                        arr.add(line);
                    }
                }
            }
        }
        scnr.close();
        return arr;
    }
}
