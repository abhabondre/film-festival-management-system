import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    public Main() {
        setTitle("Film Festival Management");
        setSize(300, 200);
        setLayout(new GridBagLayout()); // centers components automatically

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0); // spacing between buttons
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JButton filmBtn = new JButton("Manage Films");
        filmBtn.setBounds(50, 40, 200, 30);
        gbc.gridy = 1; add(filmBtn, gbc);

        JButton filmCrewBtn = new JButton("Manage Film Crews");
        filmCrewBtn.setBounds(50, 90, 200, 30);
        gbc.gridy = 2; add(filmCrewBtn, gbc);

        JButton venueBtn = new JButton("Manage Venues");
        venueBtn.setBounds(50, 140, 200, 30);
        gbc.gridy = 3; add(venueBtn, gbc);

        JButton screeningBtn = new JButton("Manage Screenings");
        screeningBtn.setBounds(50, 190, 200, 30);
        gbc.gridy = 4; add(screeningBtn, gbc);

        JButton attendeeBtn = new JButton("Manage Attendees");
        attendeeBtn.setBounds(50, 240, 200, 30);
        gbc.gridy = 5; add(attendeeBtn, gbc);

        JButton ticketBtn = new JButton("Manage Tickets");
        ticketBtn.setBounds(50, 290, 200, 30);
        gbc.gridy = 6; add(ticketBtn, gbc);

        JButton awardBtn = new JButton("Manage Awards");
        awardBtn.setBounds(50, 340, 200, 30);
        gbc.gridy = 0; add(filmBtn, gbc);

        filmBtn.addActionListener(e -> new FilmFrame());
        filmCrewBtn.addActionListener(e -> new FilmCrewFrame());
        venueBtn.addActionListener(e -> new VenueFrame());
        screeningBtn.addActionListener(e -> new ScreeningFrame());
        attendeeBtn.addActionListener(e -> new AttendeeFrame());
        ticketBtn.addActionListener(e -> new TicketFrame());
        awardBtn.addActionListener(e -> new AwardFrame());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}


