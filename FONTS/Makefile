######################
#		MAKEFILE
# To compile and build the program: 'make program'
# To clean manifest, *.jar and *.class: 'make clean'
#####################


# bash commands
JAVAC = javac --release 8 -cp
JAR = jar cmf 

#############################
#    VARIABLE DEFINITION
#############################

# path to the presentation controller
presentationController = presentation/controllers/PresentationCtrl.java
# path to the data controller
dataController = data/controllers/DataCtrl.java
# path to the domain controller
domainController = domain/controllers/DomainCtrl.java

# path to every domain class
domainObjects = domain/*.java

presentationObjects = presentation/*.java

files = $(presentationController) $(dataController)  \
	$(domainController) $(domainObjects) \
	$(presentationObjects) $(presentationController)  Main.java

# collection of compiled classes
classes = $(files:.java=.class)

# manifest name
manifest = manifest.mf

# executable name
programName = program.jar



#############################
#    RULES DEFINITION
#############################

# compile and build main program
program:
	echo 'Main-Class: Main' > $(manifest)
	$(JAVAC) $(files)
	$(JAR) $(manifest) $(programName) $(classes)

# clean all
clean:
	rm -f $(classes) $(manifest) *.jar 
