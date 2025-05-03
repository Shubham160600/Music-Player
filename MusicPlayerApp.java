package MusicPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 * A simple console-based MP3 Music Player application
 */
public class MusicPlayerApp {
    public static void main(String[] args) {
        MusicPlayer player = new MusicPlayer();   
        // Add some sample songs to the playlist
        player.addSong(new Song("Shape of You", "Ed Sheeran", "÷", 235));
        player.addSong(new Song("Blinding Lights", "The Weeknd", "After Hours", 200));
        player.addSong(new Song("Dance Monkey", "Tones and I", "The Kids Are Coming", 210));
        player.addSong(new Song("Bad Guy", "Billie Eilish", "When We All Fall Asleep, Where Do We Go?", 194));
        player.addSong(new Song("Don't Start Now", "Dua Lipa", "Future Nostalgia", 183));        
        MusicPlayerUI ui = new MusicPlayerUI(player);
        ui.startUI();
    }
}
/**
 * Represents a music track with title, artist, album and duration
 */
class Song {
    private String title;
    private String artist;
    private String album;
    private int durationInSeconds;   
    public Song(String title, String artist, String album, int durationInSeconds) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.durationInSeconds = durationInSeconds;
    }
    public String getTitle() {
        return title;
    }
    public String getArtist() {
        return artist;
    }
    public String getAlbum() {
        return album;
    }    
    public int getDurationInSeconds() {
        return durationInSeconds;
    }
    @Override
    public String toString() {
        return String.format("%s - %s (Album: %s, %d:%02d)", 
                            title, artist, album, 
                            durationInSeconds / 60, durationInSeconds % 60);
    }
}
/**
 * Core music player functionality including playlist management and playback controls
 */
class MusicPlayer {
    private List<Song> playlist;
    private int currentSongIndex;
    private boolean isPlaying;
    private boolean isPaused;   
    public MusicPlayer() {
        playlist = new ArrayList<>();
        currentSongIndex = 0;
        isPlaying = false;
        isPaused = false;
    }
    public void addSong(Song song) {
        playlist.add(song);
    }
    public List<Song> getAllSongs() {
        return playlist;
    }
    public void play() {
        if (playlist.isEmpty()) {
            System.out.println("Playlist is empty. Please add songs first.");
            return;
        }    
        if (isPaused) {
            isPaused = false;
            isPlaying = true;
            System.out.println("Resuming: " + playlist.get(currentSongIndex));
        } else {
            isPlaying = true;
            System.out.println("Now playing: " + playlist.get(currentSongIndex));
        }
    }   
    public void pause() {
        if (isPlaying && !isPaused) {
            isPaused = true;
            System.out.println("Paused: " + playlist.get(currentSongIndex));
        } else if (isPaused) {
            System.out.println("Already paused.");
        } else {
            System.out.println("No song is playing.");
        }
    }    
    public void stop() {
        if (isPlaying || isPaused) {
            isPlaying = false;
            isPaused = false;
            System.out.println("Stopped playing: " + playlist.get(currentSongIndex));
        } else {
            System.out.println("No song is playing.");
        }
    }
    public void next() {
        if (playlist.isEmpty()) {
            System.out.println("Playlist is empty.");
            return;
        }        
        boolean wasPlaying = isPlaying && !isPaused;
        stop();        
        currentSongIndex = (currentSongIndex + 1) % playlist.size();
        if (wasPlaying) {
            play();
        } else {
            System.out.println("Selected: " + playlist.get(currentSongIndex));
        }
    }
    public void previous() {
        if (playlist.isEmpty()) {
            System.out.println("Playlist is empty.");
            return;
        }   
        boolean wasPlaying = isPlaying && !isPaused;
        stop();        
        currentSongIndex = (currentSongIndex - 1 + playlist.size()) % playlist.size();
        if (wasPlaying) {
            play();
        } else {
            System.out.println("Selected: " + playlist.get(currentSongIndex));
        }
    }
    public void replay() {
        if (playlist.isEmpty()) {
            System.out.println("Playlist is empty.");
            return;
        }        
        stop();
        System.out.println("Replaying: " + playlist.get(currentSongIndex));
        play();
    }  
    public Song getCurrentSong() {
        if (playlist.isEmpty()) {
            return null;
        }
        return playlist.get(currentSongIndex);
    }   
    public boolean isPlaying() {
        return isPlaying && !isPaused;
    }    
    public boolean isPaused() {
        return isPaused;
    }
}
/**
 * User Interface for interacting with the Music Player
 */
