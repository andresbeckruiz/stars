package edu.brown.cs.abeckrui.mockaroo;

public class MockPerson {

    private String _firstName;
    private String _lastName;
    private String _dateTime;
    private String _email;
    private String _gender;
    private String _streetAddress;

    public MockPerson(String firstName, String lastName, String dateTime, String email,
    String gender, String streetAddress){
        _firstName = firstName;
        _lastName = lastName;
        _dateTime = dateTime;
        _email = email;
        _gender = gender;
        _streetAddress = streetAddress;
    }

    /**
     * This method prints all the MockPersons fields to the console
     */
    public void printFields() {
        System.out.println("First Name: " + _firstName + ", Last Name: " + _lastName + ", Datetime: "
                + _dateTime + ", Email Address: " + _email + ", Gender: " + _gender +
                ", Street Address: " + _streetAddress);
    }

}
