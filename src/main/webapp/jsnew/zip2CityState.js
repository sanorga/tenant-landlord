function getStateCityScript2(e,t,l,a){var n=document.getElementById(e).value;""==n?alert("Select country"):"US"==n&&getStateCityScript(t,l,a)}function clearStateCity(e,t,l){document.getElementById(e).value="",document.getElementById(t).value="",document.getElementById(l).value=""}function getStateCityScript(e,t,l){var a=document.getElementById(t),n=document.getElementById(l),u=document.getElementById(e).value;if(u&&u.length>0){$.ajax({url:"lookup/"+u+"/cityStateByZip.json",dataType:"json",timeout:5e3}).done(function(e){return null==e?(a.value="",n.value="",void alert("Not a valid Zipcode")):(a.value=e.city,void(n.value=e.state))}).fail(function(){a.value="",n.value="",alert("City lookup failed.")})}}