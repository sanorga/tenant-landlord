<script type="text/javascript">
$('*').keypress(function(e)
{
    // if the key pressed is the enter keyif (e.keyCode == 13)
    {
        if (e.target.nodeName != "TEXTAREA"){
         return false;
        }
    }
});
</script>
