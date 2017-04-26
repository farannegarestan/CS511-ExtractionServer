package teamonline.server;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jaunt.Element;
import com.jaunt.Elements;
import com.jaunt.NotFound;
import com.jaunt.ResponseException;
import com.jaunt.UserAgent;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.AnswerAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.util.TypesafeMap;
import edu.stanford.nlp.util.TypesafeMap.Key;

public class ProcessorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
    public ProcessorServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
		dispatcher.forward(request,response);
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String serializedClassifier = "dell_first_25_data_ner-model.ser.gz";
		System.out.println("Processing started...");
		String url = request.getParameter("url");
		String linkClass = request.getParameter("linkClass");
		UserAgent userAgent = new UserAgent();
		List<String> titles = new ArrayList<String>();
		
		List<Map<String,String>> values = new ArrayList<>();
		Set<String> columns = new HashSet<>();
		
		try {
			userAgent.visit(url);
			Elements links = userAgent.doc.findEvery("<a class=\""+linkClass+"\">");
			for(Element link : links) 
				titles.add(link.getAt("title")); 
			
			// Tokenizing
			ArrayList<List<CoreLabel>> titleTokens = new ArrayList<List<CoreLabel>>();
			for(String title: titles) {
				List<CoreLabel> tokens = new ArrayList<>();
				title = title.replace('-', ' ');
				Reader reader = new StringReader(title);
				PTBTokenizer<Word> tokenizer = PTBTokenizer.newPTBTokenizer(reader);
				Word nextToken = null;
				while(tokenizer.hasNext()) {
					nextToken = tokenizer.next();
					tokens.add(new CoreLabel(nextToken));
				}
				titleTokens.add(tokens);
			}
			
			AbstractSequenceClassifier<CoreLabel> classifier = CRFClassifier.getClassifier(serializedClassifier);
			
			for (int i=0; i < 3; i++ ) {
				List<CoreLabel> out = classifier.classify(titleTokens.get(i));
				Map<String, String> map = new HashMap<>();
				for(CoreLabel lable: out) {
					String text = "";
					String answer = "";
					for(Class<?> key: lable.keySet()) {
						String val = lable.getString( (Class<Key<String>>)key);
						//System.out.println(key + "  :  "+val);
						if (key.toString().endsWith("TextAnnotation")) {
							text = val;
						} else if (key.toString().endsWith("AnswerAnnotation")) {
							answer = val;
						}
					}
					columns.add(answer);
					map.put(answer, map.getOrDefault(answer, "") +" " + text);
				}
				values.add(map);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.getSession(true).setAttribute("columns", columns);
		request.getSession(true).setAttribute("values", values);
	}

}