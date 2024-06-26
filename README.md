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

- CLEAR: clear the map

- REMOVEALL: remove all members for a key and removes the key from the dict

- ITEMS: get all the pairs


## Prerequisites

### For Mac
#### Install JAVA (11+)

1. Install Java 11 with Homebrew: Open your Terminal and execute the following command to install AdoptOpenJDK's Java 11:


    ```
   brew install --cask adoptopenjdk11
    ```


3. Configure Environment Variables: Next, you need to configure the JAVA_HOME environment variable.
Open your shell configuration file (~/.bash_profile for bash or ~/.zshrc for zsh):
   
   ```
   nano ~/.bash_profile
   ```
   
   Or, if you are using zsh:
   
   ```
   nano ~/.zshrc
   ```
   
   Add the following lines to the end of the file:
   
    ```
    export JAVA_HOME=$(/usr/libexec/java_home)
    export PATH=$JAVA_HOME/bin:$PATH
    ```
   
   Save the file by pressing Control + X, then Y to confirm, and Enter to finalize.


5. Reload the Configuration File: Execute the following command to reload your shell configuration:
    ```source ~/.bash_profile``` Or, if you are using zsh:  ```source ~/.zshrc```


6. Verify the Java Installation:

   Run the following command in Terminal to verify that Java is installed correctly and to confirm the version is Java 11:
   
   ```
   java -version
   ```
       
   If everything is set up correctly, you should see information about the installed Java version.

   
   #### Install Gradle on Mac
   
   ```
   brew install gradle
   gradle -v
   ```
   



### For Windows
1. Install JAVA 11+ and Gradle
2. Check version




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


## Testing


Get unit test coverage report

After gradle build, go to ```MultiStringsDictionaryApp/app/build/reports/tests/packages/index.html```, copy path and open by browser to see unit test coverage


End to End testing doc: https://docs.google.com/document/d/1p1yGoHOGiRYSzuxHY4DzSzi6JwgcuVZBATvH2vYzrFQ/edit?usp=sharing




