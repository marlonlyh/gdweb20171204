$(document).ready(function() {			
			new Marquee(
			{	
			MSClassID : "JINGDONGBox",
			ContentID : "JINGDONGContentID",
			TabID	  : "JINGDONGNumID",
            Direction : 0,
            Step: 0.1,
            Width	: 709,
            Height : 223,
            Timer: 20,
            DelayTime:3000,
            WaitTime: 0,
            ScrollStep:280,
            SwitchType: 2,
            AutoStart : 1
			})
			$(".itab ul").idTabs("!mouseover");
});