package f1.data.parse.telemetry;

//Key value for the lapsPerSetup hashMap. Used to identify if the setup is the same, but a tire change has occured.
//Looks at the fitted compound id, so soft 1 != soft 2, etc.
public record SetupTireKey(int setupNumber, int fittedTireId) {
}
