<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.LinkedList" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="search.Searcher" %>
<%@ page import="org.apache.lucene.search.ScoreDoc" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Search result for <%=request.getParameter("curPage")%></title>
<style> 
	#search{ 
	width:78px; 
	height:28px; 
	font:14px "宋体"
	} 
	#textArea{ 
	width:300px; 
	height:30px; 
	font:14px "宋体"
	} 
	.queryword{
	color:#dd4b39
	}
</style> 
</head>
<body>

<%!
	ArrayList<ScoreDoc> scoreDocs = new ArrayList();
	String searchStr;
	int curPage;
%>

<% 
	Searcher searcher;								// get searcher
	if (session.getAttribute("searcher") == null){
		session.setAttribute("searcher", new Searcher("/home/yaokai/workspace/WebInfoLab/indexCnTest"));
	}
	searcher = (Searcher)session.getAttribute("searcher");
%>

<%
	String tmp = request.getParameter("curPage");	// get current page number
	if(tmp==null){
 		tmp="1";
	}
	curPage = Integer.parseInt(tmp);
%>

<%
	searchStr = request.getParameter("search");
	if (searchStr != null)
		searchStr = new String(searchStr.getBytes("ISO8859_1"), "UTF-8");
%>

	<form >
      <div>
      	<input id = "textArea" name="search"></input>
      	<input id = "search" type="submit" value="Search" />
      </div>
    </form>
    
	
<%
	if (searchStr != null && !searchStr.isEmpty()) {
		if (scoreDocs.isEmpty())
			scoreDocs.add(new ScoreDoc(0, Integer.MAX_VALUE));
		LinkedList<String> results = searcher.search(searchStr, scoreDocs.get(curPage-1));
		for (String result : results)
			out.println(result);
		scoreDocs.add(searcher.getLastScore());
	}
%>	

	<div >
	    <a href="result.jsp?curPage=<%=curPage+1%>&search=<%=searchStr%>" >下一页</a>
		<a href="result.jsp?curPage=<%=(curPage>1)?curPage:1%>&search=<%=searchStr%>" >上一页</a>
		<a href="result.jsp?curPage=1&search=<%=searchStr%>" >首页</a>
		<br/>
	</div>

</body>
</html>