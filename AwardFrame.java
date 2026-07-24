import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AwardFrame extends JFrame {
    JButton addButton, viewButton, updateButton, deleteButton;

    public AwardFrame() {
        setTitle("Award Management");
        setSize(350, 250);
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        addButton = new JButton("Add Award");
        viewButton = new JButton("View Awards");
        updateButton = new JButton("Update Award");
        deleteButton = new JButton("Delete Award");

        // Add buttons in vertical order
        add(addButton, gbc);

        gbc.gridy++;
        add(viewButton, gbc);

        gbc.gridy++;
        add(updateButton, gbc);

        gbc.gridy++;
        add(deleteButton, gbc);

        // Event Listeners
        addButton.addActionListener(e -> new AddAwardDialog(this));
        viewButton.addActionListener(e -> viewAwards());
        updateButton.addActionListener(e -> new UpdateAwardDialog(this));
        deleteButton.addActionListener(e -> new DeleteAwardDialog(this));

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    // View Awards (Show in JTable)
    private void viewAwards() {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM award";
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
            JOptionPane.showMessageDialog(this, new JScrollPane(table), "All Awards", JOptionPane.PLAIN_MESSAGE);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error displaying awards: " + ex.getMessage());
        }
    }
}
