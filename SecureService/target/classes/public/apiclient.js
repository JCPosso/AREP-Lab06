var apiclient = (function () {
    return{
     getFormData: function ($form){
                var unindexed_array = $form.serializeArray();
                var indexed_array = {};

                $.map(unindexed_array, function(n, i){
                    indexed_array[n['name']] = n['value'];
                });

                return indexed_array;
            },
        login: function(){
           var $form = $('#loginForm');
           var datos = this.getFormData($form);
           console.log(datos);
			$.ajax({
		        url: '/login',
		        type: 'POST',
		        contentType: 'application/json',
		        data: JSON.stringify(datos),
		        success: function(data){
                    if(data == "Success"){
                        alert("You are now logged in")
                        document.location.href="home.html";
                    }
		        },
		        error: function(x){
		        	console.error("encountered a problem: ", x);
		        }
		    });
			return false;

        },
        logout: function(){
			$.ajax({
		        url: '/logout',
		        type: 'POST',
		        success: function(data){
		        	alert("You are now logged out")
		        	document.location.href="/";
		        },
		        error: function(x){
		        	console.error("encountered a problem: ", x);
		        }
		    });
			return false;

		}
    };
})();