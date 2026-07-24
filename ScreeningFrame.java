import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ScreeningFrame extends JFrame {
    JButton addButton, updateButton, deleteButton, viewButton;

    public ScreeningFrame() {
        setTitle("Film Management");
        setSize(350, 250);
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        addButton = new JButton("Add Screening");
        updateButton = new JButton("Update Screening");
        deleteButton = new JButton("Delete Screening");
        viewButton = new JButton("View All Screenings");

        // Add buttons in vertical order
        add(addButton, gbc);

        gbc.gridy++;
        add(viewButton, gbc);

        gbc.gridy++;
        add(updateButton, gbc);

        gbc.gridy++;
        add(deleteButton, gbc);

        // Action listeners
        addButton.addActionListener(e -> new AddScreeningDialog(this));
        updateButton.addActionListener(e -> new UpdateScreeningDialog(this));
        deleteButton.addActionListener(e -> new DeleteScreeningDialog(this));
        viewButton.addActionListener(e -> viewScreenings());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void viewScreenings() {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM Screening";
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
            JOptionPane.showMessageDialog(this, new JScrollPane(table), "All Screenings", JOptionPane.PLAIN_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

