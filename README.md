# ScoreBoard

ScoreBoard is a Java library for managing and keeping track of ongoing matches. It provides functionality to add and finish matches, update match scores, and retrieve the current list of matches in a sorted order based on total scores and start time of matches.

### Installation

This project uses Maven for its build system. To install the library, follow these steps:

1. Clone the repository to your local machine:
 
`git clone https://github.com/kgonia/scoreboard.git`


2. Navigate to the project directory:

`cd scoreboard
`

3. Install the library in your local Maven repository:

`mvn clean install`

This will build the project and place the generated .jar file into your local Maven repository. After running this command, other Maven projects on your machine will be able to include ScoreBoard as a dependency.

### Usage

To use ScoreBoard in your project, you will need to add it as a dependency in your project's pom.xml file. Below is an example of how you can do this:

```
<dependencies>
    <dependency>
        <groupId>org.example</groupId>
        <artifactId>sport</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
</dependencies>
```

Once you have added ScoreBoard as a dependency, you can import and use its classes in your project like any other Java library. For example:

```
import org.example.ScoreBoard;

public class Main {
    public static void main(String[] args) {
        ScoreBoard scoreBoard = new ScoreBoard();
        // Use scoreBoard...
    }
}
```
