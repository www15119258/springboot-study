<!DOCTYPE html>
<html>
<head>
    <meta charset="utf‐8">
    <link rel="stylesheet" href="/css/index.css">
    <link rel="stylesheet" href="/css/main.css">
    <title>后台管理</title>
</head>
<body>
	<div id="app">
		<el-container class="main-wrap">
	  		<el-header>
	  			<iframe src="/sys/header" frameborder="no" border="0" scrolling="no"></iframe>
	  		</el-header>
		  	<el-container>
		    	<el-aside width="200px">
		    		<iframe src="/sys/aside" frameborder="no" border="0"></iframe>
		    	</el-aside>
		    	<el-main>
		    		<iframe id="main_frame" src="/sys/dashboard" frameborder="no" border="0"></iframe>
		    	</el-main>
		  	</el-container>
		</el-container>
	</div>
</body>
  	<script src="/js/vue.js"></script>
  	<!-- import JavaScript -->
  	<script src="/js/index.js"></script>
  	<script>
    	new Vue({
      		el: '#app',
      		data: function() {
        		return { 
        		}
      		},
      		methods: {
      		},
      		mounted() {
      		}
    	})
  	</script>
  	<script>
  		function goPage(data) {
  			document.title = data.name;
  			document.getElementById('main_frame').src = data.url;
  		}
  	</script>
</html>