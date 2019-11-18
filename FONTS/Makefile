######################
#		MAKEFILE
# To compile and build the program: 'make program'
# To compile and build a driver: 'make driverX' where X=CLASS
# To compile and test a class: 'make testX' where X=CLASS
# To clean manifest, *.jar and *.class: 'make clean'
#####################


# bash commands
JAVAC = javac -cp
JAR = jar cmf

#############################
#    VARIABLE DEFINITION
#############################

# path to the presentation controller
presentationController = presentation/controllers/PresentationCtrl.java
# path to the data controller
dataController = data/controllers/DataCtrl.java
# path to every domain class
domainObjects = domain/*.java
# path to the domain controller
domainController = domain/controllers/DomainCtrl.java

etc = domain/Algorithm.java domain/Stats.java
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

# driver DomainCtrl
domainControllerDriver = $(dataController) $(domainObjects) $(domainController) \
                         domain/drivers/DriverDomainCtrl.java $(domainObjects)

# driver DataCtrl
dataControllerDriver = $(dataController) data/drivers/DriverDataCtrl.java

# driver and junit utils
utilsDriver = $(utils) domain/drivers/UtilsTest.java domain/drivers/DriverUtils.java

# stats driver
statsDriver = domain/Stats.java domain/drivers/DriverStats.java domain/Stats.java

# globalStats driver
globalStatsDriver = domain/Stats.java domain/GlobalStats.java domain/Stats.java domain/drivers/DriverGlobalStats.java

# huffman driver
huffmanDriver = domain/Huffman.java domain/drivers/DriverHuffman.java domain/Huffman.java

# collection of files needed to run the main program
files = $(domainObjects) $(domainController) $(dataController) $(domainObjects) \
        $(domainController) $(presentationController)

# collection of compiled classes
classes = $(files:.java=.class)

# manifest name
manifest = manifest.mf

# executable name
programName = program.jar


#############################
#    RULES DEFINITION
#############################

# compile and build drivers
driverLZ78:
	echo 'Main-Class: domain.drivers.DriverLZ78' > $(manifest)
	$(JAVAC) $(dataController) $(lz78Driver)
	$(JAR) $(manifest) DriverLZ78.jar $(lz78Driver:.java=.class)

driverLZW:
	echo 'Main-Class: domain.drivers.DriverLZW' > $(manifest)
	$(JAVAC) $(dataController) $(lzwDriver)
	$(JAR) $(manifest) DriverLZW.jar $(lzwDriver:.java=.class)

driverLZSS:
	echo 'Main-Class: domain.drivers.DriverLZSS' > $(manifest)
	$(JAVAC) $(dataController) $(lzssDriver)
	$(JAR) $(manifest) DriverLZSS.jar $(lzssDriver:.java=.class)

driverJPEG:
	echo 'Main-Class: domain.drivers.DriverJPEG' > $(manifest)
	$(JAVAC) $(dataController) $(jpegDriver)
	$(JAR) $(manifest) DriverJPEG.jar $(jpegDriver:.java=.class)

driverDomainCtrl:
	echo 'Main-Class: domain.drivers.DriverDomainCtrl' > $(manifest)
	$(JAVAC) $(dataController) $(domainControllerDriver)
	$(JAR) $(manifest) DriverDomainCtrl.jar $(domainControllerDriver:.java=.class)

driverDataCtrl:
	echo 'Main-Class: data.drivers.DriverDataCtrl' > $(manifest)
	$(JAVAC) $(dataController) $(dataControllerDriver)
	$(JAR) $(manifest) DriverDataCtrl.jar $(dataControllerDriver:.java=.class)



testUtils:
	javac -cp .:"../EXE/junit/*"  domain/drivers/UtilsTest.java
	java -cp .:"../EXE/junit/*" org.junit.runner.JUnitCore domain.drivers.UtilsTest

driverStats:
	echo 'Main-Class: domain.drivers.DriverStats' > $(manifest)
	$(JAVAC) $(statsDriver)
	$(JAR) $(manifest) DriverStats.jar $(statsDriver:.java=.class)

driverGlobalStats:
	echo 'Main-Class: domain.drivers.DriverGlobalStats' > $(manifest)
	$(JAVAC) $(globalStatsDriver)
	$(JAR) $(manifest) DriverGlobalStats.jar $(globalStatsDriver:.java=.class)


driverHuffman:
	echo 'Main-Class: domain.drivers.DriverHuffman' > $(manifest)
	$(JAVAC) $(huffmanDriver)
	$(JAR) $(manifest) DriverHuffman.jar $(huffmanDriver:.java=.class)

# compile and build main program
program:
	echo 'Main-Class: presentation.controllers.PresentationCtrl' > $(manifest)
	$(JAVAC) $(files)
	$(JAR) $(manifest) $(programName) $(classes)

# clean all
clean:
	rm -f $(classes) $(manifest) *.jar $(lz78Driver:.java=.class) \
	$(lzwDriver:.java=.class) $(lzssDriver:.java=.class) $(jpegDriver:.java=.class) \
	$(domainControllerDriver:.java=.class)  $(dataControllerDriver:.java=.class) \
	$(utilsDriver:.java=.class)  $(statsDriver:.java=.class) \
	$(globalStatsDriver:.java=.class) $(huffmanDriver:.java=.class) domain/drivers/UtilsTest.class
