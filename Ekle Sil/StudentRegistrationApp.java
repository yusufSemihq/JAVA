import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class StudentRegistrationApp {
    private Connection connection;
    private JFrame frame;
    private JTextField idField, nameField, ageField;
    private JTextArea outputArea;

    public StudentRegistrationApp() {
        try {
            // Veritabanı bağlantısı oluştur
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_db", "username", "password");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Veritabanı bağlantısı başarısız oldu.");
        }

        frame = new JFrame("Öğrenci Kayıt Uygulaması");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        idField = new JTextField();
        nameField = new JTextField();
        ageField = new JTextField();
        inputPanel.add(new JLabel("ID:"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("İsim:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Yaş:"));
        inputPanel.add(ageField);

        JButton addButton = new JButton("Ekle");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStudent();
            }
        });

        JButton viewButton = new JButton("Görüntüle");
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewStudents();
            }
        });

        outputArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(outputArea);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void addStudent() {
        String name = nameField.getText();
        int age = Integer.parseInt(ageField.getText());
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO students (name, age) VALUES (?, ?)");
            statement.setString(1, name);
            statement.setInt(2, age);
            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Öğrenci başarıyla eklendi.");
            nameField.setText("");
            ageField.setText("");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Öğrenci eklenirken bir hata oluştu.");
        }
    }

    private void viewStudents() {
        outputArea.setText("");
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM students");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                outputArea.append("ID: " + id + ", İsim: " + name + ", Yaş: " + age + "\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Öğrencileri görüntülemede bir hata oluştu.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StudentRegistrationApp();
            }
        });
    }
}
