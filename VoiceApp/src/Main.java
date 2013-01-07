import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;

public class Main {

	public static void main(String args[]) {
		ConfigurationManager cm = new ConfigurationManager(Main.class.getResource("speech/config.xml"));
		Recognizer recognizer = (Recognizer) cm.lookup("recognizer");
		recognizer.allocate();
		Microphone microphone = (Microphone) cm.lookup("microphone");
		if (!microphone.startRecording()) {
			System.out.println("Cannot start microphone.");
			recognizer.deallocate();
			System.exit(1);
		}
		do {
			System.out.println("Start speaking please. Press Ctrl-C to quit.\n");
			Result result = recognizer.recognize();
			if (result != null) {
				String resultText = result.getBestFinalResultNoFiller();
				System.out.println((new StringBuilder()).append("You said: ").append(resultText).append('\n').toString());
			} else {
				System.out.println("I can't hear what you said.\n");
			}
		} while (true);
	}
}
