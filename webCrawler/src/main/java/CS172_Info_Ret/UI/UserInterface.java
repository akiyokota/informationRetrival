package CS172_Info_Ret.UI;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import CS172_Info_Ret.SearchEngine.Searcher.searcher;


public class UserInterface extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		resp.getWriter().println("hello from UI");
		BufferedReader reader = req.getReader();

	    System.out.println(reader.readLine());
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		String query = req.getReader().readLine().toString();
		searcher s = new searcher();
		List<String> results = s.DocToList(s.search(s.buildQuery(query)));
		
		String json = new Gson().toJson(results);
		System.out.println(json);
		resp.getWriter().println(json);

	}

}
