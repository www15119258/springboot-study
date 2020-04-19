<!DOCTYPE html>
<html>
<head>
    <meta charset="utf‐8">
    <link rel="stylesheet" href="/css/index.css">
    <title>权限列表页面</title>
</head>
<body>
	<div id="app">
		<el-button type="primary" @click="query();">查询</el-button>
		<el-button type="primary" @click="add();" v-if="showAddBtn">新增权限</el-button>
    	<el-table :data="data">
	      	<el-table-column prop="name" label="名称">
	      	</el-table-column>
	      	<el-table-column prop="perm" label="权限字符">
	      	</el-table-column>
	      	<el-table-column prop="description" label="描述">
	      	</el-table-column>
	      	<el-table-column label="操作" width="100">
		      	<template slot-scope="scope">
		        	<el-button type="text" size="small" v-if="showEditBtn"
		        		@click="edit(scope.row)">编辑</el-button>
	        		<el-button type="text" size="small" v-if="showDeleteBtn"
		        		@click="deleteRow(scope.row)">删除</el-button>
		      	</template>
		    </el-table-column>
	    </el-table>
	    <div class="block">
	    	<el-pagination
  				@size-change="handleSizeChange"
  				@current-change="handleCurrentChange"
  				:current-page="currentPage"
  				:page-sizes="[10, 20, 50]"
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
  	<script src="/js/api.js"></script>
  	<script>
    	new Vue({
      		el: '#app',
      		data: function() {
        		return { 
        			data: [],
        			currentPage: 1,
        			pageSize: 10,
        			total: 0,
        			showAddBtn: false,
        			showEditBtn: false,
        			showDeleteBtn: false
        		}
      		},
      		methods: {
      			add() {
      				window.location = '/security/perm/add';
      			},
      			edit(row) {
      				window.location = '/security/perm/edit/' + row.id;
      			},
      			deleteRow(row) {
      				this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
			          	confirmButtonText: '确定',
			          	cancelButtonText: '取消',
			          	type: 'warning'
			        }).then(() => {
			          	axios.delete('/security/perm/delete/' + row.id).then(() => {
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
      				axios.get('/security/perm/findPage/' + (this.currentPage - 1) + '/' + this.pageSize).then((response) => {
	      				this.data = response.data.content;
	      				this.total = response.data.totalElements;
	      			});
      			}
      		},
      		mounted() {
      			this.query();
      			this.$hasPerm('jbf:security:perm:save').then((result) => { this.showAddBtn =  result.data });
      			this.$hasPerm('jbf:security:perm:edit').then((result) => { this.showEditBtn =  result.data });
      			this.$hasPerm('jbf:security:perm:delete').then((result) => { this.showDeleteBtn =  result.data });
      		}
    	})
  	</script>
</html>