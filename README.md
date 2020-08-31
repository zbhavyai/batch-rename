## Batch Rename
A simple Java utility to program to recursively rename files or folders by replacing spaces with underscore


### Features
+ Easily configure whether to recursively descend into child directories or not
+ Easily configure whether to rename child directories themselves or not
+ Maintain a list of files extensions that are to be excluded from renaming (default skipped extension is .mp3)


### Usage
1. The program can be fired using simple java compile and run
    ```
    javac BatchRename/BatchRename.java BatchRename/RemoveDirectory.java BatchRename/Renaming.java
    java BatchRename.BatchRename
    ```

1. Wrapper scripts for Linux and Windows are also provided

    For linux,
    ```
    chmod +x BatchRename.sh
    ./BatchRename.sh
    ```

    For windows,
    ```
    BatchRename.cmd
    ```

1. If directory path is not provided as a command line arguement, the program shall prompt for input of directory name, both absolute or relative from location of the program shall do

1. The program shall not handle the permissions, and may not rename files/directories if the user don't have access. To rename such files/directories, run the program using apt permissions.

