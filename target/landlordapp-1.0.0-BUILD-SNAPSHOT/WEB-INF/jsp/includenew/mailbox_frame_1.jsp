<!-- The body of the page -->
<div id="main" class="container">
        <div class="row mailbox_area">
            <div class="section_icon">
                <img src="img/icon_mailbox.png" alt="Mailbox" width="120">
            </div>
            <div id="mailbox_container">
                <%@ include file="/WEB-INF/jsp/includenew/search_bar_1.jsp" %>
                <div class="user_stat_list">
                    <section class="property_cards">
                        <div class="content">
                             <%@ include file="/WEB-INF/jsp/includenew/property_cards_gen_1.jsp" %>
                        </div>
                    </section>
                    
                    <div class="nav_pager">
                        <a href="<c:out value="${jsMailbox_Holder}"/>s?page=${prevPage}" class="pgr"><img src="img/pgr_prv.png" alt="Previous page" width="20" /></a>
                        <c:out value="${pageNo }"/> of <c:out value="${pages }"/> pages
                        <a href="<c:out value="${jsMailbox_Holder}"/>s?page=${nextPage}" class="pgr"><img src="img/pgr_nxt.png" alt="Next page" width="20" /></a>
                    </div>

                </div><!-- ################# EO .user_stat_list ################# -->
                <div class="mailbox_content">
                    <div class="content">
                         <div id="<c:out value="${jsMailbox_Holder}"/>"></div>
                     </div>
                </div><!-- ################# EO .mailbox_contet ################# -->
            </div><!-- ################# EO #mailbox_container ################# -->
        </div>
        <!-- .row -->
        <!--         <div class="save_buttons text-right">
            <button class="btn btn-sm btn-secondary">Save</button>
            <button class="btn btn-sm btn-secondary">Cancel</button>
        </div> -->
    </div>
    <!-- #main.container -->