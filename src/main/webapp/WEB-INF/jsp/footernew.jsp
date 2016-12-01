    <!-- #main.container -->
    <footer id="footer" class="main-footer">
        <div class="container">
            <a href="home.htm" class="colophon_link back_home pull-left">Go back to home</a>
            <c:choose>
              	<c:when test="${ !(myPrimaryRole == 'CS')}">
                	<a href="logout.htm" class="pull-right colophon_link">Log out</a>
         		</c:when>
         		<c:otherwise>
         		 	<a href="#" class="pull-right colophon_link">Log out</a>
         		</c:otherwise>
            </c:choose>
           
            <jsp:useBean id="date" class="java.util.Date" />
            <p class="colophon">&#169;2010 - <fmt:formatDate value="${date}" pattern="yyyy" /> Tenant Evaluation, LLC. All rights reserved.</p>
        </div>
    </footer>
</body>
</html>