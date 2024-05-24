# About The Project

MultiStringsDictionary App is a command line application that stores a multivalue dictionary in memory. All keys and memebrs are strings. 

## Functions

- KEYS: get all the keys of the dictionary

- MEMBERS: get all the members of a given key

- ADD: add a key-member pair to the dictionary

- REMOVE: remove a pair, given the pair's key and member

- KEYEXISTS: true/false if a given key existed in the dictionary

- MEMBEREXISTS: true/false if a member exists within a key

- ALLMEMBERS: get all the members in the dictionary



### For more details and assumptions:

1. Qianlin Design Doc
2. Orignal Requirement
   




## Prerequisites

### For Mac
#### install JAVA (11+)

1. Install Java 11 with Homebrew: Open your Terminal and execute the following command to install AdoptOpenJDK's Java 11:


    ```brew install --cask adoptopenjdk11```


2. Configure Environment Variables: Next, you need to configure the JAVA_HOME environment variable.
Open your shell configuration file (~/.bash_profile for bash or ~/.zshrc for zsh):

    ```nano ~/.bash_profile```

Or, if you are using zsh:

    ```nano ~/.zshrc```
Add the following lines to the end of the file:

    ```
    export JAVA_HOME=$(/usr/libexec/java_home)
    export PATH=$JAVA_HOME/bin:$PATH
    ```
Save the file by pressing Control + X, then Y to confirm, and Enter to finalize.


3. Reload the Configuration File: Execute the following command to reload your shell configuration:
    ```source ~/.bash_profile``` Or, if you are using zsh:  ```source ~/.zshrc```


4. Verify the Java Installation:

Run the following command in Terminal to verify that Java is installed correctly and to confirm the version is Java 11:
    ```java -version```
If everything is set up correctly, you should see information about the installed Java version.


#### Install Gradle on Mac
```
brew install gradle
gradle -v
```




### For Windows






## Build and Run
Build and run unit testing
```
gradle build
```

Run
```
gradle run --console=plain
```

Exit
```
EXIT
```

For commands, please refer to 1. Qianlin Design Doc  ;  2. Orignal Requirement


## Testing


Get unit test coverage report
After gradle build, go to ```MultiStringsDictionaryApp/app/build/reports/tests/packages/index.html```, copy path and open by browser


End to End testing Doc
link:



Cloning the Code
Clone this code into your local using the following command

git clone https://github.com/aditya-sridhar/simple-gradle-java-app.git

Building the Application
The application can be built using the following command

Windows : gradlew.bat build

Linux/MacOS: ./gradlew build

Running the Application
The application can be run using the following command

Windows : gradlew.bat run

Linux/MacOS: ./gradlew run

Tests
After the Application is built using the Build command, the test results report can be found in below file build/reports/tests/test/index.html



