import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE;
class FileScan {

    public static void main(String[] args) {
        File selectedFile = null;
        int lineCount = 0;
        int wordCount = 0;
        int charCount = 0;

        try {
            if (args.length > 0) {
                String filename = args[0];
                File workingDirectory = new File(System.getProperty("user.dir"));
                selectedFile = new File(workingDirectory, filename);

                if (!selectedFile.exists()) {
                    System.out.println("File not found: " + filename);
                    return;
                }
            } else {
                JFileChooser chooser = new JFileChooser();
                File workingDirectory = new File(System.getProperty("user.dir"));
                chooser.setCurrentDirectory(workingDirectory);

                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    selectedFile = chooser.getSelectedFile();
                } else {
                    System.out.println("No file selected!!! ... exiting.\nRun the program again and select a file.");
                    return;
                }
            }
            Path filePath = selectedFile.toPath();
            InputStream in = new BufferedInputStream(Files.newInputStream(filePath, CREATE));
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String rec;
            while ((rec = reader.readLine()) != null) {
                lineCount++;
                charCount += rec.length();

                String[] words = rec.trim().split("\\s+");
                if (!rec.trim().isEmpty()) {
                    wordCount += words.length;
                }
                System.out.printf("Line %4d: %s%n", lineCount, rec);
            }

            reader.close();
            System.out.println("\n--- File Summary ---");
            System.out.println("File Name        : " + selectedFile.getName());
            System.out.println("Total Lines      : " + lineCount);
            System.out.println("Total Words      : " + wordCount);
            System.out.println("Total Characters : " + charCount);

        } catch (FileNotFoundException e) {
            System.out.println("File not found!!!");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
