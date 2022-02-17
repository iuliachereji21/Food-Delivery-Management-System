package org.ro.tuc.pt.business;

import org.ro.tuc.pt.dataPersistance.Serializator;

import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class used for holding the users of the application, and for managing the register and log in operations.
 * @author Chereji Iulia
 */
public class UsersManagement {
    private HashSet<User> users;
    private User currentUser=null;
    private String nextClientID;

    /**
     * The constructor sets the initial set of users by reading them from the "users.txt" file.
     */
    public UsersManagement()
    {
        users=new HashSet<>();
        users= Serializator.readUsersFromFile();

        int maxID=0;
        Iterator<User> iterator= users.iterator();
        while (iterator.hasNext())
        {
            User user= iterator.next();
            if(user.getId().charAt(0)=='C')
            {
                int nr=Integer.parseInt(user.getId().substring(1));
                if (nr>maxID)
                    maxID=nr;
            }
        }
        nextClientID="C"+(maxID+1);
        System.out.println(nextClientID);
    }

    /**
     * @return the user which is currently logged in the application.
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * @return the set of all the users in the application.
     */
    public HashSet<User> getUsers() {
        return users;
    }

    /**
     * Sets the current user to null.
     */
    public void logOut() { currentUser=null; }

    /**
     * Checks if the introduced data corresponds to an existing user and if it does sets him as the current user.
     * @param username the username introduced by the user
     * @param password the password introduced by the user
     * @return 0 if the input was incorrect, 1 if the current user is an admin, 2 if he is a client, 3 if he is an employee
     */
    public int CheckLogIn(String username, String password)
    {
        Iterator<User> iterator=users.iterator();
        while (iterator.hasNext()) {
            User user= iterator.next();
            if(user.getUsername().equals(username) && user.getPassword().equals(password)) {
                currentUser=user;
                char c=user.getId().charAt(0);
                if(c=='A') return 1;
                else if(c=='C') return 2;
                else return 3;
            }
        }
        return 0;
    }

    /**
     * Checks if the attempt of registering is correct and if it is, creates a new user and sets him as the current user.
     * @param username non empty string
     * @param dateOfBirth String in the format "yyyy-mm-dd"
     * @param address any String
     * @param phone String with 10 digits
     * @param email String in the format "xxx@xxx.xxx"
     * @param password any String
     * @param repeatedPassword any String
     * @return -1 if the register was successful, 0 if empty username, 1 username already exists, 2 if incorrect date of birth format, 3 if incorrect phone format, 4 if wrong email format, 5 if already exists email, 6 if no password, 7 if passwords don't match
     */
    public int checkRegister(String username, String dateOfBirth, String address, String phone, String email, String password, String repeatedPassword)
    {
        if(username==null || username.isEmpty()) return 0;
        Iterator<User> iterator1= users.iterator();
        while (iterator1.hasNext()) if(iterator1.next().getUsername().equals(username)) return 1;

        if(dateOfBirth!= null && !dateOfBirth.isEmpty()) {
            if(dateOfBirth.length()!=10) return 2;
            char[] chars2 = dateOfBirth.toCharArray();
            if (chars2[4]!='-' || chars2[7]!='-') return 2;
            try {
                int year=Integer.parseInt(dateOfBirth.substring(0,4)); int month=Integer.parseInt(dateOfBirth.substring(5,7)); int day=Integer.parseInt(dateOfBirth.substring(8,10));
                if(year>2020 || year<1920 || month>12 || month<1 || day>31 || day<1) return 2;
            }
            catch (NumberFormatException e) { return 2; }
        }
        if(phone != null && !phone.isEmpty()) {
            if(phone.length()!=10) return 3;
            for(int i=0;i<9;i++) if(!Character.isDigit(phone.charAt(i))) return 3;
        }
        if(email==null || email.isEmpty() || !this.valEmail(email)) return 4;
        Iterator<User> iterator= users.iterator();
        while (iterator.hasNext()) if(iterator.next().getEmail().equals(email)) return 5;
        if(password==null || password.isEmpty()) return 6;
        if(repeatedPassword==null || repeatedPassword.isEmpty() || password.compareTo(repeatedPassword)!=0) return 7;

        User newUser= new User(nextClientID,username,dateOfBirth,address,phone,email,password);
        users.add(newUser);
        currentUser=newUser;
        int nr=Integer.parseInt(nextClientID.substring(1));
        nextClientID="C"+(nr+1);
        return -1;
    }

    private boolean valEmail(String input) {
        String emailFormat = "^[A-Z0-9._]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern emailPat = Pattern.compile(emailFormat,Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPat.matcher(input);
        return matcher.find();
    }
}
