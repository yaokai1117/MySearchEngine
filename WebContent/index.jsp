<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.LinkedList" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="search.Searcher" %>
<%@ page import="org.apache.lucene.search.ScoreDoc" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<%!
	Searcher searcher = new Searcher("/home/yaokai/workspace/WebInfoLab/indexCnTest");
	ArrayList<ScoreDoc> scoreDocs = new ArrayList();
	String searchStr;
	int curPage;
%>
<%
	String tmp = request.getParameter("curPage");	// get current page number
	if(tmp==null){
 		tmp="1";
	}
	curPage = Integer.parseInt(tmp);
%>

	<form >
      <div><input name="search"></input></div>
      <div><input type="submit" value="Search" /></div>
    </form>
<%
	searchStr = request.getParameter("search");
	if (searchStr != null)
		searchStr = new String(searchStr.getBytes("ISO8859_1"), "UTF-8");
%>
    <a href="index.jsp?curPage=<%=curPage+1%>&search=<%=searchStr%>" >下一页</a>
	<a href="index.jsp?curPage=<%=curPage-1%>&search=<%=searchStr%>" >上一页</a>
	<a href="index.jsp?curPage=1&search=<%=searchStr%>" >首页</a>
	<br/>
<%
	if (searchStr != null) {
		if (scoreDocs.isEmpty())
			scoreDocs.add(new ScoreDoc(0, Integer.MAX_VALUE));
		LinkedList<String> results = searcher.search(searchStr, scoreDocs.get(curPage-1));
		for (String result : results)
			out.println(result);
		scoreDocs.add(searcher.getLastScore());
	}
%>	
</body>
</html>