	
$(document).ready(function () {
    $('#sumByMonth').on('click', function () {
        $.ajax({
            method: 'GET',
            url: 'sumByMonth',
            dataType: 'json',
            cache: false,
        }).done(function (sum) {
			console.log(sum);
        	drawChart(sum);
        });
    });

	$('#sumByMonthBetween').on('click', function () {
        var monthFrom = $('#monthFrom').val();
		var monthTo = $('#monthTo').val();
        $.ajax({
            method: 'GET',
            url: 'sumByMonthBetween',
            dataType: 'json',
			data:{
				monthFrom : monthFrom,
				monthTo : monthTo
			},
            cache: false,
        }).done(function (sum) {
			console.log(sum);
        	drawChart2(sum);
        });
    });
});


function drawChart(sum){
	var ctx = document.getElementById('myChart').getContext('2d');
	var myChart = new Chart(ctx, {
	    type: 'bar',
	    data: {
	        labels: ['1', '2', '3', '4', '5', '6','7','8','9','10','11','12'],
	        datasets: [{
	            label: 'Thống kê sản phẩm theo tháng',
	            data: sum,
	            backgroundColor: 
	                'rgba(255, 99, 132, 0.2)',
	            
	            borderColor: 
	                'rgba(255, 99, 132, 1)',
	            borderWidth: 1
	        }]
	    },
	    options: {
	        scales: {
	            y: {
	                beginAtZero: true
	            }
	        }
	    }
	});
}

function drawChart2(sum){
	var ctx = document.getElementById('myChart').getContext('2d');
	var myChart = new Chart(ctx, {
	    type: 'bar',
	    data: {
	        labels: ['5', '6','7','8','9','10','11','12'],
	        datasets: [{
	            label: 'Thống kê sản phẩm theo tháng',
	            data: sum,
	            backgroundColor: 
	                'rgba(255, 99, 132, 0.2)',
	            
	            borderColor: 
	                'rgba(255, 99, 132, 1)',
	            borderWidth: 1
	        }]
	    },
	    options: {
	        scales: {
	            y: {
	                beginAtZero: true
	            }
	        }
	    }
	});
}