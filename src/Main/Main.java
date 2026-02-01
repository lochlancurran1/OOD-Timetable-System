package Main;

import controllers.DataManager;
import controllers.TimetableController;
import controllers.TimetableGenerator;
import Model.Timetable.TimetableService;
import View.UserInterface;

public class Main {

    /**
     * The entry point of the UL Timetabling System.
     *
     * This method loads all required CSV data, generates an automatic timetable,
     * checks for any room clashes, and finally starts the command-line interface
     * so the user can interact with the system.
     */
    public static void main(String[] args) {

        DataManager datamanager = new DataManager();
        TimetableService service = new TimetableService();

        datamanager.loadStudents("data/students.csv");
        datamanager.loadLecturers("data/lecturers.csv");
        datamanager.loadRooms("data/rooms.csv");
        datamanager.loadModules("data/modules.csv");
        datamanager.loadProgrammes("data/programmes.csv");
        datamanager.loadAdmins("data/admins.csv");

        TimetableGenerator generator = new TimetableGenerator(datamanager, service);
        generator.generateAndLog("OOD-Group19-Real/data/generated_timetable.csv");

        TimetableController controller = new TimetableController(service, datamanager);

        var conflicts = controller.findRoomConflicts();
        if (conflicts.isEmpty()) {
            System.out.println("No room conflicts found.");
        } else {
            System.out.println("Room conflicts detected:");
            for (String c : conflicts) {
                System.out.println(c);
            }
        }

        UserInterface ui = new UserInterface(controller, datamanager);
        ui.start();
    }
}

