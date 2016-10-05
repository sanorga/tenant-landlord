<script type="text/javascript">

function loadDetail(id){ $('#<c:out value="${jsMailbox_Holder}"/>').load('<c:out value="${jsMailbox_Holder}"/>/' + id + '/detailview');}

$(document).ready(function() {
    loadDetail(${applications[0].applicationId});
    function loadDetail(id){ $('#<c:out value="${jsMailbox_Holder}"/>').load('<c:out value="${jsMailbox_Holder}"/>/' + id + '/detailview'); }
    $(function() {
        var firstCardId = $('a.property_card').first().attr('href');
        $("#<c:out value="${jsMailbox_Holder}"/>").load('/ui/<c:out value="${jsMailbox_Holder}"/>/' + firstCardId + '/detailview');
        // remember to add a .container to hold the scrollbars
        $('.property_cards, .mailbox_content').perfectScrollbar();
    });

    loadDetail(${applications[0].applicationId});
});

</script>