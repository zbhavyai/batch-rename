#!/bin/bash


#compile the files at the same time
javac BatchRename/BatchRename.java BatchRename/RemoveDirectory.java BatchRename/Renaming.java

#run the package.class
#java -cp . BatchRename.BatchRename ${1}
java BatchRename.BatchRename ${1}

