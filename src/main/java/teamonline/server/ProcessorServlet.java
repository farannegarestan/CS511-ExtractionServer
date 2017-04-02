package teamonline.server;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.process.PTBTokenizer;

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
		String text = request.getParameter("text");
		String[] items = text.split(Pattern.quote("||"));
		for(String item: items) {
			Reader reader = new StringReader(item);
			PTBTokenizer<Word> tokenizer = PTBTokenizer.newPTBTokenizer(reader);
			Word nextToken = null;
			while( tokenizer.hasNext()) {
				nextToken = tokenizer.next();
				System.out.println(nextToken.word());
			}
		}
		request.getSession(true).setAttribute("text", text);
	}

}