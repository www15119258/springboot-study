<div id="app">
	<div style="position: relative;">
		<h1>后台管理系统</h1>
		<a href="javascript:void(0);" @click="logout();" style="position: absolute;
			right: 0; top: 0; line-height: 45px; cursor: pointer;">注销</a>
	</div>
</div>
<script src="/js/vue.js"></script>
<!-- import JavaScript -->
<script src="/js/index.js"></script>
<script src="/js/axios.min.js"></script>
<script>
	new Vue({
  		el: '#app',
  		data: function() {
    		return { 
    		}
  		},
  		methods: {
  			logout() {
  				axios.get('/logout').then((response) => {
	  				window.top.location = "/login.html";
	  			});
  			}
  		}
	})
</script>
