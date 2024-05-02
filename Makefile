runTests: BackendDeveloperTests.class
	java -jar ../junit5.jar --class-path=. --select-class=BackendDeveloperTests

runApp: App.class
	#java App
	java --module-path ../javafx/lib --add-modules javafx.controls App

App.class:
	#javac App.java
	javac --module-path ../javafx/lib --add-modules javafx.controls -cp .:../junit5fx.jar App.java

BackendDeveloperTests.class: BackendDeveloperTests.java
	#javac -cp ../junit5.jar:. BackendDeveloperTests.java
	javac --module-path ../javafx/lib --add-modules javafx.controls -cp .:../junit5fx.jar BackendDeveloperTests.java


runTests2:
	javac --module-path ../javafx/lib --add-modules javafx.controls -cp .:../junit5fx.jar FrontendDeveloperTests.java
	java --module-path ../javafx/lib --add-modules javafx.controls --add-opens javafx.graphics/com.sun.javafx.application=ALL-UNNAMED -jar ../junit5fx.jar -cp . -c FrontendDeveloperTests

clean:
	rm *.class
