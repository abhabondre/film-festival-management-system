import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class TicketFrame extends JFrame {
    JButton addButton, updateButton, deleteButton, viewButton;

    public TicketFrame() {
        setTitle("Film Management");
        setSize(350, 250);
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        addButton = new JButton("Add Ticket");
        updateButton = new JButton("Update Ticket");
        deleteButton = new JButton("Delete Ticket");
        viewButton = new JButton("View All Tickets");

        // Add buttons in vertical order
        add(addButton, gbc);

        gbc.gridy++;
        add(viewButton, gbc);

        gbc.gridy++;
        add(updateButton, gbc);

        gbc.gridy++;
        add(deleteButton, gbc);

        // Action listeners
        addButton.addActionListener(e -> new AddTicketDialog(this));
        updateButton.addActionListener(e -> new UpdateTicketDialog(this));
        deleteButton.addActionListener(e -> new DeleteTicketDialog(this));
        viewButton.addActionListener(e -> viewTickets());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void viewTickets() {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM Ticket";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

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
            JOptionPane.showMessageDialog(this, new JScrollPane(table), "All Tickets", JOptionPane.PLAIN_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
