import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

// Contact Class to store individual contact details
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

// Main Class for Contact Management System
public class ContactManagement extends JFrame {
    private ArrayList<Contact> contacts = new ArrayList<>();
    private JList<String> contactList;
    private DefaultListModel<String> listModel;

    public ContactManagement() {
        // Frame Title
        super("Contact Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // Left Panel (Contact List)
        listModel = new DefaultListModel<>();
        contactList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(contactList);
        add(scrollPane, BorderLayout.CENTER);

        // Right Panel (Buttons)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 5, 5));

        JButton addButton = new JButton("Add Contact");
        JButton viewButton = new JButton("View Contact");
        JButton updateButton = new JButton("Update Contact");
        JButton deleteButton = new JButton("Delete Contact");
        JButton saveButton = new JButton("Save to File");

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

    // Add Contact
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

            contacts.add(new Contact(name, phone, email, address));
            listModel.addElement(name);
        }
    }

    // View Contact
    private void viewContact() {
        int selectedIndex = contactList.getSelectedIndex();
        if (selectedIndex >= 0) {
            Contact contact = contacts.get(selectedIndex);
            JOptionPane.showMessageDialog(this, contact.toString(), "View Contact", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No contact selected.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Update Contact
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

                listModel.set(selectedIndex, contact.name);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No contact selected.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Delete Contact
    private void deleteContact() {
        int selectedIndex = contactList.getSelectedIndex();
        if (selectedIndex >= 0) {
            contacts.remove(selectedIndex);
            listModel.remove(selectedIndex);
        } else {
            JOptionPane.showMessageDialog(this, "No contact selected.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Save Contacts to File
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
