package edu.brown.cs.abeckrui.mockaroo;

/**
 * Class modeling the data fields that each person should store.
 */
public class MockPerson {

  private String firstName;
  private String lastName;
  private String dateTime;
  private String email;
  private String gender;
  private String streetAddress;

  /**
   * This constructor stores all the data fields we need for each person.
   * @param first string representing firstname
   * @param last string representing lastname
   * @param date string representing datetime
   * @param em string representing email
   * @param gen string representing gender
   * @param street string representing streetAddress
   */
  public MockPerson(String first, String last, String date, String em,
                    String gen, String street) {
    firstName = first;
    lastName = last;
    dateTime = date;
    email = em;
    gender = gen;
    streetAddress = street;
  }

  /**
   * This method prints all the MockPersons fields to the console.
   */
  public void printFields() {
    System.out.println("First Name: " + firstName + ", Last Name: " + lastName + ", Datetime: "
            + dateTime + ", Email Address: " + email + ", Gender: " + gender
            + ", Street Address: " + streetAddress);
  }

}
