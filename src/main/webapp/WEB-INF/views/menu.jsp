<%-- 
    Document   : menu
    Created on : 28.07.2017, 9:25:50
    Author     : smit
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<nav class="navbar navbar-default navbar-static">
  <div class="container">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="/">AmvSampler</a>
    </div>
    <div id="navbar" class="collapse navbar-collapse">
      <ul class="nav navbar-nav">
            <li><a href="/">Home</a></li>
            <li><a href="/source_folder">Source folder</a></li>
            <li><a href="/queue">Queue</a></li>
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                    Base of samples<span class="caret"></span>
                </a>
                <ul class="dropdown-menu">
                    <li>
                        <a href="/base_of_samples">All samples</a>
                    </li>
                    <li>
                        <a href="/base_of_samples_not_sorted">Not sorted</a>
                    </li>
                </ul>
            </li>
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                    Groups<span class="caret"></span>
                </a>
                <ul class="dropdown-menu">
                    <li>
                        <a href="/groups?groupType=titles">Groups by titles</a>
                    </li>
                    <li>
                        <a href="/groups?groupType=tags">Groups by tags</a>
                    </li>
                </ul>
            </li>
            <li><a href="/view_sample_without_tag">Samples without tags</a></li>
            <li><a href="/settings">Settings</a></li>
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                    Exit<span class="caret"></span>
                </a>
                <ul class="dropdown-menu">
                    <li><a href="/shutdown">Shutdown AmvSamples Suit</a></li>
                </ul>
            </li>
        </ul>
    </div>
  </div>
</nav>
        

