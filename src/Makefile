######################
#		MAKEFILE
# To compile and build the program: 'make program'
# To compile and build a driver: 'make driverX' where X=ALGORITHM
# To clean manifest, *.jar and *.class: 'make clean'
#####################


# bash commands
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

etc = domain/Algoritme.java domain/Estadistica.java domain/EstadisticaLocal.java
utils = domain/Utils.java

# LZ78 driver
lz78Driver = $(dataController) $(etc) $(utils)  \
			 domain/LZ78.java domain/drivers/DriverLZ78.java
# LZW driver
lzwDriver = $(dataController) $(etc) $(utils)  \
			domain/LZW.java domain/drivers/DriverLZW.java
# LZSS driver
lzssDriver = $(dataController) $(etc)  \
			 domain/LZSS.java domain/drivers/DriverLZSS.java
# JPEG driver
jpegDriver = $(dataController) $(etc) $(utils)  \
			 domain/Huffman.java domain/JPEG.java \
			 domain/drivers/DriverJPEG.java

# collection of files needed to run the main program
files = $(domainObjects) $(domainController) $(dataController) $(domainObjects) \
        $(domainController) $(presentationController)

# collection of compiled classes
classes = $(files:.java=.class)

# manifest name
manifest = manifest.mf

# executable name
programName = program.jar

# compile and build drivers
driverlz78:
	echo 'Main-Class: domain.drivers.DriverLZ78' > $(manifest)
	$(JAVAC) $(dataController) $(lz78Driver)
	$(JAR) $(manifest) DriverLZ78.jar $(lz78Driver:.java=.class)

driverlzw:
	echo 'Main-Class: domain.drivers.DriverLZW' > $(manifest)
	$(JAVAC) $(dataController) $(lzwDriver)
	$(JAR) $(manifest) DriverLZW.jar $(lzwDriver:.java=.class)

driverlzss:
	echo 'Main-Class: domain.drivers.DriverLZSS' > $(manifest)
	$(JAVAC) $(dataController) $(lzssDriver)
	$(JAR) $(manifest) DriverLZSS.jar $(lzssDriver:.java=.class)

driverjpeg:
	echo 'Main-Class: domain.drivers.DriverJPEG' > $(manifest)
	$(JAVAC) $(dataController) $(jpegDriver)
	$(JAR) $(manifest) DriverJPEG.jar $(jpegDriver:.java=.class)


# compile and build main program
program:
	echo 'Main-Class: presentation.controllers.presentationCtrl' > $(manifest)
	$(JAVAC) $(files)
	$(JAR) $(manifest) $(programName) $(classes)

# clean all
clean:
	rm -f $(classes) $(manifest) $(programName) $(lz78Driver:.java=.class) \
	$(lzwDriver:.java=.class) $(lzssDriver:.java=.class) $(jpegDriver:.java=.class)
