package teamonline.server;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.stanford.nlp.classify.Classifier;
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.util.CoreMap;

public class ProcessorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
    public ProcessorServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
		dispatcher.forward(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Processing started...");
		String text = request.getParameter("text");
		String[] items = text.split(Pattern.quote("||"));
		StringBuffer tokenized = new StringBuffer();
		ArrayList<CoreLabel> tokens = new ArrayList<CoreLabel>();
		
		// Tokenizing
		for(String item: items) {
			item = item.replace('-', ' ');
			Reader reader = new StringReader(item);
			PTBTokenizer<Word> tokenizer = PTBTokenizer.newPTBTokenizer(reader);
			Word nextToken = null;
			while(tokenizer.hasNext()) {
				nextToken = tokenizer.next();
				tokenized.append(nextToken.word());
				System.out.println(nextToken.word());
				tokens.add(new CoreLabel(nextToken));
			}
		}
		// Classification
		String serializedClassifier = "dell_first_25_data_ner-model.ser.gz";
		try {
			AbstractSequenceClassifier<CoreLabel> classifier = CRFClassifier.getClassifier(serializedClassifier);
			List<CoreLabel> out = classifier.classify(tokens);
			for(CoreLabel lable: out) {
				System.out.println("\t" + lable.word());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.getSession(true).setAttribute("text", tokenized.toString());
	}

}