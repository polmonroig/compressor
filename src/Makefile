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

# LZ78 driver
lz78Driver = domain/drivers/DriverLZ78.java
# LZW driver
lzwDriver = domain/drivers/DriverLZW.java
# LZSS driver
lzssDriver = domain/drivers/DriverLZSS.java

# collection of files needed to run the main program
files = $(domainObjects) $(domainController) $(dataController) $(domainObjects) \
        $(domainController) $(presentationController)

# collection of compiled classes
classes = $(files:.java=.class)

# manifest name
manifest = manifest.mf

# executable name
programName = program.jar

# compile and build main program
program:
	echo 'Main-Class: presentation.controllers.CtrlPresentation' > $(manifest)
	$(JAVAC) $(files)
	$(JAR) $(manifest) $(programName) $(classes)

# clean all
clean:
	rm -f $(classes) $(manifest) $(programName)
	rm -f
