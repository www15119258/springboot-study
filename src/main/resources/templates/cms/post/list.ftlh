<!DOCTYPE html>
<html>
<head>
    <meta charset="utf‐8">
    <link rel="stylesheet" href="/css/index.css">
    <title>文章列表页面</title>
</head>
<body>
	<div id="app">
		<el-button type="primary" @click="addPost();">新增文章</el-button>
    	<el-table :data="data">
	      	<el-table-column prop="title" label="标题">
	      	</el-table-column>
	      	<el-table-column prop="publishDate" label="发布日期" width="180">
	      	</el-table-column>
	      	<el-table-column prop="author" label="作者">
	      	</el-table-column>
	      	<el-table-column label="操作" width="100">
		      	<template slot-scope="scope">
		        	<el-button type="text" size="small" 
		        		@click="edit(scope.row)">编辑</el-button>
		      	</template>
		    </el-table-column>
	    </el-table>
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
        			data: []
        		}
      		},
      		methods: {
      			addPost() {
      				window.location = '/cms/post/add';
      			},
      			edit(row) {
      				window.location = '/cms/post/edit/' + row.id;
      			}
      		},
      		mounted() {
      			axios.get('/cms/post/findAll').then((response) => {
      				this.data = response.data;
      			});
      		}
    	})
  	</script>
</html>