<!DOCTYPE html>
<html>
<head>
    <meta charset="utf‐8">
    <link rel="stylesheet" href="/css/index.css">
    <title>后台管理</title>
</head>
<body>
	<div id="app">
		<el-tree :data="data" :props="defaultProps" @node-click="handleNodeClick"></el-tree>
	</div>
</body>
  	<script src="/js/vue.js"></script>
  	<!-- import JavaScript -->
  	<script src="/js/index.js"></script>
  	<script src="/js/axios.min.js"></script>
  	<script>
    	new Vue({
      		el: '#app',
      		data: function() {
        		return { 
        			data: [],
        			defaultProps: {
        				children: 'children',
          				label: 'name'
        			}
        		}
      		},
      		methods: {
      			handleNodeClick(data) {
      				if (data.url !== null && data.url !== undefined && data.url !== '') {
      					parent.goPage(data);
      				}
      			}
      		},
      		mounted() {
      			axios.get('/sys/getMenus').then((response) => {
      				this.data = response.data;
      			});
      		}
    	})
  	</script>
</html>