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
		<el-button type="primary" @click="addPost();" v-if="showAddBtn">新增文章</el-button>
    	<el-table :data="data">
	      	<el-table-column prop="title" label="标题">
	      	</el-table-column>
	      	<el-table-column prop="publishDate" label="发布日期" width="180">
	      	</el-table-column>
	      	<el-table-column prop="author" label="作者">
	      	</el-table-column>
	      	<el-table-column label="操作" width="200">
		      	<template slot-scope="scope">
		      		<el-button type="text" size="small" v-if="showAssignCategoryBtn"
		        		@click="assignCategorys(scope.row)">分配文章分类</el-button>
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
	  	<el-dialog title="文章分类列表" :visible.sync="categorysDialogVisible" :show-close="false" @opened="categorysDialogOpened">
	  		<el-tree ref="tree" node-key="id" :data="categorys" :props="defaultProps" check-strictly show-checkbox></el-tree>
	  		<span slot="footer" class="dialog-footer">
			    <el-button @click="categorysDialogVisible = false">取 消</el-button>
			    <el-button type="primary" @click="updateCategorys">更 新</el-button>
		  	</span>
		</el-dialog>
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
        			queryForm: {
        				title: '',
        				author: ''
        			},
        			currentData: {},
        			showAddBtn: false,
        			showEditBtn: false,
        			showDeleteBtn: false,
        			//showAssignCategoryBtn: false,
        			categorysDialogVisible: false,
        			categorys: [],
        			defaultProps: {
        				children: 'children',
          				label: 'name'
        			}
        		}
      		},
      		computed: {
			   	showAssignCategoryBtn () {
			     	return this.showAddBtn || this.showEditBtn;
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
      			},
      			assignCategorys(row) {
      				this.currentData = row;
      				this.categorysDialogVisible = true;
      			},
      			categorysDialogOpened() {
      				this.categorys = [];
      				axios.get('/cms/category/getTree').then((response) => {
	      				this.categorys = response.data;
	      				this.$refs['tree'].setCheckedKeys(this.currentData.categorys.map((m) => m.id));
	      			});
      			},
      			updateCategorys() {
      				let data = new Object();
      				data.postId = this.currentData.id;
      				data.categoryIds = this.$refs['tree'].getCheckedKeys();
      				axios.post('/cms/post/updateCategorys', Qs.stringify(data, { arrayFormat: 'repeat' })).then((response) => {
      					this.query();
	      				this.categorysDialogVisible = false;
	      			});
      			}
      		},
      		mounted() {
      			this.query();
      			this.$hasPerm('jbf:cms:post:save').then((result) => { this.showAddBtn =  result.data });
      			this.$hasPerm('jbf:cms:post:edit').then((result) => { this.showEditBtn =  result.data });
      			this.$hasPerm('jbf:cms:post:delete').then((result) => { this.showDeleteBtn =  result.data });
      		}
    	})
  	</script>
</html>