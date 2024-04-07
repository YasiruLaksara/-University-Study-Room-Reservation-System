import java.util.ArrayList;
import java.util.List;

//This class contains attributes and behaviours which are related to StudyRoom
class StudyRoom {
    private int roomNumber;
    private int capacity;
    private boolean availability;


    public StudyRoom(int roomNumber, int capacity) {
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.availability = true;
    }

    //Respective getters and setters for the StudyRoom attributes
    public int getRoomNumber() {
        return roomNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isAvailable() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
}


//custom exception class called StudyRoomUnavailableException
class StudyRoomUnavailableException extends Exception {
    public StudyRoomUnavailableException(String message) {
        super(message);
    }
}


//This class contains attributes and behaviours which are related to StudyRoomReservationSystem
class StudyRoomReservationSystem {
    private List<StudyRoom> studyRooms;

    public StudyRoomReservationSystem() {
        studyRooms = new ArrayList<>();
    }

    public void addStudyRoom(StudyRoom studyRoom) {
        studyRooms.add(studyRoom);
    }

    //Reserves the study room with the specified room number if it is available
    public synchronized void reserveStudyRoom(int roomNumber) throws StudyRoomUnavailableException {
        StudyRoom studyRoom = getStudyRoomByNumber(roomNumber);

        //checking whether the room is available or not(occupied)
        if (studyRoom.isAvailable()) {
            studyRoom.setAvailability(false); //Reserving the room by setting the availability status  as occupied(false)
        } else {
            throw new StudyRoomUnavailableException("Study room " + roomNumber + " is already occupied.");
        }
    }


    //Releases the study room with the specified room number
    public synchronized void releaseStudyRoom(int roomNumber) {
        StudyRoom studyRoom = getStudyRoomByNumber(roomNumber);
        studyRoom.setAvailability(true);
    }

    //Displays the availability of all study rooms
    public void displayStudyRoomStatus() {
        System.out.println("Study Room Status:");
        for (StudyRoom studyRoom : studyRooms) {
            System.out.println("Room Number: " + studyRoom.getRoomNumber() +
                    ", Capacity: " + studyRoom.getCapacity() +
                    ", Availability: " + (studyRoom.isAvailable() ? "Available" : "Occupied"));
        }
    }

    //retrieving a study room object by its room number
    private StudyRoom getStudyRoomByNumber(int roomNumber) {
        for (StudyRoom studyRoom : studyRooms) {
            if (studyRoom.getRoomNumber() == roomNumber) {
                return studyRoom;
            }
        }
        return null;
    }
}

public class Main {
    public static void main(String[] args) {
        // Create StudyRoom objects
        StudyRoom room1 = new StudyRoom(1, 4);
        StudyRoom room2 = new StudyRoom(2, 6);
        StudyRoom room3 = new StudyRoom(3, 8);

        // Create StudyRoomReservationSystem
        StudyRoomReservationSystem reservationSystem = new StudyRoomReservationSystem();

        // Add study rooms to the reservation system
        reservationSystem.addStudyRoom(room1);
        reservationSystem.addStudyRoom(room2);
        reservationSystem.addStudyRoom(room3);

        // Display initial study room status
        reservationSystem.displayStudyRoomStatus();

    }
}