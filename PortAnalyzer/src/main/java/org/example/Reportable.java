package org.example;

import java.io.IOException;

public interface Reportable {
        void writeReport(String outputFilePath) throws IOException;
}
