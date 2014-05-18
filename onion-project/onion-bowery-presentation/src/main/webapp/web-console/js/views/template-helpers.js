window.fmt = {
	    iso_date: function(milliseconds) {
	    	var d = new Date(milliseconds);
	        var curr_date = d.getDate();
	        var curr_month = d.getMonth() + 1; //Months are zero based
	        var curr_year = d.getFullYear();
	        var validDate = curr_year+ "-" + curr_month + "-" + curr_date;
	        return validDate;
	    },
	    now: function() {
	    	var d = new Date();
	    	var curr_date = d.getDate();
	    	var curr_month = d.getMonth() + 1; //Months are zero based
	    	var curr_year = d.getFullYear();
	    	var validDate = curr_year+ "-" + curr_month + "-" + curr_date;
	    	return validDate;
	    }
};