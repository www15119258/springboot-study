<!DOCTYPE html>
<html>
<head>
    <meta charset="utf‐8">
    <link rel="stylesheet" href="/css/index.css">
    <title>文章列表页面</title>
</head>
<body>
	<div id="app">
		<el-form :inline="true" :model="queryForm">
		  	<el-form-item label="标题">
		    	<el-input v-model="queryForm.title" placeholder="标题"></el-input>
		  	</el-form-item>
		  	<el-form-item label="作者">
		    	<el-input v-model="queryForm.author" placeholder="作者"></el-input>
		  	</el-form-item>
		</el-form>
		<el-button type="primary" @click="query();">查询</el-button>
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
	        		<el-button type="text" size="small" 
		        		@click="deleteRow(scope.row)">删除</el-button>
		      	</template>
		    </el-table-column>
	    </el-table>
	    <div class="block">
	    	<el-pagination
  				@size-change="handleSizeChange"
  				@current-change="handleCurrentChange"
  				:current-page="currentPage"
  				:page-sizes="[1, 2, 4]"
  				:page-size="pageSize"
  				layout="total, sizes, prev, pager, next, jumper"
  				:total="total">
	    	</el-pagination>
	  	</div>
  	</div>
</body>
  	<script src="/js/vue.js"></script>
  	<!-- import JavaScript -->
  	<script src="/js/index.js"></script>
  	<script src="/js/axios.min.js"></script>
  	<script src="/js/qs.js"></script>
  	<script>
    	new Vue({
      		el: '#app',
      		data: function() {
        		return { 
        			data: [],
        			currentPage: 1,
        			pageSize: 2,
        			total: 0,
        			queryForm: {
        				title: '',
        				author: ''
        			}
        		}
      		},
      		methods: {
      			addPost() {
      				window.location = '/cms/post/add';
      			},
      			edit(row) {
      				window.location = '/cms/post/edit/' + row.id;
      			},
      			deleteRow(row) {
      				this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
			          	confirmButtonText: '确定',
			          	cancelButtonText: '取消',
			          	type: 'warning'
			        }).then(() => {
			          	axios.delete('/cms/post/delete/' + row.id).then(() => {
			          		window.location.reload();
			          	});
			        }).catch(() => {
			          	this.$message({
			            	type: 'info',
			            	message: '已取消删除'
			          	});          
			        });
      			},
      			handleSizeChange(val) {
      				this.pageSize = val;
      				this.query();
      			},
      			handleCurrentChange(val) {
      				this.currentPage = val;
      				this.query();
      			},
      			query() {
      				axios.post('/cms/post/findPage/' + (this.currentPage - 1) + '/' + this.pageSize, Qs.stringify(this.queryForm)).then((response) => {
	      				this.data = response.data.content;
	      				this.total = response.data.totalElements;
	      			});
      			}
      		},
      		mounted() {
      			this.query();
      		}
    	})
  	</script>
</html>