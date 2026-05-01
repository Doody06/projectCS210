
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

public class Solution {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        NEOM_Core system = new NEOM_Core(); // Should be an error because we didn't write NEOM_CORE yet

        while (true) {
            try {
                System.out.println("Enter a command from 1 to 7:");
                int command = sc.nextInt();
                switch (command) {

                    //<filename> Bulk Load Tasks
                    case 1:
                        String filename = sc.next();
                        loadTasksFromFile(filename, system); //This method loads tasks AND calls addTask for each one of them.
                        continue;

                    //Process Next Task
                    case 2:
                        system.processNextTask();
                        continue;
                    //Emergency Undo
                    case 3:
                        system.undoLastAction();
                        continue;
                    //System Audit
                    case 4:
                        system.systemAudit();
                        continue;
                    //View History
                    case 5:
                        system.printDeploymentHistory();
                        continue;
                    //<sectorID> Search Sector
                    case 6:
                        int sectorID = sc.nextInt();
                        system.searchSector(sectorID);
                        continue;
                    case 7:
                        System.out.println("Exiting...");
                        sc.close();
                        return;
                    default:
                        System.out.println("Invalid command.");
                        continue;

                }

            } catch (Exception e) {
                System.out.println("Error reading input.");
            }
        }
    }

    private static void loadTasksFromFile(String filename, NEOM_Core system) {
        BufferedReader br = null; //BufferedReader's advantage is that it can do readLine()
        try {
            br = new BufferedReader(new FileReader(filename));
            String line = "";

            while ((line = br.readLine()) != null) {

                if (line.trim().isEmpty()) continue;

                // Format: sectorID, taskID, description
                String[] parts = line.split(",");

                if (parts.length < 3) continue;
                else System.out.println("Invalid file format."); 

                int sectorID = Integer.parseInt(parts[0].trim());
                String taskID = parts[1].trim();
                String desc = parts[2].trim();

                system.addTask(sectorID, taskID, desc); //we call Neom_Core here
            }

        } catch (IOException e) {
            System.out.println("File error: " + filename);
        } catch (Exception e) {
            System.out.println("Invalid file format.");
        } finally { //I read that this block is not needed in new Java versions but kept it just in case.
            try {
                if (br != null) br.close();
            } catch (IOException e) {
                System.out.println("Error closing file.");
            }
        }
    }
}