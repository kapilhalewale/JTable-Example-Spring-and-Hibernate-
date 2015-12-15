

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		HashMap<String, Object> JSONROOT = new HashMap<String, Object>();

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		if (action.equals("list")) {
		try{
		        // Fetch Data from Student Table
				List<Bean> myBean = new ArrayList<Bean>();
			       int startPageIndex= Integer.parseInt(request.getParameter("jtStartIndex"));
			       int numRecordsPerPage=  Integer.parseInt(request.getParameter("jtPageSize"));
			    	       
				myBean = Dao.getAllProduct(startPageIndex, numRecordsPerPage);
				int count = Dao.getProductCount();
		        // Return in the format required by jTable plugin
		        JSONROOT.put("Result", "OK");
		        JSONROOT.put("TotalRecordCount", count);

		        JSONROOT.put("Records", myBean);

		        // Convert Java Object to Json
		        String jsonArray = gson.toJson(JSONROOT);

		        response.getWriter().print(jsonArray);
		        System.out.println("List Product function called");
		} catch (Exception ex) {
		        JSONROOT.put("Result", "ERROR");
		        JSONROOT.put("Message", ex.getMessage());
		        String error = gson.toJson(JSONROOT);
		        response.getWriter().print(error);
		}
		}
		if (action.equals("add")) {
			System.out.println("you called add method in the controller");
			try{
		        Bean bean = new Bean();

		        if (request.getParameter("Name") != null) {
		                String name = request.getParameter("Name");
		               bean.setName(name);
		        }

		        if (request.getParameter("price") != null) {
		                float price = Integer.parseInt(request.getParameter("price"));
		                bean.setPrice(price);
		        }

		        if (request.getParameter("expiryYear") != null) {
		                String expiryYear = request.getParameter("expiryYear");
		                bean.setExpiryYear(expiryYear);
		        }
		        		Dao.add(bean);

		        // Return in the format required by jTable plugin
		        JSONROOT.put("Result", "OK");
		        JSONROOT.put("Record", bean);

		        // Convert Java Object to Json
		        String jsonArray = gson.toJson(JSONROOT);
		        response.getWriter().print(jsonArray);
		} catch (Exception ex) {
		        JSONROOT.put("Result", "ERROR");
		        JSONROOT.put("Message", ex.getMessage());
		        String error = gson.toJson(JSONROOT);
		        response.getWriter().print(error);
		}
		}
	// Drop Logic Here		
			if (action.equals("drop")) {
				System.out.println("Drop in controller");
				Dao.deleteStudent(Integer.parseInt(request.getParameter("productId")));
				JSONROOT.put("Result", "OK");

				// Convert Java Object to Json
				String jsonArray = gson.toJson(JSONROOT);
				response.getWriter().print(jsonArray);
			}
			if (action.equals("update")) {
				System.out.println("update in controller");
				Bean bean = new Bean();
				if (request.getParameter("productId") != null) {
					int productId = Integer.parseInt(request.getParameter("productId"));
					bean.setProductId(productId);
				}

		        if (request.getParameter("Name") != null) {
	                String name = request.getParameter("Name");
	               bean.setName(name);
	        }

	        if (request.getParameter("price") != null) {
	                float price = Integer.parseInt(request.getParameter("price"));
	                bean.setPrice(price);
	        }

	        if (request.getParameter("expiryYear") != null) {
	                String expiryYear = request.getParameter("expiryYear");
	                bean.setExpiryYear(expiryYear);
	        }
	        	// Method to call update operation
	        	Dao.update(bean);

				// Return in the format required by jTable plugin
				JSONROOT.put("Result", "OK");
				JSONROOT.put("Record", bean);

				// Convert Java Object to Json
				String jsonArray = gson.toJson(JSONROOT);
				response.getWriter().print(jsonArray);
			}
			if (action.equals("search")) {
				System.out.println("you called search button");
			}
		}
	}