class MusicPlayerUI {
    private MusicPlayer player;
    private Scanner scanner;
    
    public MusicPlayerUI(MusicPlayer player) {
        this.player = player;
        this.scanner = new Scanner(System.in);
    }   
    public void startUI() {
        boolean exit = false;       
        while (!exit) {
            displayMenu();
            System.out.print("Enter your choice: ");
            int choice;            
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume invalid input
                continue;
            }         
            switch (choice) {
                case 1: // Play
                    player.play();
                    break;                  
                case 2: // Pause
                    player.pause();
                    break;                   
                case 3: // Stop
                    player.stop();
                    break;                   
                case 4: // Next
                    player.next();
                    break;                    
                case 5: // Previous
                    player.previous();
                    break;                  
                case 6: // Replay
                    player.replay();
                    break;                    
                case 7: // List all songs
                    listAllSongs();
                    break;                   
                case 8: // Show player status
                    showPlayerStatus();
                    break;                   
                case 9: // Add a new song
                    addNewSong();
                    break;                  
                case 0: // Exit
                    System.out.println("Thank you for using the Music Player. Goodbye!");
                    exit = true;
                    break;                 
                default:
                    System.out.println("Invalid choice. Please try again.");
            }        
            System.out.println(); // Add an empty line for better readability
        }     
        scanner.close();
    }   
    private void displayMenu() {
        System.out.println("\n===== MUSIC PLAYER MENU =====");
        System.out.println("1. Play/Resume");
        System.out.println("2. Pause");
        System.out.println("3. Stop");
        System.out.println("4. Next song");
        System.out.println("5. Previous song");
        System.out.println("6. Replay current song");
        System.out.println("7. List all songs");
        System.out.println("8. Show player status");
        System.out.println("9. Add a new song");
        System.out.println("0. Exit");
        System.out.println("============================");
    }  
    private void listAllSongs() {
        List<Song> songs = player.getAllSongs();
        if (songs.isEmpty()) {
            System.out.println("The playlist is empty.");
            return;
        }      
        System.out.println("\n===== PLAYLIST =====");
        for (int i = 0; i < songs.size(); i++) {
            Song song = songs.get(i);
            String currentIndicator = (i == player.getAllSongs().indexOf(player.getCurrentSong())) ? " ► " : "   ";
            System.out.println((i + 1) + "." + currentIndicator + song);
        }
        System.out.println("===================");
    }  
    private void showPlayerStatus() {
        Song currentSong = player.getCurrentSong();
        if (currentSong == null) {
            System.out.println("No songs in the playlist.");
            return;
        }        
        System.out.println("\n===== PLAYER STATUS =====");
        System.out.println("Current song: " + currentSong);        
        if (player.isPlaying()) {
            System.out.println("Status: Playing");
        } else if (player.isPaused()) {
            System.out.println("Status: Paused");
        } else {
            System.out.println("Status: Stopped");
        }
        System.out.println("========================");
    }  
    private void addNewSong() {
        System.out.println("\n===== ADD NEW SONG =====");     
        System.out.print("Enter song title: ");
        String title = scanner.nextLine();      
        System.out.print("Enter artist name: ");
        String artist = scanner.nextLine();       
        System.out.print("Enter album name: ");
        String album = scanner.nextLine();     
        int duration = 0;
        boolean validInput = false;        
        while (!validInput) {
            try {
                System.out.print("Enter duration in seconds: ");
                duration = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                validInput = true;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume invalid input
            }
        }       
        Song newSong = new Song(title, artist, album, duration);
        player.addSong(newSong);      
        System.out.println("Song added successfully!");
        System.out.println("======================");
    }
}