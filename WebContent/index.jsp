<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.LinkedList" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="search.Searcher" %>
<%@ page import="org.apache.lucene.search.ScoreDoc" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>My search engine</title>
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
<% 
	Searcher searcher;
	if (session.getAttribute("searcher") == null){
		session.setAttribute("searcher", new Searcher("/home/yaokai/workspace/WebInfoLab/indexCnTest"));
	}
	searcher = (Searcher)session.getAttribute("searcher");
%>

<%!
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

 	<form action="result.jsp" name="search" method="get" 
		 enctype="application/x-www-form-urlencoded"> 
      <div 	style="text-align:center;">
      	<p align="center"><img src="logo.gif" /></p> 
      	<input id = "textArea" name="search"></input>
      	<input id = "search" type="submit" value="Search" />
      </div>
    </form>
    
</body>
</html>