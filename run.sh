export CLASSPATH="target/Chandu-1.0-SNAPSHOT.jar;javax.json-1.1.2.jar;javax.json-api-1.1.2.jar;target/test-classes"

# Generates "EXPRESS" froM JSON
java net.coderextreme.JSON2EXPRESS HelloWorldProgramOutput.json

# Programmically generates "EXPRESS" and XML
java net.coderextreme.Event
