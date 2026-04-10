package com.mycompany.poe;

/**
 *
 * @author lab_services_student: Vuyolwethu Bovu
 */
import javax.swing.JOptionPane;

public class ChatUp {

    // Registration method
    private static Registration doRegistration() {
        JOptionPane.showMessageDialog(null, "Welcome to ChatUp!");

        String name = JOptionPane.showInputDialog("Please enter your name: ");
        String surname = JOptionPane.showInputDialog("Please enter your surname: ");
        String username = JOptionPane.showInputDialog("Please enter your username: ");
        String password = JOptionPane.showInputDialog("Please enter your password: ");
        String cellPhoneNumber = JOptionPane.showInputDialog("Please enter your cell phone number: ");

        return new Registration(username, password, cellPhoneNumber, name, surname);
    }

    // Login method 
    private static boolean doLoginAttempt(Registration register) {
        JOptionPane.showMessageDialog(null, "Time to Login back to a chattered world of ups!");

        String username = JOptionPane.showInputDialog("Please enter your username: ");
        String password = JOptionPane.showInputDialog("Please enter your password: ");

        Login login = new Login(username, password, register.getName(), register.getSurname());

        boolean success = login.loginUser(register);

        // show the rubric message after the attempt
        JOptionPane.showMessageDialog(null, login.returnLoginStatus());

        return success;
    }

    // Main method
    public static void main(String[] args) {

        // Registration process 
        Registration register = doRegistration();

        if (!register.getLoginCanProceedStatus()) {
            JOptionPane.showMessageDialog(null, "Registration unsuccessful. Please restart the app and try again.");
            return;
        }
        //End of Registration process

        //Login process
        boolean success = doLoginAttempt(register);

        // Retry once (as hinted by rubric when they stated 'try again') 
        if (!success) {
            JOptionPane.showMessageDialog(null, "First login attempt failed. This is your last try");
            success = doLoginAttempt(register);
        }

        //if the 'retry' STILL fails
        if (!success) {
            JOptionPane.showMessageDialog(null, "Apologies. All attempts failed. Goodbye.");
        }

        //End of Login process
    }
}
