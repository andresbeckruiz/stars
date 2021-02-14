# README

## Stars
**TODO: Fill out this section!**

Stars 1:

NOTE: My MOCK_DATA.csv, MockPerson class, and MockPersonRunner class are located in "src/main/java/edu/brown/cs
/abeckrui/mockaroo". My data_modeling.txt file is in the same directory/level as my README.


1) My project has no known bugs at the moment.

2) One specific design choice I made regarding the MockPerson class was to make a MockPersonRunner 
class. This class handles all the logic, including creating the MockPerson objects and checking to see 
   that the mock csv data is valid. I did this so that I don't have to instantiate a MockPerson to do 
   all of this logic. 
   
I also made an interface called Method that allows classes that implement it to run commands parsed 
by the REPL using a "run" method. I did this as an attempt to make my REPL more extensive. 

3) I did not improve runtime or space complexity, as it is not required for Stars 1.

4) My system tests run using the ./cs32-test tests/student/stars/stars1/*.test commmand. I did not write 
JUnit tests as I am saving it for the later iterations of this project. 
   
5) My program can be built by doing mvn build. I was having a problem sometime where I had to make sure my 
terminal was running Java 11 before building or I would get an error (I also have Java 8 installed and setup
   my IntelliJ with Java 1.8 for TAing CS15). Make sure that Java 11 is running and my project should build 
   just fine. To run my project, enter "./run".
   
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
   
-----------------------------------------------------------------------------------------------------------------

Stars 2:

1) I am having a few bugs with my neighbors methods and radius methods. They pass the system tests,
but they do not work on larger input files, such as stardata.csv When I run the methods on the larger
data files, I am getting a stackoverflow error. I am planning to fix this for stars 3.
   
2) In addition to the design choices from stars 1 (see above), one design choice that I made that is specific 
   to my project was including a Node class. This class, which acts as a wrapper class for objects held in 
   my KDTree, allows Nodes to store and check for left and right children.
   
Another design choice that I made was making CordComparable interface. This allows my Nodes to be more extensible,
as the only requirement for the objects passed into the Nodes is that they are able to be compared some way by
coordinates, since the KDTree requires this. My CordComparable interface allows for any object that has coordinates
or values of any dimension to be held in my KDTree. 

3) I did not improve runtime or space complexity, as it is not required for Stars 2.
   The only thing I did was implement the KDTree search methods.

4) My JUnit tests can be run by typing mvn test. My Stars 1 system tests can be run by entering
   "./cs32-test tests/student/stars/stars2/*.test" into the terminal. My Stars 2 system tests can be
   run by entering "./cs32-test tests/student/stars/stars2/*.test" into the terminal.
   
5) My program can be built by doing mvn build. I was having a problem sometime where I had to make sure my
   terminal was running Java 11 before building or I would get an error (I also have Java 1.8 installed and 
   setup my IntelliJ with Java 1.8 for TAing CS15). Make sure that Java 11 is running and my project should 
   build just fine. To run my project, enter "./run". Running mvn package works as well for compiling.
   
6) i. A problem you would run into while finding points close together on the earth surface is that you would 
   have to find the distance across two points on a sphere. Since the Earth's surface is curved, we cannot
   use the Euclidean distance for points that are close together. Furthermore, you cannot travel through
   the surface of the earth to reach a point, which is another reason you would not want to use the 
   Euclidean distance. Instead, you might want to use longitude and latitude to calculate distance, or turn 
   your points into a 2D representation. However, another problem you would run into if you searched for points
   in a 2D space is if your KDTree only can build and search for points that are 3D. This would be a problem
   if your KDTree was not extensible enough.

ii. Once change I would have to make to allow Collection<Star> db = new KDTree<Star>() to run and compile
properly is to allow my KDTree to take a generic type of some type <CordComparable> for instance. Currently,
my KDTree does not take any types, it only accepts a list of nodes passed into the constrctor. Another change
I would have to make is make my KDTree star class implement the Collection interface. This would require me 
to add more methods included in this interface, such as equals and hashCode, and also to change the current 
methods I have for building to fit the Collection interface methods better. Finally, another change I would
have to make is to make my KDTree able to hold objects that don't even implement my CordComparable interface.
This would require me to find a different way to compare objects that don't have coordinates. 

7) I am planning on fixing my CheckStyle errors in Stars 3. 

-----------------------------------------------------------------------------------------------------------------

Stars 3:

NOTE: My accessibility_testing.txt file is in the same directory/level as my README.

1) This is not a bug, but my naive methods are really slow for very large inputs. 
To account for this, I made 1000 my limit for neighbor number and made 100 my limit for my radius 
for the model based testing so that I could run my tests within a reasonable time limit (my Junit tests 
take about a minute and a half to run). Besides that, I have no known bugs. 

2) In addition to the design choices I made in Stars 1 and 2 (see above), the only significant design choice
I made on my front end website was using a checkbox to tell if a naive method was being called instead of the
KDTree method. I added two checkboxes, one for neighbors and one for radius, and I used the QueryParamsMap 
   to tell if the boxes were checked or not so that I could run the appropriate method in my SubmitHandlers. 

3) I did not improve runtime or space complexity, as it is not required for Stars 3.
   
4) My JUnit tests can be run by typing mvn test. My Stars 1 system tests can be run by entering
   "./cs32-test tests/student/stars/stars2/*.test" into the terminal. My Stars 2 system tests can be
   run by entering "./cs32-test tests/student/stars/stars2/*.test" into the terminal. 
   
I also wanted to note that I did not do MBT in stars 2 because I did not have time, so I just went straight
to PBT in Stars 3.

5) My program can be built by doing mvn build. I was having a problem sometime where I had to make sure my
   terminal was running Java 11 before building or I would get an error (I also have Java 1.8 installed and setup
   my IntelliJ with Java 1.8 for TAing CS15). Make sure that Java 11 is running and my project should build and 
   compile just fine. To run my project, enter "./run". Running mvn package works as well for compiling. For 
   loading the gui, you can run "./run --gui". Note that the user still has to load star data from the terminal 
   to query stars. You should be able to find the website at http://localhost:4567/stars .
   
IMPORTANT NOTE: I was not able to run my code on department machines because I was getting an error when 
trying to run source cs032_java_reset through SSH. Therefore, I was not able to switch to Java 11 and I 
could not run mvn package or ./run. I asked on piazza about this problem but it was never answered, 
so I just wanted to let whoever is reading this know about my situation. 

6) There are no design questions for Stars 3. 

7) My project has no CheckStyle errors. 