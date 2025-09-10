import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

class Contact {
    String name, phone, email, address;

    public Contact(String name, String phone, String email, String address) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    @Override
    public String toString() {
        return name + " - " + phone + " - " + email + " - " + address;
    }
}


public class ContactManagement extends JFrame {
    private ArrayList<Contact> contacts = new ArrayList<>();
    private JList<Contact> contactList;
    private DefaultListModel<Contact> listModel;

    public ContactManagement() {
        // Frame Title
        super("Contact Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("Contact Management System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(titleLabel, BorderLayout.NORTH);

      
        listModel = new DefaultListModel<>();
        contactList = new JList<>(listModel);
        contactList.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(contactList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Saved Contacts"));
        add(scrollPane, BorderLayout.CENTER);

       
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton addButton = new JButton(" ➕ Add Contact");
        JButton viewButton = new JButton(" 👁 View Contact");
        JButton updateButton = new JButton(" ✏️ Update Contact");
        JButton deleteButton = new JButton(" 🗑 Delete Contact");
        JButton saveButton = new JButton(" 💾 Save to File");

        Font btnFont = new Font("Arial", Font.BOLD, 14);
        addButton.setFont(btnFont);
        viewButton.setFont(btnFont);
        updateButton.setFont(btnFont);
        deleteButton.setFont(btnFont);
        saveButton.setFont(btnFont);

        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(saveButton);

        add(buttonPanel, BorderLayout.EAST);

        // Action Listeners
        addButton.addActionListener(e -> addContact());
        viewButton.addActionListener(e -> viewContact());
        updateButton.addActionListener(e -> updateContact());
        deleteButton.addActionListener(e -> deleteContact());
        saveButton.addActionListener(e -> saveToFile());

        setVisible(true);
    }

    
    private void addContact() {
        JTextField nameField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField addressField = new JTextField();
        Object[] fields = {
            "Name:", nameField,
            "Phone:", phoneField,
            "Email:", emailField,
            "Address:", addressField
        };

        int result = JOptionPane.showConfirmDialog(this, fields, "Add Contact", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();
            String address = addressField.getText();

           
            Contact contact = new Contact(name, phone, email, address);
            contacts.add(contact);
            listModel.addElement(contact);  
            JOptionPane.showMessageDialog(this, "Contact Successfully Added.");
        }
    }

    
    private void viewContact() {
        int selectedIndex = contactList.getSelectedIndex();
        if (selectedIndex >= 0) {
            Contact contact = contacts.get(selectedIndex);
            JOptionPane.showMessageDialog(this, contact.toString(), "View Contact", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No contact selected.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

   
    private void updateContact() {
        int selectedIndex = contactList.getSelectedIndex();
        if (selectedIndex >= 0) {
            Contact contact = contacts.get(selectedIndex);

            JTextField nameField = new JTextField(contact.name);
            JTextField phoneField = new JTextField(contact.phone);
            JTextField emailField = new JTextField(contact.email);
            JTextField addressField = new JTextField(contact.address);
            Object[] fields = {
                "Name:", nameField,
                "Phone:", phoneField,
                "Email:", emailField,
                "Address:", addressField
            };

            int result = JOptionPane.showConfirmDialog(this, fields, "Update Contact", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                contact.name = nameField.getText();
                contact.phone = phoneField.getText();
                contact.email = emailField.getText();
                contact.address = addressField.getText();

                listModel.set(selectedIndex, contact);
            JOptionPane.showMessageDialog(this, "Contact Successfully Updated.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "No contact selected.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

   
    private void deleteContact() {
        int selectedIndex = contactList.getSelectedIndex();
        if (selectedIndex >= 0) {
            contacts.remove(selectedIndex);
            listModel.remove(selectedIndex);
            JOptionPane.showMessageDialog(this, "Contact Successfully Deleted.");
        } else {
            JOptionPane.showMessageDialog(this, "No contact selected.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

   
    private void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("contacts.txt"))) {
            for (Contact contact : contacts) {
                writer.println(contact);
            }
            JOptionPane.showMessageDialog(this, "Contacts saved to file.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving to file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new ContactManagement();
    }
}
