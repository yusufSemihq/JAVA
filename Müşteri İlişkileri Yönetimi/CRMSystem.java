import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Customer {
    private int id;
    private String name;
    private String email;
    private String phoneNumber;

    public Customer(int id, String name, String email, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}

class CustomerManagementSystem {
    private Map<Integer, Customer> customers;

    public CustomerManagementSystem() {
        customers = new HashMap<>();
    }

    public void addCustomer(Customer customer) {
        customers.put(customer.getId(), customer);
    }

    public void updateCustomer(Customer customer) {
        customers.put(customer.getId(), customer);
    }

    public void deleteCustomer(int id) {
        customers.remove(id);
    }

    public Customer getCustomerById(int id) {
        return customers.get(id);
    }

    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers.values());
    }
}

public class CRMSystem {
    private CustomerManagementSystem cms;
    private JFrame frame;
    private JTextField idField, nameField, emailField, phoneField;
    private JTextArea outputArea;

    public CRMSystem() {
        cms = new CustomerManagementSystem();

        frame = new JFrame("Müşteri İlişkileri Yönetimi");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        idField = new JTextField();
        nameField = new JTextField();
        emailField = new JTextField();
        phoneField = new JTextField();
        inputPanel.add(new JLabel("ID:"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Ad Soyad:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("E-posta:"));
        inputPanel.add(emailField);
        inputPanel.add(new JLabel("Telefon:"));
        inputPanel.add(phoneField);

        JButton addButton = new JButton("Müşteri Ekle");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCustomer();
            }
        });

        JButton viewButton = new JButton("Tüm Müşterileri Görüntüle");
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewCustomers();
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

    private void addCustomer() {
        int id = Integer.parseInt(idField.getText());
        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        Customer customer = new Customer(id, name, email, phone);
        cms.addCustomer(customer);
        JOptionPane.showMessageDialog(null, "Müşteri başarıyla eklendi.");
        clearFields();
    }

    private void viewCustomers() {
        outputArea.setText("");
        List<Customer> customers = cms.getAllCustomers();
        for (Customer customer : customers) {
            outputArea.append("ID: " + customer.getId() + ", Ad Soyad: " + customer.getName() + ", E-posta: "
                    + customer.getEmail() + ", Telefon: " + customer.getPhoneNumber() + "\n");
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CRMSystem();
            }
        });
    }
}
