runBackendTests: BackendDeveloperTests.class
	java -jar ../junit5.jar --class-path=. --select-class=BackendDeveloperTests

BackendDeveloperTests.class: BackendDeveloperTests.java
	javac -cp ../junit5.jar:. BackendDeveloperTests.java

clean:
	rm *.class
