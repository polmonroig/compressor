JAVAC = javac -cp
JAR = jar cmf

# path to the presentation controller
presentationController = presentation/controllers/CtrlPresentation.java
# path to the data controller
dataController = persistencia/controllers/CtrlPersistencia.java
# path to every domain class
domainObjects = domain/*.java
# path to the domain controller
domainController = domain/controllers/CtrlDomain.java

# collection of files needed to run the main program
files = $(domainObjects) $(domainController) $(dataController) $(domainObjects) \
        $(domainController) $(presentationController)

# collection of compiled classes
classes = $(files:.java=.class)

# manifest name
manifest = manifest.mf

# executable name
programName = program.jar

program:
	echo 'Main-Class: presentation.controllers.CtrlPresentation' > $(manifest)
	$(JAVAC) $(files)
	$(JAR) $(manifest) $(programName) $(classes)

clean:
	rm -f $(classes) $(manifest) $(programName)
	rm -f
