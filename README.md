# README

## Stars
**TODO: Fill out this section!**

NOTE: My MOCK_DATA.csv, MockPerson class, and MockPersonRunner class are located in "src/main/java/edu/brown/cs
/abeckrui/mockaroo". My data_modeling.txt file is in the same directory/level as my README.


1) My project has no known bugs at the moment.

2) One specific design choice I made regarding the MockPerson class was to make a MockPersonRunner 
class. This class handles all the logic, including creating the MockPerson objects and checking to see 
   that the mock csv data is valid. I did this so that I don't have to instantiate a MockPerson to do 
   all of this logic. 
   
I also made an interface called Method that allows classes that implement it to run commands parsed 
by the REPL. I did this as an attempt to make my REPL more extensive. 

3) I did not make any effort to improve runtime or space complexity, as it is not required for Stars 1.

4) My system tests run using the ./cs32-test tests/student/stars/stars1/*.test commmand. I did not write 
JUnit tests as I am saving it for the later iterations of this project. 
   
5) My program can be built by doing mvn build. I was having a problem sometime where I had to make sure my 
terminal was running Java 11 before building or I would get an error (I also have Java 8 installed and setup
   my IntelliJ with Java 8 for TAing CS15). Make sure that Java 11 is running and my project should build just
   fine. To run my project, enter "./run".
   
6) In order to handle more commands for the REPL, I created a HashMap in Main that maps commands (which are
   indicated by the first string a user inputs) to an object of type Method. My Method interface has a method
   called "run" that takes in an array of strings. My hope in doing this was that if I wanted to add 10+ more
   commands, someone could just create a new class and implement this interface. This would allow an object
   of this class to take in the parsed command from the REPL and handle the command internally in the class.
   
Moving forward, one way I could make my REPL even more extensible is allowing users to pass in an argument into
the REPL object instantiated dictating how they want the REPL to parse the user input. Currently, my REPL splits
the user input on spaces excluding quotation marks, so that star names are seen as 1 string. However, perhaps
someone might want to add a command that included quotation marks, which would break my REPL. Therefore, if 
I allowed my REPL to take in some type of regex pattern to split on, I could make my REPL more extensible.

7) My project has no CheckStyle errors. 
   
