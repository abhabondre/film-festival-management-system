import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AttendeeFrame extends JFrame {
    public AttendeeFrame() {
        setTitle("Attendee Management");
        setSize(350, 250);
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JButton addButton = new JButton("Add Attendee");
        JButton viewButton = new JButton("View Attendees");
        JButton updateButton = new JButton("Update Attendee");
        JButton deleteButton = new JButton("Delete Attendee");

        add(addButton, gbc);
        gbc.gridy++;
        add(viewButton, gbc);
        gbc.gridy++;
        add(updateButton, gbc);
        gbc.gridy++;
        add(deleteButton, gbc);

        addButton.addActionListener(e -> new AddAttendeeDialog());
        viewButton.addActionListener(e -> viewAttendees());
        updateButton.addActionListener(e -> new UpdateAttendeeDialog());
        deleteButton.addActionListener(e -> new DeleteAttendeeDialog());

        setVisible(true);
    }

    // View Attendees
    private void viewAttendees() {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM attendee";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            ResultSetMetaData meta = rs.getMetaData();
            int columnCount = meta.getColumnCount();

            String[] columnNames = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columnNames[i - 1] = meta.getColumnName(i);
            }

            rs.last();
            int rowCount = rs.getRow();
            rs.beforeFirst();

            String[][] data = new String[rowCount][columnCount];
            int row = 0;
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    data[row][i - 1] = rs.getString(i);
                }
                row++;
            }

            JTable table = new JTable(data, columnNames);
            JOptionPane.showMessageDialog(this, new JScrollPane(table), "All Attendees", JOptionPane.PLAIN_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error displaying attendees: " + ex.getMessage());
        }
    }
}
