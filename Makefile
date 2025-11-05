runTests:
	javac --module-path ../javafx/lib --add-modules javafx.controls -cp .:../junit5fx.jar FrontendDeveloperTests.java
	java --module-path ../javafx/lib --add-modules javafx.controls --add-opens javafx.graphics/com.sun.javafx.application=ALL-UNNAMED -jar ../junit5fx.jar -cp . -c FrontendDeveloperTests

runApp: Backend.class Frontend.class DijkstraGraph.class
	javac --module-path ../javafx/lib --add-modules javafx.controls App.java  
	java --module-path ../javafx/lib --add-modules javafx.controls App

Backend.class: Backend.java
	javac Backend.java

Frontend.class: Frontend.java
	javac --module-path ../javafx/lib --add-modules javafx.controls Frontend.java 

DijkstraGraph.class: DijkstraGraph.java
	javac --module-path ../javafx/lib --add-modules javafx.controls -cp .:../junit5fx.jar DijkstraGraph.java

clean:
	rm *.class

runBDTests: BackendDeveloperTests.class
	java -jar ../junit5.jar --class-path=. --select-class=BackendDeveloperTests

BackendDeveloperTests.class: BackendDeveloperTests.java
	javac -cp ../junit5.jar:. BackendDeveloperTests.java

